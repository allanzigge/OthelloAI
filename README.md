An AI player for the classic board game Othello, developed as part of an AI course project. The focus was on adversarial search algorithms, problem modeling, and evaluation of decision quality.

Features

Minimax algorithm with alpha-beta pruning for efficient adversarial search

Custom evaluation function that values strategic board positions beyond disc count

Configurable cut-off depth to balance performance and move quality

Consistently outperforms the baseline DumAI

Run the project

`java Othello <player1> <player2> [boardSize]`

Boardsize is deafault 8x8

Player Options
- Human
- DumAI (easy)
- OthelloAI

`java Othello human DumAI`
`java Othello human OthelloAI 6`