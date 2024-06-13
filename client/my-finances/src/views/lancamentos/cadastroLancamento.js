import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Card from '../../components/card';
import FormGroup from '../../components/form-group';
import SelectMenu from '../../components/selectMenu';
import lancamentoService from '../../services/lancamentoServices';
import { mensagemError, mensagemSucesso } from "../../components/toastr";

const CadastroLancamento = () => {

    const navigate = useNavigate();

    const [descricao, setDescricao] = useState('');
    const [ano, setAno] = useState();
    const [mes, setMes] = useState();
    const [valor, setValor] = useState();
    const [tipo, setTipo] = useState('');
    const [status, setStatus] = useState('');

    const { id } = useParams();

    const opcoesTipo = [
        { label: 'Receita', value: 'RECEITAS' },
        { label: 'Despesa', value: 'DESPESAS' },
    ];

    const opcoesMeses = [
        { label: 'Janeiro', value: '1' },
        { label: 'Fevereiro', value: '2' },
        { label: 'Março', value: '3' },
        { label: 'Abril', value: '4' },
        { label: 'Maio', value: '5' },
        { label: 'Junho', value: '6' },
        { label: 'Julho', value: '7' },
        { label: 'Agosto', value: '8' },
        { label: 'Setembro', value: '9' },
        { label: 'Outubro', value: '10' },
        { label: 'Novembro', value: '11' },
        { label: 'Dezembro', value: '12' },
    ];

    useEffect(() => {
        if (id) {
            carregarLancamento();
        }
    }, [id])

    const carregarLancamento = async () => {

        try {
            const result = await lancamentoService.get(`/api/v1/releases/${id}`);

            if (result.data) {
                setAno(result.data.year);
                setDescricao(result.data.description);
                setValor(result.data.value);
                setStatus(result.data.status);
                setTipo(result.data.type)
                setMes(result.data.month)
            }
        } catch (e) {
            mensagemError("Erro ao carregar lançamento.");
        }
    }

    const defineTipo = (selectedValue) => {
        setTipo(selectedValue);
    };

    const defineMes = (selectedValue) => {
        setMes(selectedValue);
    };

    const valida = () => {
        if (descricao.length == 0) {
            mensagemError("Descrição não pode ser vazio");
            return false;
        }
        else if (tipo == null) {
            mensagemError("Selecione um tipo");
            return false;
        }
        else if (valor == null) {
            mensagemError("Valor não pode ser vazio");
            return false;
        }
        else if (mes == null) {
            mensagemError("Selecione um mês!");
            return;
        } else if (ano == null) {
            mensagemError("Selecione um ano");
            return false;
        }
        else {
            return true;

        }
    }

    const editar= async (event) => {
        event.preventDefault();

        if (!valida()) {
            return;
        }

        let data = JSON.stringify({
            "id": id,
            "description": descricao,
            "value": valor,
            "type": tipo,
            "year": ano,
            "month": mes
        });

        try {
            const response = await lancamentoService.patch("/api/v1/releases", data);
            if (response) {
                mensagemSucesso("Lançamento editado com sucesso!")
                navigate("/lancamentos");
            }
        } catch (e) {
            if (e.response || e.status == 500) {
                mensagemError("Falha ao editar, por favor tente novamente!");
            }
        }
    }

    const cadastrar = async (event) => {
        event.preventDefault();
        if (!valida()) {
            return;
        }

        const userData = JSON.parse(localStorage.getItem("user_data"));

        let data = JSON.stringify({
            "description": descricao,
            "value": valor,
            "type": tipo,
            "user_id": userData.userId,
            "registration_date": new Date(),
            "year": ano,
            "month": mes
        });

        try {
            const response = await lancamentoService.post("/api/v1/releases", data);
            if (response) {
                mensagemSucesso("Lançamento cadastrado com sucesso!")
                navigate("/lancamentos");
            }
        } catch (e) {
            if (e.response || e.status == 500) {
                mensagemError("Falha ao cadastrar, por favor tente novamente!");
            }
        }
    }

    return (
        <>
            <Card title={id ? "Editar Lançamento" : "Cadastrar um novo lançamento"}>
                <div className="row">
                    <div className="col-lg-12">
                        <div className="bs-component">
                            <form onSubmit={id ? editar : cadastrar }>
                                <fieldset>
                                    <FormGroup label="Descrição: *" htmlFor="descricao" >
                                        <input type="text"
                                            value={descricao}
                                            onChange={e => setDescricao(e.target.value)}
                                            className="form-control" id="descricao"
                                            placeholder="Digite a descrição" />
                                    </FormGroup>
                                    <div className='row'>
                                        <div className='col-md-6'>
                                            <FormGroup label="Ano: *" htmlFor="ano" >
                                                <input type="number"
                                                    value={ano}
                                                    onChange={e => setAno(e.target.value)}
                                                    className="form-control" id="ano" aria-describedby="emailHelp"
                                                    placeholder="Digite o ano" />
                                            </FormGroup>
                                        </div>
                                        <div className='col-md-6'>
                                            <FormGroup label="Mês: *" htmlFor="mes" >
                                                <SelectMenu options={opcoesMeses} value={mes} onChange={defineMes} />
                                            </FormGroup>
                                        </div>
                                    </div>
                                    <div className='row'>
                                        <div className='col-md-4'>
                                            <FormGroup label="Valor: *" htmlFor="valor" >
                                                <input type="number"
                                                    value={valor}
                                                    onChange={e => setValor(e.target.value)}
                                                    className="form-control" id="valor" aria-describedby="emailHelp"
                                                    placeholder="Digite o valor" />
                                            </FormGroup>
                                        </div>
                                        <div className='col-md-4'>
                                            <FormGroup label="Tipo: *" htmlFor="tipo" >
                                                <SelectMenu options={opcoesTipo} value={tipo} onChange={defineTipo} />
                                            </FormGroup>
                                        </div>
                                        <div className='col-md-4'>
                                            <FormGroup label="Status: *" htmlFor="status" >
                                                <input type="status"
                                                    value={status}
                                                    onChange={e => setValor(e.target.value)}
                                                    className="form-control" id="status" aria-describedby="emailHelp"
                                                    placeholder="Status" disabled />
                                            </FormGroup>
                                        </div>
                                    </div>
                                    <button type="submit" style={{ marginRight: '15px' }}
                                        className="btn btn-success">{id ? "Salvar" : "Cadastrar"}</button>
                                    <button type="button"
                                        className="btn btn-secondary" onClick={() => navigate("/")}>Voltar</button>
                                </fieldset>
                            </form>
                        </div>
                    </div>
                </div>
            </Card>
        </>
    );
};

export default CadastroLancamento;
