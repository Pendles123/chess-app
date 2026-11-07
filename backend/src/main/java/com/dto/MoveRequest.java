package com.dto;


import com.validations.Position;
import com.validations.PromotionPiece;

public record MoveRequest(
        Position from, Position to, PromotionPiece promotionPiece
) {}
