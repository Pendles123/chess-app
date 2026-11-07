package com.chesspieces;

import com.chessgame.PlayerColour;

public class Knight extends ChessPieces {

    PieceType pieceType = PieceType.KNIGHT;

    public Knight(int curY, int curX, PlayerColour colour, PieceType pieceType) {
        super(curY, curX, colour, pieceType);
    }
    @Override
    public boolean pieceMovement(int curY, int curX, int newY, int newX, PlayerColour colour) {
        if (Math.abs(newX - curX) == 1 && Math.abs(newY - curY) == 2) {
            return true;
        } else return Math.abs(newX - curX) == 2 && Math.abs(newY - curY) == 1;
    }

    public Knight copy() {
        return new Knight(this.curY, this.curX, this.colour, this.pieceType);
    }

    public String toString() {
        return "Knight" + getColour();
    }

}
