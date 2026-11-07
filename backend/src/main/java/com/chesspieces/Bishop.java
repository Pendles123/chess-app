package com.chesspieces;

import com.chessgame.PlayerColour;

public class Bishop extends ChessPieces {

    public Bishop(int curY, int curX, PlayerColour colour, PieceType pieceType) {
        super(curY, curX, colour, pieceType);
    }

    @Override
    public boolean pieceMovement(int curY, int curX, int newY, int newX, PlayerColour colour) {
        return Math.abs(newX - curX) == Math.abs(newY - curY);
        //Math.abs(newX - curX) >= 1 && Math.abs(newY - curY) >= 1;
    }

    public Bishop copy() {
        return new Bishop(this.curY, this.curX, this.colour, this.pieceType);
    }

    public String toString() {
        return "Bishop" + getColour();
    }
}
