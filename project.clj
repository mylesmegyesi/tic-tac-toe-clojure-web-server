(defproject org.clojars.mylesmegyesi/tic-tac-toe-web-server "0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.clojars.mylesmegyesi/tic-tac-toe "0.1-SNAPSHOT"]
                 [org.clojars.mylesmegyesi/http-server "0.7-SNAPSHOT"]]
  :dev-dependencies [[speclj "1.2.0"]]
  :test-path "spec/"
  :main tic-tac-toe-web-server.main)