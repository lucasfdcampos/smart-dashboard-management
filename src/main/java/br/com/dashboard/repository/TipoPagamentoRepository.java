package br.com.dashboard.repository;

import br.com.dashboard.model.TipoPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPagamentoRepository extends JpaRepository<TipoPagamento, Long> {

    @Query("select t from TipoPagamento t where t.descricao = ?1")
    TipoPagamento findByDescricao(String descricao);

    @Query("from TipoPagamento t where lower(t.descricao) like %:searchTerm%")
    Page<TipoPagamento> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}
