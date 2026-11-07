import { Link } from "react-router-dom";

export default function Menu() {
    return (
        <nav>
            <ul>
                <li><Link to="/board">Board</Link></li>
            </ul>
        </nav>
    );
}