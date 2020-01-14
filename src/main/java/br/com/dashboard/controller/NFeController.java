package br.com.dashboard.controller;

import br.com.dashboard.model.NFe;
import br.com.dashboard.service.NFeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/nfes")
public class NFeController {

    @Autowired
    private NFeService nFeService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NFe getNFe(@PathVariable("id") Long id) {
        return this.nFeService.findById(id);
    }

    @GetMapping(value = "/chave/{chave}")
    @ResponseStatus(HttpStatus.OK)
    public NFe getNFeChave(@PathVariable("chave") String chave) {
        return this.nFeService.findByChave(chave);
    }

    @GetMapping(value = "/numero/{numero}")
    @ResponseStatus(HttpStatus.OK)
    public NFe getNFeNumero(@PathVariable("numero") String numero) {
        return this.nFeService.findByNumero(numero);
    }

    @PostMapping(value = "/add")
    public NFe addNFe(@RequestBody NFe nFe) {
        this.nFeService.save(nFe);
        return nFe;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NFe updateNFe(@PathVariable("id") Long id, @RequestBody NFe nFe) {
        this.nFeService.update(id, nFe);
        return nFe;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteNFe(@PathVariable("id") Long id) {
        this.nFeService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<NFe> findAll() {
        return this.nFeService.findAll();
    }

    @GetMapping(value = "/list/{idCliente}")
    @ResponseStatus(HttpStatus.OK)
    public List<NFe> findAllByCliente(@PathVariable("idCliente") Long idCliente) {
        return this.nFeService.findAllByCliente(idCliente);
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFe> findAllPagination() {
        return this.nFeService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFe> search(@RequestParam("searchTerm") String searchTerm,
                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.nFeService.search(searchTerm, page, size);
    }
}