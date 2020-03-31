package br.com.dashboard.repository;

import br.com.dashboard.model.Municipio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, String> {

    @Query("select m from Municipio m where m.codigo = ?1")
    Municipio findByCodigo(String codigo);

    @Query("from Municipio m where lower(m.nome) like %:searchTerm%")
    Page<Municipio> searchNome(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("from Municipio m where lower(m.codigo) like %:searchTerm%")
    Page<Municipio> searchCodigo(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("select m from Municipio m where m.nome = ?1 and m.uf = ?2")
    Municipio findByNomeAndUF(String nome, String uf);

    @Query("from Municipio m where lower(m.nome) like %:searchTerm%")
    Page<Municipio> search(@Param("searchTerm") String searchTerm, Pageable pageable);
}