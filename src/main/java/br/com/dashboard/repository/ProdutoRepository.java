package br.com.dashboard.repository;

import br.com.dashboard.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("from Produto p where lower(p.codigoEmpresa) like %:searchTerm%")
    Page<Produto> searchCodigo(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("from Produto p where lower(p.descricao) like %:searchTerm%")
    Page<Produto> searchDescricao(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("select p from Produto p where p.descricao = ?1")
    Produto findByDescricao(String descricao);

    @Query("from Produto p where lower(p.descricao) like %:searchTerm%")
    Page<Produto> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}