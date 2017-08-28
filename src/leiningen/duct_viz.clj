(ns leiningen.duct-viz
  (:require [leiningen.core.eval :refer [eval-in-project]]
            [leiningen.core.main :as main]
            [leiningen.core.project :as project]
            [clojure.tools.cli :as cli]
            [clojure.string :as str]))

(def cli-options
  [["-c" "--config-file CONFIG_FILE" "Duct config file."
    :default "config.edn"]
   ["-d" "--dev" "Use :dev profile."]
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
  (when-let [{:keys [config-file dev output-file vertical]} (parse-cli-options "duct-viz" cli-options args)]
    (eval-in-project
      (cond-> project
              true (project/merge-profiles [{:dependencies [['duct-viz "0.1.2-SNAPSHOT"]]}])
              dev (project/merge-profiles [:dev]))
      `(-> (clojure.java.io/resource ~config-file)
           (duct.core/read-config)
           (duct.core/prep)
           (integrant.core/dependency-graph)
           (leiningen.duct-viz.graph/save (some? ~vertical) ~output-file))
      '(do (require 'duct.core 'integrant.core 'leiningen.duct-viz.graph)
           (duct.core/load-hierarchy)))
    (main/info "Wrote dependency graph to:" output-file)))
