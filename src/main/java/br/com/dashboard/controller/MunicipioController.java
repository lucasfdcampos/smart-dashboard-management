package br.com.dashboard.controller;

import br.com.dashboard.model.Municipio;
import br.com.dashboard.service.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioService municipioService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Municipio getMunicipio(@PathVariable("codigo") String codigo) {
        return this.municipioService.findById(codigo);
    }

    @GetMapping(value = "/nome/{nome}/uf/{uf}")
    @ResponseStatus(HttpStatus.OK)
    public Municipio getMunicipioNomeAndUf(@PathVariable("nome") String nome, @PathVariable("uf") String uf) {
        return this.municipioService.findByNomeAndUF(nome, uf);
    }

    @PostMapping(value = "/add")
    public Municipio addMunicipio(@RequestBody Municipio municipio) {
        this.municipioService.save(municipio);
        return municipio;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Municipio updateMunicipio(@PathVariable("codigo") String codigo, @RequestBody Municipio municipio) {
        this.municipioService.update(codigo, municipio);
        return municipio;
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<String> deleteMunicipio(@PathVariable("codigo") String codigo) {
        this.municipioService.delete(codigo);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Municipio> findAll() {
        return this.municipioService.findAll();
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<Municipio> findAllPagination() {
        return this.municipioService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<Municipio> search(@RequestParam("searchTerm") String searchTerm,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                  @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.municipioService.search(searchTerm, page, size);
    }
}