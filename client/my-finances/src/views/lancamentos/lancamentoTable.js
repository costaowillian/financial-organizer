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
                            <td>{item.descricao}</td>
                            <td>{item.valor}</td>
                            <td>{item.tipo}</td>
                            <td>{item.data}</td>
                            <td>{item.situacao}</td>
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
