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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ExecuteMoveTest {
/*
    @Test
    void hasMovedVariableIsTrueAfterMovement() {
        // Arrange: build a small, predictable board position.
        Game game = new Game(1L);
        ChessBoard board = new ChessBoard();
        game.setBoard(board);
        game.setMoveCounter(10);
        Position from = new Position(0, 0);
        Position to = new Position(7,0);
        Move move = new Move(from, to);
        ExecuteMove executeMove = new ExecuteMove(game);

        // Include both kings so validation methods that inspect kings
        // have a valid board position to work with.
        board.setBoard(0, 1,
                new King(0, 1, PlayerColour.WHITE, PieceType.KING));
        board.setBoard(7, 7,
                new King(7, 7, PlayerColour.BLACK, PieceType.KING));
        board.setBoard(0, 0,
                new Rook(0, 0, PlayerColour.WHITE, PieceType.ROOK));
        board.setBoard(0, 6,
                new Rook(0, 6, PlayerColour.WHITE, PieceType.ROOK));


        // Act: call the one method this test is about.
        executeMove.updateBoard(game, move, board);
        Optional<ChessPieces> optionalMovedRook = board.getPiece(to.getRank(), to.getFile());
        Optional<ChessPieces> optionalNotMovedRook = board.getPiece(0,6);
        //Moved Rook
        ChessPieces theRook = optionalMovedRook.get();
        boolean hasMovedValue = theRook.getHasMoved();
        //Not moved Rook
        ChessPieces notMovedRook = optionalNotMovedRook.get();
        boolean hasNotMoved = notMovedRook.getHasMoved();

        // Assert: verify correct hasMoved value.
        assertTrue(hasMovedValue);
        assertFalse(hasNotMoved);
    }
*/
}
