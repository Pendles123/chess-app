import { PieceSymbols } from "../../chess/movement/PieceConstants.js";

export default function Piece({piece}) {

    const symbol = PieceSymbols[piece.player][piece.pieceType];

    return <span>{symbol}</span>
}