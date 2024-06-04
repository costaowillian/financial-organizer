import React, { useState } from 'react';
import Card from '../../components/card';
import FormGroup from '../../components/form-group';
import LancamentoTable from './lancamentoTable';

const Lancamentos = () => {

    const dadosDaTabela = [
        {
            id: 1,
            descricao: 'Salário',
            valor: 'R$ 4.200,00',
            tipo: 'Receita',
            data: '01/02/2019',
            situacao: 'Pendente'
        },
        {
            id: 2,
            descricao: 'Salário',
            valor: 'R$ 4.200,00',
            tipo: 'Despesas',
            data: '01/02/2019',
            situacao: 'Pendente'
        },
        {
            id: 3,
            descricao: 'Salário',
            valor: 'R$ 4.200,00',
            tipo: 'Receita',
            data: '01/02/2019',
            situacao: 'Pendente'
        },
    ];


    const [searchText, setSearchText] = useState('');

    const filteredData = dadosDaTabela.filter(item => item.tipo.toLowerCase().includes(searchText.toLowerCase()));

    return (
        <>
            <Card title="Buscar Lançamentos:">
                <div className="bs-component">
                    <FormGroup htmlFor="search" >
                        <input
                            type="text"
                            id='search'
                            className="form-control"
                            placeholder="Pesquisar por tipo..."
                            value={searchText}
                            onChange={e => setSearchText(e.target.value)}
                        />
                    </FormGroup>
                </div>
            </Card>
            <div className="row">
                <div className="col-lg-12">
                    <div className="page-header">
                        <h3 id="tables">Lançamentos:</h3>
                    </div>

                    <div className="bs-component">
                        <LancamentoTable  data={filteredData} />
                    </div>
                </div>
            </div>
        </>
    );
};

export default Lancamentos;
