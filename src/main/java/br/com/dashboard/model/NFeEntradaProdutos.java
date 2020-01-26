package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "nfe_entrada_produtos")
public class NFeEntradaProdutos {

    private static final long serialVersionUID = 1L;

    public NFeEntradaProdutos() {
        super();
    }

    public NFeEntradaProdutos(Long id, NFeEntrada nfeEntrada, String descricaoProduto, @Size(max = 8) String ncm,
                              @Size(max = 4) String cfop, @Size(max = 2) String unidade, @NotNull Double quantidade,
                              @NotNull Double valorUnitario, @NotNull Double valorTotal) {
        super();
        this.id = id;
        this.nfeEntrada = nfeEntrada;
        this.descricaoProduto = descricaoProduto;
        this.ncm = ncm;
        this.cfop = cfop;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nfeEntrada")
    private NFeEntrada nfeEntrada;

    @Column(length = 100, name = "descricao_produto")
    private String descricaoProduto;

    @Size(max = 8)
    @Column(length = 8, name = "ncm")
    private String ncm;

    @Size(max = 4)
    @Column(length = 4, name = "cfop")
    private String cfop;

    @Size(max = 2)
    @Column(length = 2, name = "unidade")
    private String unidade;

    @NotNull
    @Column(nullable = false, name = "quantidade")
    private Double quantidade;

    @NotNull
    @Column(nullable = false, name = "valor_unitario")
    private Double valorUnitario;

    @NotNull
    @Column(nullable = false, name = "valor_total")
    private Double valorTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NFeEntrada getNfeEntrada() {
        return nfeEntrada;
    }

    public void setNfeEntrada(NFeEntrada nfeEntrada) {
        this.nfeEntrada = nfeEntrada;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public String getNcm() {
        return ncm;
    }

    public void setNcm(String ncm) {
        this.ncm = ncm;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "NFeEntradaProdutos [id=" + id + ", nfeEntrada=" + nfeEntrada +
                ", descricaoProduto=" + descricaoProduto + ", ncm=" + ncm + ", cfop=" + cfop +
                ", unidade=" + unidade + ", quantidade=" + quantidade + ", valorUnitario=" + valorUnitario +
                ", valorTotal=" + valorTotal + "]";
    }
}