package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contas_receber")
public class ContasReceber implements Serializable {

    private static long serialVersionUID = 1L;

    public ContasReceber() {
        super();
    }

    public ContasReceber(Long id, @NotNull @Size(max = 9) String documento, @NotNull Cliente cliente,
                         @NotNull String descricao, @NotNull TipoPagamento tipoPagamento, Date dataVencimento,
                         @NotNull Double valor, Date dataRecebimento, Double valorRecebido, StatusConta status) {
        super();
        this.id = id;
        this.documento = documento;
        this.cliente = cliente;
        this.descricao = descricao;
        this.tipoPagamento = tipoPagamento;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.dataRecebimento = dataRecebimento;
        this.valorRecebido = valorRecebido;
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
    @JoinColumn(name = "cliente", referencedColumnName = "id")
    private Cliente cliente;

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
    @Column(name = "data_recebimento")
    private Date dataRecebimento;

    @Column(name = "valor_recebido")
    private Double valorRecebido;

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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Double getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(Double valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public StatusConta getStatus() {
        return status;
    }

    public void setStatus(StatusConta status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ContasReceber [id=" + id + ", documento=" + documento + ", cliente=" + cliente +
                ", descricao=" + descricao + ", tipoPagamento=" + tipoPagamento +
                ", dataVencimento=" + dataVencimento + ", valor=" + valor + ", dataRecebimento=" + dataRecebimento +
                ", valorRecebido=" + valorRecebido + ", status=" + status + "]";
    }
}