import React, { useEffect, useState } from 'react';
import Card from '../../components/card';
import FormGroup from '../../components/form-group';
import LancamentoTable from './lancamentoTable';
import lancamentoService from '../../services/lancamentoServices';
import { mensagemError, mensagemSucesso } from '../../components/toastr';

import { ConfirmDialog, confirmDialog } from 'primereact/confirmdialog';
import { useNavigate } from 'react-router-dom';

const Lancamentos = () => {

    const navigate = useNavigate();

    const [searchText, setSearchText] = useState('');

    const [dadosDaTabela, setDadosDaTabela] = useState([]);

    useEffect(() => {
        fetchData();
    }, [dadosDaTabela]);

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

    const confirmarEfetivacao = (id) => {
        confirmDialog({
            message: 'Deseja efetivar este lançamento?',
            header: 'Efetivar lançamento',
            icon: 'pi pi-info-circle',
            defaultFocus: 'reject',
            acceptClassName: 'p-button-danger',
            accept: () => efetivar(id),
        });
    };

    const efetivar = async (id) => {
        try {
            await lancamentoService.put(`/api/v1/releases/${id}`, "EFETIVADO");
            dadosDaTabela.map(x => {
                if (x.id == id) {
                    x.status = "EFETIVADO"
                }
            });
            mensagemSucesso("Lançamento efetivado com sucesso!");
        }
        catch (e) {
            mensagemError("Por favor, tente novamente!");
        }
    }

    const confirmarCancelamento = (id) => {
        confirmDialog({
            message: 'Deseja cancelar este lançamento?',
            header: 'cancelar lançamento',
            icon: 'pi pi-info-circle',
            defaultFocus: 'reject',
            acceptClassName: 'p-button-danger',
            accept: () => cancelar(id),
        });
    };

    const cancelar = async (id) => {
        try {
            await lancamentoService.put(`/api/v1/releases/${id}`, "CANCELADO");
            dadosDaTabela.map(x => {
                if (x.id == id) {
                    x.status = "CANCELADO"
                }
            });
            mensagemSucesso("Lançamento cancelado com sucesso!");
        }
        catch (e) {
            mensagemError("Por favor, tente novamente!");
        }
    }

    const deletar = async (id) => {
        try {
            await lancamentoService.delete(`/api/v1/releases/${id}`);
            setDadosDaTabela(dadosDaTabela.filter(item => item.id !== id));
            mensagemSucesso("Lançamento Deletado com sucesso!");
        } catch (e) {
            if (e.response && e.response.status === 404) {
                mensagemError("Por favor, tente novamente!");
            } else {
                mensagemError("Ocorreu um erro inesperado!");
            }
        }
    }

    const editar = (id) => {
        navigate(`/cadastro-lancamentos/${id}`);
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
                        {filteredData.length > 0 ? <LancamentoTable data={filteredData}
                            deletarItem={confirmarDeletar} editarItem={editar}
                            efetivarItem={confirmarEfetivacao} cancelarItem={confirmarCancelamento} /> : <p>Não há lançamentos para serem exibidos!</p>}

                    </div>
                </div>
            </div>
            <ConfirmDialog />
        </>
    );
};

export default Lancamentos;
