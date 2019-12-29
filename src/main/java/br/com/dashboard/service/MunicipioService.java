package br.com.dashboard.service;

import br.com.dashboard.model.Municipio;
import br.com.dashboard.repository.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MunicipioService {

    @Autowired
    private MunicipioRepository municipioRepository;

    public MunicipioService() {
    }

    public MunicipioService(MunicipioRepository municipioRepository) {
        super();
        this.municipioRepository = municipioRepository;
    }

    public void save(Municipio municipio) {
        this.municipioRepository.save(municipio);
    }

    public void update(String codigo, Municipio municipio) {
        municipio.setCodigo(codigo);
        this.municipioRepository.save(municipio);
    }

    public void delete(String codigo) {
        Municipio searchMunicipio = null;
        try {
            searchMunicipio = this.municipioRepository.findByCodigo(codigo);
            this.municipioRepository.delete(searchMunicipio);
        } catch (Exception e) { }
    }

    public Municipio findByCodigo(String codigo) {
        return this.municipioRepository.findByCodigo(codigo);
    }

    public Municipio findByNomeAndUF(String nome, String uf) {
        return this.municipioRepository.findByNomeAndUF(nome, uf);
    }

    public List<Municipio> findAll() {
        return this.municipioRepository.findAll();
    }

    public Page<Municipio> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return new PageImpl<>(this.municipioRepository.findAll(), pageRequest, size);
    }

    public Page<Municipio> search(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return this.municipioRepository.search(searchTerm.toLowerCase(), pageRequest);
    }
}