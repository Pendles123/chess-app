import { useChess } from "../context/ChessProvider.jsx";

export default function HomePage() {
    const { startGame } = useChess()
    return (

        <div className="home-page">
        <p>Welcome to Chess Hustler</p>
            <button onClick={() => {
                console.log("Play Chess clicked");
                startGame();
            }}>
                Play Chess
            </button>
        </div>
    )
}