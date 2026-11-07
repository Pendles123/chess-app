package com.dto;

import com.chessgame.PlayerColour;
import com.chesspieces.PieceType;

public record PieceDTO(PieceType pieceType, PlayerColour player) {}
