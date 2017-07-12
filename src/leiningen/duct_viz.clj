(ns leiningen.duct-viz
  (:require [leiningen.core.eval :refer [eval-in-project]]
            [leiningen.core.main :as main]
            [leiningen.core.project :as project]
            [clojure.tools.cli :as cli]
            [clojure.string :as str]))

(def cli-options
  [["-d" "--duct-file DUCT_FILE" "Duct config file."
    :default "config.edn"]
   ["-o" "--output-file OUTPUT_FILE" "Output file path, png image."
    :default "system.png"]
   ["-v" "--vertical" "Use vertical layout."]
   ["-m" "--man" "Help."]])

(defn- usage
  [command summary errors]
  (->> [(str "Usage: lein " command " [options]")
        ""
        "Options:"
        summary]
       (str/join \newline)
       println)

  (when errors
    (println "\nErrors:")
    (doseq [e errors] (println " " e))))

(defn- parse-cli-options
  "Parses the CLI options; handles --man and errors (returning nil) or just
  returns the parsed options."
  [command cli-options args]
  (let [{:keys [options errors summary]} (cli/parse-opts args cli-options)]
    (if (or (:man options) errors)
      (usage command summary errors)
      options)))

(defn duct-viz
  "Visualizes Duct system using Graphviz."
  [project & args]
  (when-let [{:keys [duct-file output-file vertical]} (parse-cli-options "duct-viz" cli-options args)]
    (eval-in-project (project/merge-profiles project [{:dependencies [['duct-viz "0.1.0-SNAPSHOT"]]}])
                     `(-> (clojure.java.io/resource ~duct-file)
                          (duct.core/read-config)
                          (duct.core/prep)
                          (integrant.core/dependency-graph)
                          :dependencies
                          (leiningen.duct-viz.graph/save (some? ~vertical) ~output-file))
                     '(require 'duct.core 'integrant.core 'leiningen.duct-viz.graph))
    (main/info "Wrote dependency graph to:" output-file)))
