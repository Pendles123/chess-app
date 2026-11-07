package com.dto;
import com.chessgame.GameStatus;
import com.chessgame.PlayerColour;

public record GameStateResponse(PieceDTO[][] board, long gameId, GameStatus status, PlayerColour currentPlayer) {}
