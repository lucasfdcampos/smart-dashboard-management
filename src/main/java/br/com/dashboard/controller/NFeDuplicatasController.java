package br.com.dashboard.controller;

import br.com.dashboard.model.NFeDuplicatas;
import br.com.dashboard.service.NFeDuplicatasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/nfes/{idNFe}/nfeduplicatas")
public class NFeDuplicatasController {

    @Autowired
    private NFeDuplicatasService nFeDuplicatasService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NFeDuplicatas getNFeDuplicata(@PathVariable("idNFe") Long idNFe,
                                         @PathVariable("idNFeDuplicata") Long idNFeDuplicata) {
        return this.nFeDuplicatasService.findByIdNFeDuplicataAndIdNFe(idNFeDuplicata, idNFe);
    }

    @PostMapping(value = "/add")
    public NFeDuplicatas addNFeDuplicata(@PathVariable("idNFe") Long idNFe,
                                         @RequestBody NFeDuplicatas nFeDuplicatas) {
        this.nFeDuplicatasService.save(idNFe, nFeDuplicatas);
        return nFeDuplicatas;
    }

    @PutMapping(value = "/update/{idNFeDuplicata}")
    @ResponseStatus(HttpStatus.OK)
    public NFeDuplicatas updateNFeDuplicata(@PathVariable("idNFe") Long idNFe,
                                            @PathVariable("idNFeDuplicata") Long idNFeDuplicata,
                                            @RequestBody NFeDuplicatas nFeDuplicatas) {
        this.nFeDuplicatasService.update(idNFe, idNFeDuplicata, nFeDuplicatas);
        return nFeDuplicatas;
    }

    @DeleteMapping(value = "/{idNFe}/duplicata/{idNFeDuplicata}")
    public ResponseEntity<String> deleteNFeDuplicata(@PathVariable("idNFe") Long idNFe,
                                                     @PathVariable("idNFeDuplicata") Long idNFeDuplicata) {
        this.nFeDuplicatasService.delete(idNFe, idNFeDuplicata);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<NFeDuplicatas> findAll(@PathVariable("idNFe") Long idNFe) {
        return this.nFeDuplicatasService.findAllByNFe(idNFe);
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeDuplicatas> findAllPagination(@PathVariable("idNFe") Long idNFe) {
        return this.nFeDuplicatasService.findAllPaginationByNFe(idNFe);
    }

    @GetMapping(value = "/search/{idNFe}")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeDuplicatas> search(@PathVariable("idNFe") Long idNFe,
                                      @RequestParam("searchTerm") String searchTerm,
                                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                      @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.nFeDuplicatasService.searchByNFe(idNFe, searchTerm, page, size);
    }
}
