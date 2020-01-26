package br.com.dashboard.service;


import br.com.dashboard.model.NFeEntrada;
import br.com.dashboard.model.NFeEntradaDuplicatas;
import br.com.dashboard.model.NFeEntradaProdutos;
import br.com.dashboard.repository.NFeEntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class NFeEntradaService {

    @Autowired
    private NFeEntradaRepository nFeEntradaRepository;

    @Autowired
    private NFeEntradaProdutosService nFeEntradaProdutosService;

    @Autowired
    private NFeEntradaDuplicatasService nFeEntradaDuplicatasService;

    public NFeEntradaService() {
        super();
    }

    public NFeEntradaService(NFeEntradaRepository nFeEntradaRepository) {
        super();
        this.nFeEntradaRepository = nFeEntradaRepository;
    }

    public void save(NFeEntrada nFeEntrada) {
        this.nFeEntradaRepository.save(nFeEntrada);

        Iterator<NFeEntradaProdutos> nFeEntradaProdutosIterator = nFeEntrada.getNfeEntradaProdutos().iterator();
        while (nFeEntradaProdutosIterator.hasNext()) {
            NFeEntradaProdutos nFeEntradaProdutos = nFeEntradaProdutosIterator.next();
            nFeEntradaProdutos.setNfeEntrada(nFeEntrada);

            nFeEntradaProdutosService.save(nFeEntrada.getId(), nFeEntradaProdutos);
        }

        Iterator<NFeEntradaDuplicatas> nFeEntradaDuplicatasIterator = nFeEntrada.getNfeEntradaDuplicatas().iterator();
        while (nFeEntradaDuplicatasIterator.hasNext()) {
            NFeEntradaDuplicatas nFeEntradaDuplicatas = nFeEntradaDuplicatasIterator.next();
            nFeEntradaDuplicatas.setNfeEntrada(nFeEntrada);

            nFeEntradaDuplicatasService.save(nFeEntrada.getId(), nFeEntradaDuplicatas);
        }
    }

    public void update(Long id, NFeEntrada nFeEntrada) {
        nFeEntrada.setId(id);
        this.nFeEntradaRepository.save(nFeEntrada);
    }

    public void delete(Long id) {
        this.nFeEntradaRepository.deleteById(id);
    }

    public NFeEntrada findById(Long id) {
        return this.nFeEntradaRepository.getOne(id);
    }

    public NFeEntrada findByChave(String chave) {
        return this.nFeEntradaRepository.findByChave(chave);
    }

    public NFeEntrada findByFornecedorAndNumero(Long idFornecedor, String numero) {
        return this.nFeEntradaRepository.findByFornecedorAndNumero(idFornecedor, numero);
    }

    public List<NFeEntrada> findAll() {
        return this.nFeEntradaRepository.findAll();
    }

    public List<NFeEntrada> findAllByFornecedor(Long idFornecedor) {
        return this.nFeEntradaRepository.findAllByFornecedor(idFornecedor);
    }

    public Page<NFeEntrada> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "numero");
        return new PageImpl<>(this.nFeEntradaRepository.findAll(), pageRequest, size);
    }

    public Page<NFeEntrada> search(Long idFornecedor, String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "numero");
        return this.nFeEntradaRepository.search(idFornecedor, searchTerm.toLowerCase(), pageRequest);
    }
}