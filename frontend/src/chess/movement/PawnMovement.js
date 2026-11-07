
export function PawnMovement(from, to) {

// com.chesspieces.Pawn advances White/Black
    if (Math.abs(to.y - from.y) === 1) {
        if(colour.equals(PlayerColour.WHITE)) {
            if (to.x === from.x && to.y - from.y === 1) {
                return true;
            }
        }
        if (colour.equals(PlayerColour.BLACK)) {
            if (to.x === from.x && from.y - to.y === 1) {
                return true;
            }
        }
    }

    // com.chesspieces.Pawn double advance White/Black
    if (Math.abs(to.y - from.y) === 2) {
        if (!hasMoved) {
            if (colour.equals(PlayerColour.WHITE)) {
                if (to.x === from.x && to.y - from.y === 2) {
                    return true;
                }
            }
            if (colour.equals(PlayerColour.BLACK)) {
                if (to.x === from.x && from.y - to.y === 2) {
                    return true;
                }
            }
        }
    }

    // com.chesspieces.Pawn captures White/Black
    if(Math.abs(to.x - from.x) === 1) {

        if (to.y - from.y === 1 && colour.equals(PlayerColour.WHITE)) {
            return true;
        }
        return from.y - to.y === 1 && colour.equals(PlayerColour.BLACK);
    }
    return false;
}