package br.com.dashboard.controller;

import br.com.dashboard.model.Fornecedor;
import br.com.dashboard.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fornecedor getFornecedor(@PathVariable("id") Long id) {
        return this.fornecedorService.findById(id);
    }

    @GetMapping(value = "/nome/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public Fornecedor getFornecedorNome(@PathVariable("nome") String nome) {
        return this.fornecedorService.findByNome(nome);
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    @ResponseStatus(HttpStatus.OK)
    public Fornecedor getFornecedorCnpj(@PathVariable("cnpj") String cnpj) {
        return this.fornecedorService.findByCnpj(cnpj);
    }

    @PostMapping(value = "/add")
    public Fornecedor addFornecedor(@RequestBody Fornecedor fornecedor) {
        this.fornecedorService.save(fornecedor);
        return fornecedor;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fornecedor updateFornecedor(@PathVariable("id") Long id, @RequestBody Fornecedor fornecedor) {
        this.fornecedorService.update(id, fornecedor);
        return fornecedor;
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<String> deleteFornecedor(@PathVariable("id") Long id) {
        this.fornecedorService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Fornecedor> findAll() {
        return this.fornecedorService.findAll();
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<Fornecedor> findAllPagination() {
        return this.fornecedorService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<Fornecedor> search(@RequestParam("searchTerm") String searchTerm,
                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                   @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.fornecedorService.search(searchTerm, page, size);
    }
}