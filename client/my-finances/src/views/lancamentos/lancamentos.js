import React, { useEffect, useState, useRef } from 'react';
import Card from '../../components/card';
import FormGroup from '../../components/form-group';
import LancamentoTable from './lancamentoTable';
import lancamentoService from '../../services/lancamentoServices';
import { mensagemError, mensagemSucesso } from '../../components/toastr';

import { ConfirmDialog, confirmDialog } from 'primereact/confirmdialog';

const Lancamentos = () => {

    const [searchText, setSearchText] = useState('');

    const [dadosDaTabela, setDadosDaTabela] = useState([]);

    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        const response = await lancamentoService.get("api/v1/releases?page=0&size=50&direction=asc");
        if (response.data._embedded) {
            setDadosDaTabela(response.data._embedded.releasesDTOList);
        }
    }

    const confirmarDeletar = (id) => {
        confirmDialog({
            message: 'Deseja deletar o lançamento?',
            header: 'Deletar lançamento',
            icon: 'pi pi-info-circle',
            defaultFocus: 'reject',
            acceptClassName: 'p-button-danger',
            accept: () => deletar(id),
        });
    };

    const deletar = async (id) => {
        try {
            await lancamentoService.delete(`/api/v1/releases/${id}`);
            setDadosDaTabela(dadosDaTabela.filter(item => item.id !== id));
            mensagemSucesso("Lançamento Deletado com sucesso!");
        } catch (e) {
            console.log(e)
            if (e.response && e.response.status === 404) {
                mensagemError("Por favor, tente novamente!");
            } else {
                mensagemError("Ocorreu um erro inesperado!");
            }
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
                        {filteredData.length > 0 ? <LancamentoTable data={filteredData} deletarItem={confirmarDeletar} /> : <p>Não há lançamentos para serem exibidos!</p>}

                    </div>
                </div>
            </div>
            <ConfirmDialog />
        </>
    );
};

export default Lancamentos;
