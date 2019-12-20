package br.com.dashboard.repository;

import br.com.dashboard.model.ContasPagar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContasPagarRepository extends JpaRepository<ContasPagar, Long> {

    @Query("select c from ContasPagar c where c.fornecedor.id = ?1")
    List<ContasPagar> findAllByFornecedor(Long idFornecedor);

    @Query("select c from ContasPagar c where c.tipoPagamento.id = ?1")
    List<ContasPagar> findAllByTipoPagamento(Long idTipoPagamento);

    @Query("from ContasPagar c where c.fornecedor.id = :idFornecedor and lower(c.descricao) like %:searchTerm%")
    Page<ContasPagar> search(@Param("idFornecedor") Long idFornecedor, @Param("searchTerm") String searchTerm,
                             Pageable pageable);
}