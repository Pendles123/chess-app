import Square from "./Square.jsx"

function Row({row, rowIndex}) {

    return (
        <div className="row">

            {row.map((square, colIndex) =>
                    (
                        <Square key={`${rowIndex}-${colIndex}`}
                                square={square}
                                isDark={(rowIndex + colIndex) % 2 === 1}
                                rowIndex={rowIndex}
                                colIndex={colIndex}/>
                    ))}
        </div>
            )
}

export default Row