import React from "react";
import Card from "../components/card";
import FormGroup from "../components/form-group";

class Cadastrar extends React.Component {

    state = {
        nome: '',
        email: '',
        senha: '',
        senhaConfirm: '',
    }

    enviar = () => {
        console.log(this.state);
    }

    render() {
        return (
            <>
                <div className="row">
                    <div className="col-md-6" style={{ position: "relative", left: '300px' }}>
                        <div className="bs-docs-section"></div>
                        <Card title="Cadastrar">
                            <div className="row">
                                <div className="col-lg-12">
                                    <div className="bs-component">
                                        <form onSubmit={() => this.enviar()}>
                                            <fieldset>
                                                <FormGroup label="Nome: *" htmlFor="nome" >
                                                    <input type="text"
                                                        value={this.state.nome}
                                                        onChange={e => this.setState({ nome: e.target.value })}
                                                        className="form-control" id="nome"
                                                        placeholder="Digite o seu nome" />
                                                </FormGroup>
                                                <FormGroup label="Email: *" htmlFor="email" >
                                                    <input type="email"
                                                        value={this.state.email}
                                                        onChange={e => this.setState({ email: e.target.value })}
                                                        className="form-control" id="email" aria-describedby="emailHelp"
                                                        placeholder="Digite o Email" />
                                                </FormGroup>
                                                <FormGroup label="Senha: *" htmlFor="senha" >
                                                    <input type="password"
                                                        value={this.state.senha}
                                                        onChange={e => this.setState({ senha: e.target.value })}
                                                        className="form-control" id="senha"
                                                        placeholder="Digite sua Senha" />
                                                </FormGroup>
                                                <FormGroup label="Confirmar senha: *" htmlFor="confirmar-senha" >
                                                    <input type="password"
                                                        value={this.state.senhaConfirm}
                                                        onChange={e => this.setState({ senhaConfirm: e.target.value })}
                                                        className="form-control" id="confirmar-senha"
                                                        placeholder="Confirme a Senha" />
                                                </FormGroup>

                                                <button type="submit" style={{ marginRight: '15px' }}
                                                    className="btn btn-success">Cadastrar</button>
                                                <button type="button"
                                                    className="btn btn-secondary ">Voltar</button>
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
}

export default Cadastrar;