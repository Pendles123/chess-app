package com.validations;

public class Move {

    private Position from;
    private Position to;
    private PromotionPiece promotion;

    public Move() {}
    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }
    public Move(Position from, Position to, PromotionPiece promotion) {
        this.from = from;
        this.to = to;
        this.promotion = promotion;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public PromotionPiece getPromotion() {
        return promotion;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public void setTo(Position to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "From" + from.toString() + ", To" + to.toString();
    }
}
