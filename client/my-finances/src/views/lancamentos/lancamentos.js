import React, { useEffect, useState } from 'react';
import Card from '../../components/card';
import FormGroup from '../../components/form-group';
import LancamentoTable from './lancamentoTable';
import lancamentoService from '../../services/lancamentoServices';

const Lancamentos = () => {

    const [searchText, setSearchText] = useState('');

    const [dadosDaTabela, setDadosDaTabela] = useState([]);

    useEffect(() => {
        fetchData();
    }, [])
    const fetchData = async () => {
        const response = await lancamentoService.get("api/v1/releases?page=0&size=50&direction=asc");
        if (response) {
            setDadosDaTabela(response.data._embedded.releasesDTOList);
        }
    }

    const filteredData = dadosDaTabela.filter(item => item.status.toLowerCase().includes(searchText.toLowerCase()));

    return (
        <>
            <Card title="Buscar Lançamentos:">
                <div className="bs-component">
                    <FormGroup htmlFor="search" >
                        <input
                            type="text"
                            id='search'
                            className="form-control"
                            placeholder="Pesquisar por situação..."
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
                        {filteredData.length > 0? <LancamentoTable data={filteredData} /> : <p>Não há lançamentos para serem exibidos!</p>}
                        
                    </div>
                </div>
            </div>
        </>
    );
};

export default Lancamentos;
