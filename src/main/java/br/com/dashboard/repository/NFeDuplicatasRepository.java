package br.com.dashboard.repository;

import br.com.dashboard.model.NFeDuplicatas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NFeDuplicatasRepository extends JpaRepository<NFeDuplicatas, Long> {

    @Query("select n from NFeDuplicatas n where n.id = ?1 and n.nfe.id = ?2 order by n.numero")
    NFeDuplicatas findByIdNFeDuplicataAndIdNFe(Long idNFeDuplicata, Long idNFe);

    @Query("select n from NFeDuplicatas n where n.nfe.id = ?1 order by n.numero")
    List<NFeDuplicatas> findAllByNFe(Long idNFe);

    @Query("from NFeDuplicatas n where n.nfe.id = :idNFe and lower(n.numero) like %:searchTerm%")
    Page<NFeDuplicatas> search(@Param("idNFe") Long idNFe, @Param("searchTerm") String searchTerm, Pageable pageable);
}