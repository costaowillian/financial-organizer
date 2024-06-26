import React from 'react';
import { useNavigate } from 'react-router-dom';

const LancamentoTable = ({ data, deletarItem, editarItem, efetivarItem, cancelarItem }) => {

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
                        <th scope="col">Alterar Situação</th>
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
                                <button className="btn btn-primary"
                                disabled={item.status !== "PENDENTE"}
                                style={{ marginRight: '8px' }}
                                onClick={e=> efetivarItem(item.id)}>Efetivar</button>
                                <button className="btn btn-danger" onClick={e=> cancelarItem(item.id)}>Cancelar</button>
                            </td>
                            <td>
                                <button className="btn btn-primary" title='editar' style={{ marginRight: '8px' }} onClick={e => editarItem(item.id)}><i className='pi pi-pencil'></i></button>
                                <button onClick={e => deletarItem(item.id)}  title='excluir' className="btn btn-danger"><i className='pi pi-trash'></i></button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
};

export default LancamentoTable;
