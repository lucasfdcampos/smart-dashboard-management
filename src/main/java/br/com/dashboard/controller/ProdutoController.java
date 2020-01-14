package br.com.dashboard.controller;

import br.com.dashboard.model.Produto;
import br.com.dashboard.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getProduto(@PathVariable("id") Long id) {
        return this.produtoService.findById(id);
    }

    @GetMapping(value = "/descricao/{descricao}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getProdutoDescricao(@PathVariable("descricao") String descricao) {
        return this.produtoService.findByDescricao(descricao);
    }

    @PostMapping(value = "/add")
    public Produto addProduto(@RequestBody Produto produto) {
        this.produtoService.save(produto);
        return produto;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto updateProduto(@PathVariable("id") Long id, @RequestBody Produto produto) {
        this.produtoService.update(id, produto);
        return produto;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduto(@PathVariable("id") Long id) {
        this.produtoService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> findAll() {
        return this.produtoService.findAll();
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<Produto> findAllPagination() {
        return this.produtoService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<Produto> search(@RequestParam("searchTerm") String searchTerm,
                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.produtoService.search(searchTerm, page, size);
    }
}