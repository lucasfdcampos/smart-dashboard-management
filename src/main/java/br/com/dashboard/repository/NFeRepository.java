package br.com.dashboard.repository;

import br.com.dashboard.model.NFe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NFeRepository extends JpaRepository<NFe, Long> {

    @Query("select n from NFe n where n.chave = ?1")
    NFe findByChave(String chave);

    @Query("select n from NFe n where n.numero = ?1")
    NFe findByNumero(String numero);

    @Query("select n from NFe n where n.cliente.id = ?1")
    List<NFe> findAllByCliente(Long idCliente);

    @Query("from NFe n where lower(n.numero) like %:searchTerm%")
    Page<NFe> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}
