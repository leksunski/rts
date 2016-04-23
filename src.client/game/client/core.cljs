(ns ^:figwheel-always game.client.core
  (:require
    [cljs.core.async :refer [<! put! chan]]
    [com.stuartsierra.component :as component]
    [jayq.core :as jayq :refer [$]]
    [promesa.core :as p]
    [cats.core :as m]
    [game.client.common :as common :refer [new-jsobj]]
    [game.client.config :as config]
    [game.client.controls :as controls]
    [game.client.ground :as ground]
    [game.client.page-game :as page-game]
    [game.client.page-lobby :as page-lobby]
    [game.client.page-game-lobby :as page-game-lobby]
    [game.client.page-not-found :as page-not-found]
    [game.client.renderer :as renderer]
    [game.client.routing :as routing]
    [game.client.scene :as scene]
    [game.client.socket :as socket]
    [game.shared.state :as state
     :refer [system s-add-component s-readd-component with-simple-cause]]
    )
  )

(enable-console-print!)

(println "Reloaded client core")

(s-readd-component system :config config/config)

(s-add-component system :socket (socket/new-init-socket))
(s-add-component system :simplex (new-jsobj #(new js/SimplexNoise)))
;(s-add-component system :ground (ground/new-init-ground))

(s-add-component system :routing (routing/new-router))
(s-add-component system :page-game (page-game/new-game))
(s-add-component system :page-lobby (page-lobby/new-lobby))
(s-add-component system :page-game-lobby (page-game-lobby/new-game-lobby))
(s-add-component system :page-not-found (page-not-found/new-page-not-found))

(def ran (atom false))

(defn debug
  []
  (m/mlet
    [mapdata (socket/rpc (:socket @system) "get-map" :data 2)]
    (println "get-map" mapdata))
  )

(defn main
  []
  (reset! ran true)
  (println "main")
  (with-simple-cause #(swap! system component/stop-system))
  (with-simple-cause #(swap! system component/start-system)))
  
(if @ran (main) (js/$ (main)))