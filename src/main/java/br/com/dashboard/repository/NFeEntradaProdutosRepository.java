package br.com.dashboard.repository;

import br.com.dashboard.model.NFeEntradaProdutos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NFeEntradaProdutosRepository extends JpaRepository<NFeEntradaProdutos, Long> {

    @Query("select n from NFeEntradaProdutos n where n.id = ?1 and n.nfeEntrada.id = ?2")
    NFeEntradaProdutos findByIdNFeEntradaProdutoAndIdNFeEntrada(Long idNFeEntradaProduto, Long idNFeEntrada);

    @Query("select n from NFeEntradaProdutos n where n.nfeEntrada.id = ?1 order by n.descricaoProduto")
    List<NFeEntradaProdutos> findAllByNFeEntrada(Long idNFeEntrada);

    @Query("from NFeEntradaProdutos n where n.nfeEntrada.id = :idNFeEntrada and lower(n.descricaoProduto) " +
            "like %:searchTerm%")
    Page<NFeEntradaProdutos> search(@Param("idNFeEntrada") Long idNFeEntrada, @Param("searchTerm") String searchTerm,
                                    Pageable pageable);
}
