package br.com.dashboard.controller;

import br.com.dashboard.model.Fornecedor;
import br.com.dashboard.repository.FornecedorRepository;
import br.com.dashboard.service.FornecedorService;
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
@RequestMapping(path = "fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping("/list")
    public ModelAndView listarFornecedores(ModelMap model, HttpServletRequest request) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        Page<Fornecedor> fornecedorPage = fornecedorRepository.findAll(PageRequest.of(page, size));

        model.addAttribute("fornecedores", fornecedorPage);
        model.addAttribute("conteudo", "/providers/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/nome")
    public ModelAndView listarFornecedoresPorNome(ModelMap model, HttpServletRequest request,
                                                  @RequestParam(value = "nome") String nome) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        if (nome == null) {
            return new ModelAndView("redirect:/providers/list");
        }

        model.addAttribute("fornecedores", this.fornecedorService.searchNome(nome, page, size));
        model.addAttribute("conteudo", "/providers/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/cnpj")
    public ModelAndView listarFornecedoresPorCnpj(ModelMap model, HttpServletRequest request,
                                                  @RequestParam(value = "cnpj") String cnpj) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        if (cnpj == null) {
            return new ModelAndView("redirect:/providers/list");
        }

        model.addAttribute("fornecedores", this.fornecedorService.searchCnpj(cnpj, page, size));
        model.addAttribute("conteudo", "/providers/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Fornecedor getFornecedor(Long id) {
        return this.fornecedorService.findById(id);
    }




























    // ####### METODOS API-REST

    @GetMapping(value = "/nome/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public Fornecedor getFornecedorNome(@PathVariable("nome") String nome) {
        return this.fornecedorService.findByNome(nome);
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    @ResponseStatus(HttpStatus.OK)
    public Fornecedor getFornecedorCnpj(@PathVariable("cnpj") String cnpj) {
        return this.fornecedorService.findByCnpj(cnpj);
    }

    @PostMapping(value = "/add")
    public Fornecedor addFornecedor(@RequestBody Fornecedor fornecedor) {
        this.fornecedorService.save(fornecedor);
        return fornecedor;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fornecedor updateFornecedor(@PathVariable("id") Long id, @RequestBody Fornecedor fornecedor) {
        this.fornecedorService.update(id, fornecedor);
        return fornecedor;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteFornecedor(@PathVariable("id") Long id) {
        this.fornecedorService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list-api")
    @ResponseStatus(HttpStatus.OK)
    public List<Fornecedor> findAll() {
        return this.fornecedorService.findAll();
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<Fornecedor> findAllPagination() {
        return this.fornecedorService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<Fornecedor> search(@RequestParam("searchTerm") String searchTerm,
                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                   @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.fornecedorService.searchNome(searchTerm, page, size);
    }
}