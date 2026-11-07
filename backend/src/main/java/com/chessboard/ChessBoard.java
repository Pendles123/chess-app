package com.chessboard;

import com.chesspieces.ChessPieces;
import java.util.Optional;

public class ChessBoard {

    private ChessPieces[][] squares;
    private final int boardWidth = 8;
    private final int boardHeight = 8;
    public ChessBoard() {
        this.squares = new ChessPieces[boardHeight][boardWidth];
    }

    public ChessPieces[][] getBoard() {
        return squares;
    }

    public void setBoard(int i, int j, ChessPieces piece) {
        squares[i][j] = piece;
    }

    public Optional<ChessPieces> getPiece(int i, int j) {
        return Optional.ofNullable(squares[i][j]);
    }

    public ChessBoard deepCopy() {
        ChessBoard copy = new ChessBoard();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                ChessPieces piece = this.getPiece(rank, file).orElse(null);
                if (piece != null) {
                    copy.setBoard(rank, file, piece.copy()); // use each piece’s copy() method
                }
            }
        }
        return copy;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getBoardWidth() {
        return boardWidth;
    }
}
