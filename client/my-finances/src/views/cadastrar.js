import React, { useState } from "react";
import Card from "../components/card";
import FormGroup from "../components/form-group";

import { useNavigate } from "react-router-dom";
import authService from "../services/authServices";
import { mensagemError } from "../components/toastr";

const Cadastrar = () => {

    const navigate = useNavigate([]);

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [senhaConfirm, setSenhaConfirm] = useState('');

    const valida = () => {
        if(nome.length == 0) {
            mensagemError("Nome não pode ser vazio");
            return false;
        }
        else if(email.length == 0 || !email.includes("@")) {
            mensagemError("Digite um e-mail válida");
            return false;
        }
        else if(senha.length == 0 || senha.length > 6) {
            mensagemError("Digite uma senha válida");
            return false;
        }
        else if(senhaConfirm.length == 0) {
            mensagemError("Campom de confirmação não pode ser vazio!");
            return;
        } else if(senha !== senhaConfirm) {
            mensagemError("A senha de confirmação deve ser a mesma do campo senha");
            return false;
        } 
        else {
        return true;

        }
    }

    const enviar = async (event) => {
        event.preventDefault();

        if(!valida()) {
            return;
        }

        let credentials = JSON.stringify({
            "name": nome,
            "email": email,
            "password": senha,
            "permissions": [ 1 ]
        });

        try {
            const response = await authService.cadastrar(credentials);
            if(response) {
                navigate("/login");
            }
        } catch (e) {   
            if (e.response && e.response.data && e.response.data.message === 'E-mail already registered!') {
                mensagemError("Já existe uma conta com esse E-mail ");
            }
            
            if(e.status == 403 || e.status == 500) {
                mensagemError("Falha ao entrar, por favor tente novamente!");
            }
        } 
    }

    return (
        <>
            <div className="row">
                <div className="col-md-6" style={{ position: "relative", left: '300px' }}>
                    <div className="bs-docs-section"></div>
                    <Card title="Cadastrar">
                        <div className="row">
                            <div className="col-lg-12">
                                <div className="bs-component">
                                    <form onSubmit={enviar}>
                                        <fieldset>
                                            <FormGroup label="Nome: *" htmlFor="nome" >
                                                <input type="text"
                                                    value={nome}
                                                    onChange={e => setNome(e.target.value)}
                                                    className="form-control" id="nome"
                                                    placeholder="Digite o seu nome" />
                                            </FormGroup>
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
                                                    placeholder="Digite sua Senha" />
                                            </FormGroup>
                                            <FormGroup label="Confirmar senha: *" htmlFor="confirmar-senha" >
                                                <input type="password"
                                                    value={senhaConfirm}
                                                    onChange={e => setSenhaConfirm(e.target.value)}
                                                    className="form-control" id="confirmar-senha"
                                                    placeholder="Confirme a Senha" />
                                            </FormGroup>

                                            <button type="submit" style={{ marginRight: '15px' }}
                                                className="btn btn-success">Cadastrar</button>
                                            <button type="button"
                                                className="btn btn-secondary" onClick={() => navigate("/login")}>Voltar</button>
                                        </fieldset>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </Card>
                </div>
            </div>
        </>
    )

}

export default Cadastrar;