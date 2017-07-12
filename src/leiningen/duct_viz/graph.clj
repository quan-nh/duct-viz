(ns leiningen.duct-viz.graph
  (:require [rhizome.viz :as viz]))

(defn save [graph vertical? filename]
  (viz/save-graph (keys graph) graph
                  :cluster->descriptor (fn [n] {:label n})
                  :node->cluster namespace
                  :node->descriptor (fn [n] {:label (name n)})
                  :vertical? vertical?
                  :filename filename))
