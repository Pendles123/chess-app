package com.chesspieces;

import com.chessgame.PlayerColour;

public abstract class ChessPieces {
    protected final PlayerColour colour;
    protected int curX;
    protected int curY;
    protected boolean hasMoved = false;
    protected final PieceType pieceType;

    public ChessPieces(int curY, int curX, PlayerColour colour, PieceType pieceType){
        this.curY = curY;
        this.curX = curX;
        this.colour = colour;
        this.pieceType = pieceType;
    }

    public abstract boolean pieceMovement(int curY, int curX, int newY, int newX, PlayerColour colour);

    public abstract ChessPieces copy();

    public void move(int newY, int newX) {
        this.curX = newX;
        this.curY = newY;
        this.hasMoved = true;
    }
    public PlayerColour getColour() {
        return colour;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

}

