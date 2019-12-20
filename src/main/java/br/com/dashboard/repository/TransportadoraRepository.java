package br.com.dashboard.repository;

import br.com.dashboard.model.Transportadora;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportadoraRepository extends JpaRepository<Transportadora, Long> {

    @Query("select t from Transportadora t where t.nome = ?1")
    Transportadora findByNome(String nome);

    @Query("select t from Transportadora t where t.cnpj = ?1")
    Transportadora findByCnpj(String cnpj);

    @Query("from Transportadora t where lower(t.nome) like %:searchTerm%")
    Page<Transportadora> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}