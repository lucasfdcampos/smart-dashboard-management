package br.com.dashboard.repository;

import br.com.dashboard.model.NFeEntrada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NFeEntradaRepository extends JpaRepository<NFeEntrada, Long> {

    @Query("select n from NFeEntrada n where n.chave = ?1")
    NFeEntrada findByChave(String chave);

    @Query("select n from NFeEntrada n where n.fornecedor.id = ?1 and n.numero = ?2")
    NFeEntrada findByFornecedorAndNumero(Long idFornecedor, String numero);

    @Query("select n from NFeEntrada n where n.fornecedor.id = ?1")
    List<NFeEntrada> findAllByFornecedor(Long idFornecedor);

    @Query("from NFeEntrada n where n.fornecedor.id = :idFornecedor and lower(n.numero) like %:searchTerm%")
    Page<NFeEntrada> search(@Param("idFornecedor") Long idFornecedor, @Param("searchTerm") String searchTerm,
                            Pageable pageable);
}