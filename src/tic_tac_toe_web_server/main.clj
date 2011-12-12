(ns tic-tac-toe-web-server.main
  (:import
    (HttpServer HttpServer)
    (java.io IOException))
  (:use [tic-tac-toe-web-server.request-handlers.get-move :only [new-get-move]])
  (:gen-class))

(defn request-handlers []
  (list (new-get-move)))

(defn start-server [port]
  (let [server (HttpServer. port (request-handlers))]
    (try
      (.start server)
      (catch IOException e))))

(defn parse-port [port]
  (Integer/parseInt port))

(defn -main [& args]
    (start-server (parse-port (first args))))
