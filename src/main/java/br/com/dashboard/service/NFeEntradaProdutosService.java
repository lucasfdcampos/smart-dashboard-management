package br.com.dashboard.service;

import br.com.dashboard.model.NFeEntradaProdutos;
import br.com.dashboard.repository.NFeEntradaProdutosRepository;
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
public class NFeEntradaProdutosService {

    @Autowired
    private NFeEntradaProdutosRepository nFeEntradaProdutosRepository;

    @Autowired
    private NFeEntradaService nFeEntradaService;

    public NFeEntradaProdutosService() {
        super();
    }

    public NFeEntradaProdutosService(NFeEntradaProdutosRepository nFeEntradaProdutosRepository,
                                     NFeEntradaService nFeEntradaService) {
        super();
        this.nFeEntradaProdutosRepository = nFeEntradaProdutosRepository;
        this.nFeEntradaService = nFeEntradaService;
    }

    public void save(Long idNFeEntrada, NFeEntradaProdutos nFeEntradaProdutos) {
        nFeEntradaProdutos.setNfeEntrada(this.nFeEntradaService.findById(idNFeEntrada));
        this.nFeEntradaProdutosRepository.save(nFeEntradaProdutos);
    }

    public void update(Long idNFeEntrada, Long idNFeEntradaProdutos, NFeEntradaProdutos nFeEntradaProdutos) {
        nFeEntradaProdutos.setNfeEntrada(this.nFeEntradaService.findById(idNFeEntrada));
        nFeEntradaProdutos.setId(idNFeEntradaProdutos);
        this.nFeEntradaProdutosRepository.save(nFeEntradaProdutos);
    }

    public void delete(Long idNFeEntrada, Long idNFeEntradaProdutos) {
        this.nFeEntradaProdutosRepository.
                delete(this.findByIdNFeEntradaProdutoAndIdNFeEntrada(idNFeEntradaProdutos, idNFeEntrada));
    }

    public NFeEntradaProdutos findByIdNFeEntradaProdutoAndIdNFeEntrada(Long idNFeEntradaProdutos, Long idNFeEntrada) {
        return this.nFeEntradaProdutosRepository.
                findByIdNFeEntradaProdutoAndIdNFeEntrada(idNFeEntradaProdutos, idNFeEntrada);
    }

    public List<NFeEntradaProdutos> findAllByNFeEntrada(Long idNFeEntrada) {
        return this.nFeEntradaProdutosRepository.findAllByNFeEntrada(idNFeEntrada);
    }

    public Page<NFeEntradaProdutos> findAllPaginationByNFeEntrada(Long idNFeEntrada) {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "descricaoProduto");
        return new PageImpl<>(this.nFeEntradaProdutosRepository.findAllByNFeEntrada(idNFeEntrada), pageRequest, size);
    }

    public Page<NFeEntradaProdutos> searchByNFeEntrada(Long idNFeEntrada, String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "descricaoProduto");
        return this.nFeEntradaProdutosRepository.search(idNFeEntrada, searchTerm.toLowerCase(), pageRequest);
    }
}