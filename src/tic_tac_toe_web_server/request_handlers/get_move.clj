(ns tic-tac-toe-web-server.request-handlers.get-move
  (:use [clojure.contrib.json :only [read-json]]
        [tic-tac-toe.constants :only [P1 P2 NOONE]]
        [tic-tac-toe.computer :only [computer-types]]
        [tic-tac-toe.game-state :only [game-state]])
  (:import [HttpServer ResponseHeader Headers.ConnectionClose Headers.DateHeader Responses.OK Exceptions.ResponseException]
           [java.io ByteArrayInputStream]))

(defn parse-board [string-board]
  (read-json string-board)
  )

(defn convert-player [value]
  (cond
    (nil? value) NOONE
    :else value
    )
  )

(defn convert-board [board]
  (let [converted-players (vec (map convert-player board))]
    (vector (subvec converted-players 0 3) (subvec converted-players 3 6) (subvec converted-players 6 9))
    )
  )

(defn convert-move [move]
  (+ (* (first move) 3) (second move))
  )

(defn get-computer-move [player board]
  ((:fn (computer-types 3)) #(game-state % false) player board)
  )

(defn- response-headers [move]
  (list
    (ResponseHeader. "Content-Length" (str (.length move)))
    (ResponseHeader. "Content-Type" "application/json") (ConnectionClose.) (DateHeader.))
  )

(defn get-callback [callback move]
  (str callback "(" move ")")
  )

(defn- getResponse [request]
  (let [player (.get (.parameters request) "player")
        board (.get (.parameters request) "board")
        callback (.get (.parameters request) "callback")
        response (get-callback callback (str (convert-move (get-computer-move player (convert-board (parse-board board))))))]
    (OK. (response-headers response) (ByteArrayInputStream. (.getBytes response)))
    )
  )

(deftype get-move []
  HttpServer.RequestHandler
  (canRespond [this request] (and (= "GET" (.action request)) (= "/get-move" (.url request))))
  (getResponse [this request] (getResponse request)))

(defn new-get-move []
  (get-move.))

