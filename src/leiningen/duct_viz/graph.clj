(ns leiningen.duct-viz.graph
  (:require [rhizome.viz :as viz]))

(defn save [{:keys [dependencies dependents]} vertical? filename]
  (viz/save-graph (set (concat (keys dependencies) (keys dependents)))
                  dependencies
                  :cluster->descriptor (fn [n] {:label n})
                  :node->cluster namespace
                  :node->descriptor (fn [n] {:label (name n)})
                  :vertical? vertical?
                  :filename filename))
