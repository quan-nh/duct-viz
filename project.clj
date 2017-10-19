(defproject duct-viz "0.1.3"
  :description "Visualizes Duct system using Graphviz."
  :url "https://github.com/tentamen/duct-viz"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/tools.cli "0.3.5"]
                 [rhizome "0.2.7"]]
  :eval-in-leiningen true
  :deploy-repositories [["clojars" {:sign-releases false}]])
