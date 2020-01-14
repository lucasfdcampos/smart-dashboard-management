package br.com.dashboard.controller;

import br.com.dashboard.model.ContasPagar;
import br.com.dashboard.service.ContasPagarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/contaspagar")
public class ContasPagarController {

    @Autowired
    private ContasPagarService contasPagarService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContasPagar getContaPagar(@PathVariable("id") Long id) {
        return this.contasPagarService.findById(id);
    }

    @PostMapping(value = "/add")
    public ContasPagar addContaPagar(@RequestBody ContasPagar contaPagar) {
        this.contasPagarService.save(contaPagar);
        return contaPagar;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContasPagar updateContaPagar(@PathVariable("id") Long id, @RequestBody ContasPagar contaPagar) {
        this.contasPagarService.update(id, contaPagar);
        return contaPagar;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteContaPagar(@PathVariable("id") Long id) {
        this.contasPagarService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ContasPagar> findAll() {
        return this.contasPagarService.findAll();
    }

    @GetMapping(value = "/list-fornecedor/{idFornecedor}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContasPagar> findAllByFornecedor(@PathVariable("idFornecedor") Long idFornecedor) {
        return this.contasPagarService.findAllByFornecedor(idFornecedor);
    }

    @GetMapping(value = "/list-tipopagamento/{idTipoPagamento}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContasPagar> findAllByTipoPagamento(@PathVariable("idTipoPagamento") Long idTipoPagamento) {
        return this.contasPagarService.findAllByTipoPagamento(idTipoPagamento);
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContasPagar> findAllPagination() {
        return this.contasPagarService.findAllPagination();
    }

    @GetMapping(value = "/search/{idFornecedor}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContasPagar> search(@PathVariable("idNFornecedor") Long idFornecedor,
                                    @RequestParam("searchTerm") String searchTerm,
                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.contasPagarService.search(idFornecedor, searchTerm, page, size);
    }
}