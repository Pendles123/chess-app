import { BrowserRouter, Routes, Route } from "react-router-dom";

import ChessProvider from "./context/ChessProvider.jsx";

import MainLayout from "./components/layout/MainLayout";

import Home from "./pages/HomePage.jsx";

import BoardPage from "./pages/BoardPage.jsx";

export default function App() {

    return (

            <BrowserRouter>

                <ChessProvider>

                <Routes>

                    <Route path="/" element={<MainLayout />}>

                        <Route index element={<Home />} />

                        <Route path="board" element={<BoardPage />} />

                    </Route>

                </Routes>

                </ChessProvider>

            </BrowserRouter>

    );

}