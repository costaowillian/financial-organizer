import React, { Suspense, lazy } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import NavBar from "../components/navbar";

const Login = lazy(() => import('../views/login'));
const Cadastrar = lazy(() => import('../views/cadastrar'));
const Home = lazy(() => import('../views/home'));

function Router() {
    return (
        <BrowserRouter>
            <NavBar />
            <Suspense fallback={<div>Loading...</div>}>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/cadastrar" element={<Cadastrar />} />
                </Routes>
            </Suspense>
        </BrowserRouter>

    )
}

export default Router;