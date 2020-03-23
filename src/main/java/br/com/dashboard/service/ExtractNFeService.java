package br.com.dashboard.service;

import br.com.dashboard.model.*;
import br.inf.portalfiscal.nfe.schema.nfe.TNfeProc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Transactional
public class ExtractNFeService {

    private static final String directory = "C:\\xml";

    private TNfeProc xNFe;

    private static final String cnpjEmissor = "33564743000143";

    @Autowired
    private NFeService nFeService;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private TransportadoraService transportadoraService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private TipoPagamentoService tipoPagamentoService;

    @Autowired
    private ContasReceberService contasReceberService;

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private NFeEntradaService nFeEntradaService;

    @Autowired
    private ContasPagarService contasPagarService;

    public ExtractNFeService() {
    }

    public String sweepDirectory() throws Exception {
        File dir = new File(directory);

        // Unzip files
        for (File arquivo : dir.listFiles()) {
            String mimeType = Files.probeContentType(arquivo.toPath());

            if (mimeType.equals("application/x-zip-compressed")) {
                unzip(arquivo.getAbsolutePath());
                arquivo.delete();
            }
        }

        // Xml files
        for (File arquivo : dir.listFiles()) {
            String mimeType = Files.probeContentType(arquivo.toPath());

            if (mimeType.equals("text/xml")) {
                readXml(arquivo.getAbsolutePath());
                arquivo.delete();
            }
        }
        return "OK";
    }

    private void unzip(String zipFilePath) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry = zipInputStream.getNextEntry();

        // iterates entries (xmls)
        while (zipEntry != null) {
            String filePath = ExtractNFeService.directory + File.separator + zipEntry.getName();

            if (!zipEntry.isDirectory()) {
                extractFile(zipInputStream, filePath);
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    private void extractFile(ZipInputStream zipInputStream, String filePath) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipInputStream.read(bytesIn)) != -1) {
            bufferedOutputStream.write(bytesIn, 0, read);
        }
        bufferedOutputStream.close();
    }

    private void readXml(String xmlFilePath) throws Exception {
        String xml = extractXml(xmlFilePath);
        xNFe = getNfeProc(xml);

        if (xNFe != null) {

            if (validarNFe()) {
                System.out.println("#\nCHAVE: " + xNFe.getProtNFe().getInfProt().getChNFe().trim());
                persistirDados();
            }
        }
    }

    private String extractXml(String xmlFile) throws IOException {
        String linha = "";
        StringBuilder xml = new StringBuilder();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                new FileInputStream(xmlFile)));

        while ((linha = in.readLine()) != null) {
            xml.append(linha);
        }
        in.close();

        return xml.toString();
    }

    @SuppressWarnings("unchecked")
    private TNfeProc getNfeProc(String xml) throws Exception {
        try {
            JAXBContext context = JAXBContext.newInstance(TNfeProc.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), TNfeProc.class).getValue();

        } catch (JAXBException e) {
            throw new Exception(e.toString());
        }
    }

    /**
     * Método para validar se a NFe é modelo 55.
     */
    private boolean validarNFe() {
        return (xNFe.getNFe().getInfNFe().getIde().getMod().trim().equals("55"));
    }

    /**
     * Método para validar se a NFe é da própria empresa emissora ou para a própria empresa.
     * Verifica o CNPJ do emissor/destinatário.
     */
    private boolean validarEmissorNFe(String cnpj) {
        boolean valid = false;

        if (cnpj.equals(ExtractNFeService.cnpjEmissor)) {
            valid = true;
        }
        return valid;
    }

    private void persistirDados() throws ParseException {

        // NFe saida
        if (validarEmissorNFe(xNFe.getNFe().getInfNFe().getEmit().getCNPJ().trim())) {

            NFe nFe = null;
            try {
                nFe = this.nFeService.findByChave(xNFe.getProtNFe().getInfProt().getChNFe().trim());
            } catch (Exception e) { }

            if (nFe == null || nFe.getChave().isEmpty()) {

                // -- Municipio
                Municipio municipio = persistirMunicipioDestinatario();
                System.out.println(municipio);

                // - Transportadora
                Transportadora transportadora = persistirTransportadora();
                System.out.println(transportadora);

                // -- Cliente
                Cliente cliente = persistirCliente(municipio);
                System.out.println(cliente);

                // -- Produtos
                List<NFeProdutos> nFeProdutos = persistirNFeProdutos();
                for (NFeProdutos nFeProduto : nFeProdutos) {
                    System.out.println(nFeProduto);
                }

                // -- Duplicatas
                List<NFeDuplicatas> nFeDuplicatas = persistirNFeDuplicatas();
                for (NFeDuplicatas nFeDuplicata : nFeDuplicatas) {
                    System.out.println(nFeDuplicata);
                }

                // -- NFe
                nFe = persistirNFe(cliente, transportadora, nFeProdutos, nFeDuplicatas);
                System.out.println(nFe);

                // -- Tipo Pagamento
                TipoPagamento tipoPagamento = persistirTipoPagamento();
                System.out.println(tipoPagamento);

                // -- Contas a Receber
                persistirContasReceber(nFe, cliente, tipoPagamento);

                System.out.println("\nPERSISTIDO NFE SAIDA: " + nFe.getNumero());
            }

            // NFe entrada
        } else if(validarEmissorNFe(xNFe.getNFe().getInfNFe().getDest().getCNPJ().trim())) {

            NFeEntrada nFeEntrada = null;
            try {
                nFeEntrada = this.nFeEntradaService.findByChave(xNFe.getProtNFe().getInfProt().getChNFe().trim());
            } catch (Exception e) { }

            if (nFeEntrada == null || nFeEntrada.getChave().isEmpty()) {

                // -- Municipio
                Municipio municipio = persistirMunicipioEmitente();
                System.out.println(municipio);

                // - Transportadora
                Transportadora transportadora = persistirTransportadora();
                System.out.println(transportadora);

                // -- Fornecedor
                Fornecedor fornecedor = persistirFornecedor(municipio);
                System.out.println(fornecedor);

                // -- Produtos
                List<NFeEntradaProdutos> nFeEntradaProdutos = persistirNFeEntradaProdutos();
                for (NFeEntradaProdutos nFeEntradaProduto : nFeEntradaProdutos) {
                    System.out.println(nFeEntradaProduto);
                }

                // -- Duplicatas
                List<NFeEntradaDuplicatas> nFeEntradaDuplicatas = persistirNFeEntradaDuplicatas();
                for (NFeEntradaDuplicatas nFeEntradaDuplicata : nFeEntradaDuplicatas) {
                    System.out.println(nFeEntradaDuplicata);
                }

                // -- NFe Entrada
                nFeEntrada = persistirNFeEntrada(fornecedor, transportadora, nFeEntradaProdutos, nFeEntradaDuplicatas);
                System.out.println(nFeEntrada);

                // -- Tipo Pagamento
                TipoPagamento tipoPagamento = persistirTipoPagamento();
                System.out.println(tipoPagamento);

                // -- Contas a Pagar
                persistirContasPagar(nFeEntrada, fornecedor, tipoPagamento);

                System.out.println("\nPERSISTIDO NFE ENTRADA: " + nFeEntrada.getNumero());
            }
        }
    }

    private Municipio persistirMunicipioDestinatario() {
        Municipio municipio = new Municipio();
        municipio.setCodigo(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getCMun().trim());
        municipio.setNome(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getXMun().toUpperCase().trim());
        municipio.setUf(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getUF().value());

        Municipio searchMunicipio = null;
        try {
            searchMunicipio = this.municipioService.findByCodigo(municipio.getCodigo());
        } catch (Exception e) { }

        if (searchMunicipio == null) {
            this.municipioService.save(municipio);
        } else {
            this.municipioService.update(searchMunicipio.getCodigo(), municipio);
        }
        return municipio;
    }

    private Municipio persistirMunicipioEmitente() {
        Municipio municipio = new Municipio();
        municipio.setCodigo(xNFe.getNFe().getInfNFe().getEmit().getEnderEmit().getCMun().trim());
        municipio.setNome(xNFe.getNFe().getInfNFe().getEmit().getEnderEmit().getXMun().toUpperCase().trim());
        municipio.setUf(xNFe.getNFe().getInfNFe().getEmit().getEnderEmit().getUF().value());

        Municipio searchMunicipio = null;
        try {
            searchMunicipio = this.municipioService.findByCodigo(municipio.getCodigo());
        } catch (Exception e) { }

        if (searchMunicipio == null) {
            this.municipioService.save(municipio);
        } else {
            this.municipioService.update(searchMunicipio.getCodigo(), municipio);
        }
        return municipio;
    }

    private Transportadora persistirTransportadora() {

        // 9 - Sem Ocorrencia de Transporte
        if (xNFe.getNFe().getInfNFe().getTransp().getModFrete().equals("9")) {
            return null;
        }

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

        Transportadora searchTransportadora = null;
        try {
            searchTransportadora = this.transportadoraService.findByCnpj(transportadora.getCnpj());
        } catch (Exception e) { }

        if (searchTransportadora == null) {
            this.transportadoraService.save(transportadora);
        } else {
            this.transportadoraService.update(searchTransportadora.getId(), transportadora);
        }
        return transportadora;
    }

    private Cliente persistirCliente(Municipio municipio) {
        Municipio searchMunicipio = null;
        try {
            searchMunicipio = this.municipioService.findByCodigo(municipio.getCodigo());
        } catch (Exception e) { }

        String cnpj = "";
        if (xNFe.getNFe().getInfNFe().getDest().getCNPJ() == null) {
            cnpj = xNFe.getNFe().getInfNFe().getDest().getCPF().trim();
        } else {
            cnpj = xNFe.getNFe().getInfNFe().getDest().getCNPJ().trim();
        }

        String ie = "";
        if (xNFe.getNFe().getInfNFe().getDest().getIE() != null) {
            ie = xNFe.getNFe().getInfNFe().getDest().getIE().trim();
        }

        Cliente cliente = new Cliente();
        cliente.setNome(xNFe.getNFe().getInfNFe().getDest().getXNome().toUpperCase().trim());
        cliente.setCnpj(cnpj.trim());
        cliente.setIe(ie.trim());
        cliente.setEndereco(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getXLgr().toUpperCase().trim());
        cliente.setNumero(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getNro().toUpperCase().trim());
        cliente.setBairro(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getXBairro().toUpperCase().trim());
        cliente.setMunicipio(searchMunicipio);
        cliente.setUf(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getUF().value());
        cliente.setCep(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getCEP().trim());
        cliente.setFone(xNFe.getNFe().getInfNFe().getDest().getEnderDest().getFone().trim());
        cliente.setEmail(xNFe.getNFe().getInfNFe().getDest().getEmail().toUpperCase().trim());

        Cliente searchCliente = null;
        try {
            searchCliente = this.clienteService.findByCnpj(cliente.getCnpj());
        } catch (Exception e) { }

        if (searchCliente == null) {
            this.clienteService.save(cliente);
        } else {
            this.clienteService.update(searchCliente.getId(), cliente);
        }
        return cliente;
    }

    private Fornecedor persistirFornecedor(Municipio municipio) {
        Municipio searchMunicipio = null;
        try {
            searchMunicipio = this.municipioService.findByCodigo(municipio.getCodigo());
        } catch (Exception e) { }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(xNFe.getNFe().getInfNFe().getEmit().getXNome().toUpperCase().trim());
        fornecedor.setCnpj(xNFe.getNFe().getInfNFe().getEmit().getCNPJ().trim());
        fornecedor.setIe(xNFe.getNFe().getInfNFe().getEmit().getIE().trim());
        fornecedor.setEndereco(xNFe.getNFe().getInfNFe().getEmit().getEnderEmit().getXLgr().toUpperCase().trim());
        fornecedor.setNumero(xNFe.getNFe().getInfNFe().getEmit().getEnderEmit().getNro().toUpperCase().trim());
        fornecedor.setBairro(xNFe.getNFe().getInfNFe().getEmit().getEnderEmit().getXBairro().toUpperCase().trim());
        fornecedor.setMunicipio(searchMunicipio);
        fornecedor.setUf(xNFe.getNFe().getInfNFe().getEmit().getEnderEmit().getUF().value());
        fornecedor.setCep(xNFe.getNFe().getInfNFe().getEmit().getEnderEmit().getCEP().trim());
        fornecedor.setFone(xNFe.getNFe().getInfNFe().getEmit().getEnderEmit().getFone().trim());

        Fornecedor searchFornecedor = null;
        try {
            searchFornecedor = this.fornecedorService.findByCnpj(fornecedor.getCnpj());
        } catch (Exception e) { }

        if (searchFornecedor == null) {
            this.fornecedorService.save(fornecedor);
        } else {
            this.fornecedorService.update(searchFornecedor.getId(), fornecedor);
        }
        return fornecedor;
    }

    private List<NFeProdutos> persistirNFeProdutos() {
        List<NFeProdutos> nFeProdutos = new ArrayList<>();

        for (int index = 0; index < xNFe.getNFe().getInfNFe().getDet().size(); index++) {
            Produto produto = persistirProduto(index);
            System.out.println(produto);

            NFeProdutos nFeProduto = persistirNFeProduto(index, produto);
            nFeProdutos.add(nFeProduto);
        }
        return nFeProdutos;
    }

    private Produto persistirProduto(int index) {
        Produto produto = new Produto();
        produto.setCodigoEmpresa(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getCProd().trim());
        produto.setDescricao(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getXProd().toUpperCase().trim());

        Produto searchProduto = null;
        try {
            searchProduto = this.produtoService.findByDescricao(produto.getDescricao());
        } catch (Exception e) { }

        if (searchProduto == null) {
            this.produtoService.save(produto);
        } else {
            this.produtoService.update(searchProduto.getId(), produto);
        }
        return produto;
    }

    private NFeProdutos persistirNFeProduto(int index, Produto produto) {
        Produto searchProduto = null;
        try {
            searchProduto = this.produtoService.findById(produto.getId());
        } catch (Exception e) { }

        NFeProdutos nFeProduto = new NFeProdutos();
        nFeProduto.setProduto(searchProduto);
        nFeProduto.setNcm(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getNCM().trim());
        nFeProduto.setCfop(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getCFOP().trim());
        nFeProduto.setUnidade(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getUCom().toUpperCase().trim());
        nFeProduto.setQuantidade(converterToDouble(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getQCom()));
        nFeProduto.setValorUnitario(converterToDouble(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getVUnCom()));
        nFeProduto.setValorTotal(converterToDouble(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getVProd()));
        return nFeProduto;
    }

    private List<NFeDuplicatas> persistirNFeDuplicatas() throws ParseException {
        List<NFeDuplicatas> nFeDuplicatas = new ArrayList<>();

        if (xNFe.getNFe().getInfNFe().getCobr() == null) {
            NFeDuplicatas nFeDuplicata = new NFeDuplicatas();
            nFeDuplicata.setNumero("001");
            nFeDuplicata.setDataVencimento(converterToDate(xNFe.getNFe().getInfNFe().getIde().getDhEmi()));
            nFeDuplicata.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getPag().get(0).getDetPag().getVPag()));
            nFeDuplicatas.add(nFeDuplicata);

        } else {
            for (int index = 0; index < xNFe.getNFe().getInfNFe().getCobr().getDup().size(); index++) {
                NFeDuplicatas nFeDuplicata = new NFeDuplicatas();
                nFeDuplicata.setNumero(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getNDup().trim());
                nFeDuplicata.setDataVencimento(converterToDateSimple(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getDVenc()));
                nFeDuplicata.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getVDup()));
                nFeDuplicatas.add(nFeDuplicata);
            }
        }
        return nFeDuplicatas;
    }

    private NFe persistirNFe(Cliente cliente, Transportadora transportadora, List<NFeProdutos> nFeProdutos,
                             List<NFeDuplicatas> nFeDuplicatas) throws ParseException {
        Cliente searchCliente = null;
        try {
            searchCliente = this.clienteService.findById(cliente.getId());
        } catch (Exception e) { }

        Transportadora searchTransportadora = null;
        if (transportadora != null) {
            try {
                searchTransportadora = this.transportadoraService.findById(transportadora.getId());
            } catch (Exception e) {
            }
        }

        Double valorLiquido = 0d;
        if (xNFe.getNFe().getInfNFe().getCobr() == null) {
            valorLiquido = converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF());
        } else {
            valorLiquido = converterToDouble(xNFe.getNFe().getInfNFe().getCobr().getFat().getVLiq());
        }

        NFe nFe = new NFe();
        nFe.setChave(xNFe.getProtNFe().getInfProt().getChNFe().trim());
        nFe.setCodigo(xNFe.getNFe().getInfNFe().getIde().getCNF().trim());
        nFe.setNumero(xNFe.getNFe().getInfNFe().getIde().getNNF().trim());
        nFe.setTipo(xNFe.getNFe().getInfNFe().getIde().getTpNF().trim());
        nFe.setSerie(xNFe.getNFe().getInfNFe().getIde().getSerie().trim());
        nFe.setDataEmissao(converterToDate(xNFe.getNFe().getInfNFe().getIde().getDhEmi()));
        nFe.setNaturezaOperacao(xNFe.getNFe().getInfNFe().getIde().getNatOp().toUpperCase().trim());
        nFe.setFinalidadeEmissao(xNFe.getNFe().getInfNFe().getIde().getFinNFe().trim());
        nFe.setCliente(searchCliente);
        nFe.setNfeProdutos(nFeProdutos);
        nFe.setNfeDuplicatas(nFeDuplicatas);
        nFe.setValorProdutos(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVProd()));
        nFe.setValorDesconto(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVDesc()));
        nFe.setValorLiquido(valorLiquido);
        nFe.setValorTotal(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF()));
        nFe.setTransportadora(searchTransportadora);
        this.nFeService.save(nFe);
        return nFe;
    }

    private TipoPagamento persistirTipoPagamento() {
        TipoPagamento tipoPagamento;

        String detPag = xNFe.getNFe().getInfNFe().getPag().get(0).getDetPag().getTPag().trim();

        // Modelo atual [sempre com valor 99-Outros]
        if (detPag.equals("99")) {

            TipoPagamento searchTipoPagamento = null;
            if (xNFe.getNFe().getInfNFe().getCobr() == null) {
                try {
                    searchTipoPagamento = this.tipoPagamentoService.findByDescricao("DEPOSITO");
                } catch (Exception e) { }

                if (searchTipoPagamento == null) {
                    tipoPagamento = new TipoPagamento();
                    tipoPagamento.setDescricao("DEPOSITO");
                    tipoPagamento.setStatus(StatusPagamento.VISTA);
                    this.tipoPagamentoService.save(tipoPagamento);

                } else {
                    tipoPagamento = searchTipoPagamento;
                }

            } else {
                try {
                    searchTipoPagamento = this.tipoPagamentoService.findByDescricao("BOLETO");
                } catch (Exception e) { }

                if (searchTipoPagamento == null) {
                    tipoPagamento = new TipoPagamento();
                    tipoPagamento.setDescricao("BOLETO");
                    tipoPagamento.setStatus(StatusPagamento.PRAZO);
                    this.tipoPagamentoService.save(tipoPagamento);
                } else {
                    tipoPagamento = searchTipoPagamento;
                }
            }
        } else {
            String descricao;
            StatusPagamento statusPagamento;

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
                case "14":
                case "15":
                    descricao = "BOLETO";
                    statusPagamento = StatusPagamento.PRAZO;
                    break;
                default:
                    descricao = "DEPOSITO";
                    statusPagamento = StatusPagamento.VISTA;
            }

            TipoPagamento searchTipoPagamento = null;
            try {
                searchTipoPagamento = this.tipoPagamentoService.findByDescricao(descricao);
            } catch (Exception e) { }

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

    private void persistirContasReceber(NFe nFe, Cliente cliente, TipoPagamento tipoPagamento) throws ParseException {
        StatusConta statusConta = verificarStatusConta(tipoPagamento);

        Cliente searchCliente = null;
        try {
            searchCliente = this.clienteService.findById(cliente.getId());
        } catch (Exception e) { }

        TipoPagamento searchTipoPagamento = null;
        try {
            searchTipoPagamento = this.tipoPagamentoService.findById(tipoPagamento.getId());
        } catch (Exception e) { }

        if (xNFe.getNFe().getInfNFe().getCobr() == null) {
            ContasReceber contasReceber = new ContasReceber();
            contasReceber.setDocumento(nFe.getNumero().trim());
            contasReceber.setCliente(searchCliente);
            contasReceber.setDescricao("NFe: " + nFe.getNumero().trim() + " - Cliente: " + searchCliente.getNome().trim());
            contasReceber.setTipoPagamento(searchTipoPagamento);
            contasReceber.setDataVencimento(converterToDate(xNFe.getNFe().getInfNFe().getIde().getDhEmi()));
            contasReceber.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF()));
            contasReceber.setDataRecebimento(contasReceber.getDataVencimento());
            contasReceber.setValorRecebido(contasReceber.getValor());
            contasReceber.setStatus(statusConta);
            this.contasReceberService.save(contasReceber);

        } else {
            for (int index = 0; index < xNFe.getNFe().getInfNFe().getCobr().getDup().size(); index++) {
                ContasReceber contasReceber = new ContasReceber();
                contasReceber.setDocumento(nFe.getNumero().trim());
                contasReceber.setCliente(searchCliente);
                contasReceber.setDescricao("NFe: " + nFe.getNumero().trim() + " - Cliente: " + searchCliente.getNome().trim());
                contasReceber.setTipoPagamento(searchTipoPagamento);
                contasReceber.setDataVencimento(converterToDateSimple(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getDVenc()));
                contasReceber.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getVDup()));
                contasReceber.setStatus(statusConta);
                this.contasReceberService.save(contasReceber);
            }
        }
    }

    private StatusConta verificarStatusConta(TipoPagamento tipoPagamento) {
        StatusConta statusConta;
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

    private List<NFeEntradaProdutos> persistirNFeEntradaProdutos() {
        List<NFeEntradaProdutos> nFeEntradaProdutos = new ArrayList<>();

        for (int index = 0; index < xNFe.getNFe().getInfNFe().getDet().size(); index++) {
            NFeEntradaProdutos nFeEntradaProduto = new NFeEntradaProdutos();
            nFeEntradaProduto.setDescricaoProduto(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getXProd().toUpperCase().trim());
            nFeEntradaProduto.setNcm(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getNCM().trim());
            nFeEntradaProduto.setCfop(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getCFOP().trim());
            nFeEntradaProduto.setUnidade(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getUCom().toUpperCase().trim());
            nFeEntradaProduto.setQuantidade(converterToDouble(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getQCom()));
            nFeEntradaProduto.setValorUnitario(converterToDouble(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getVUnCom()));
            nFeEntradaProduto.setValorTotal(converterToDouble(xNFe.getNFe().getInfNFe().getDet().get(index).getProd().getVProd()));
            nFeEntradaProdutos.add(nFeEntradaProduto);
        }
        return nFeEntradaProdutos;
    }

    private List<NFeEntradaDuplicatas> persistirNFeEntradaDuplicatas() throws ParseException {
        List<NFeEntradaDuplicatas> nFeEntradaDuplicatas = new ArrayList<>();

        if (xNFe.getNFe().getInfNFe().getCobr() == null) {
            NFeEntradaDuplicatas nFeEntradaDuplicata = new NFeEntradaDuplicatas();
            nFeEntradaDuplicata.setNumero("001");
            nFeEntradaDuplicata.setDataVencimento(converterToDate(xNFe.getNFe().getInfNFe().getIde().getDhEmi()));
            nFeEntradaDuplicata.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getPag().get(0).getDetPag().getVPag()));
            nFeEntradaDuplicatas.add(nFeEntradaDuplicata);

        } else {
            for (int index = 0; index < xNFe.getNFe().getInfNFe().getCobr().getDup().size(); index++) {
                NFeEntradaDuplicatas nFeEntradaDuplicata = new NFeEntradaDuplicatas();
                nFeEntradaDuplicata.setNumero(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getNDup().trim());
                nFeEntradaDuplicata.setDataVencimento(converterToDateSimple(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getDVenc()));
                nFeEntradaDuplicata.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getVDup()));
                nFeEntradaDuplicatas.add(nFeEntradaDuplicata);
            }
        }
        return nFeEntradaDuplicatas;
    }

    private NFeEntrada persistirNFeEntrada(Fornecedor fornecedor, Transportadora transportadora,
                                           List<NFeEntradaProdutos> nFeEntradaProdutos,
                                           List<NFeEntradaDuplicatas> nFeEntradaDuplicatas) throws ParseException {
        Fornecedor searchFornecedor = null;
        try {
            searchFornecedor = this.fornecedorService.findById(fornecedor.getId());
        } catch (Exception e) { }

        Transportadora searchTransportadora = null;
        try {
            searchTransportadora = this.transportadoraService.findById(transportadora.getId());
        } catch (Exception e) { }

        Double valorLiquido = 0d;
        if (xNFe.getNFe().getInfNFe().getCobr() == null) {
            valorLiquido = converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF());
        } else {
            valorLiquido = converterToDouble(xNFe.getNFe().getInfNFe().getCobr().getFat().getVLiq());
        }

        NFeEntrada nFeEntrada = new NFeEntrada();
        nFeEntrada.setChave(xNFe.getProtNFe().getInfProt().getChNFe().trim());
        nFeEntrada.setCodigo(xNFe.getNFe().getInfNFe().getIde().getCNF().trim());
        nFeEntrada.setNumero(xNFe.getNFe().getInfNFe().getIde().getNNF().trim());
        nFeEntrada.setTipo(xNFe.getNFe().getInfNFe().getIde().getTpNF().trim());
        nFeEntrada.setSerie(xNFe.getNFe().getInfNFe().getIde().getSerie().trim());
        nFeEntrada.setDataEmissao(converterToDate(xNFe.getNFe().getInfNFe().getIde().getDhEmi()));
        nFeEntrada.setDataEntrada(new Date());
        nFeEntrada.setNaturezaOperacao(xNFe.getNFe().getInfNFe().getIde().getNatOp().toUpperCase().trim());
        nFeEntrada.setFinalidadeEmissao(xNFe.getNFe().getInfNFe().getIde().getFinNFe().trim());
        nFeEntrada.setFornecedor(searchFornecedor);
        nFeEntrada.setNfeEntradaProdutos(nFeEntradaProdutos);
        nFeEntrada.setNfeEntradaDuplicatas(nFeEntradaDuplicatas);
        nFeEntrada.setValorProdutos(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVProd()));
        nFeEntrada.setValorDesconto(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVDesc()));
        nFeEntrada.setValorLiquido(valorLiquido);
        nFeEntrada.setValorTotal(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF()));
        nFeEntrada.setTransportadora(searchTransportadora);
        this.nFeEntradaService.save(nFeEntrada);
        return nFeEntrada;
    }

    private void persistirContasPagar(NFeEntrada nFeEntrada, Fornecedor fornecedor,
                                      TipoPagamento tipoPagamento) throws ParseException {
        StatusConta statusConta = verificarStatusConta(tipoPagamento);

        Fornecedor searchFornecedor = null;
        try {
            searchFornecedor = this.fornecedorService.findById(fornecedor.getId());
        } catch (Exception e) { }

        TipoPagamento searchTipoPagamento = null;
        try {
            searchTipoPagamento = this.tipoPagamentoService.findById(tipoPagamento.getId());
        } catch (Exception e) { }

        if (xNFe.getNFe().getInfNFe().getCobr() == null) {
            ContasPagar contasPagar = new ContasPagar();
            contasPagar.setDocumento(nFeEntrada.getNumero().trim());
            contasPagar.setFornecedor(searchFornecedor);
            contasPagar.setDescricao("NFe: " + nFeEntrada.getNumero().trim() + " - Fornecedor: " + searchFornecedor.getNome().trim());
            contasPagar.setTipoPagamento(searchTipoPagamento);
            contasPagar.setDataVencimento(converterToDate(xNFe.getNFe().getInfNFe().getIde().getDhEmi()));
            contasPagar.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getTotal().getICMSTot().getVNF()));
            contasPagar.setDataPagamento(contasPagar.getDataVencimento());
            contasPagar.setValorPago(contasPagar.getValor());
            contasPagar.setStatus(statusConta);
            this.contasPagarService.save(contasPagar);

        } else {
            for (int index = 0; index < xNFe.getNFe().getInfNFe().getCobr().getDup().size(); index++) {
                ContasPagar contasPagar = new ContasPagar();
                contasPagar.setDocumento(nFeEntrada.getNumero().trim());
                contasPagar.setFornecedor(searchFornecedor);
                contasPagar.setDescricao("NFe: " + nFeEntrada.getNumero().trim() + " - Fornecedor: " + searchFornecedor.getNome().trim());
                contasPagar.setTipoPagamento(searchTipoPagamento);
                contasPagar.setDataVencimento(converterToDateSimple(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getDVenc()));
                contasPagar.setValor(converterToDouble(xNFe.getNFe().getInfNFe().getCobr().getDup().get(index).getVDup()));
                contasPagar.setStatus(statusConta);
                this.contasPagarService.save(contasPagar);
            }
        }
    }

    private Date converterToDateSimple(String data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(data);
    }

    private Date converterToDate(String data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return simpleDateFormat.parse(data);
    }

    private Double converterToDouble(String valor) {
        return Double.parseDouble(valor);
    }
}