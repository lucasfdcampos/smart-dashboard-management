package br.com.dashboard.controller;

import br.com.dashboard.model.Produto;
import br.com.dashboard.repository.ProdutoRepository;
import br.com.dashboard.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/list")
    public ModelAndView listarProdutos(ModelMap model, HttpServletRequest request) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        Page<Produto> produtoPage = produtoRepository.findAll(PageRequest.of(page, size));

        model.addAttribute("produtos", produtoPage);
        model.addAttribute("conteudo", "/products/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/codigo")
    public ModelAndView listarProdutosPorCodigo(ModelMap model, HttpServletRequest request,
                                                @RequestParam(value = "codigo") String codigo) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        if (codigo == null) {
            return new ModelAndView("redirect:/produto/list");
        }

        model.addAttribute("produtos", this.produtoService.searchCodigo(codigo, page, size));
        model.addAttribute("conteudo", "/products/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/descricao")
    public ModelAndView listarProdutosPorDescricao(ModelMap model, HttpServletRequest request,
                                                   @RequestParam(value = "descricao") String descricao) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        if (descricao == null) {
            return new ModelAndView("redirect:/produto/list");
        }

        model.addAttribute("produtos", this.produtoService.searchDescricao(descricao, page, size));
        model.addAttribute("conteudo", "/products/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Produto getProduto(Long id) {
        return this.produtoService.findById(id);
    }


    // ####### METODOS API-REST

    @GetMapping(value = "/descricao/{descricao}")
    @ResponseStatus(HttpStatus.OK)
    public Produto getProdutoDescricao(@PathVariable("descricao") String descricao) {
        return this.produtoService.findByDescricao(descricao);
    }

    @PostMapping(value = "/add")
    public Produto addProduto(@RequestBody Produto produto) {
        this.produtoService.save(produto);
        return produto;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Produto updateProduto(@PathVariable("id") Long id, @RequestBody Produto produto) {
        this.produtoService.update(id, produto);
        return produto;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteProduto(@PathVariable("id") Long id) {
        this.produtoService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list-api")
    @ResponseStatus(HttpStatus.OK)
    public List<Produto> findAll() {
        return this.produtoService.findAll();
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<Produto> findAllPagination() {
        return this.produtoService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<Produto> search(@RequestParam("searchTerm") String searchTerm,
                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.produtoService.search(searchTerm, page, size);
    }
}