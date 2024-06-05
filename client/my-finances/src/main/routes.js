import React, { Suspense, lazy } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import NavBar from "../components/navbar";

const Login = lazy(() => import('../views/login'));
const Cadastrar = lazy(() => import('../views/cadastrar'));
const Home = lazy(() => import('../views/home'));
const Lancamentos = lazy(() => import('../views/lancamentos/lancamentos'));
const CadastroLancamentos = lazy(() => import('../views/lancamentos/cadastroLancamento'));

function Router() {
    return (
        <BrowserRouter>
            <NavBar />
            <Suspense fallback={<div>Loading...</div>}>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/cadastrar" element={<Cadastrar />} />
                    <Route path="/lancamentos" element={<Lancamentos />} />
                    <Route path="/cadastro-lancamentos/" element={<CadastroLancamentos />} />
                    <Route path="/cadastro-lancamentos/:id" element={<CadastroLancamentos />} />
                </Routes>
            </Suspense>
        </BrowserRouter>

    )
}

export default Router;