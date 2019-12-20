package br.com.dashboard.service;

import br.com.dashboard.model.Transportadora;
import br.com.dashboard.repository.TransportadoraRepository;
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
public class TransportadoraService {

    @Autowired
    private TransportadoraRepository transportadoraRepository;

    public TransportadoraService() {
        super();
    }

    public TransportadoraService(TransportadoraRepository transportadoraRepository) {
        super();
        this.transportadoraRepository = transportadoraRepository;
    }

    public void save(Transportadora transportadora) {
        this.transportadoraRepository.save(transportadora);
    }

    public void update(Long id, Transportadora transportadora) {
        transportadora.setId(id);
        this.transportadoraRepository.save(transportadora);
    }

    public void delete(Long id) {
        this.transportadoraRepository.deleteById(id);
    }

    public Transportadora findById(Long id) {
        Transportadora transportadora = this.transportadoraRepository.getOne(id);
        return transportadora;
    }

    public Transportadora findByNome(String nome) {
        return this.transportadoraRepository.findByNome(nome);
    }

    public Transportadora findByCnpj(String cnpj) {
        return this.transportadoraRepository.findByCnpj(cnpj);
    }

    public List<Transportadora> findAll() {
        return this.transportadoraRepository.findAll();
    }

    public Page<Transportadora> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return new PageImpl<>(this.transportadoraRepository.findAll(), pageRequest, size);
    }

    public Page<Transportadora> search(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return this.transportadoraRepository.search(searchTerm.toLowerCase(), pageRequest);
    }
}