import React, { useState } from "react"
import Card from '../components/card';
import FormGroup from "../components/form-group";

import { useNavigate } from "react-router-dom";

const Login = () => {

    const navigate = useNavigate([]);

    const [email, setEmail] = useState();
    const [senha, setSenha] = useState();

    const entrar = (event) => {
        event.preventDefault();
        console.log("email: " + email);
        console.log("senha: " + senha);
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
                                                        className="form-control" id="email" aria-describedby="emailHelp"
                                                        placeholder="Digite o Email" />
                                                </FormGroup>
                                                <FormGroup label="Senha: *" htmlFor="senha" >
                                                    <input type="password"
                                                        value={senha}
                                                        onChange={e => setSenha(e.target.value)}
                                                        className="form-control" id="senha"
                                                        placeholder="Password" />
                                                </FormGroup>
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