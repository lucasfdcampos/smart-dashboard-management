package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "nfe_entrada_duplicatas")
public class NFeEntradaDuplicatas {

    private static final long serialVersionUID = 1L;

    public NFeEntradaDuplicatas() {
        super();
    }

    public NFeEntradaDuplicatas(Long id, NFeEntrada nfeEntrada, @NotNull @Size(max = 3) String numero,
                                Date dataVencimento, @NotNull Double valor) {
        super();
        this.id = id;
        this.nfeEntrada = nfeEntrada;
        this.numero = numero;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nfeEntrada")
    private NFeEntrada nfeEntrada;

    @NotNull
    @Size(max = 3)
    @Column(nullable = false, length = 3, name = "numero")
    private String numero;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "data_vencimento")
    private Date dataVencimento;

    @NotNull
    @Column(nullable = false, name = "valor")
    private Double valor;

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    @Override
    public String toString() {
        return "NFeEntradaDuplicatas [id=" + id + ", nfeEntrada=" + nfeEntrada + ", numero=" + numero +
                ", dataVencimento=" + dataVencimento + ", valor=" + valor + "]";
    }
}