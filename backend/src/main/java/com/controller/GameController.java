package com.controller;

import com.chessgame.Game;
import com.dto.GameStateResponse;
import com.dto.GameStatusUpdate;
import com.dto.MoveRequest;
import com.dto.MoveResponse;
import com.service.GameService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // This is the Start New Game Controller
    @PostMapping
    public GameStateResponse startGame() {
        return gameService.startNewGame();
    }

    // This is the Move Request Controller
    @PostMapping("/{gameId}/move")
    public MoveResponse makeMove(
            @PathVariable long gameId,
            @RequestBody MoveRequest request
    ) {
        return gameService.applyMove(gameId, request);
    }

    // This is the Resign Game Controller
    @PostMapping("/{gameId}/resign")
    public GameStateResponse resignGame(
            @PathVariable long gameId) {
        return gameService.playerResigned(gameId);
    }

    // This is the Draw Agreement Controller
    @PostMapping("/{gameId}/draw")
    public GameStateResponse drawAgreement(
            @PathVariable long gameId,
            @RequestBody GameStatusUpdate status)
    {
        return gameService.drawAgreementStatusChange(gameId);
    }
}
