package br.com.dashboard.repository;

import br.com.dashboard.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c from Cliente c where c.nome = ?1")
    Cliente findByNome(String nome);

    @Query("select c from Cliente c where c.cnpj = ?1")
    Cliente findByCnpj(String cnpj);

    @Query("from Cliente c where lower(c.nome) like %:searchTerm%")
    Page<Cliente> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}