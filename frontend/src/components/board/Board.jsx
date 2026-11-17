
import { useChess } from "../../context/ChessProvider.jsx";
import Row from "./Row.jsx";
import "../../styles/Chessboard.css";

function Board() {
    const { board, currentPlayer } = useChess();

    if (!board || board.length === 0) {
        return <div>Loading...</div>;
    }

    const isBlackTurn = currentPlayer === "BLACK";

    const rowOrder = Array.from(
        { length: 8 },
        (_, index) => isBlackTurn ? index : 7 - index
    );

    return (
        <div className="board">
            {rowOrder.map((rowIndex) => (
                <Row
                    key={rowIndex}
                    row={board[rowIndex]}
                    rowIndex={rowIndex}
                    isBlackTurn={isBlackTurn}
                />
            ))}
        </div>
    );
}

export default Board;
