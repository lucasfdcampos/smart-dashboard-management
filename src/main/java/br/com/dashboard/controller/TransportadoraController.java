package br.com.dashboard.controller;

import br.com.dashboard.model.Transportadora;
import br.com.dashboard.service.TransportadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/transportadoras")
public class TransportadoraController {

    @Autowired
    private TransportadoraService transportadoraService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transportadora getTransportadora(@PathVariable("id") Long id) {
        return this.transportadoraService.findById(id);
    }

    @GetMapping(value = "/nome/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public Transportadora getTransportadoraNome(@PathVariable("nome") String nome) {
        return this.transportadoraService.findByNome(nome);
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    @ResponseStatus(HttpStatus.OK)
    public Transportadora getTransportadoraCnpj(@PathVariable("cnpj") String cnpj) {
        return this.transportadoraService.findByCnpj(cnpj);
    }

    @PostMapping(value = "/add")
    public Transportadora addTransportadora(@RequestBody Transportadora transportadora) {
        this.transportadoraService.save(transportadora);
        return transportadora;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transportadora updateTransportadora(@PathVariable("id") Long id,
                                               @RequestBody Transportadora transportadora) {
        this.transportadoraService.update(id, transportadora);
        return transportadora;
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<String> deleteTransportadora(@PathVariable("id") Long id) {
        this.transportadoraService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Transportadora> findAll() {
        return this.transportadoraService.findAll();
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<Transportadora> findAllPagination() {
        return this.transportadoraService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<Transportadora> search(@RequestParam("searchTerm") String searchTerm,
                                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.transportadoraService.search(searchTerm, page, size);
    }
}