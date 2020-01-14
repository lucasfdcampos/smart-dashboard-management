package br.com.dashboard.controller;

import br.com.dashboard.model.Cliente;
import br.com.dashboard.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente getCliente(@PathVariable("id") Long id) {
        return this.clienteService.findById(id);
    }

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

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente updateCliente(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
        this.clienteService.update(id, cliente);
        return cliente;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable("id") Long id) {
        this.clienteService.delete(id);
        return new ResponseEntity<String>("DELETED", HttpStatus.OK);
    }

    @GetMapping(value = "/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> findAll() {
        return this.clienteService.findAll();
    }

    @GetMapping(value = "/list-pagination")
    @ResponseStatus(HttpStatus.OK)
    public Page<Cliente> findAllPagination() {
        return this.clienteService.findAllPagination();
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<Cliente> search(@RequestParam("searchTerm") String searchTerm,
                                @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "size", required = false, defaultValue = "0") int size) {
        return this.clienteService.search(searchTerm, page, size);
    }
}