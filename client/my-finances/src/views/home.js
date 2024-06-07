import React, { useContext, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import userService from '../services/userSerivces';
import { Context } from '../context/authContex';

const Home = () => {

    const auth = useContext(Context);

    const [saldo, setSaldo] = useState(0.00);

    const fetchSaldo = async () => {
        try {
            const result = await userService.get(`/api/v1/user/${auth.getUserId()}/balance`);
            setSaldo(result.data);
        } catch (e) {
            if(e.status == 404 || e.status == 500) {
                setSaldo(0.00);
            }
        }
    }

    useEffect(() => {
        fetchSaldo() ;
    }, []);

    return (
        <>
            <div className="jumbotron">
                <h1 className="display-3">Bem vindo!</h1>
                <p className="lead">Seu saldo para o mês atual é de {" "}
                    <strong>
                        {Intl.NumberFormat('pt-br', { style: 'currency', currency: 'brl' }).format(saldo)}
                    </strong>
                </p>
                <hr className="my-4" />
                <p>E essa é sua área administrativa</p>
                <p className="lead">
                    <Link className="btn btn-primary btn-lg" to="/cadastro-lancamentos" style={{ marginRight: '8px' }} role="button">Cadastrar Lançamento</Link>
                    <Link className="btn btn-primary btn-lg" to="/cadastro-lancamentos" role="button">Cadastrar usuário</Link>
                </p>
            </div>
        </>
    );
};

export default Home;
