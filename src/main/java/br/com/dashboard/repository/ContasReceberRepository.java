package br.com.dashboard.repository;

import br.com.dashboard.model.ContasReceber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContasReceberRepository extends JpaRepository<ContasReceber, Long> {

    @Query("select c from ContasReceber c where c.cliente.id = ?1")
    List<ContasReceber> findAllByCliente(Long idCliente);

    @Query("select c from ContasReceber c where c.tipoPagamento.id = ?1")
    List<ContasReceber> findAllByTipoPagamento(Long idTipoPagamento);

    @Query("from ContasReceber c where c.cliente.id = :idCliente and lower(c.documento) like %:searchTerm%")
    Page<ContasReceber> search(@Param("idCliente") Long idCliente, @Param("searchTerm") String searchTerm,
                               Pageable pageable);
}