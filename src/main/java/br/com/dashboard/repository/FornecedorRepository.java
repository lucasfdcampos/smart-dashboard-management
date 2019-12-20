package br.com.dashboard.repository;

import br.com.dashboard.model.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    @Query("select f from Fornecedor f where f.nome = ?1")
    Fornecedor findByNome(String nome);

    @Query("select f from Fornecedor f where f.cnpj = ?1")
    Fornecedor findByCnpj(String cnpj);

    @Query("from Fornecedor f where lower(f.nome) like %:searchTerm%")
    Page<Fornecedor> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}
