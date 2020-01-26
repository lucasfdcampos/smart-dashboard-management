package br.com.dashboard.controller;

import br.com.dashboard.model.NFeEntradaDuplicatas;
import br.com.dashboard.service.NFeEntradaDuplicatasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/nfes-entrada/{idNFeEntrada}/nfe-entrada-duplicatas")
public class NFeEntradaDuplicatasController {

    @Autowired
    private NFeEntradaDuplicatasService nFeEntradaDuplicatasService;

    @GetMapping(value = "/{idNFeEntradaDuplicata}")
    @ResponseStatus(HttpStatus.OK)
    public NFeEntradaDuplicatas getNFeEntradaDuplicata(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                                       @PathVariable("idNFeEntradaDuplicata") Long idNFeEntradaDuplicata) {
        return this.nFeEntradaDuplicatasService.
                findByIdNFeEntradaDuplicataAndIdNFeEntrada(idNFeEntradaDuplicata, idNFeEntrada);
    }

    @PostMapping(value = "/add")
    public NFeEntradaDuplicatas addNFeEntradaDuplicata(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                                       @RequestBody NFeEntradaDuplicatas nFeEntradaDuplicatas) {
        this.nFeEntradaDuplicatasService.save(idNFeEntrada, nFeEntradaDuplicatas);
        return nFeEntradaDuplicatas;
    }

    @PutMapping(value = "/update/{idNFeEntradaDuplicata}")
    @ResponseStatus(HttpStatus.OK)
    public NFeEntradaDuplicatas updateNFeEntradaDuplicata(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                                          @PathVariable("idNFeEntradaDuplicata") Long idNFeEntradaDuplicata,
                                                          @RequestBody NFeEntradaDuplicatas nFeEntradaDuplicatas) {
        this.nFeEntradaDuplicatasService.update(idNFeEntrada, idNFeEntradaDuplicata, nFeEntradaDuplicatas);
        return nFeEntradaDuplicatas;
    }

    @DeleteMapping(value = "/{idNFeEntrada}/duplicata/{idNFeEntradaDuplicata}")
    public ResponseEntity<String> deleteNFeEntradaDuplicata(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                                            @PathVariable("idNFeEntradaDuplicata") Long idNFeEntradaDuplicata) {
        this.nFeEntradaDuplicatasService.delete(idNFeEntrada, idNFeEntradaDuplicata);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    List<NFeEntradaDuplicatas> findAll(@PathVariable("idNFeEntrada") Long idNFeEntrada) {
        return this.nFeEntradaDuplicatasService.findAllByNFeEntrada(idNFeEntrada);
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeEntradaDuplicatas> findAllPagination(@PathVariable("idNFeEntrada") Long idNFeEntrada) {
        return this.nFeEntradaDuplicatasService.findAllPaginationByNFeEntrada(idNFeEntrada);
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeEntradaDuplicatas> search(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                             @RequestParam("searchTerm") String searchTerm,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.nFeEntradaDuplicatasService.searchByNFeEntrada(idNFeEntrada, searchTerm, page, size);
    }
}