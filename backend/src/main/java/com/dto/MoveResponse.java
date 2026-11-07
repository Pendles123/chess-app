package com.dto;

import com.chessgame.GameStatus;
import com.chessgame.PlayerColour;

public record MoveResponse(
        boolean valid,
        PieceDTO[][] board,
        GameStatus status,
        PlayerColour currentPlayer
) {}
