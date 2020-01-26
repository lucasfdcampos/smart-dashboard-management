package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "nfe_entrada")
public class NFeEntrada implements Serializable {

    private static long serialVersionUID = 1L;

    public NFeEntrada() {
        super();
    }

    public NFeEntrada(Long id, @NotNull @Size(max = 44) String chave, @NotNull @Size(max = 8) String codigo,
                      @NotNull @Size(max = 9) String numero, @NotNull @Size(max = 1) String tipo,
                      @NotNull @Size(max = 3) String serie, Date dataEmissao, Date dataEntrada,
                      String naturezaOperacao, @NotNull @Size(max = 1) String finalidadeEmissao,
                      @NotNull Fornecedor fornecedor, List<NFeEntradaProdutos> nfeEntradaProdutos,
                      List<NFeEntradaDuplicatas> nfeEntradaDuplicatas, @NotNull Double valorProdutos,
                      @NotNull Double valorDesconto, @NotNull Double valorLiquido, @NotNull Double valorTotal,
                      @NotNull Transportadora transportadora) {
        super();
        this.id = id;
        this.chave = chave;
        this.codigo = codigo;
        this.numero = numero;
        this.tipo = tipo;
        this.serie = serie;
        this.dataEmissao = dataEmissao;
        this.dataEntrada = dataEntrada;
        this.naturezaOperacao = naturezaOperacao;
        this.finalidadeEmissao = finalidadeEmissao;
        this.fornecedor = fornecedor;
        this.nfeEntradaProdutos = nfeEntradaProdutos;
        this.nfeEntradaDuplicatas = nfeEntradaDuplicatas;
        this.valorProdutos = valorProdutos;
        this.valorDesconto = valorDesconto;
        this.valorLiquido = valorLiquido;
        this.valorTotal = valorTotal;
        this.transportadora = transportadora;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 44)
    @Column(nullable = false, length = 44, name = "chave")
    private String chave;

    @NotNull
    @Size(max = 8)
    @Column(nullable = false, length = 8, name = "codigo")
    private String codigo;

    @NotNull
    @Size(max = 9)
    @Column(nullable = false, length = 9, name = "numero")
    private String numero;

    @NotNull
    @Size(max = 1)
    @Column(nullable = false, length = 1, name ="tipo")
    private String tipo;

    @NotNull
    @Size(max = 3)
    @Column(nullable = false, length = 3, name = "serie")
    private String serie;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "data_emissao")
    private Date dataEmissao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "data_entrada")
    private Date dataEntrada;

    @Column(nullable = false, name = "natureza_operacao")
    private String naturezaOperacao;

    @NotNull
    @Size(max = 1)
    @Column(nullable = false, length = 1, name = "finalidade_emissao")
    private String finalidadeEmissao;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fornecedor", referencedColumnName = "id")
    private Fornecedor fornecedor;

    @OneToMany(mappedBy = "nfeEntrada", cascade = CascadeType.ALL)
    private List<NFeEntradaProdutos> nfeEntradaProdutos;

    @OneToMany(mappedBy = "nfeEntrada", cascade = CascadeType.ALL)
    private List<NFeEntradaDuplicatas> nfeEntradaDuplicatas;

    @NotNull
    @Column(nullable = false, name = "valor_produtos")
    private Double valorProdutos;

    @NotNull
    @Column(nullable = false, name = "valor_desconto")
    private Double valorDesconto;

    @NotNull
    @Column(nullable = false, name = "valor_liquido")
    private Double valorLiquido;

    @NotNull
    @Column(nullable = false, name = "valor_total")
    private Double valorTotal;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transportadora", referencedColumnName = "id")
    private Transportadora transportadora;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public String getNaturezaOperacao() {
        return naturezaOperacao;
    }

    public void setNaturezaOperacao(String naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
    }

    public String getFinalidadeEmissao() {
        return finalidadeEmissao;
    }

    public void setFinalidadeEmissao(String finalidadeEmissao) {
        this.finalidadeEmissao = finalidadeEmissao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<NFeEntradaProdutos> getNfeEntradaProdutos() {
        return nfeEntradaProdutos;
    }

    public void setNfeEntradaProdutos(List<NFeEntradaProdutos> nfeEntradaProdutos) {
        this.nfeEntradaProdutos = nfeEntradaProdutos;
    }

    public List<NFeEntradaDuplicatas> getNfeEntradaDuplicatas() {
        return nfeEntradaDuplicatas;
    }

    public void setNfeEntradaDuplicatas(List<NFeEntradaDuplicatas> nfeEntradaDuplicatas) {
        this.nfeEntradaDuplicatas = nfeEntradaDuplicatas;
    }

    public Double getValorProdutos() {
        return valorProdutos;
    }

    public void setValorProdutos(Double valorProdutos) {
        this.valorProdutos = valorProdutos;
    }

    public Double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(Double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Double getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(Double valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Transportadora getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Transportadora transportadora) {
        this.transportadora = transportadora;
    }

    public void addProduto(NFeEntradaProdutos nFeEntradaProduto) {
        if (this.nfeEntradaProdutos == null) {
            this.nfeEntradaProdutos = new ArrayList<>();
        }
        nFeEntradaProduto.setNfeEntrada(this);
        this.nfeEntradaProdutos.add(nFeEntradaProduto);
    }

    public void addDuplicata(NFeEntradaDuplicatas nFeEntradaDuplicata) {
        if (this.nfeEntradaDuplicatas == null) {
            this.nfeEntradaDuplicatas = new ArrayList<>();
        }
        nFeEntradaDuplicata.setNfeEntrada(this);
        this.nfeEntradaDuplicatas.add(nFeEntradaDuplicata);
    }

    @Override
    public String toString() {
        return "NFeEntrada [id=" + id + ", chave=" + chave + ", codigo=" + codigo + ", numero=" + numero +
                ", tipo=" + tipo + ", serie=" + serie + ", dataEmissao=" + dataEmissao +
                ", dataEntrada=" + dataEntrada + ", naturezaOperacao=" + naturezaOperacao +
                ", finalidadeEmissao=" + finalidadeEmissao + ", fornecedor=" + fornecedor +
                ", valorProdutos=" + valorProdutos + ", valorDesconto=" + valorDesconto +
                ", valorLiquido=" + valorLiquido + ", valorTotal=" + valorTotal +
                ", transportadora=" + transportadora + "]";
    }
}