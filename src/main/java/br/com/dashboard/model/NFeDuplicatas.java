package br.com.dashboard.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "nfe_duplicatas")
public class NFeDuplicatas implements Serializable {

    private static final long serialVersionUID = 1L;

    public NFeDuplicatas() {
        super();
    }

    public NFeDuplicatas(Long id, NFe nfe, @NotNull @Size(max = 3) String numero, Date dataVencimento,
                         @NotNull Double valor) {
        this.id = id;
        this.nfe = nfe;
        this.numero = numero;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nfe")
    private NFe nfe;

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

    public NFe getNfe() {
        return nfe;
    }

    public void setNfe(NFe nfe) {
        this.nfe = nfe;
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
        return "NfeDuplicatas [id=" + id + ", nfe=" + nfe + ", numero=" + numero +
                ", dataVencimento=" + dataVencimento + ", valor=" + valor + "]";
    }
}