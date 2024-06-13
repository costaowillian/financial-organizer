import React, { Suspense, lazy, useContext } from "react";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import NavBar from "../components/navbar";
import { Context } from "../context/authContex";

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
                    <Route path="/" element={
                        <CustomRoute>
                            <Home />
                        </CustomRoute>} />

                    <Route path="/login" element={<Login />} />

                    <Route path="/cadastrar" element={<Cadastrar />} />

                    <Route path="/lancamentos" element={
                        <CustomRoute>
                            <Lancamentos />
                        </CustomRoute>} />

                    <Route path="/cadastro-lancamentos/:id?" element={
                        <CustomRoute>
                            <CadastroLancamentos />
                        </CustomRoute>} />
                </Routes>
            </Suspense>
        </BrowserRouter>
    )
}

function CustomRoute({ children }) {
    const auth = useContext(Context);
    if(!auth.loading) {
        return auth.isAuthenticated && auth.isTokenValid ? <>{children}</> : <Navigate to="/login" />;
    }
}

export default Router;