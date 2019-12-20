package br.com.dashboard.repository;

import br.com.dashboard.model.NFeProdutos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NFeProdutosRepository extends JpaRepository<NFeProdutos, Long> {

    @Query("select n from NFeProdutos n where n.id = ?1 and n.nfe.id = ?2")
    NFeProdutos findByIdNFeProdutoAndIdNFe(Long idNFeProdutos, Long idNFe);

    @Query("select n from NFeProdutos n where n.nfe.id = ?1 order by n.produto.id")
    List<NFeProdutos> findAllByNFe(Long idNFe);

    @Query("from NFeProdutos n where n.nfe.id = :idNFe and lower(n.produto.descricao) like %:searchTerm%")
    Page<NFeProdutos> search(@Param("idNFe") Long idNFe, @Param("searchTerm") String searchTerm, Pageable pageable);
}