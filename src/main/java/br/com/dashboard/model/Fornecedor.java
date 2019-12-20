package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "fornecedores")
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    public Fornecedor() {
        super();
    }

    public Fornecedor(Long id, @NotNull String nome, @NotNull @Size(max = 14) String cnpj,
                      @NotNull @Size(max = 12) String ie, @NotNull String endereco, @Size(max = 10) String numero,
                      @NotNull String bairro, @NotNull Municipio municipio, @NotNull @Size(max = 2) String uf,
                      @NotNull String cep, String fone, String celular, String email) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.ie = ie;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.municipio = municipio;
        this.uf = uf;
        this.cep = cep;
        this.fone = fone;
        this.celular = celular;
        this.email = email;
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

    @Size(max = 10)
    @Column(length = 10, name = "numero")
    private String numero;

    @NotNull
    @Column(nullable = false, name = "bairro")
    private String bairro;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "municipio", referencedColumnName = "codigo")
    private Municipio municipio;

    @NotNull
    @Size(max = 2)
    @Column(nullable = false, length = 2, name = "uf")
    private String uf;

    @NotNull
    @Column(nullable = false, name = "cep")
    private String cep;

    @Column(name = "fone")
    private String fone;

    @Column(name = "celular")
    private String celular;

    @Column(name = "email")
    private String email;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Fornecedor [id=" + id + ", nome=" + nome + ", cnpj=" + cnpj + ", ie=" + ie +
                ", endereco=" + endereco + ", numero=" + numero + ", bairro=" + bairro + ", municipio=" + municipio +
                ", uf=" + uf + ", cep=" + cep + ", fone=" + fone + ", celular=" + celular + ", email=" + email + "]";
    }
}