package br.com.dashboard.controller;

import br.com.dashboard.model.NFeProdutos;
import br.com.dashboard.service.NFeProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/nfes/{idNFe}/nfeprodutos")
public class NFeProdutosController {

    @Autowired
    private NFeProdutosService nFeProdutosService;

    @GetMapping(value = "/{idNFeProduto}")
    @ResponseStatus(HttpStatus.OK)
    public NFeProdutos getNFeProduto(@PathVariable("idNFe") Long idNFe,
                                     @PathVariable("idNFeProduto") Long idNFeProduto) {
        return this.nFeProdutosService.findByIdNFeProdutoAndIdNFe(idNFeProduto, idNFe);
    }

    @PostMapping(value = "/add")
    public NFeProdutos addNFeProduto(@PathVariable("idNFe") Long idNFe,
                                     @RequestBody NFeProdutos nFeProdutos) {
        this.nFeProdutosService.save(idNFe, nFeProdutos);
        return nFeProdutos;
    }

    @PutMapping(value = "/update/{idNFeProduto}")
    @ResponseStatus(HttpStatus.OK)
    public NFeProdutos updateNFeProduto(@PathVariable("idNFe") Long idNFe,
                                        @PathVariable("idNFeProduto") Long idNFeProduto,
                                        @RequestBody NFeProdutos nFeProdutos) {
        this.nFeProdutosService.update(idNFe, idNFeProduto, nFeProdutos);
        return nFeProdutos;
    }

    @DeleteMapping(value = "/{idNFe}/produto/{idNFeProduto}")
    public ResponseEntity<String> deleteNFeProduto(@PathVariable("idNFe") Long idNFe,
                                                   @PathVariable("idNFeProduto") Long idNFeProduto) {
        this.nFeProdutosService.delete(idNFe, idNFeProduto);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    List<NFeProdutos> findAll(@PathVariable("idNFe") Long idNFe) {
        return this.nFeProdutosService.findAllByNFe(idNFe);
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeProdutos> findAllPagination(@PathVariable("idNFe") Long idNFe) {
        return this.nFeProdutosService.findAllPaginationByNFe(idNFe);
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeProdutos> search(@PathVariable("idNFe") Long idNFe,
                                    @RequestParam("searchTerm") String searchTerm,
                                    @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.nFeProdutosService.searchByNFe(idNFe, searchTerm, page, size);
    }
}