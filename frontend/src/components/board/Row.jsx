
import Square from "./Square.jsx";

function Row({ row, rowIndex, isBlackTurn }) {

    const colOrder = Array.from(
        { length: 8 },
        (_, index) => isBlackTurn ? 7 - index : index
    );

    return (
        <div className="row">
            {colOrder.map((colIndex) => (
                <Square
                    key={`${rowIndex}-${colIndex}`}
                    square={row[colIndex]}
                    rowIndex={rowIndex}
                    colIndex={colIndex}
                />
            ))}
        </div>
    );
}

export default Row;
