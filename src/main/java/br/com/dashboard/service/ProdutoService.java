package br.com.dashboard.service;

import br.com.dashboard.model.Produto;
import br.com.dashboard.repository.ProdutoRepository;
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
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoService() {
        super();
    }

    public ProdutoService(ProdutoRepository produtoRepository) {
        super();
        this.produtoRepository = produtoRepository;
    }

    public Page<Produto> searchCodigo(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "codigoEmpresa");
        return this.produtoRepository.searchCodigo(searchTerm.toLowerCase(), pageRequest);
    }

    public Page<Produto> searchDescricao(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "descricao");
        return this.produtoRepository.searchDescricao(searchTerm.toLowerCase(), pageRequest);
    }





    public void save(Produto produto) {
        this.produtoRepository.save(produto);
    }

    public void update(Long id, Produto produto) {
        produto.setId(id);
        this.produtoRepository.save(produto);
    }

    public void delete(Long id) {
        this.produtoRepository.deleteById(id);
    }

    public Produto findById(Long id) {
        return this.produtoRepository.getOne(id);
    }

    public Produto findByDescricao(String descricao) {
        return this.produtoRepository.findByDescricao(descricao);
    }

    public List<Produto> findAll() {
        return this.produtoRepository.findAll();
    }

    public Page<Produto> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "descricao");
        return new PageImpl<>(this.produtoRepository.findAll(), pageRequest, size);
    }

    public Page<Produto> search(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "descricao");
        return this.produtoRepository.search(searchTerm.toLowerCase(), pageRequest);
    }
}