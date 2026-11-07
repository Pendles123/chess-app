package com.chessgame;

import com.chessboard.ChessBoard;
import com.chesspieces.*;

import java.util.Arrays;
import java.util.Optional;

public class GameStartUp {

    public void clearBoard(Game game) {
        ChessBoard newBoard = game.getBoard();
        ChessPieces[][] resetBoard = newBoard.getBoard();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                resetBoard[row][col] = null;
            }
        }
    }


    public void go(Game game) {

        ChessBoard newBoard = game.getBoard();
        // Setting up pieces for new board
        for (int i = 0; i < 8; i++) {
            // Pawns (White/Black)
            if (i == 1 || i == 6) {
                if (i == 1) {for (int j = 0; j < 8; j++) {newBoard.setBoard(i, j, new Pawn(i, j, PlayerColour.WHITE, -1, PieceType.PAWN));}}
                if (i == 6) {for (int j = 0; j < 8; j++) {newBoard.setBoard(i, j, new Pawn(i, j, PlayerColour.BLACK, -1, PieceType.PAWN));}}
            }

            if (i == 0 || i == 7) {
                // White Minor/Major Pieces
                if (i == 0) {
                    for (int j = 0; j < 8; j++) {
                        if (j == 0 || j == 7) {newBoard.setBoard(i, j, new Rook(i, j, PlayerColour.WHITE, PieceType.ROOK));} // Rooks
                        if (j == 1 || j == 6) {newBoard.setBoard(i, j, new Knight(i, j, PlayerColour.WHITE, PieceType.KNIGHT));} // Knights
                        if (j == 2 || j == 5) {newBoard.setBoard(i, j, new Bishop(i, j, PlayerColour.WHITE, PieceType.BISHOP));} // Bishops
                        if (j == 3) {newBoard.setBoard(i, j, new Queen(i, j, PlayerColour.WHITE, PieceType.QUEEN));} // com.chesspieces.Queen
                        if (j == 4) {newBoard.setBoard(i, j, new King(i, j, PlayerColour.WHITE, PieceType.KING));} // com.chesspieces.King
                    }
                }
                // Black Minor/Major Pieces
                if (i == 7) {
                        for (int j = 0; j < 8; j++) {
                            if (j == 0 || j == 7) {newBoard.setBoard(i, j, new Rook(i, j, PlayerColour.BLACK, PieceType.ROOK));} // Rooks
                            if (j == 1 || j == 6) {newBoard.setBoard(i, j, new Knight(i, j, PlayerColour.BLACK, PieceType.KNIGHT));} // Knights
                            if (j == 2 || j == 5) {newBoard.setBoard(i, j, new Bishop(i, j, PlayerColour.BLACK, PieceType.BISHOP));} // Bishops
                            if (j == 3) {newBoard.setBoard(i, j, new Queen(i, j, PlayerColour.BLACK, PieceType.QUEEN));} // com.chesspieces.Queen
                            if (j == 4) {newBoard.setBoard(i, j, new King(i, j, PlayerColour.BLACK, PieceType.KING));} // com.chesspieces.King
                    }
                }
            }
        }
    }
}
