import { Outlet } from "react-router-dom";
import Menu from "./Menu";

export default function MainLayout() {

    return (
        <div className="layout">

            <aside className="sidebar">
                <Menu />
            </aside>

            <main className="content">
                <Outlet />
            </main>

        </div>
    );

}