import React, { useState } from "react";
import Card from '../components/card';
import FormGroup from "../components/form-group";

import { useNavigate } from "react-router-dom";
import api from '../services/api';

const Login = () => {

    const navigate = useNavigate([]);

    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');

    const [errorMessage, setErrorMessage] = useState('');

    const entrar = async (event) => {
        event.preventDefault();
        let credentials = JSON.stringify({
            "email": email,
            "password": senha
        });
        
        try {
            const response = await authService.login(credentials);
            if(response) {
                navigate("/");
            }
        } catch (e) {   
            if (e.response && e.response.data && e.response.data.message === 'Invalid email/password supplied') {
                setErrorMessage("Email ou senha inválidos, por favor tente novamente");
            }
            
            if(e.status == 403 || e.status == 500) {
                setErrorMessage("Falha ao entrar, por favor tente novamente!");
            }
        } 
    }

    return (
        <>
            <div className="row">
                <div className="col-md-6" style={{ position: "relative", left: '300px' }}>
                    <div className="bs-docs-section">
                        <Card title="Login">
                            <div className="row">
                                <div className="col-lg-12">
                                    <div className="bs-component">
                                        <form onSubmit={entrar}>
                                            <fieldset>
                                                <FormGroup label="Email: *" htmlFor="email" >
                                                    <input type="email"
                                                        value={email}
                                                        onChange={e => setEmail(e.target.value)}
                                                        className="form-control" id="email"
                                                        placeholder="Digite o Email" />
                                                </FormGroup>
                                                <FormGroup label="Senha: *" htmlFor="senha" >
                                                    <input type="password"
                                                        value={senha}
                                                        onChange={e => setSenha(e.target.value)}
                                                        className="form-control" id="senha"
                                                        placeholder="Password" />
                                                </FormGroup>

                                                <span style={{color: "#dc3545"}}>
                                                    {errorMessage ? errorMessage: ''}
                                                    <br/>
                                                </span>
                                                <button type="submit" style={{ marginRight: '15px' }}
                                                    className="btn btn-success">Entrar</button>
                                                <button type="button"
                                                    className="btn btn-secondary" onClick={() => navigate("/cadastrar")}>Cadastrar</button>
                                            </fieldset>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </Card>
                    </div>
                </div>
            </div>
        </>
    )

}

export default Login;