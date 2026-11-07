import { useChess } from "../../context/ChessProvider.jsx"
import Row from "./Row.jsx"



function Board() {

    const { board } = useChess()

    if (!board || board.length === 0) {
        return <div>Loading...</div>
    }

    return (
        <div className="board">

        {board.map(
            (row, rowIndex) => (<Row key={rowIndex} row={row} rowIndex={rowIndex}/>)
        )}

    </div>

    )
}

export default Board;