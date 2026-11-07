import "../../styles/ChessBoardStyle.css";

import { useChess } from "../../context/ChessProvider.jsx";

import Piece from "./Piece.jsx";

function Square({square, rowIndex, colIndex, currentPlayer}) {

    const {selectedSquare, selectSquare, movePiece} = useChess();

    const coordinate = {
        rank: rowIndex,
        file: colIndex
    };

    const isSelected =
        selectedSquare?.rank === rowIndex &&
        selectedSquare?.file === colIndex;

    // This function is incomplete. The function will initiate the "selectedSquare" hook with ANY square that is selected...even empty or opponent squares
    function handleClick() {
        if (selectedSquare === null) {
            selectSquare(coordinate)
            return
        }

        // Cancelling a selectSquare (eg. deselect piece to select another) --- INCOMPLETE FUNCTION
        if (selectSquare !== null) {
            if (selectedSquare === coordinate || ) {

            }
            selectedSquare(null)
        }
            movePiece(selectedSquare, coordinate)

    }
        return (
            <div className={`square ${isSelected ? "selected" : ""}`} onClick={handleClick}>

                {square && (<Piece piece={square.piece}/>)}

            </div>
        )

}
export default Square
