package com.chessgame;

import com.chessboard.ChessBoard;

public class Game {

    private final long id;
    private ChessBoard board;
    private PlayerColour currentPlayer;
    private int moveCounter;
    private GameStatus status;

    public Game(long id) {
        this.id = id;
        this.currentPlayer = PlayerColour.WHITE;
        this.moveCounter = 0;
        this.status = GameStatus.NORMAL;
    }

    public void advanceTurn() {
        currentPlayer =
                (currentPlayer == PlayerColour.WHITE)
                        ? PlayerColour.BLACK
                        : PlayerColour.WHITE;
        moveCounter++;
    }

    public long getId() {
        return id;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public PlayerColour getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    public void setMoveCounter(int moveCounter) {this.moveCounter = moveCounter;}
}
