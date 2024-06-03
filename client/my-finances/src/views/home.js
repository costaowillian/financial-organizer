import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Home = () => {

    const [saldo, setSaldo] = useState(0.00);

    return (
        <>
            <div className="jumbotron">
                <h1 className="display-3">Bem vindo!</h1>
                <p className="lead">Seu saldo para o mês atual é de
                    <strong>
                        {Intl.NumberFormat('pt-br', { style: 'currency', currency: 'brl' }).format(saldo)}
                    </strong>
                </p>
                <hr className="my-4" />
                <p>E essa é sua área administrativa</p>
                <p className="lead">
                    <Link className="btn btn-primary btn-lg" to="/login" role="button"><i class="fa fa-users"></i>Cadastrar Lançamento</Link>
                </p>
            </div>
        </>
    );
};

export default Home;
