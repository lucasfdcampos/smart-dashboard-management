package br.com.dashboard.repository;

import br.com.dashboard.model.NFeEntradaDuplicatas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NFeEntradaDuplicatasRepository extends JpaRepository<NFeEntradaDuplicatas, Long> {

    @Query("select n from NFeEntradaDuplicatas n where n.id = ?1 and n.nfeEntrada.id = ?2")
    NFeEntradaDuplicatas findByIdNFeEntradaDuplicataAndIdNFeEntrada(Long idNFeEntradaDuplicata, Long idNFeEntrada);

    @Query("select n from NFeEntradaDuplicatas n where n.nfeEntrada.id = ?1 order by n.numero")
    List<NFeEntradaDuplicatas> findAllByNFeEntrada(Long idNFeEntrada);

    @Query("from NFeEntradaDuplicatas n where n.nfeEntrada.id = :idNFeEntrada and lower(n.numero) like %:searchTerm%")
    Page<NFeEntradaDuplicatas> search(@Param("idNFeEntrada") Long idNFeEntrada, @Param("searchTerm") String searchTerm,
                                      Pageable pageable);
}
