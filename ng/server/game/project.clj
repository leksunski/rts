(defproject game "0.1.0-SNAPSHOT"
  :description "A real time strategy game"
  :url "http://emh.lart.no/rts-prod"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122"]
                 [sablono "0.3.6"]
                 [hiccups "0.3.0"]
                 [clojurewerkz/neocons "3.1.0"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [sablono "0.3.6"]
                 [org.omcljs/om "0.9.0"]
                 [jayq/jayq "2.5.4"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-figwheel "0.4.0"]]

  :source-paths ["src"]

  :clean-targets ["out.dev"
                  "out.prod"
                  ]

  :cljsbuild {
    :builds [{:id "dev"
              :source-paths ["src" "src.dev"]
              :compiler {
                :output-to "out.dev/game.js"
                :output-dir "out.dev"
                :target :nodejs
                :optimizations :none
                :source-map true}}
             {:id "prod"
              :source-paths ["src"]
              :compiler {
                :output-to "out.prod/game.js"
                :output-dir "out.prod"
                :target :nodejs
                ; compiling with optimizations takes too much memory
                ;:optimizations :simple}}]}
                :optimizations :none}}]}
  :figwheel {
             :server-port 3450
             }
            )
