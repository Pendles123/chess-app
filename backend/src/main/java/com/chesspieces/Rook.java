package com.chesspieces;

import com.chessgame.PlayerColour;

public class Rook extends ChessPieces implements Castling {

    public Rook(int curY, int curX, PlayerColour colour, PieceType pieceType) {
        super(curY, curX, colour, pieceType);
    }

    public boolean canCastle() {
        return !hasMoved;
    }

    @Override
    public boolean pieceMovement(int curY, int curX, int newY, int newX, PlayerColour colour) {

        // Lateral movement
        if (newY == curY && newX != curX) {
           return true;
        }
        return newX == curX && newY != curY;
    }
    public Rook copy() {
        Rook clonedRook = new Rook(this.curY, this.curX, this.colour, this.pieceType);
        clonedRook.hasMoved = this.hasMoved;
        return clonedRook;
    }


    public String toString() {
        return "Rook" + getColour();
    }

    public int getCurX() {
        return curX;
    }
}
