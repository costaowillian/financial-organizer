import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Card from '../../components/card';
import FormGroup from '../../components/form-group';
import SelectMenu from '../../components/selectMenu';

const CadastroLancamento = () => {

    const navigate = useNavigate();

    const [descricao, setDescricao] = useState('');
    const [ano, setAno] = useState();
    const [mes, setMes] = useState();
    const [valor, setValor] = useState();
    const [status, setStatus] = useState('');

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

    const definiTipo = (selectedValue) => {
        setStatus(selectedValue);
    };

    const definiMes = (selectedValue) => {
        setMes(selectedValue);
    };

    return (
        <>
            <Card title="Cadastrar um novo lançamento">
                <div className="row">
                    <div className="col-lg-12">
                        <div className="bs-component">
                            <form >
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
                                                <SelectMenu options={opcoesMeses} onChange={definiMes} />
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
                                                <SelectMenu options={opcoesTipo} onChange={definiTipo} />
                                            </FormGroup>
                                        </div>
                                        <div className='col-md-4'>
                                            <FormGroup label="Status: *" htmlFor="status" >
                                            <input type="status"
                                                    value={status}
                                                    onChange={e => setValor(e.target.value)}
                                                    className="form-control" id="status" aria-describedby="emailHelp"
                                                    placeholder="Status" disabled="true" />
                                            </FormGroup>
                                        </div>
                                    </div>
                                    <button type="submit" style={{ marginRight: '15px' }}
                                        className="btn btn-success">Cadastrar</button>
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
