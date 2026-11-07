package com.validations;

import com.chessboard.ChessBoard;
import com.chessgame.Game;
import com.chessgame.GameStatus;
import com.chessgame.PlayerColour;
import com.chesspieces.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Iterator;

public class Validation {

    private Game game;
    private ChessBoard board;
    private PlayerColour opponent;
    private PlayerColour player;

    public Validation(Game game) {
        this.game = game;
        this.board = game.getBoard();
        this.player = game.getCurrentPlayer();
        this.opponent = (player == PlayerColour.WHITE)
                ? PlayerColour.BLACK
                : PlayerColour.WHITE;
    }
    public String toString() {
        return Rook.class.toString();
    }

    public <T extends ChessPieces> boolean isMoveValid(T piece, Move move) {

        // Castling
        if (piece instanceof King) {
            boolean isCastlingAttempt =
                    Math.abs(move.getFrom().getFile() - move.getTo().getFile()) == 2
                            && move.getFrom().getRank() == move.getTo().getRank();

            if (isCastlingAttempt) {
                System.out.println("Can Castle");
                return canCastleWith((King) piece, move, board);
            }
        }

        // Piece Movement
        if(!piece.pieceMovement(move.getFrom().getRank(), move.getFrom().getFile(), move.getTo().getRank(), move.getTo().getFile(), player)) {
            System.out.println(player + " Piece Movement error");
            return false;
        }

        Optional<ChessPieces> thePiece = board.getPiece(move.getTo().getRank(), move.getTo().getFile()); //move[3], move[2]
        ChessPieces moveSquare = null;
        if (thePiece.isPresent()) {
            moveSquare = board.getPiece(move.getTo().getRank(), move.getTo().getFile()).get();
        }

        // Checks to see if square is taken by a piece of the same colour
        if (moveSquare != null && moveSquare.getColour().equals(player)) {
            System.out.println("Your piece occupies this square");
            return false;
        }

        // Checks enPassant is valid
        if(piece instanceof Pawn && move.getFrom().getFile() != move.getTo().getFile()) {
            if(!enPassant(move, game)) {
                System.out.println("En Passant is valid");
                return false;
            }
        }

        // Checks if piece is pinned
        if(isPiecePinned(move.getFrom(), player)) {
            System.out.println("Piece is pinned");
            return false;
        }

        // Checks if checkmate
        if (checkmate(player)) {
            game.setStatus(GameStatus.CHECKMATE);
            System.out.println("Checkmate");
            return false;
        }

        // Checks if pawn promotion (to major piece) can occur
        if (move.getPromotion() != null) {
            System.out.println("Can promote pawn to a major piece");
            return canPawnPromote(move);
        }
        return true;
    }

    // Returns an ArrayList of coordinates (destinations) any given piece can move to.
    // This is useful for highlighting possible moves for UI.
    public ArrayList<Position> legalMoves(Position square) {
        ArrayList<Move> listOfLegalMoves = new ArrayList<>();
        ArrayList<Position> positionsOfLegalMoves = new ArrayList<>();
        Optional<ChessPieces> selectedSquare = board.getPiece(square.getRank(), square.getFile());
        if (selectedSquare.isPresent()) {
            ChessPieces piece = selectedSquare.get();
            if (piece instanceof Pawn) {
                listOfLegalMoves.addAll(canPawnMove(square, player));
            }
            if (piece instanceof Rook) {
                listOfLegalMoves.addAll(canRookMove(square, player));
            }
            if (piece instanceof Knight) {
                listOfLegalMoves.addAll(canKnightMove(square, player));
            }
            if (piece instanceof Bishop) {
                listOfLegalMoves.addAll(canBishopMove(square, player));
            }
            if (piece instanceof Queen) {
                listOfLegalMoves.addAll(canQueenMove(square, player));
            }
            if (piece instanceof King) {
                listOfLegalMoves.addAll(canKingMove(square, player));

                King king = (King) piece;

                Position kingSideDestination = new Position(square.getRank(), square.getFile() + 2);
                Move kingSideMove = new Move(square, kingSideDestination);

                if (canCastleWith(king, kingSideMove, board)) {
                    positionsOfLegalMoves.add(kingSideDestination);
                }

                Position queenSideDestination = new Position(square.getRank(), square.getFile() - 2);
                Move queenSideMove = new Move(square, queenSideDestination);

                if (canCastleWith(king, queenSideMove, board)) {
                    positionsOfLegalMoves.add(queenSideDestination);
                }
            }
        }

        for (Move moves : listOfLegalMoves) {
            positionsOfLegalMoves.add(moves.getTo());
        }
        return positionsOfLegalMoves;
    }


    // CHECK THIS ---- I BELIEVE IT NEEDS REVISION
    public boolean canPawnPromote(Move move) {
        Optional<ChessPieces> optPiece = board.getPiece(move.getFrom().getRank(), move.getFrom().getFile());
        if (optPiece.isPresent()) {
            ChessPieces piece = optPiece.get();
            return piece instanceof Pawn && promotionHelperMethod(move.getTo());
        }
        return false;
    }

    public boolean promotionHelperMethod(Position move) {
        if (player == PlayerColour.WHITE && move.getRank() == 7) {
            return true;
        } else return player == PlayerColour.BLACK && move.getRank() == 0;
    }

    public boolean isPiecePinned(Position move, PlayerColour player) {
        Optional<ChessPieces> piece = board.getPiece(move.getRank(), move.getFile());
        boolean kingInCheck = false;
        if (piece.isPresent()) {
            ChessPieces thePiece = piece.get();
            board.setBoard(move.getRank(), move.getFile(), null);
            kingInCheck = isKingInCheck(player);
            board.setBoard(move.getRank(), move.getFile(), thePiece);
        }
        return kingInCheck;
    }

    public boolean checkmate(PlayerColour player) {
        if(pinnedKing(player) && isKingInCheck(player)) {
            System.out.println("Checkmate");
            return true;
        }
        return false;
    }

    // En Passant
    public boolean enPassant(Move move, Game game) {
        Optional<ChessPieces> opponentPawn = board.getPiece(move.getFrom().getRank(), move.getTo().getFile());//move[1], move[2]
        Optional<ChessPieces> destinationSquare = board.getPiece(move.getTo().getRank(), move.getTo().getFile());
        if(opponentPawn.isPresent() && destinationSquare.isEmpty()) {
            ChessPieces theOpponentPawn = opponentPawn.get();
            if (theOpponentPawn instanceof Pawn && !theOpponentPawn.getColour().equals(player)) {
                int opponentPawnCounter = ((Pawn) theOpponentPawn).getEnPassantMoveCounter();
                return opponentPawnCounter >= 0
                        && opponentPawnCounter == game.getMoveCounter() - 1;
            }
        }
        return false;
    }

    // Finds the rooks that CAN castle
    public ArrayList<Position> rooksThatCanCastle(ChessBoard board, PlayerColour player) {
        ArrayList<Position> rookPositions = findPiece(Rook.class, player);

        for (Iterator<Position> iterator = rookPositions.iterator(); iterator.hasNext(); ) {
            Position position = iterator.next();
            ChessPieces piece = board.getPiece(position.getRank(), position.getFile())
                    .orElseThrow(() -> new IllegalStateException("Expected a piece but found none!"));

            if (!(piece instanceof Rook) || !((Castling) piece).canCastle()) {
                iterator.remove();
            }
        }
        return rookPositions;
    }

    public boolean confirmCastlingRookIsThere(int direction, ChessBoard board) {

        Position theKing = findKing(player);
        int theKingRank = theKing.getRank();
        ChessPieces rookInQuestion;
        int rookFile;

        if (direction == 1) {
            rookFile = 7;
        } else {
            rookFile = 0;
        }

        // This is the conditional statement that ascertains which rook the king is castling with
            Optional<ChessPieces> optionalRook = board.getPiece(theKingRank, rookFile);
            if (optionalRook.isPresent()) {
                rookInQuestion = optionalRook.get();
                return rookInQuestion instanceof Rook;
            }

        System.out.println("The required Rook is NOT there");
        return false;
    }

    // Castling method
    public boolean canCastleWith(King king, Move move, ChessBoard board) {

        ArrayList<Position> rookPositions = rooksThatCanCastle(board, player);

        if (rookPositions.isEmpty() || !king.canCastle()) { // One of them has moved, so no castling allowed
            System.out.println("No Rook OR King has moved");
            return false;
        }

        Position theKing = findKing(player);
        int rookFile = move.getTo().getFile() > 4 ? 7 : 0;
        int direction = move.getTo().getFile() > 4 ? 1 : -1;

        // This is the conditional statement that ascertains which rook the king is castling with
        if (!confirmCastlingRookIsThere(direction, board)) {
            return false;
        }

        // Check to see if a piece is in the way or the king moves through check
        for (int file = theKing.getFile() + direction; file != rookFile; file += direction) {
            if (board.getPiece(theKing.getRank(), file).isPresent()) {
                System.out.println("A piece is blocking castling");
                return false;
            }

            // King crosses one square, then lands two squares from its start.
            if (Math.abs(file - theKing.getFile()) <= 2
                    && isSquareAttackedByOpponent(
                    new Position(theKing.getRank(), file), player)) {
                System.out.println("Can't move through check");
                return false;
            }
        }
        return true;
    }

    // GENERIC METHOD TO FIND PIECES
    public <T extends ChessPieces> ArrayList<Position> findPiece(Class<T> thePieceClass, PlayerColour player) {
        ArrayList<Position> piecePositions = new ArrayList<>();
        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                Optional<ChessPieces> piece = board.getPiece(i, j);
                if (piece.isPresent()) {
                    ChessPieces foundPiece = piece.get();
                    if (foundPiece.getColour().equals(player) && thePieceClass.isInstance(foundPiece)) { // Removed ! before foundPiece
                        piecePositions.add(new Position(i, j));
                    }
                }
            }
        }
        return piecePositions;
    }

    public Position findKing(PlayerColour player) {

        Position kingPosition = new Position();
        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                Optional<ChessPieces> piece = board.getPiece(i, j);
                if (piece.isPresent()) {
                    ChessPieces foundPiece = piece.get();
                    if (foundPiece.getColour().equals(player) && foundPiece instanceof King) {
                        kingPosition.setRank(i);
                        kingPosition.setFile(j);
                        return kingPosition;
                    }
                }
            }
        }
        System.out.println("Cannot find King");
        return kingPosition;
    }

// Methods that find the squares the opponent controls
    public ArrayList<Position> pawnsControlledSquares(ArrayList<Position> pawnPositions) {
        ArrayList<Position> controlledSquares = new ArrayList<>();

        if(pawnPositions.isEmpty()) {return controlledSquares;}

            if (opponent.equals(PlayerColour.WHITE)) {
                for (Position pawn : pawnPositions) {
                    int firstElement = pawn.getFile();
                    int secondElement = pawn.getRank();
                    if (secondElement == 0) {
                        controlledSquares.add(new Position(firstElement + 1, secondElement + 1));
                    } else if (secondElement == 7) {
                        controlledSquares.add(new Position(firstElement + 1, secondElement - 1));
                    } else {
                        controlledSquares.add(new Position(firstElement + 1, secondElement + 1));
                        controlledSquares.add(new Position(firstElement + 1, secondElement - 1));
                    }
                }
            } else {
                for (Position pawn : pawnPositions) {
                    int firstElement = pawn.getFile();
                    int secondElement = pawn.getRank();
                    if (secondElement == 0) {
                        controlledSquares.add(new Position(firstElement - 1, secondElement + 1));
                    } else if (secondElement == 7) {
                        controlledSquares.add(new Position(firstElement - 1, secondElement - 1));
                    } else {
                        controlledSquares.add(new Position(firstElement - 1, secondElement + 1));
                        controlledSquares.add(new Position(firstElement - 1, secondElement - 1));
                    }
                }
            }
        return controlledSquares;
    }

    private void addRookControlledSquares(Position position, ArrayList<Position> controlledSquares) {
        int row = position.getRank();
        int col = position.getFile();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // All 4 vertical and horizontal
        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int currentRow = row + dx;
            int currentCol = col + dy;

            while (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
                Optional<ChessPieces> squares = board.getPiece(currentRow, currentCol);
                if (squares.isEmpty()) {
                    controlledSquares.add(new Position(currentRow, currentCol));
                } else {
                    controlledSquares.add(new Position(currentRow, currentCol)); // Include occupied square
                    break; // Stop further traversal
                }
                currentRow += dx;
                currentCol += dy;
            }
        }
    }

    public ArrayList<Position> rookControlledSquares(ArrayList<Position> rookPositions) { // {{2,5}, {6,3}}

        ArrayList<Position> controlledSquares = new ArrayList<>();
        if(rookPositions.isEmpty()) {
            return controlledSquares;
        }

        for (Position position : rookPositions) {
            addRookControlledSquares(position, controlledSquares);
        }
        return controlledSquares;

    }

    private void addKnightControlledSquares(Position position, ArrayList<Position> controlledSquares) {
        int row = position.getRank();
        int col = position.getFile();
        int[][] directions = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}}; // All 4 diagonals

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int currentRow = row + dx;
            int currentCol = col + dy;

            if (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
                controlledSquares.add(new Position(currentRow, currentCol));
            }
        }
    }

    public ArrayList<Position> knightControlledSquares(ArrayList<Position> knightPositions) {
        ArrayList<Position> controlledSquares = new ArrayList<>();
        if(knightPositions.isEmpty()) {
            return controlledSquares;
        }
        for (Position position : knightPositions) {
            addKnightControlledSquares(position, controlledSquares);
        }
        return controlledSquares;
    }

    private void addBishopControlledSquares(Position position, ArrayList<Position> controlledSquares) {
        int row = position.getRank();
        int col = position.getFile();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; // All 4 diagonals

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int currentRow = row + dx;
            int currentCol = col + dy;

            while (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
                Optional<ChessPieces> squares = board.getPiece(currentRow, currentCol);
                if (squares.isEmpty()) {
                    controlledSquares.add(new Position(currentRow, currentCol));
                } else {
                    controlledSquares.add(new Position(currentRow, currentCol)); // Include occupied square
                    break; // Stop further traversal
                }
                currentRow += dx;
                currentCol += dy;
            }
        }
    }

    public ArrayList<Position> bishopControlledSquares(ArrayList<Position> bishopPositions) {
        ArrayList<Position> controlledSquares = new ArrayList<>();
        if (bishopPositions.isEmpty()) {
            return controlledSquares;
        }

        for (Position position : bishopPositions) {
            addBishopControlledSquares(position, controlledSquares);
        }
        return controlledSquares;
    }

    public ArrayList<Position> queenControlledSquares(ArrayList<Position> oppositeQueen) {
        ArrayList<Position> controlledSquares = new ArrayList<>();

        for (Position queenPosition : oppositeQueen) {
            ArrayList<Position> queenAsRook = new ArrayList<>();
            queenAsRook.add(queenPosition);
            ArrayList<Position> queenAsBishop = new ArrayList<>();
            queenAsBishop.add(queenPosition);

            controlledSquares.addAll(rookControlledSquares(queenAsRook));
            controlledSquares.addAll(bishopControlledSquares(queenAsBishop));
        }
        return controlledSquares;
    }

    public void addKingControlledSquares(Position position, ArrayList<Position> controlledSquares) {
        int row = position.getRank();
        int col = position.getFile();
        int[][] directions = {{1, 1}, {1, -1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, 1}, {-1, -1}}; // All king moves

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int currentRow = row + dx;
            int currentCol = col + dy;

            if (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
                Optional<ChessPieces> squares = board.getPiece(currentRow, currentCol);
                if (squares.isEmpty()) {
                    controlledSquares.add(new Position(currentRow, currentCol));  // Original controlledSquares.add(new int[]{currentRow, currentCol});
                } else {
                    controlledSquares.add(new Position(currentRow, currentCol)); // Include occupied squares
                }
                currentRow += dx;
                currentCol += dy;
            }
        }
    }

    public ArrayList<Position> kingControlledSquares(ArrayList<Position> kingPosition) {
        ArrayList<Position> controlledSquares = new ArrayList<>();
        if (kingPosition.isEmpty()) {
            return controlledSquares;
        }

        for (Position position : kingPosition) {
            addKingControlledSquares(position, controlledSquares);
        }
        return controlledSquares;
    }


    public ArrayList<Position> listOfKingMoves(Position kingPosition, PlayerColour player) { // (5,2)
        ArrayList<Position> moveList = new ArrayList<>();
        int row = kingPosition.getRank();
        int col = kingPosition.getFile();
        int[][] directions = {{1, 1}, {1, -1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {-1, 1}, {-1, -1}}; // All king moves

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int currentRow = row + dx;
            int currentCol = col + dy;

            if (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
                moveList.add(new Position(currentRow, currentCol));
            }
        }
        return moveList;
    }


    // Stalemate
    public ArrayList<Move> canPawnMove(Position pawnPosition, PlayerColour player) {
        Position toPosition;
        ArrayList<Move> possibleMoves = new ArrayList<>();
        int pawnMovement = player.equals(PlayerColour.WHITE) ? 1 : -1;
        int promotionMovement = pawnPosition.getRank() + pawnMovement;
        int doublePromotionMovement = pawnPosition.getRank() + (pawnMovement * 2);
        int[] attackingMovement = new int[]{-1, 1};
        int[] lesserPawnCoordinate = new int[]{promotionMovement, pawnPosition.getFile()};
        int[] doublePawnCoordinate = new int[]{doublePromotionMovement, pawnPosition.getFile()};
        Optional<ChessPieces> lesserPossiblePosition = board.getPiece(lesserPawnCoordinate[0], lesserPawnCoordinate[1]);
        Optional<ChessPieces> doublePossiblePosition = board.getPiece(doublePawnCoordinate[0], doublePawnCoordinate[1]);
        Optional<ChessPieces> currentPawnPosition = board.getPiece(pawnPosition.getRank(), pawnPosition.getFile());
        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                if (currentPawnPosition.isPresent() &&  // NEED A CONDITION ifEmpty AND A CONDITION FOR isPresent WHEN ATTACKING
                        currentPawnPosition.get().pieceMovement(pawnPosition.getRank(), pawnPosition.getFile(), i, j, player)) {
                    toPosition = new Position(i, j);
                    possibleMoves.add(new Move(pawnPosition, toPosition, null));
                    if (Math.abs(pawnPosition.getRank() - i) == 2 && pawnPosition.getFile() == j && lesserPossiblePosition.isPresent()) {
                        possibleMoves.remove(toPosition);
                    }
                }
            }
        }

        for (int movement : attackingMovement) {
            if (lesserPawnCoordinate[1] + movement >= 0 && lesserPawnCoordinate[1] + movement <= 7) {
                Optional<ChessPieces> theAttackedSquare = board.getPiece(lesserPawnCoordinate[0], lesserPawnCoordinate[1] + movement);
                if (theAttackedSquare.isPresent()) {
                    ChessPieces theAttackedPiece = theAttackedSquare.get();
                    if (!theAttackedPiece.getColour().equals(player) && !isPiecePinned(pawnPosition, player)) {
                        toPosition = new Position(lesserPawnCoordinate[0], lesserPawnCoordinate[1] + movement);
                        possibleMoves.add(new Move(pawnPosition, toPosition, null));
                    }
                }
            }
        }

        if (lesserPossiblePosition.isEmpty() && !isPiecePinned(pawnPosition, player)) {
            toPosition = new Position(lesserPawnCoordinate[0], lesserPawnCoordinate[1]);
            possibleMoves.add(new Move(pawnPosition, toPosition, null));
        }

        if (currentPawnPosition.isPresent()) {
            if (doublePossiblePosition.isEmpty() && !isPiecePinned(pawnPosition, player)  ) {
                toPosition = new Position(doublePawnCoordinate[0], doublePawnCoordinate[1]);
                possibleMoves.add(new Move(pawnPosition, toPosition, null));
            }
        }

        return possibleMoves;
    }
    public ArrayList<Move> canRookMove(Position rookPosition, PlayerColour player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        int row = rookPosition.getRank();
        int col = rookPosition.getFile();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // All 4 vertical and horizontal
        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int currentRow = row + dx;
            int currentCol = col + dy;

            while (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
                Optional<ChessPieces> possiblePosition = board.getPiece(currentRow, currentCol);
                if (possiblePosition.isPresent()) {
                    ChessPieces attackedPiece = possiblePosition.get();
                    if (!attackedPiece.getColour().equals(player) && !isPiecePinned(rookPosition, player)) {
                        possibleMoves.add(new Move(rookPosition, new Position(currentRow, currentCol), null));
                    }
                    break;
                }
                if (!isPiecePinned(rookPosition, player)) {
                    possibleMoves.add(new Move(rookPosition, new Position(currentRow, currentCol), null));
                }
                currentRow += dx;
                currentCol += dy;
            }
        }
        return possibleMoves;
    }
    public ArrayList<Move> canKnightMove(Position knightPosition, PlayerColour player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        int row = knightPosition.getRank();
        int col = knightPosition.getFile();
        int[][] directions = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {-1, 2}, {1, -2}, {-1, -2}}; // All 4 diagonals

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int currentRow = row + dx;
            int currentCol = col + dy;

            if (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
                Optional<ChessPieces> possiblePosition = board.getPiece(currentRow, currentCol);
                if (possiblePosition.isPresent()) {
                    ChessPieces attackedPiece = possiblePosition.get();
                    if (!attackedPiece.getColour().equals(player) && !isPiecePinned(knightPosition, player)) {
                        possibleMoves.add(new Move(knightPosition, new Position(currentRow, currentCol), null));
                    }
                }
                if (possiblePosition.isEmpty() && !isPiecePinned(knightPosition, player)) {
                    possibleMoves.add(new Move(knightPosition, new Position(currentRow, currentCol), null));
                }
            }
        }
    return possibleMoves;
    }
    public ArrayList<Move> canBishopMove(Position bishopPosition, PlayerColour player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        int row = bishopPosition.getRank();
        int col = bishopPosition.getFile();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; // All 4 diagonals

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int currentRow = row + dx;
            int currentCol = col + dy;

            while (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
                Optional<ChessPieces> possiblePosition = board.getPiece(currentRow, currentCol);
                Position bishopCoordinate = new Position(currentRow, currentCol);
                if (possiblePosition.isPresent()) {
                    ChessPieces attackedPiece = possiblePosition.get();
                    if (!attackedPiece.getColour().equals(player) && !isPiecePinned(bishopPosition, player)) {
                        possibleMoves.add(new Move(bishopPosition, bishopCoordinate, null));
                    }
                    break;
                }
                if (!isPiecePinned(bishopCoordinate, player)) {
                    possibleMoves.add(new Move(bishopPosition, bishopCoordinate, null));
                }
                currentRow += dx;
                currentCol += dy;
            }
        }
        return possibleMoves;
    }
    public ArrayList<Move> canQueenMove(Position queenPosition, PlayerColour player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        possibleMoves.addAll(canRookMove(queenPosition, player));
        possibleMoves.addAll(canBishopMove(queenPosition, player));
        return possibleMoves;
    }
    public ArrayList<Move> canKingMove(Position kingPosition, PlayerColour player) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        int row = kingPosition.getRank();
        int col = kingPosition.getFile();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // All 8 moves

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int currentRow = row + dx;
            int currentCol = col + dy;

            if (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8) {
                Optional<ChessPieces> possiblePosition = board.getPiece(currentRow, currentCol);
                if (possiblePosition.isPresent()) {
                    ChessPieces attackedPiece = possiblePosition.get();
                    if (!attackedPiece.getColour().equals(player) && !pinnedKing(player)) {
                        possibleMoves.add(new Move(kingPosition, new Position(currentRow, currentCol), null));
                    }
                }
                if (!pinnedKing(player)) { //possiblePosition.isEmpty() &&
                    possibleMoves.add(new Move(kingPosition, new Position(currentRow, currentCol), null));
                }
            }
        }
        return possibleMoves;
    }
    public boolean stalemate (PlayerColour player) {
        for (int i = 0; i < board.getBoardHeight(); i++) {
            for (int j = 0; j < board.getBoardWidth(); j++) {
                Optional<ChessPieces> piece = board.getPiece(i, j);
                if (piece.isPresent()) {
                    ChessPieces thePiece = piece.get();
                    if(thePiece.getColour().equals(player)) {
                        Position pieceCoordinate = new Position(i, j);
                        if (thePiece instanceof Pawn && !canPawnMove(pieceCoordinate, player).isEmpty()) return false;
                        if (thePiece instanceof Rook && !canRookMove(pieceCoordinate, player).isEmpty()) return false;
                        if (thePiece instanceof Knight && !canKnightMove(pieceCoordinate, player).isEmpty()) return false;
                        if (thePiece instanceof Bishop && !canBishopMove(pieceCoordinate, player).isEmpty()) return false;
                        if (thePiece instanceof Queen && !canQueenMove(pieceCoordinate, player).isEmpty()) return false;
                        if (thePiece instanceof King && !canKingMove(pieceCoordinate, player).isEmpty()) return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean pinnedKing (PlayerColour player){
        Position kingPosition = findKing(player);
        ArrayList<Position> possibleKingMoves = listOfKingMoves(kingPosition, player);
        ArrayList<Position> controlledSquares = new ArrayList<>();

        controlledSquares.addAll(pawnsControlledSquares(findPiece(Pawn.class, opponent)));
        controlledSquares.addAll(knightControlledSquares(findPiece(Knight.class, opponent)));
        controlledSquares.addAll(bishopControlledSquares(findPiece(Bishop.class, opponent)));
        controlledSquares.addAll(rookControlledSquares(findPiece(Rook.class, opponent)));
        controlledSquares.addAll(queenControlledSquares(findPiece(Queen.class, opponent)));
        controlledSquares.addAll(kingControlledSquares(findPiece(King.class, opponent)));

        for (Position move : possibleKingMoves) {
            if (!controlledSquares.contains(move)) {
                return false; // King has at least one safe move
            }
        }

        return true;
    }

    public boolean isKingInCheck(PlayerColour player) {
        return isSquareAttackedByOpponent(findKing(player), player);
    }

    private boolean isSquareAttackedByOpponent(Position square, PlayerColour player) {
        ArrayList<Position> controlledSquares = new ArrayList<>();
        controlledSquares.addAll(pawnsControlledSquares(findPiece(Pawn.class, opponent)));
        controlledSquares.addAll(knightControlledSquares(findPiece(Knight.class, opponent)));
        controlledSquares.addAll(bishopControlledSquares(findPiece(Bishop.class, opponent)));
        controlledSquares.addAll(rookControlledSquares(findPiece(Rook.class, opponent)));
        controlledSquares.addAll(queenControlledSquares(findPiece(Queen.class, opponent)));
        controlledSquares.addAll(kingControlledSquares(findPiece(King.class, opponent)));

        for (Position squares : controlledSquares) {
            System.out.println(squares);
        }
        return controlledSquares.contains(square);
    }
}
