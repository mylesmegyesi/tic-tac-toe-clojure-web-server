(ns tic-tac-toe-web-server.request-handlers.get-move-spec
  (:use [speclj.core]
        [tic-tac-toe-web-server.request-handlers.get-move :only [new-get-move parse-board convert-board convert-player convert-move get-computer-move]]
        [tic-tac-toe.constants :only [P1 P2 NOONE]])
  (:import [HttpServer Request Exceptions.ResponseException Responses.OK]
           [java.util.HashMap]))

(describe "get-move"

  (with get-move (new-get-move))

  (context "can respond"
    (it "responds to GET at /get-move"
      (should (.canRespond @get-move (Request. "GET" "/get-move" "" {} {})))
      )
    (it "doesn't respond to POST at /get-move"
      (should-not (.canRespond @get-move (Request. "POST" "/get-move" "" {} {})))
      )
    (it "doesn't respond to GET at /somethingelse"
      (should-not (.canRespond @get-move (Request. "GET" "/somethingelse" "" {} {})))
      )
    )

  (context "parse board"
    (it "parses a json string"
      (should= ["O" nil nil nil nil nil nil nil nil] (parse-board "[\"O\",null,null,null,null,null,null,null,null]"))
      (should= ["O" nil nil nil nil nil "X" nil nil] (parse-board "[\"O\",null,null,null,null,null,\"X\",null,null]"))
      )
    )

  (context "convert player"
    (it "converts an X"
      (should= P1 (convert-player "X"))
      )
    (it "converts an O"
      (should= P2 (convert-player "O"))
      )
    (it "converts an empty space"
      (should= NOONE (convert-player nil))
      )
    )

  (context "convert board"
    (it "converts a 1D array to a 2D array"
      (should= [[P2 NOONE NOONE] [NOONE NOONE NOONE] [NOONE NOONE NOONE]] (convert-board ["O" nil nil nil nil nil nil nil nil]))
      (should= [[P2 NOONE NOONE] [NOONE NOONE NOONE] [P1 NOONE NOONE]] (convert-board ["O" nil nil nil nil nil "X" nil nil]))
      )
    )

  (context "convert move"
    (it "converts a 2D index to an integer"
      (should= 2 (convert-move [0 2]))
      (should= 5 (convert-move [1 2]))
      (should= 8 (convert-move [2 2]))
      )
    )

  (context "get computer move"
    (it "gets a move"
      (should-not= nil (get-computer-move P2 [[P2 NOONE NOONE] [NOONE NOONE NOONE] [P1 NOONE NOONE]]))
      )
    )

  (context "get response"
    (it "returns ok"
      (should= OK (type (.getResponse @get-move (Request. "GET" "/get-move" "" {} {"board" "[\"O\",null,null,null,null,null,null,null,null]" "player" "O"}))))
      )
    )

  )
