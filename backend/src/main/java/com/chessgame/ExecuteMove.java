package com.chessgame;


import com.chessboard.ChessBoard;
import com.chesspieces.*;
import com.validations.Move;
import com.validations.PromotionPiece;
import com.validations.Validation;

import java.util.Optional;

public class ExecuteMove {
    private Game game;
    private PlayerColour player;

    public ExecuteMove(Game game) {
    this.game = game;
    this.player = game.getCurrentPlayer();
    }

    public void castlingExecution(Move theMove, ChessBoard board) {
        if(theMove.getTo().getFile() > theMove.getFrom().getFile()) {
            Optional<ChessPieces> castlingRook = board.getPiece(theMove.getFrom().getRank(), 7);
            if (castlingRook.isPresent()) {
                ChessPieces theCastlingRook = castlingRook.get();
                ChessPieces clonedRook = theCastlingRook.copy();
                clonedRook.move(theMove.getFrom().getRank(), 5); // the parameters represent the NEW coordinates of the piece (cloned piece) move(int newY, int newX)
                board.setBoard(theMove.getFrom().getRank(), 7, null);
                board.setBoard(theMove.getFrom().getRank(), 5, clonedRook);
                System.out.println("PieceType is " + clonedRook.getPieceType());
            }
        } else {
            Optional<ChessPieces> castlingRook = board.getPiece(theMove.getFrom().getFile(), 0);
            if (castlingRook.isPresent()) {
                ChessPieces theCastlingRook = castlingRook.get();
                ChessPieces clonedRook = theCastlingRook.copy();
                clonedRook.move(theMove.getFrom().getRank(), 3);
                board.setBoard(theMove.getFrom().getRank(), 0, null);
                board.setBoard(theMove.getFrom().getRank(), 3, clonedRook);
            }
        }
    }

    public void movementExecution(ChessPieces thePiece, Move theMove, ChessBoard board) {
        ChessPieces clonedPiece = thePiece.copy();
        clonedPiece.move(theMove.getTo().getRank(), theMove.getTo().getFile());
        board.setBoard(theMove.getFrom().getRank(), theMove.getFrom().getFile(), null);
        board.setBoard(theMove.getTo().getRank(), theMove.getTo().getFile(), clonedPiece);
        // Pawn's (for current player) enPassantMoveCounter needs to match moveCounter if it moved 2 squares
        if(thePiece instanceof Pawn && Math.abs(theMove.getTo().getRank() - theMove.getFrom().getRank()) == 2) {
            if(clonedPiece instanceof Pawn) {
                ((Pawn) clonedPiece).setEnPassantMoveCounter(game.getMoveCounter());
            }
        }
    }

    public void pawnPromotion(Move theMove, ChessBoard board) {
        PromotionPiece thePromotion = theMove.getPromotion();
        switch (thePromotion) {
            case PromotionPiece.QUEEN:
                board.setBoard(theMove.getTo().getRank(), theMove.getTo().getFile(), new Queen(theMove.getTo().getRank(), theMove.getTo().getFile(), player, PieceType.QUEEN));
                break;
            case PromotionPiece.ROOK:
                board.setBoard(theMove.getTo().getRank(), theMove.getTo().getFile(), new Rook(theMove.getTo().getRank(), theMove.getTo().getFile(), player, PieceType.ROOK));
                break;
            case PromotionPiece.BISHOP:
                board.setBoard(theMove.getTo().getRank(), theMove.getTo().getFile(), new Bishop(theMove.getTo().getRank(), theMove.getTo().getFile(), player, PieceType.BISHOP));
                break;
            case PromotionPiece.KNIGHT:
                board.setBoard(theMove.getTo().getRank(), theMove.getTo().getFile(), new Knight(theMove.getTo().getRank(), theMove.getTo().getFile(), player, PieceType.KNIGHT));
                break;
        }
    }

    public ChessBoard updateBoard(Game game, Move theMove, ChessBoard board) {

        Validation validation = new Validation(game);

        // Optional<ChessPieces> to process board movement
        Optional<ChessPieces> piece = board.getPiece(theMove.getFrom().getRank(), theMove.getFrom().getFile()); //arrayMove[1], arrayMove[0]

        // Castling Processing Method
        if(piece.isPresent() && piece.get() instanceof King && Math.abs(theMove.getFrom().getFile() - theMove.getTo().getFile()) == 2) { //arrayMove[0] - arrayMove[2]
            castlingExecution(theMove, board);
        }
        //Move Piece execution
        if (piece.isPresent()) {
            ChessPieces thePiece = piece.get();
            movementExecution(thePiece, theMove, board);
        }
        // En Passant -> this notifies the player that they took a piece via En Passant (because destination coordinate wouldn't have an opponent piece on it)
        if (piece.isPresent() && piece.get() instanceof Pawn && (validation.enPassant(theMove, game))) {
            board.setBoard(theMove.getFrom().getRank(), theMove.getTo().getFile(), null);
        }
        // Pawn Promotion
        if (piece.isPresent() && piece.get() instanceof Pawn && (theMove.getTo().getRank() == 0 || theMove.getTo().getRank() == 7)) {
            pawnPromotion(theMove, board);
        }

        game.advanceTurn();

        if (validation.isKingInCheck(player)) {
            game.setStatus(GameStatus.CHECK);
        }

        if (validation.checkmate(player)) {
            game.setStatus(GameStatus.CHECKMATE);
        }

        if (validation.stalemate(player)) {
            game.setStatus(GameStatus.STALEMATE);
        }

        if (validation.stalemate(player)) { // You need a method for the "50 move rule", "Threefold repetition", "Insufficient material" and "Mutual agreement"
            game.setStatus(GameStatus.DRAW);
        }
        // For the Threefold Repetition rule, consider an array (maybe a HashMap) that stores type ChessBoard,
        // and after every turn check to see if the same board appears...maybe within the last 5-10 turns? (the frequency will need to be confirmed)

    return board;
    }
}
