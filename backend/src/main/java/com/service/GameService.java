package com.service;

import com.chessboard.ChessBoard;
import com.chessgame.Game;
import com.chessgame.ExecuteMove;
import com.chessgame.GameStatus;
import com.chessgame.PlayerColour;
import com.chesspieces.ChessPieces;
import com.dto.*;
import com.repository.GameRepository;
import com.validations.Move;
import com.validations.PromotionPiece;
import com.validations.Validation;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    private final Validation validation;
    private final GameRepository repository;

    public GameService(Validation validation, GameRepository repository, ExecuteMove updateBoard) {
        this.validation = validation;
        this.repository = repository;
    }

    public MoveResponse applyMove(long gameId, MoveRequest request) {

        // get the correct board for this game
        ChessBoard updatedBoard;
        Game currentGame = repository.getGame(gameId);
        ChessBoard board = currentGame.getBoard();
        GameStatus status = currentGame.getStatus();
        ExecuteMove execution = new ExecuteMove(currentGame);
        PromotionPiece promotedPawn = request.promotionPiece();
        PieceDTO[][] dtoBoard;

        // convert request to Move object
        Move move = new Move(
                request.from(),
                request.to(),
                request.promotionPiece()
        );
        Optional<ChessPieces> pieces = board.getPiece(move.getFrom().getRank(), move.getFrom().getFile());

        if (pieces.isEmpty()) {
            dtoBoard = mapBoard(board.getBoard());
            return new MoveResponse(false, dtoBoard, status, currentGame.getCurrentPlayer());
        }

        ChessPieces piece = pieces.get();

        boolean isValid = validation.isMoveValid(piece, move);

        if (!isValid) {
            dtoBoard = mapBoard(board.getBoard());
            return new MoveResponse(false, dtoBoard, status, currentGame.getCurrentPlayer());
        }

        execution.updateBoard(currentGame, move, board);
        updatedBoard = board.deepCopy();
        currentGame.setBoard(updatedBoard);
        currentGame.advanceTurn();
        repository.saveBoard(currentGame);
        dtoBoard = mapBoard(updatedBoard.getBoard());
        
        return new MoveResponse(true, dtoBoard, status, currentGame.getCurrentPlayer());
    }

    public GameStateResponse startNewGame() {
        long gameId = repository.createNewGame();
        Game game = repository.getGame(gameId);
        ChessBoard board = game.getBoard();
        GameStatus status = game.getStatus();
        PlayerColour currentPlayer = game.getCurrentPlayer();
        PieceDTO[][] dtoBoard = mapBoard(board.getBoard());

        return new GameStateResponse(dtoBoard, gameId, status, currentPlayer);
    }

    public GameStateResponse drawAgreementStatusChange(long gameId) {
        Game game = repository.getGame(gameId);
        game.setStatus(GameStatus.DRAW);
        ChessBoard board = game.getBoard();
        PieceDTO[][] dtoBoard = mapBoard(board.getBoard());
        return new GameStateResponse(dtoBoard, game.getId(), game.getStatus(), game.getCurrentPlayer());
    }

    public GameStateResponse playerResigned(long gameId) {
        Game game = repository.getGame(gameId);
        game.setStatus(GameStatus.RESIGNED);
        ChessBoard board = game.getBoard();
        PieceDTO[][] dtoBoard = mapBoard(board.getBoard());
        return new GameStateResponse(dtoBoard, game.getId(), game.getStatus(), game.getCurrentPlayer());
    }

    protected PieceDTO toDTO(ChessPieces pieces) {
        if (pieces == null) {return null;}

        return new PieceDTO(pieces.getPieceType(), pieces.getColour());
    }

    public PieceDTO[][] mapBoard(ChessPieces[][] board) {
        PieceDTO[][] dtoBoard = new PieceDTO[8][8];

        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                dtoBoard[rank][file] = toDTO(board[rank][file]);
            }
        }
        return dtoBoard;
    }
}
