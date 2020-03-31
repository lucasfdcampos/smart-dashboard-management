package br.com.dashboard.controller;

import br.com.dashboard.model.Transportadora;
import br.com.dashboard.repository.TransportadoraRepository;
import br.com.dashboard.service.TransportadoraService;
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
@RequestMapping(path = "transportadora")
public class TransportadoraController {

    @Autowired
    private TransportadoraService transportadoraService;

    @Autowired
    private TransportadoraRepository transportadoraRepository;

    @GetMapping("/list")
    public ModelAndView listarTransportadoras(ModelMap model, HttpServletRequest request) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        Page<Transportadora> transportadoraPage = transportadoraRepository.findAll(PageRequest.of(page, size));

        model.addAttribute("transportadoras", transportadoraPage);
        model.addAttribute("conteudo", "/shippings/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/nome")
    public ModelAndView listarTransportadorasPorNome(ModelMap model, HttpServletRequest request,
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
            return new ModelAndView("redirect:/shippings/list");
        }

        model.addAttribute("transportadoras", transportadoraService.searchNome(nome, page, size));
        model.addAttribute("conteudo", "/shippings/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/cnpj")
    public ModelAndView ListarTransportadorasPorCnpj(ModelMap model, HttpServletRequest request,
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
            return new ModelAndView("redirect:/shippings/list");
        }

        model.addAttribute("transportadoras", transportadoraService.searchCnpj(cnpj, page, size));
        model.addAttribute("conteudo", "/shippings/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Transportadora getTransportadora(Long id) {
        return this.transportadoraService.findById(id);
    }


    // ####### METODOS API-REST

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
    public ResponseEntity<String> deleteTransportadora(@PathVariable("id") Long id) {
        this.transportadoraService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list-api")
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