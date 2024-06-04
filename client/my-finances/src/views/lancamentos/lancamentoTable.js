import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const LancamentoTable = ({data}) => {

    const navigate = useNavigate();

    return (
        <>
            <table className="table table-hover">
                <thead>
                    <tr>
                        <th scope="col">Descrição</th>
                        <th scope="col">Valor</th>
                        <th scope="col">Tipo</th>
                        <th scope="col">Data</th>
                        <th scope="col">Situação</th>
                        <th scope="col">Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {data.map(item => (
                        <tr key={item.id}>
                            <td>{item.description}</td>
                            <td>{Intl.NumberFormat('pt-br', { style: 'currency', currency: 'brl' }).format(item.value)}</td>
                            <td>{item.type}</td>
                            <td>{Intl.DateTimeFormat('pt-BR').format(new Date(item.registration_date))}</td>
                            <td>{item.status}</td>
                            <td>
                                <Link className="btn btn-primary" to='/'>Editar</Link>
                                <Link className="btn btn-danger" to='/'>Deletar</Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
};

export default LancamentoTable;
