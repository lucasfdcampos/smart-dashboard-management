package br.com.dashboard.controller;

import br.com.dashboard.model.TipoPagamento;
import br.com.dashboard.service.TipoPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/tipopagamento")
public class TipoPagamentoController {

    @Autowired
    private TipoPagamentoService tipoPagamentoService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoPagamento getTipoPagamento(@PathVariable("id") Long id) {
        return this.tipoPagamentoService.findById(id);
    }

    @GetMapping(value = "/descricao/{descricao}")
    @ResponseStatus(HttpStatus.OK)
    public TipoPagamento getTipoPagamentoDescricao(@PathVariable("descricao") String descricao) {
        return this.tipoPagamentoService.findByDescricao(descricao);
    }

    @PostMapping(value = "/add")
    public TipoPagamento addTipoPagamento(@RequestBody TipoPagamento tipoPagamento) {
        this.tipoPagamentoService.save(tipoPagamento);
        return tipoPagamento;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TipoPagamento updateTipoPagamento(@PathVariable("id") Long id, TipoPagamento tipoPagamento) {
        this.tipoPagamentoService.update(id, tipoPagamento);
        return tipoPagamento;
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<String> deleteTipoPagamento(@PathVariable("id") Long id) {
        this.tipoPagamentoService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<TipoPagamento> findAll() {
        return this.tipoPagamentoService.findAll();
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<TipoPagamento> findAllPagination() {
        return this.tipoPagamentoService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<TipoPagamento> search(@RequestParam("searchTerm") String searchTerm,
                                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.tipoPagamentoService.search(searchTerm, page, size);
    }
}