package com.chesspieces;

import com.chessgame.PlayerColour;

public class Pawn extends ChessPieces {

    PieceType pieceType = PieceType.PAWN;
    public int enPassantMoveCounter;

    public Pawn(int curY, int curX, PlayerColour colour, int enPassantMoveCounter, PieceType pieceType) {
        super(curY, curX, colour, pieceType);
        this.enPassantMoveCounter = enPassantMoveCounter;
    }

    @Override
    public boolean pieceMovement(int curY, int curX, int newY, int newX, PlayerColour colour) { // rank = Y, file = X

        // com.chesspieces.Pawn advances White/Black
        if (Math.abs(newY - curY) == 1) {
            if(colour.equals(PlayerColour.WHITE)) {
                if (newX == curX && newY - curY == 1) {
                    return true;
                }
            }
            if (colour.equals(PlayerColour.BLACK)) {
                if (newX == curX && curY - newY == 1) {
                    return true;
                }
            }
        }

        // com.chesspieces.Pawn double advance White/Black
        if (Math.abs(newY - curY) == 2) {
            if (!hasMoved) {
                if (colour.equals(PlayerColour.WHITE)) {
                    if (newX == curX && newY - curY == 2) {
                        return true;
                    }
                }
                if (colour.equals(PlayerColour.BLACK)) {
                    if (newX == curX && curY - newY == 2) {
                        return true;
                    }
                }
            }
        }

        // com.chesspieces.Pawn captures White/Black
        if(Math.abs(newX - curX) == 1) {

            if (curY - newY == 1 && colour.equals(PlayerColour.WHITE)) {
                return true;
            }
            return newY - curY == 1 && colour.equals(PlayerColour.BLACK);
        }
        return false;
    }

    @Override
    public PlayerColour getColour() {
        return super.getColour();
    }

    public int getEnPassantMoveCounter() {return enPassantMoveCounter;}
    public void setEnPassantMoveCounter(int counter) {
        this.enPassantMoveCounter = counter;
    }

    public Pawn copy() {
        Pawn clonedPawn = new Pawn(this.curY, this.curX, this.colour, this.enPassantMoveCounter, this.pieceType);
        clonedPawn.hasMoved = this.hasMoved;
        return clonedPawn;
    }

    @Override
    public String toString() {
        return "Pawn" + getColour();
    }

}
