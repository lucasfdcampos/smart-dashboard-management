package br.com.dashboard.xmlpersistence;

import br.com.dashboard.model.*;
import br.com.dashboard.service.*;
import br.inf.portalfiscal.nfe.schema.nfe.TNfeProc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ExtractNFe {

    private TNfeProc xNFe;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private TransportadoraService transportadoraService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private NFeService nFeService;

    @Autowired
    private ContasReceberService contasReceberService;

    @Autowired
    private TipoPagamentoService tipoPagamentoService;

    public ExtractNFe() {
        //this.municipioDao = new MunicipioDao();
    }

    public TNfeProc getNFe() {
        return xNFe;
    }

    public void setNFe(TNfeProc xNFe) {
        this.xNFe = xNFe;
    }

    /**
     * Método para validar se a NFe é da própria empresa emissora.
     * Verifica o CNPJ do emissor e da Chave NFe.
     */
    private boolean validarEmissorNFe(String cnpj, String chave) {

        boolean valid = false;
        String cnpjEmissor = "33564743000143";

        if ((cnpjEmissor.equals(cnpj)) && (cnpjEmissor.equals(chave.substring(6, 20)))) {
            valid = true;
        }
        return valid;
    }

    public void persistirDados() {

        System.out.println("CHAVE: " + xNFe.getProtNFe().getInfProt().getChNFe());

        try {
            if (validarEmissorNFe(xNFe.getNFe().getInfNFe().getEmit().getCNPJ(),
                    xNFe.getProtNFe().getInfProt().getChNFe())) {

                // -- Municipio
                Municipio municipio = persistirMunicipioDestinatario();
                System.out.println(municipio);

                /*
                // -- Transportadora
                Transportadora transportadora = persistirTransportadora();
                System.out.println(transportadora);

                // -- Cliente
                Cliente cliente = persistirCliente(municipio);
                System.out.println(cliente);

                // -- Produtos
                List<NFeProdutos> nFeProdutos = persistirNFeProdutos();
                for (int i = 0; i < nFeProdutos.size(); i++) {
                    System.out.println(nFeProdutos.get(i));
                }

                // -- Duplicatas
                List<NFeDuplicatas> nFeDuplicatas = persistirNFeDuplicatas();
                for (int j = 0; j < nFeDuplicatas.size(); j++) {
                    System.out.println(nFeDuplicatas.get(j));
                }

                // -- NFe
                NFe nFe = persistirNFe(cliente, nFeProdutos, nFeDuplicatas, transportadora);
                System.out.println(nFe);

                // -- Tipo Pagamento
                TipoPagamento tipoPagamento = persistirTipoPagamento();
                System.out.println(tipoPagamento);

                // -- Contas a Receber
                persistirContasReceber(nFe, cliente, tipoPagamento);

                System.out.println("PERSISTIDO NFE: " + nFe.getNumero());
                */
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private Municipio persistirMunicipioDestinatario() {

        Municipio municipio = new Municipio();
        municipio.setCodigo(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getCMun().trim());
        municipio.setNome(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getXMun().toUpperCase().trim());
        municipio.setUf(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getUF().value());




        //this.municipioService.save(municipio);


        /*
        Municipio searchMunicipio = null;
        try {
            searchMunicipio = this.municipioService.findByCodigo(municipio.getCodigo());
        } catch (Exception e) { }

        if (searchMunicipio == null) {
            this.municipioService.save(municipio);
        } else {
            this.municipioService.update(searchMunicipio.getCodigo(), municipio);
        }
        */

        return municipio;
    }

    private Transportadora persistirTransportadora() {

        String endereco = xNFe.getNFe().getInfNFe().getTransp().getTransporta().getXEnder()
                .replace(xNFe.getNFe().getInfNFe().getTransp().getTransporta().getXMun().trim() +
                        xNFe.getNFe().getInfNFe().getTransp().getTransporta().getUF().value().trim(),
                        "").toUpperCase().trim();

        Transportadora transportadora = new Transportadora();
        transportadora.setNome(xNFe.getNFe().getInfNFe().getTransp().getTransporta().getXNome().toUpperCase().trim());
        transportadora.setCnpj(xNFe.getNFe().getInfNFe().getTransp().getTransporta().getCNPJ().trim());
        transportadora.setIe(xNFe.getNFe().getInfNFe().getTransp().getTransporta().getIE().trim());
        transportadora.setEndereco(endereco);
        transportadora.setMunicipio(xNFe.getNFe().getInfNFe().getTransp().getTransporta().getXMun().toUpperCase().trim());
        transportadora.setUf(xNFe.getNFe().getInfNFe().getTransp().getTransporta().getUF().value());

        /*
        Transportadora searchTransportadora = null;
        try {
            searchTransportadora = this.transportadoraService.findByCnpj(transportadora.getCnpj());
        } catch (Exception e) { }

        if (searchTransportadora == null) {
            this.transportadoraService.save(transportadora);
        } else {
            this.transportadoraService.update(searchTransportadora.getId(), transportadora);
        }
        */

        return transportadora;
    }

    private Cliente persistirCliente(Municipio municipio) {

        Cliente cliente = new Cliente();
        cliente.setNome(xNFe.getNFe().getInfNFe().getDest().getXNome().toUpperCase().trim());
        cliente.setCnpj(xNFe.getNFe().getInfNFe().getDest().getCNPJ().trim());
        cliente.setIe(xNFe.getNFe().getInfNFe().getDest().getIE().trim());
        cliente.setEndereco(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getXLgr().toUpperCase().trim());
        cliente.setNumero(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getNro().toUpperCase().trim());
        cliente.setBairro(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getXBairro().toUpperCase().trim());
        cliente.setMunicipio(municipio);
        cliente.setUf(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getUF().value());
        cliente.setCep(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getCEP().trim());
        cliente.setFone(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getFone().trim());
        cliente.setEmail(xNFe.getNFe().getInfNFe().getDest().getEmail().toUpperCase().trim());

        /*
        Cliente searchCliente = null;
        try {
            searchCliente = this.clienteService.findByCnpj(cliente.getCnpj());
        } catch (Exception e) { }

        if (searchCliente == null) {
            this.clienteService.save(cliente);
        } else {
            this.clienteService.update(searchCliente.getId(), cliente);
        }
        */

        return cliente;
    }

    private List<NFeProdutos> persistirNFeProdutos() {

        List<NFeProdutos> nFeProdutos = null;

        for (int index = 0; index < xNFe.getNFe().getInfNFe().getDet().size(); index++) {
            Produto produto = persistirProduto(index);
            NFeProdutos nFeProduto = persistirNFeProduto(index, produto);
            nFeProdutos.add(nFeProduto);
        }
        return nFeProdutos;
    }

    private Produto persistirProduto(int index) {

        Produto produto = new Produto();
        produto.setCodigoEmpresa(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getCProd().trim());
        produto.setDescricao(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getXProd().toUpperCase().trim());

        /*
        Produto searchProduto = null;
        try {
            searchProduto = this.produtoService.findByDescricao(produto.getDescricao());
        } catch (Exception e) { }

        if (searchProduto == null) {
            this.produtoService.save(produto);
        } else {
            this.produtoService.update(produto.getId(), produto);
        }
        */

        return produto;
    }

    private NFeProdutos persistirNFeProduto(int index, Produto produto) {

        NFeProdutos nFeProduto = new NFeProdutos();
        nFeProduto.setProduto(produto);
        nFeProduto.setNcm(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getNCM().trim());
        nFeProduto.setCfop(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getCFOP().trim());
        nFeProduto.setUnidade(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getUCom().toUpperCase().trim());
        nFeProduto.setQuantidade(converterToDouble(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getQCom()));
        nFeProduto.setValorUnitario(converterToDouble(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getVUnCom()));
        nFeProduto.setValorTotal(converterToDouble(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getVProd()));
        return nFeProduto;
    }

    private List<NFeDuplicatas> persistirNFeDuplicatas() throws ParseException {

        List<NFeDuplicatas> nFeDuplicatas = null;

        if ((xNFe.getNFe().getInfNFe().getCobr().getDup() == null) ||
                (xNFe.getNFe().getInfNFe().getCobr().getDup().size() == 0)) {

            NFeDuplicatas nFeDuplicata = new NFeDuplicatas();
            nFeDuplicata.setNumero("001");
            nFeDuplicata.setDataVencimento(converterToDate(xNFe.getNFe().getInfNFe().getIde().getDhEmi()));
            nFeDuplicata.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getPag().get(0).getDetPag().getVPag()));
            nFeDuplicatas.add(nFeDuplicata);

        } else {
            for (int index = 0; index < xNFe.getNFe().getInfNFe().getCobr().getDup().size(); index++) {
                NFeDuplicatas nFeDuplicata = new NFeDuplicatas();
                nFeDuplicata.setNumero(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getNDup().trim());
                nFeDuplicata.setDataVencimento(converterToDate(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getDVenc()));
                nFeDuplicata.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getVDup()));
                nFeDuplicatas.add(nFeDuplicata);
            }
        }
        return nFeDuplicatas;
    }

    private NFe persistirNFe(Cliente cliente, List<NFeProdutos> nFeProdutos, List<NFeDuplicatas> nFeDuplicatas,
                             Transportadora transportadora) throws ParseException {

        NFe nFe = new NFe();
        nFe.setChave(xNFe.getProtNFe().getInfProt().getChNFe().trim());
        nFe.setCodigo(xNFe.getNFe().getInfNFe().getIde().getCNF().trim());
        nFe.setNumero(xNFe.getNFe().getInfNFe().getIde().getNNF().trim());
        nFe.setTipo(xNFe.getNFe().getInfNFe().getIde().getTpNF().trim());
        nFe.setSerie(xNFe.getNFe().getInfNFe().getIde().getSerie().trim());
        nFe.setDataEmissao(converterToDate(xNFe.getNFe().getInfNFe().getIde().getDhEmi()));
        nFe.setNaturezaOperacao(xNFe.getNFe().getInfNFe().getIde().getNatOp().toUpperCase().trim());
        nFe.setFinalidadeEmissao(xNFe.getNFe().getInfNFe().getIde().getFinNFe().trim());
        nFe.setCliente(cliente);
        nFe.setNfeProdutos(nFeProdutos);
        nFe.setNfeDuplicatas(nFeDuplicatas);
        nFe.setValorProdutos(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVProd()));
        nFe.setValorDesconto(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVDesc()));
        nFe.setValorLiquido(converterToDouble(xNFe.getNFe().getInfNFe().getCobr().getFat().getVLiq()));
        nFe.setValorTotal(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF()));
        nFe.setTransportadora(transportadora);
        /*
        this.nFeService.save(nFe);
        */
        return nFe;
    }

    private TipoPagamento persistirTipoPagamento() {

        TipoPagamento tipoPagamento = null;

        String detPag = xNFe.getNFe().getInfNFe().getPag().get(0).getDetPag().getTPag().trim();

        // Modelo atual [sempre com valor 99-Outros]
        if (detPag.equals("99")) {

            if ((xNFe.getNFe().getInfNFe().getCobr().getDup() == null) ||
                    (xNFe.getNFe().getInfNFe().getCobr().getDup().size() == 0)) {

                TipoPagamento searchTipoPagamento = this.tipoPagamentoService.findByDescricao("DEPOSITO");

                if (searchTipoPagamento == null) {
                    tipoPagamento = new TipoPagamento();
                    tipoPagamento.setDescricao("DEPOSITO");
                    tipoPagamento.setStatus(StatusPagamento.VISTA);
                    /*
                    this.tipoPagamentoService.save(tipoPagamento);
                    */

                } else {
                    tipoPagamento = searchTipoPagamento;
                }

            } else {
                TipoPagamento searchTipoPagamento = this.tipoPagamentoService.findByDescricao("BOLETO");

                if (searchTipoPagamento == null) {
                    tipoPagamento = new TipoPagamento();
                    tipoPagamento.setDescricao("BOLETO");
                    tipoPagamento.setStatus(StatusPagamento.PRAZO);
                    /*
                    this.tipoPagamentoService.save(tipoPagamento);
                    */
                } else {
                    tipoPagamento = searchTipoPagamento;
                }
            }
        } else {

            String descricao;
            StatusPagamento statusPagamento = null;

            switch (detPag) {
                case "01":
                    descricao = "DINHEIRO";
                    statusPagamento = StatusPagamento.VISTA;
                    break;
                case "02":
                    descricao = "CHEQUE";
                    statusPagamento = StatusPagamento.PRAZO;
                    break;
                case "03":
                    descricao = "CARTAO DE CREDITO";
                    statusPagamento = StatusPagamento.PRAZO;
                    break;
                case "04":
                    descricao = "CARTAO DE DEBITO";
                    statusPagamento = StatusPagamento.VISTA;
                    break;
                case "05":
                    descricao = "CREDITO LOJA";
                    statusPagamento = StatusPagamento.PRAZO;
                    break;
                case "10":
                    descricao = "VALE ALIMENTACAO";
                    statusPagamento = StatusPagamento.VISTA;
                    break;
                case "11":
                    descricao = "VALE REFEICAO";
                    statusPagamento = StatusPagamento.VISTA;
                    break;
                case "12":
                    descricao = "VALE PRESENTE";
                    statusPagamento = StatusPagamento.VISTA;
                    break;
                case "13":
                    descricao = "VALE COMBUSTIVEL";
                    statusPagamento = StatusPagamento.VISTA;
                    break;
                case "15":
                    descricao = "BOLETO";
                    statusPagamento = StatusPagamento.PRAZO;
                    break;
                default:
                    descricao = "DEPOSITO";
                    statusPagamento = StatusPagamento.VISTA;
            }

            TipoPagamento searchTipoPagamento = this.tipoPagamentoService.findByDescricao(descricao);

            if (searchTipoPagamento == null) {
                tipoPagamento = new TipoPagamento();
                tipoPagamento.setDescricao(descricao);
                tipoPagamento.setStatus(statusPagamento);
                this.tipoPagamentoService.save(tipoPagamento);
            } else {
                tipoPagamento = searchTipoPagamento;
            }
        }
        return tipoPagamento;
    }

    private void persistirContasReceber(NFe nFe, Cliente cliente,
                                                 TipoPagamento tipoPagamento) throws ParseException {

        StatusConta statusConta = verificarStatusConta(tipoPagamento);

        if ((xNFe.getNFe().getInfNFe().getCobr().getDup() == null) ||
                (xNFe.getNFe().getInfNFe().getCobr().getDup().size() == 0)) {

            ContasReceber contasReceber = new ContasReceber();
            contasReceber.setDocumento(nFe.getNumero());
            contasReceber.setCliente(cliente);
            contasReceber.setDescricao("NFe: " + nFe.getNumero().trim() + " - Cliente: " + cliente.getNome().trim());
            contasReceber.setTipoPagamento(tipoPagamento);
            contasReceber.setDataVencimento(converterToDate(xNFe.getNFe().getInfNFe().getIde().getDhEmi()));
            contasReceber.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF()));
            contasReceber.setDataRecebimento(contasReceber.getDataVencimento());
            contasReceber.setValorRecebido(contasReceber.getValor());
            contasReceber.setStatus(statusConta);
            /*
            this.contasReceberService.save(contasReceber);
            */

        } else {
            for (int index = 0; index < xNFe.getNFe().getInfNFe().getCobr().getDup().size(); index++) {
                ContasReceber contasReceber = new ContasReceber();
                contasReceber.setDocumento(nFe.getNumero());
                contasReceber.setCliente(cliente);
                contasReceber.setDescricao("NFe: " + nFe.getNumero().trim() + " - Cliente: " + cliente.getNome().trim());
                contasReceber.setTipoPagamento(tipoPagamento);
                contasReceber.setDataVencimento(converterToDate(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getDVenc()));
                contasReceber.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getVDup()));
                contasReceber.setStatus(statusConta);
                /*
                this.contasReceberService.save(contasReceber);
                */
            }
        }
    }

    private StatusConta verificarStatusConta(TipoPagamento tipoPagamento) {

        StatusConta statusConta = null;
        StatusPagamento statusPagamento = null;
        try {
            statusPagamento = this.tipoPagamentoService.findById(tipoPagamento.getId()).getStatus();
        } catch (Exception e) { }

        if (statusPagamento == null) {
            statusConta = StatusConta.A_RECEBER;

        } else {
            if (statusPagamento.equals(StatusPagamento.VISTA)) {
                statusConta = StatusConta.RECEBIDO;
            } else {
                statusConta = StatusConta.A_RECEBER;
            }
        }
        return statusConta;
    }

    private Date converterToDate(String data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return simpleDateFormat.parse(data);
    }

    private Double converterToDouble(String valor) {
        return Double.parseDouble(valor);
    }
}