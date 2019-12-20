package br.com.dashboard.controller;

import br.com.dashboard.model.ContasReceber;
import br.com.dashboard.service.ContasReceberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/contasreceber")
public class ContasReceberController {

    @Autowired
    private ContasReceberService contasReceberService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContasReceber getContaReceber(@PathVariable("id") Long id) {
        return this.contasReceberService.findById(id);
    }

    @PostMapping(value = "/add")
    public ContasReceber addContaReceber(@RequestBody ContasReceber contaReceber) {
        this.contasReceberService.save(contaReceber);
        return contaReceber;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContasReceber updateContaReceber(@PathVariable("id") Long id, @RequestBody ContasReceber contaReceber) {
        this.contasReceberService.update(id, contaReceber);
        return contaReceber;
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<String> deleteContaReceber(@PathVariable("id") Long id) {
        this.contasReceberService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<ContasReceber> findAll() {
        return this.contasReceberService.findAll();
    }

    @GetMapping(value = "/list-cliente/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContasReceber> findAllByCliente(@PathVariable("idCliente") Long idCliente) {
        return this.contasReceberService.findAllByCliente(idCliente);
    }

    @GetMapping(value = "/list-tipopagamento/{idTipoPagamento}")
    @ResponseStatus(HttpStatus.OK)
    public List<ContasReceber> findAllByTipoPagamento(@PathVariable("idTipoPagamento") Long idTipoPagamento) {
        return this.contasReceberService.findAllByTipoPagamento(idTipoPagamento);
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContasReceber> findAllPagination() {
        return this.contasReceberService.findAllPagination();
    }

    @GetMapping(value = "/search/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public Page<ContasReceber> search(@PathVariable("idCliente") Long idCliente,
                                      @RequestParam("searchTerm") String searchTerm,
                                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.contasReceberService.search(idCliente, searchTerm, page, size);
    }
}
