package br.com.dashboard.controller;

import br.com.dashboard.model.Cliente;
import br.com.dashboard.repository.ClienteRepository;
import br.com.dashboard.service.ClienteService;
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
@RequestMapping(path = "cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/list")
    public ModelAndView listarClientes(ModelMap model, HttpServletRequest request) {

        int page = 0; // default page number is 0 (yes it is weird)
        int size = 10; // default page size is 5

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        Page<Cliente> clientePage = clienteRepository.findAll(PageRequest.of(page, size));

        model.addAttribute("clientes", clientePage);
        model.addAttribute("conteudo", "/clients/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/nome")
    public ModelAndView listarClientesPorNome(ModelMap model, HttpServletRequest request,
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
            return new ModelAndView("redirect:/cliente/list");
        }

        model.addAttribute("clientes", this.clienteService.searchNome(nome, page, size));
        model.addAttribute("conteudo", "/clients/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/cnpj")
    public ModelAndView listarClientesPorCnpj(ModelMap model, HttpServletRequest request,
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
            return new ModelAndView("redirect:/cliente/list");
        }

        model.addAttribute("clientes", this.clienteService.searchCnpj(cnpj, page, size));
        model.addAttribute("conteudo", "/clients/list");

        return new ModelAndView("index2", model);
    }

    @GetMapping("/findOne")
    @ResponseBody
    public Cliente getCliente(Long id) {
        return this.clienteService.findById(id);
    }














    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public String updateCliente(Cliente cliente) {
        this.clienteService.update(cliente.getId(), cliente);
        return "redirect:/list";
    }

    // ####### METODOS API-REST


    @GetMapping(value = "/nome/{nome}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente getClienteNome(@PathVariable("nome") String nome) {
        return this.clienteService.findByNome(nome);
    }

    @GetMapping(value = "/cnpj/{cnpj}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente getClienteCnpj(@PathVariable("cnpj") String cnpj) {
        return this.clienteService.findByCnpj(cnpj);
    }

    @PostMapping(value = "/add")
    public Cliente addCliente(@RequestBody Cliente cliente) {
        this.clienteService.save(cliente);
        return cliente;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable("id") Long id) {
        this.clienteService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list-api")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> findAll() {
        return this.clienteService.findAll();
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<Cliente> findAllPagination(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                           @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.clienteService.findAllPagination(page, size);
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<Cliente> search(@RequestParam("searchTerm") String searchTerm,
                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.clienteService.searchNome(searchTerm, page, size);
    }
}