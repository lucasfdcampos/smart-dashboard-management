package br.com.dashboard.service;

import br.com.dashboard.model.Cliente;
import br.com.dashboard.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteService() {
        super();
    }

    public ClienteService(ClienteRepository clienteRepository) {
        super();
        this.clienteRepository = clienteRepository;
    }

    public void save(Cliente cliente) {
        this.clienteRepository.save(cliente);
    }

    public void update(Long id, Cliente cliente) {
        cliente.setId(id);
        this.clienteRepository.save(cliente);
    }

    public void delete(Long id) {
        this.clienteRepository.deleteById(id);
    }

    public Cliente findById(Long id) {
        return this.clienteRepository.getOne(id);
    }

    public Cliente findByNome(String nome) {
        return this.clienteRepository.findByNome(nome);
    }

    public Cliente findByCnpj(String cnpj) {
        return this.clienteRepository.findByCnpj(cnpj);
    }

    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }

    public Page<Cliente> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return new PageImpl<>(this.clienteRepository.findAll(), pageRequest, size);
    }

    public Page<Cliente> search(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return this.clienteRepository.search(searchTerm.toLowerCase(), pageRequest);
    }
}