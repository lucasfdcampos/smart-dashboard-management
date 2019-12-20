package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "municipios")
public class Municipio implements Serializable {

    private static long serialVersionUID = 1L;

    public Municipio() {
        super();
    }

    public Municipio(@Size(max = 7) String codigo, @NotNull String nome, @NotNull @Size(max = 2) String uf) {
        super();
        this.codigo = codigo;
        this.nome = nome;
        this.uf = uf;
    }

    @Id
    @Size(max = 7)
    @Column(nullable = false, length = 7, name = "codigo")
    private String codigo;

    @NotNull
    @Column(nullable = false, name = "nome")
    private String nome;

    @NotNull
    @Size(max = 2)
    @Column(nullable = false, length = 2, name = "uf")
    private String uf;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Municipio [codigo=" + codigo + ", nome=" + nome + ", uf=" + uf + "]";
    }
}