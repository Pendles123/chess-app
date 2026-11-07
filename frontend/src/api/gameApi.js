const API_BASE_URL = "http://localhost:8080/api/games";

/*
 * Start a new game.
 *
 * Spring Boot:
 * POST /api/games
 *
 * Returns:
 * GameStateResponse
 *
 * {
 *     board,
 *     gameId,
 *     status,
 *     currentPlayer
 * }
 */
export async function createGame() {

    const response = await fetch(API_BASE_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        }
    });

    if (!response.ok) {
        throw new Error(`Failed to create game: ${response.status}`);
    }

    return await response.json();
}


/*
 * Make a move.
 *
 * Spring Boot:
 * POST /api/games/{gameId}/move
 *
 * Returns:
 * MoveResponse
 *
 * {
 *     valid,
 *     board,
 *     status,
 *     currentPlayer
 * }
 */
export async function makeMove(gameId, moveRequest) {

    const response = await fetch(
        `${API_BASE_URL}/${gameId}/move`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(moveRequest)
        }
    );

    if (!response.ok) {
        throw new Error(`Server move request failed: ${response.status}`);
    }

    return await response.json();
}


/*
 * Agree to a draw.
 *
 * Spring Boot:
 * POST /api/games/{gameId}/draw
 *
 * Returns:
 * GameStateResponse
 */
export async function agreeDraw(gameId) {

    const response = await fetch(
        `${API_BASE_URL}/${gameId}/draw`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        }
    );

    if (!response.ok) {
        throw new Error(`Failed to agree to draw: ${response.status}`);
    }

    return await response.json();
}


/*
 * Resign the game.
 *
 * Spring Boot:
 * POST /api/games/{gameId}/resign
 *
 * Returns:
 * GameStateResponse
 */
export async function resignGame(gameId) {

    const response = await fetch(
        `${API_BASE_URL}/${gameId}/resign`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        }
    );

    if (!response.ok) {
        throw new Error(`Failed to resign game: ${response.status}`);
    }

    return await response.json();
}