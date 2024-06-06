import React, { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Context } from "../context/authContex";

const NavBar = () => {

    const navigate = useNavigate();
    const auth = useContext(Context);

    const sair = () => {
        auth.logout();
        navigate("/login");
    }

    return (
        <>
            <div className="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
                <div className="container">
                    <Link className="navbar-brand" to="/" style={{ fontSize: '20px', fontWeight: 'bolder' }}>Minhas Finanças</Link>
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    {auth.isAuthenticated ? 
                    <div className="collapse navbar-collapse" id="navbarResponsive">
                    <ul className="navbar-nav">
                        <li className="nav-item">
                            <Link className="nav-link" to="/">Home</Link>
                        </li>
                        <li className="nav-item">
                            <Link className="nav-link" to="/lancamentos">Lançamentos</Link>
                        </li>
                        <li className="nav-item">
                            <button className="nav-link" onClick={sair}>sair</button>
                        </li>
                    </ul>
                </div> : ""}
                    
                </div>
            </div>
        </>
    )
}

export default NavBar;