package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "produtos")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    public Produto() {
        super();
    }

    public Produto(Long id, String codigoEmpresa, @NotNull String descricao) {
        super();
        this.id = id;
        this.codigoEmpresa = codigoEmpresa;
        this.descricao = descricao;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_empresa")
    private String codigoEmpresa;

    @NotNull
    @Column(nullable = false, name = "descricao")
    private String descricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Produto [id=" + id + ", codigoEmpresa=" + codigoEmpresa + ", descricao=" + descricao + "]";
    }
}