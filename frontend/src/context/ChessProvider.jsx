import {
    createContext,
    useContext,
    useState
} from "react";
import * as gameApi from "../api/gameApi.js";
import { useNavigate } from "react-router-dom";

const ChessContext = createContext()

export default function ChessProvider({ children }) {
    const [gameState, setGameState] = useState(null)
    const [selectedSquare, setSelectedSquare] = useState(null)
    const [board, setBoard] = useState([]);
    const [gameId, setGameId] = useState(null);
    const [gameStatus, setGameStatus] = useState(null);
    const [currentPlayer, setCurrentPlayer] = useState(null);
    const navigate = useNavigate();

    const startGame = async () => {
    try {
        const gameState = await gameApi.createGame();

        setBoard(gameState.board);
        setGameId(gameState.gameId);
        setGameStatus(gameState.status);
        setCurrentPlayer(gameState.currentPlayer);
        navigate("/board");
    } catch (error) {
        console.error("Unable to start game:", error);
    }
    };

    async function movePiece(from, to) {
        console.log(from, to)
        const move = {from, to};
        try {
            const moveResponse = await gameApi.makeMove(gameId, move);

            if (!moveResponse.valid) {
                console.log("Move is invalid");
                return;
            }

            setBoard(moveResponse.board);
            setGameStatus(moveResponse.status);
            setCurrentPlayer(moveResponse.currentPlayer);

        } catch (error) {
            console.error("Unable to request move:", error);
        }
    }

    function selectSquare(coordinate) {
        setSelectedSquare(coordinate)
    }

    return (
        <ChessContext.Provider value={{ gameState, movePiece, selectSquare, selectedSquare, board, gameId, gameStatus, currentPlayer, startGame}}>
            {children}
        </ChessContext.Provider>
    )
}

export function useChess() {
    return useContext(ChessContext)
}
