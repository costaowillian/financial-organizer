import React from "react";
import { Link } from "react-router-dom";

const NavBar = () => {

    return (
        <>
            <div className="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
                <div className="container">
                    <Link className="navbar-brand" to="/" style={{ fontSize: '20px', fontWeight: 'bolder' }}>Minhas Finanças</Link>
                    <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarResponsive">
                        <ul className="navbar-nav">
                            <li className="nav-item">
                                <Link className="nav-link" to="/login">Home</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/cadastrar">Lançamentos</Link>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </>
    )
}

export default NavBar;