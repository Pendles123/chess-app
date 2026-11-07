package com.chesspieces;

import com.chessgame.PlayerColour;

public class Queen extends ChessPieces {

    public Queen(int curY, int curX, PlayerColour colour, PieceType pieceType) {
        super(curY, curX, colour, pieceType);
    }
    @Override
    public boolean pieceMovement(int curY, int curX, int newY, int newX, PlayerColour colour) {
        // Lateral movement
        if (newY == curY && newX != curX) {
            return true;
        }
        if (newX == curX && newY != curY) {
            return true;
        }
        return Math.abs(newX - curX) >= 1 && Math.abs(newY - curY) >= 1;
    }

    public Queen copy() {
        return new Queen(this.curY, this.curX, this.colour, this.pieceType);
    }

    public String toString() {
        return "Queen" + getColour();
    }
}
