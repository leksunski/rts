(ns ^:figwheel-always game.client.page-sente-test
  (:require
    [cats.core :as m]
    [cljs.pprint :as pprint]
    [clojure.string :as string :refer [join]]
    [com.stuartsierra.component :as component]
    [game.client.common :as common :refer [list-item header]]
    [game.client.routing :as routing]
    [game.client.socket :as socket]
    [jayq.core :as jayq :refer [$]]
    [promesa.core :as p]
    [rum.core :as rum]
    [sablono.core :as sablono :refer-macros [html]]
    [taoensso.sente  :as sente  :refer (cb-success?)]
    )
  (:require-macros [game.shared.macros :as macros :refer [defcom]])
  )

(enable-console-print!)

(def page-id (routing/get-page-selector :sente-test))

(rum/defc
  sente-view
  [component]
  (let
    [h (header "Sente Test")]
    (html [:div { :class "container" } h])))

(defn send-loop
  [component]
  (if
    @(:send-loop-enabled component)
    (do
      (if
        (:open? @(:state (:sente-setup component)))
        (do
         (println "send-fn" )
         ((:send-fn (:sente-setup component))
            [:sente-test/ping {:had-a-callback? "yes"}]
            5000
            (fn [cb-reply] (println "cb-reply" cb-reply)))))
      (js/setTimeout (partial send-loop component) 1000))))

(defcom 
  new-sente-test
  [sente-setup]
  [send-loop-enabled]
  (fn [component]
    (let
      [component (assoc component :send-loop-enabled (atom true))]
      (send-loop component)
      (rum/mount (sente-view component) (aget ($ page-id) 0))
      (routing/init-page ($ page-id))
      component))
  (fn [component]
    (reset! send-loop-enabled false)
    component))