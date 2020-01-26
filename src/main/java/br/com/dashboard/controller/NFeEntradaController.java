package br.com.dashboard.controller;

import br.com.dashboard.model.NFeEntrada;
import br.com.dashboard.service.NFeEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/nfes-entrada")
public class NFeEntradaController {

    @Autowired
    private NFeEntradaService nFeEntradaService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NFeEntrada getNFeEntrada(@PathVariable("id") Long id) {
        return this.nFeEntradaService.findById(id);
    }

    @GetMapping(value = "/chave/{chave}")
    @ResponseStatus(HttpStatus.OK)
    public NFeEntrada getNFeEntradaChave(@PathVariable("chave") String chave) {
        return this.nFeEntradaService.findByChave(chave);
    }

    @GetMapping(value = "/numero/{fornecedor}/{numero}")
    @ResponseStatus(HttpStatus.OK)
    public NFeEntrada getNFeEntradaFornecedorAndNumero(@PathVariable("idFornecedor") Long idFornecedor,
                                                       @PathVariable("numero") String numero) {
        return this.nFeEntradaService.findByFornecedorAndNumero(idFornecedor, numero);
    }

    @PostMapping(value = "/add")
    public NFeEntrada addNFeEntrada(@RequestBody NFeEntrada nFeEntrada) {
        this.nFeEntradaService.save(nFeEntrada);
        return nFeEntrada;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NFeEntrada updateNFeEntrada(@PathVariable("id") Long id, @RequestBody NFeEntrada nFeEntrada) {
        this.nFeEntradaService.update(id, nFeEntrada);
        return nFeEntrada;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteNFeEntrada(@PathVariable("id") Long id) {
        this.nFeEntradaService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<NFeEntrada> findAll() {
        return this.nFeEntradaService.findAll();
    }

    @GetMapping(value = "/list/{idFornecedor}")
    @ResponseStatus(HttpStatus.OK)
    public List<NFeEntrada> findAllByFornecedor(@PathVariable("idFornecedor") Long idFornecedor) {
        return this.nFeEntradaService.findAllByFornecedor(idFornecedor);
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeEntrada> findAllPagination() {
        return this.nFeEntradaService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeEntrada> search(@RequestParam("idFornecedor") Long idFornecedor,
                                   @RequestParam("searchTerm") String searchTerm,
                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                   @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.nFeEntradaService.search(idFornecedor, searchTerm, page, size);
    }
}