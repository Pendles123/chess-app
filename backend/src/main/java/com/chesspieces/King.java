package com.chesspieces;

import com.chessgame.PlayerColour;

public class King extends ChessPieces implements Castling {

    public King(int curY, int curX, PlayerColour colour, PieceType pieceType) {
        super(curY, curX, colour, pieceType);
    }

    @Override
    public boolean pieceMovement(int curY, int curX, int newY, int newX, PlayerColour colour) {

        if (Math.abs(newX - curX) == 2 && newY == curY) {
            return true;
        }

        return Math.abs(newX - curX) <= 1 && Math.abs(newY - curY) <= 1;

    }

    public boolean canCastle() {
        return !hasMoved;
    }

    public King copy() {
        King clonedKing = new King(this.curY, this.curX, this.colour, this.pieceType);
        clonedKing.hasMoved = this.hasMoved;
        return clonedKing;
    }

    public String toString() {
        return "King" + getColour();
    }

    public int getCurX() {
        return curX;
    }

    public int getCurY() {
        return curY;
    }
}
