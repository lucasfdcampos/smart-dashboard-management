package br.com.dashboard.controller;

import br.com.dashboard.model.Municipio;
import br.com.dashboard.repository.MunicipioRepository;
import br.com.dashboard.service.MunicipioService;
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
@RequestMapping(path = "municipio")
public class MunicipioController {

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private MunicipioRepository municipioRepository;

    @GetMapping("/list")
    public ModelAndView listarMunicipios(ModelMap model, HttpServletRequest request) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        Page<Municipio> municipioPage = municipioRepository.findAll(PageRequest.of(page, size));

        model.addAttribute("municipios", municipioPage);
        model.addAttribute("conteudo", "/cities/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/nome")
    public ModelAndView listarMunicipiosPorNome(ModelMap model, HttpServletRequest request,
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
            return new ModelAndView("redirect:/cities/list");
        }

        model.addAttribute("municipios", this.municipioService.searchNome(nome, page, size));
        model.addAttribute("conteudo", "/cities/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/codigo")
    public ModelAndView listarMunicipiosPorCodigo(ModelMap model, HttpServletRequest request,
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
            return new ModelAndView("redirect:/cities/list");
        }

        model.addAttribute("municipios", this.municipioService.searchCodigo(codigo, page, size));
        model.addAttribute("conteudo", "/cities/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Municipio getMunicipio(String codigo) {
        return this.municipioService.findByCodigo(codigo);
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

    @PutMapping(value = "/{codigo}")
    @ResponseStatus(HttpStatus.OK)
    public Municipio updateMunicipio(@PathVariable("codigo") String codigo, @RequestBody Municipio municipio) {
        this.municipioService.update(codigo, municipio);
        return municipio;
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<String> deleteMunicipio(@PathVariable("codigo") String codigo) {
        try {
            this.municipioService.delete(codigo);
            return new ResponseEntity<String>("DELETED", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/list-api")
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