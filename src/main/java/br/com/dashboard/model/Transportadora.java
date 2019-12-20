package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "transportadoras")
public class Transportadora implements Serializable {

    private static final long serialVersionUID = 1L;

    public Transportadora() {
        super();
    }

    public Transportadora(Long id, @NotNull String nome, @NotNull @Size(max = 14) String cnpj,
                          @NotNull @Size(max = 12) String ie, @NotNull String endereco, @NotNull String municipio,
                          @NotNull @Size(max = 2) String uf) {
        super();
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.ie = ie;
        this.endereco = endereco;
        this.municipio = municipio;
        this.uf = uf;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "nome")
    private String nome;

    @NotNull
    @Size(max = 14)
    @Column(nullable = false, length = 14, name = "cnpj")
    private String cnpj;

    @NotNull
    @Size(max = 12)
    @Column(nullable = false, length = 12, name = "ie")
    private String ie;

    @NotNull
    @Column(nullable = false, name = "endereco")
    private String endereco;

    @NotNull
    @Column(nullable = false, name = "municipio")
    private String municipio;

    @NotNull
    @Size(max = 2)
    @Column(nullable = false, length = 2, name = "uf")
    private String uf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Transportadora [id=" + id + ", nome=" + nome + ", cnpj=" + cnpj + ", ie=" + ie +
                ", endereco=" + endereco + ", municipio=" + municipio + ", uf=" + uf + "]";
    }
}