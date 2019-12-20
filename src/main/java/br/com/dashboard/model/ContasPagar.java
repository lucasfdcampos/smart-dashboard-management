package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contas_pagar")
public class ContasPagar implements Serializable {

    private static long serialVersionUID = 1L;

    public ContasPagar() {
        super();
    }

    public ContasPagar(Long id, @NotNull @Size(max = 9) String documento, @NotNull Cliente fornecedor,
                       @NotNull String descricao, @NotNull TipoPagamento tipoPagamento, Date dataVencimento,
                       @NotNull Double valor, Date dataPagamento, Double valorPago, StatusConta status) {
        super();
        this.id = id;
        this.documento = documento;
        this.fornecedor = fornecedor;
        this.descricao = descricao;
        this.tipoPagamento = tipoPagamento;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.valorPago = valorPago;
        this.status = status;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 9)
    @Column(nullable = false, length = 9, name = "documento")
    private String documento;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fornecedor", referencedColumnName = "id")
    private Cliente fornecedor;

    @NotNull
    @Column(nullable = false, name = "descricao")
    private String descricao;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tipo_pagamento", referencedColumnName = "id")
    private TipoPagamento tipoPagamento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "data_vencimento")
    private Date dataVencimento;

    @NotNull
    @Column(nullable = false, name = "valor")
    private Double valor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_pagamento")
    private Date dataPagamento;

    @Column(name = "valor_pago")
    private Double valorPago;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name="status")
    private StatusConta status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Cliente getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Cliente fornecedor) {
        this.fornecedor = fornecedor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContasPagar [id=" + id + ", documento=" + documento + ", fornecedor=" + fornecedor +
                ", descricao=" + descricao + ", tipoPagamento=" + tipoPagamento + ", dataVencimento=" + dataVencimento +
                ", valor=" + valor + ", dataPagamento=" + dataPagamento + ", valorPago=" + valorPago +
                ", status=" + status + "]";
    }
}
