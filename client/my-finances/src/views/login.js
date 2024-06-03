import React from "react"
import Card from '../components/card';
import FormGroup from "../components/form-group";

class Login extends React.Component {

    state = {
        email: "",
        senha: "",
    }

    entrar = () => {
        console.log("email: " + this.state.email);
        console.log("senha: " + this.state.senha);
    }

    render() {
        return (
            <>
                <div className="container">
                    <div className="row">
                        <div className="col-md-6" style={{ position: "relative", left: '300px' }}>
                            <div className="bs-docs-section">
                                <Card title="Login">
                                    <div className="row">
                                        <div className="col-lg-12">
                                            <div className="bs-component">
                                                <form onSubmit={() => this.entrar()}>
                                                    <fieldset>
                                                        <FormGroup label="Email: *" htmlFor="email" >
                                                            <input type="email" 
                                                                value={this.state.email}
                                                                onChange={e => this.setState({email: e.target.value})}
                                                                className="form-control" id="email" aria-describedby="emailHelp"
                                                                placeholder="Digite o Email" />
                                                        </FormGroup>
                                                        <FormGroup label="Senha: *" htmlFor="senha" >
                                                            <input type="password" 
                                                                value={this.state.senha}
                                                                onChange={e => this.setState({senha: e.target.value})}
                                                                className="form-control" id="senha"
                                                                placeholder="Password" />
                                                        </FormGroup>
                                                        <button type="submit" style={{ marginRight: '15px' }}
                                                            className="btn btn-success">Entrar</button>
                                                        <button type="button"
                                                            className="btn btn-secondary ">Cadastrar</button>
                                                    </fieldset>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </Card>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        )
    }
}

export default Login;