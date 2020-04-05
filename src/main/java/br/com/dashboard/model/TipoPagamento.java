package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "tipos_pagamento")
public class TipoPagamento implements Serializable {

    private static long serialVersionUID = 1L;

    public TipoPagamento() {
        super();
    }

    public TipoPagamento(Long id, @NotNull @NotEmpty @NotBlank String descricao, StatusPagamento status) {
        super();
        this.id = id;
        this.descricao = descricao;
        this.status = status;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "Informe a descrição.")
    @NotBlank(message = "Descrição não pode ser em branco.")
    @Column(nullable = false, name = "descricao")
    private String descricao;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name="status")
    private StatusPagamento status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TipoPagamento [id=" + id + ", descricao=" + descricao + ", status=" + status + "]";
    }
}