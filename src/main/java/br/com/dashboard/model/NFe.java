package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "nfe")
public class NFe implements Serializable {

    private static long serialVersionUID = 1L;

    public NFe() {
        super();
    }

    public NFe(Long id, @NotNull @Size(max = 44) String chave, @NotNull @Size(max = 8) String codigo,
               @NotNull @Size(max = 9) String numero, @NotNull @Size(max = 1) String tipo,
               @NotNull @Size(max = 3) String serie, Date dataEmissao, String naturezaOperacao,
               @NotNull @Size(max = 1) String finalidadeEmissao, @NotNull Cliente cliente,
               List<NFeProdutos> nfeProdutos, List<NFeDuplicatas> nfeDuplicatas,
               @NotNull Double valorProdutos, @NotNull Double valorDesconto, @NotNull Double valorLiquido,
               @NotNull Double valorTotal, Transportadora transportadora) {
        super();
        this.id = id;
        this.chave = chave;
        this.codigo = codigo;
        this.numero = numero;
        this.tipo = tipo;
        this.serie = serie;
        this.dataEmissao = dataEmissao;
        this.naturezaOperacao = naturezaOperacao;
        this.finalidadeEmissao = finalidadeEmissao;
        this.cliente = cliente;
        this.nfeProdutos = nfeProdutos;
        this.nfeDuplicatas = nfeDuplicatas;
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

    @Column(nullable = false, name = "natureza_operacao")
    private String naturezaOperacao;

    @NotNull
    @Size(max = 1)
    @Column(nullable = false, length = 1, name = "finalidade_emissao")
    private String finalidadeEmissao;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente", referencedColumnName = "id")
    private Cliente cliente;

    @OneToMany(mappedBy = "nfe", cascade = CascadeType.ALL)
    private List<NFeProdutos> nfeProdutos;

    @OneToMany(mappedBy = "nfe", cascade = CascadeType.ALL)
    private List<NFeDuplicatas> nfeDuplicatas;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<NFeProdutos> getNfeProdutos() {
        return nfeProdutos;
    }

    public void setNfeProdutos(List<NFeProdutos> nfeProdutos) {
        this.nfeProdutos = nfeProdutos;
    }

    public List<NFeDuplicatas> getNfeDuplicatas() {
        return nfeDuplicatas;
    }

    public void setNfeDuplicatas(List<NFeDuplicatas> nfeDuplicatas) {
        this.nfeDuplicatas = nfeDuplicatas;
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

    public void addProduto(NFeProdutos nFeProduto) {
        if (this.nfeProdutos == null) {
            this.nfeProdutos = new ArrayList<>();
        }
        nFeProduto.setNfe(this);
        this.nfeProdutos.add(nFeProduto);
    }

    public void addDuplicata(NFeDuplicatas nFeDuplicata) {
        if (this.nfeDuplicatas == null) {
            this.nfeDuplicatas = new ArrayList<>();
        }
        nFeDuplicata.setNfe(this);
        this.nfeDuplicatas.add(nFeDuplicata);
    }

    @Override
    public String toString() {
        return "NFe [id=" + id + ", chave=" + chave + ", codigo=" + codigo + ", numero=" + numero + ", tipo=" + tipo +
                ", serie=" + serie + ", dataEmissao=" + dataEmissao + ", naturezaOperacao=" + naturezaOperacao +
                ", finalidadeEmissao=" + finalidadeEmissao + ", cliente=" + cliente +
                ", valorProdutos=" + valorProdutos + ", valorDesconto=" + valorDesconto +
                ", valorLiquido=" + valorLiquido + ", valorTotal=" + valorTotal +
                ", transportadora=" + transportadora + "]";
    }
}