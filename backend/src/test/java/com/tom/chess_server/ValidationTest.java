package com.tom.chess_server;

import com.chessboard.ChessBoard;
import com.chessgame.ExecuteMove;
import com.chessgame.Game;
import com.chessgame.PlayerColour;
import com.chesspieces.*;
import com.validations.Move;
import com.validations.Position;
import com.validations.Validation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {
/*
    @Test
    void legalMovesReturnsAllFourteenSquaresForAnUnblockedRook() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        Position rookPosition = new Position(3, 3);

        board.setBoard(3, 3,
                new Rook(3, 3, PlayerColour.WHITE, PieceType.ROOK));

        // Include both kings so validation methods that inspect kings
        // have a valid board position to work with.
        board.setBoard(0, 0,
                new King(0, 0, PlayerColour.WHITE, PieceType.KING));
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        ArrayList<Position> moves = validation.legalMoves(rookPosition);

        // Assert: verify the count and a few representative directions.
        assertEquals(14, moves.size());
        assertTrue(moves.contains(new Position(0, 3))); // upward
        assertTrue(moves.contains(new Position(7, 3))); // downward
        assertTrue(moves.contains(new Position(3, 0))); // left
        assertTrue(moves.contains(new Position(3, 7))); // right
    }

    @Test
    void legalMovesReturnsZeroSquaresForBlockedRook() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        Position rookPosition = new Position(3, 3);

        board.setBoard(3, 3,
                new Rook(3, 3, PlayerColour.WHITE, PieceType.ROOK));

        // Include both kings so validation methods that inspect kings
        // have a valid board position to work with.
        board.setBoard(0, 0,
                new King(0, 0, PlayerColour.WHITE, PieceType.KING));
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(4, 3, new Knight(4, 3, PlayerColour.WHITE, PieceType.KNIGHT));
        board.setBoard(3, 4, new Knight(3, 4, PlayerColour.WHITE, PieceType.KNIGHT));
        board.setBoard(2, 3, new Knight(2, 3, PlayerColour.WHITE, PieceType.KNIGHT));
        board.setBoard(3, 2, new Knight(3, 2, PlayerColour.WHITE, PieceType.KNIGHT));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        ArrayList<Position> moves = validation.legalMoves(rookPosition);

        // Assert: verify the count and a few representative directions.
        assertEquals(0, moves.size());
    }

    @Test
    void whitePawnCanEnPassant() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);
        game.setMoveCounter(10);
        Position from = new Position(4, 3);
        Position to = new Position(5,2);
        Move move = new Move(from, to);


        board.setBoard(4, 3,
                new Pawn(3, 3, PlayerColour.WHITE, 0, PieceType.PAWN));

        // Include both kings so validation methods that inspect kings
        // have a valid board position to work with.
        board.setBoard(0, 0,
                new King(0, 0, PlayerColour.WHITE, PieceType.KING));
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(4, 2, new Pawn(4, 3, PlayerColour.BLACK, game.getMoveCounter() - 1, PieceType.PAWN));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean moves = validation.enPassant(move, game);

        // Assert: verify the pawn can en passant.
        assertTrue(moves);
    }

    @Test
    void whitePawnCantEnPassantDueToDestinationSquareBeingOccupied() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);
        game.setMoveCounter(10);
        Position from = new Position(4, 3);
        Position to = new Position(5,2);
        Move move = new Move(from, to);


        board.setBoard(4, 3,
                new Pawn(3, 3, PlayerColour.WHITE, 0, PieceType.PAWN));

        // Include both kings so validation methods that inspect kings
        // have a valid board position to work with.
        board.setBoard(0, 0,
                new King(0, 0, PlayerColour.WHITE, PieceType.KING));
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(4, 2, new Pawn(4, 3, PlayerColour.BLACK, game.getMoveCounter() - 1, PieceType.PAWN));
        board.setBoard(5, 2, new Queen(4, 3, PlayerColour.BLACK, PieceType.QUEEN));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean moves = validation.enPassant(move, game);

        // Assert: verify the pawn can en passant.
        assertFalse(moves);
    }

    @Test
    void whitePawnCantEnPassantDueToEnPassantMoveCounterRequirements() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);
        game.setMoveCounter(10);
        Position from = new Position(4, 3);
        Position to = new Position(5,2);
        Move move = new Move(from, to);


        board.setBoard(4, 3,
                new Pawn(3, 3, PlayerColour.WHITE, 0, PieceType.PAWN));

        // Include both kings so validation methods that inspect kings
        // have a valid board position to work with.
        board.setBoard(0, 0,
                new King(0, 0, PlayerColour.WHITE, PieceType.KING));
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(4, 2, new Pawn(4, 3, PlayerColour.BLACK, 8, PieceType.PAWN));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean moves = validation.enPassant(move, game);

        // Assert: verify the pawn can en passant.
        assertFalse(moves);
    }

    @Test
    void kingSideCastlingCantOccurDueToKingHasMoved() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);
        game.setMoveCounter(10);
        Position from = new Position(0, 4);
        Position to = new Position(0,6);
        Move move = new Move(from, to);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        whiteKing.move(0,4);
        board.setBoard(0, 4, whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(4, 2, new Pawn(4, 3, PlayerColour.BLACK, 8, PieceType.PAWN));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean moves = validation.canCastleWith(whiteKing, move, board);

        // Assert: verify that castling can't occur.
        assertFalse(moves);
    }

    @Test
    void kingSideCastlingCantOccurDueToRookHasMoved() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);
        game.setMoveCounter(10);
        Position from = new Position(0, 4);
        Position to = new Position(0,6);
        Move move = new Move(from, to);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        board.setBoard(0, 4,
                whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        Rook kingSideRook = new Rook(0, 7, PlayerColour.WHITE, PieceType.ROOK);
        kingSideRook.move(0, 7);
        board.setBoard(4, 2, kingSideRook);

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean moves = validation.canCastleWith(whiteKing, move, board);

        // Assert: verify that castling can't occur.
        assertFalse(validation.canCastleWith(whiteKing, move, board));
    }

    @Test
    void kingSideCastlingCanOccur() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);
        Position from = new Position(0, 4);
        Position to = new Position(0,6);
        Move move = new Move(from, to);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        board.setBoard(0, 4, whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 7,
                new Rook(0, 7, PlayerColour.WHITE, PieceType.ROOK));

        // Act: call the one method this test is about.

        // Assert: verify that castling can't occur.
        assertTrue(whiteKing.canCastle());
    }

    @Test
    void kingSideRookIsThere() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);
        Position from = new Position(0, 4);
        Position to = new Position(0,6);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        board.setBoard(0, 4, whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 7,
                new Rook(0, 7, PlayerColour.WHITE, PieceType.ROOK));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean isRookThere = validation.confirmCastlingRookIsThere(1, board);

        // Assert: verify that castling can't occur.
        assertTrue(isRookThere);
    }

    @Test
    void stalemate() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        board.setBoard(0, 4, whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 6,
                new Rook(0, 6, PlayerColour.WHITE, PieceType.ROOK));
        board.setBoard(6, 0,
                new Rook(6, 0, PlayerColour.WHITE, PieceType.ROOK));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean isStalemate = validation.stalemate(PlayerColour.BLACK);

        // Assert: verify that castling can't occur.
        assertTrue(isStalemate);
    }

    @Test
    void notStalemate() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        board.setBoard(0, 4, whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 6,
                new Rook(0, 6, PlayerColour.WHITE, PieceType.ROOK));
        board.setBoard(5, 0,
                new Rook(5, 0, PlayerColour.WHITE, PieceType.ROOK));

        game.advanceTurn();
        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean isStalemate = validation.stalemate(PlayerColour.BLACK);

        // Assert: verify that castling can't occur.
        assertFalse(isStalemate);
    }

    @Test
    void kingCantMove() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        board.setBoard(0, 4, whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 6,
                new Rook(0, 6, PlayerColour.WHITE, PieceType.ROOK));
        board.setBoard(5, 0,
                new Rook(5, 0, PlayerColour.WHITE, PieceType.ROOK));

        game.advanceTurn();
        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        Position blackKing = new Position(7, 7);
        ArrayList<Move> noMovesAvailable = validation.canKingMove(blackKing, PlayerColour.BLACK);

        // Assert: verify that castling can't occur.
        assertFalse(noMovesAvailable.isEmpty());
    }

    @Test
    void pinnedKing() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        board.setBoard(0, 4, whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 6,
                new Rook(0, 6, PlayerColour.WHITE, PieceType.ROOK));
        board.setBoard(5, 0,
                new Rook(5, 0, PlayerColour.WHITE, PieceType.ROOK));

        game.advanceTurn();
        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean isStalemate = validation.pinnedKing(PlayerColour.BLACK);

        // Assert: verify that castling can't occur.
        assertFalse(isStalemate);
    }

    @Test
    void findPieceTestForBothRooks() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        board.setBoard(0, 4, whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 6,
                new Rook(0, 6, PlayerColour.WHITE, PieceType.ROOK));
        board.setBoard(5, 0,
                new Rook(5, 0, PlayerColour.WHITE, PieceType.ROOK));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        ArrayList<Position> rookPositions = validation.findPiece(Rook.class, PlayerColour.WHITE);
        int numberOfRooks = rookPositions.size();

        // Assert: verify that castling can't occur.
        assertEquals(2, numberOfRooks);
    }

    @Test
    void stalemateForWhite() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        King whiteKing = new King(0, 4, PlayerColour.BLACK, PieceType.KING);
        board.setBoard(0, 4, whiteKing);
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.WHITE, PieceType.KING));
        board.setBoard(0, 6,
                new Rook(0, 6, PlayerColour.BLACK, PieceType.ROOK));
        board.setBoard(6, 0,
                new Rook(6, 0, PlayerColour.BLACK, PieceType.ROOK));

        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean isStalemate = validation.stalemate(PlayerColour.WHITE);

        // Assert: verify that castling can't occur.
        assertTrue(isStalemate);
    }

    @Test
    void pieceMovementToEnPassant() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);
        game.setMoveCounter(6);

        board.setBoard(0, 4,
                new King(0, 4, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.WHITE, PieceType.KING));
        board.setBoard(3, 4,
                new Pawn(3, 4, PlayerColour.WHITE, game.getMoveCounter(), PieceType.PAWN));
        board.setBoard(6, 3,
                new Pawn(6, 3, PlayerColour.BLACK, 0, PieceType.PAWN));

        Position from = new Position(3,4);
        Position to = new Position(4,4);
        Optional<ChessPieces> optionalPiece = board.getPiece(from.getRank(), from.getFile());
        ExecuteMove movements = new ExecuteMove(game);
        // White Pawn Advances
        Move move = new Move(from, to);
        Validation validation = new Validation(game);
        if (optionalPiece.isPresent()) {
            ChessPieces piece = optionalPiece.get();
            assertTrue(validation.isMoveValid(piece,move));
        }
        movements.updateBoard(game, move, board);
        validation = new Validation(game);
        from.setRank(6);
        from.setFile(3);
        to.setRank(4);
        to.setFile(3);
        // Black Pawn advances
        move.setFrom(from);
        move.setTo(to);
        if (optionalPiece.isPresent()) {
            ChessPieces piece = optionalPiece.get();
            assertTrue(validation.isMoveValid(piece,move));
        }
        movements.updateBoard(game, move, board);
        validation = new Validation(game);
        from.setRank(4);
        from.setFile(4);
        to.setRank(3);
        to.setFile(3);
        // White Pawn executes en passant
        move.setFrom(from);
        move.setTo(to);
        if (optionalPiece.isPresent()) {
            ChessPieces piece = optionalPiece.get();
            assertTrue(validation.isMoveValid(piece,move));
        }
        movements.updateBoard(game, move, board);

        Optional<ChessPieces> optionalCapturedPiece = board.getPiece(6, 3);
        assertTrue(optionalCapturedPiece.isEmpty());

    }

    @Test
    void isMoveValidTest() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        board.setBoard(0, 4,
                new King(0, 4, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.WHITE, PieceType.KING));
        ChessPieces whitePawn = new Pawn(3, 4, PlayerColour.WHITE, game.getMoveCounter(), PieceType.PAWN);
        board.setBoard(3, 4, whitePawn);

        board.setBoard(6, 3,
                new Pawn(6, 3, PlayerColour.BLACK, 0, PieceType.PAWN));

        Position from = new Position(3, 4);
        Position to = new Position(4,4);
        Move move = new Move(from, to);
        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean validMove = validation.isMoveValid(whitePawn, move);

        // Assert: verify that castling can't occur.
        assertTrue(validMove);
    }

    @Test
    void isKnightPinnedTest() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 4,
                new King(0, 4, PlayerColour.WHITE, PieceType.KING));
        ChessPieces whiteKnight = new Knight(2, 2, PlayerColour.WHITE, PieceType.KNIGHT);
        board.setBoard(2, 2, whiteKnight);

        board.setBoard(3, 1,
                new Bishop(3, 1, PlayerColour.BLACK, PieceType.BISHOP));

        Position from = new Position(2, 2);
        Position to = new Position(3,4);
        Move move = new Move(from, to);
        Validation validation = new Validation(game);

        // Act: call the one method this test is about.
        boolean validMove = validation.isMoveValid(whiteKnight, move);

        // Assert: verify that castling can't occur.
        assertFalse(validMove);
    }
*/
    @Test
    void kingSideCastlingUpdatingBoardTest() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);

        King whiteKing = new King(0, 4, PlayerColour.WHITE, PieceType.KING);
        board.setBoard(0, 4, whiteKing);
       // board.setBoard(7, 7,
                //new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 7,
                new Rook(0, 7, PlayerColour.WHITE, PieceType.ROOK));
        board.setBoard(0, 0,
                new Rook(0, 0, PlayerColour.WHITE, PieceType.ROOK));

        Position from = new Position(whiteKing.getCurY(), whiteKing.getCurX());
        Position to = new Position(0,6);
        Move move = new Move(from, to);
        Validation validation = new Validation(game);
        assertTrue(validation.isMoveValid(whiteKing, move));

        ExecuteMove executeMove = new ExecuteMove(game);
        executeMove.updateBoard(game, move, board);

        Optional<ChessPieces> optionalKingsPreviousSquare = board.getPiece(from.getRank(), from.getFile());
        assertTrue(optionalKingsPreviousSquare.isEmpty());

        Optional<ChessPieces> optionalRooksPreviousSquare = board.getPiece(0, 7);
        assertTrue(optionalRooksPreviousSquare.isEmpty());

        Optional<ChessPieces> optionalKingsNewSquare = board.getPiece(to.getRank(), to.getFile());
        assertTrue(optionalKingsNewSquare.isPresent());
        PieceType kingsNewSquare = optionalKingsNewSquare.get().getPieceType();
        assertEquals(PieceType.KING, kingsNewSquare);

        Optional<ChessPieces> optionalRooksNewSquare = board.getPiece(from.getRank(), 5);
        assertTrue(optionalRooksNewSquare.isPresent());
        PieceType rooksNewSquare = optionalRooksNewSquare.get().getPieceType();
        assertEquals(PieceType.ROOK, rooksNewSquare);

        // Act: call the one method this test is about.
        ArrayList<Position> rookPositions = validation.findPiece(Rook.class, PlayerColour.WHITE);
        int numberOfRooks = rookPositions.size();

        // Assert: verify that castling can't occur.
        assertEquals(2, numberOfRooks);
    }
}
