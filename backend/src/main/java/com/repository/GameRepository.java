package com.repository;

import com.chessgame.Game;
import com.chessgame.GameStartUp;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class GameRepository {
    private final Map<Long, Game> games = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Game getGame(long gameId) {
        return games.get(gameId);
    }

    public long nextGame() {
       return idGenerator.getAndIncrement();
    }

    public void saveBoard(Game game) {
        games.put(game.getId(), game);
    }

    public long createNewGame() {
        long gameId = nextGame();
        Game game = new Game(gameId);
        GameStartUp buildTheBoard = new GameStartUp();
        buildTheBoard.go(game);
        games.put(gameId, game);
        return gameId;
    }
}
