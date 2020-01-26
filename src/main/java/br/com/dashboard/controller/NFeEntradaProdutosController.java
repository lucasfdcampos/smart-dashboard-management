package br.com.dashboard.controller;

import br.com.dashboard.model.NFeEntradaProdutos;
import br.com.dashboard.service.NFeEntradaProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/nfes-entrada/{idNFeEntrada}/nfe-entrada-produtos")
public class NFeEntradaProdutosController {

    @Autowired
    private NFeEntradaProdutosService nFeEntradaProdutosService;

    @GetMapping(value = "/{idNFeEntradaProduto}")
    @ResponseStatus(HttpStatus.OK)
    public NFeEntradaProdutos getNFeEntradaProduto(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                                   @PathVariable("idNFeEntradaProduto") Long idNFeEntradaProduto) {
        return this.nFeEntradaProdutosService.
                findByIdNFeEntradaProdutoAndIdNFeEntrada(idNFeEntradaProduto, idNFeEntrada);
    }

    @PostMapping(value = "/add")
    public NFeEntradaProdutos addNFeEntradaProduto(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                                   @RequestBody NFeEntradaProdutos nFeEntradaProdutos) {
        this.nFeEntradaProdutosService.save(idNFeEntrada, nFeEntradaProdutos);
        return nFeEntradaProdutos;
    }

    @PutMapping(value = "/update/{idNFeEntradaProduto}")
    @ResponseStatus(HttpStatus.OK)
    public NFeEntradaProdutos updateNFeEntradaProduto(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                                      @PathVariable("idNFeEntradaProduto") Long idNFeEntradaProduto,
                                                      @RequestBody NFeEntradaProdutos nFeEntradaProdutos) {
        this.nFeEntradaProdutosService.update(idNFeEntrada, idNFeEntradaProduto, nFeEntradaProdutos);
        return nFeEntradaProdutos;
    }

    @DeleteMapping(value = "/{idNFe}/produto/{idNFeEntradaProduto}")
    public ResponseEntity<String> deleteNFeEntradaProduto(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                                          @PathVariable("idNFeEntradaProduto") Long idNFeEntradaProduto) {
        this.nFeEntradaProdutosService.delete(idNFeEntrada, idNFeEntradaProduto);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    List<NFeEntradaProdutos> findAll(@PathVariable("idNFeEntrada") Long idNFeEntrada) {
        return this.nFeEntradaProdutosService.findAllByNFeEntrada(idNFeEntrada);
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeEntradaProdutos> findAllPagination(@PathVariable("idNFeEntrada") Long idNFeEntrada) {
        return this.nFeEntradaProdutosService.findAllPaginationByNFeEntrada(idNFeEntrada);
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<NFeEntradaProdutos> search(@PathVariable("idNFeEntrada") Long idNFeEntrada,
                                           @RequestParam("searchTerm") String searchTerm,
                                           @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.nFeEntradaProdutosService.searchByNFeEntrada(idNFeEntrada, searchTerm, page, size);
    }
}