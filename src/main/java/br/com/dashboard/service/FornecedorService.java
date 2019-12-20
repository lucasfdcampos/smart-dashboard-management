package br.com.dashboard.service;

import br.com.dashboard.model.Fornecedor;
import br.com.dashboard.repository.FornecedorRepository;
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
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public FornecedorService() {
        super();
    }

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        super();
        this.fornecedorRepository = fornecedorRepository;
    }

    public void save(Fornecedor fornecedor) {
        this.fornecedorRepository.save(fornecedor);
    }

    public void update(Long id, Fornecedor fornecedor) {
        fornecedor.setId(id);
        this.fornecedorRepository.save(fornecedor);
    }

    public void delete(Long id) {
        this.fornecedorRepository.deleteById(id);
    }

    public Fornecedor findById(Long id) {
        return this.fornecedorRepository.getOne(id);
    }

    public Fornecedor findByNome(String nome) {
        return this.fornecedorRepository.findByNome(nome);
    }

    public Fornecedor findByCnpj(String cnpj) {
        return this.fornecedorRepository.findByCnpj(cnpj);
    }

    public List<Fornecedor> findAll() {
        return this.fornecedorRepository.findAll();
    }

    public Page<Fornecedor> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return new PageImpl<>(this.fornecedorRepository.findAll(), pageRequest, size);
    }

    public Page<Fornecedor> search(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return this.fornecedorRepository.search(searchTerm.toLowerCase(), pageRequest);
    }
}
