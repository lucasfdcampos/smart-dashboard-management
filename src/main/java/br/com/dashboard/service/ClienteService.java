package br.com.dashboard.service;

import br.com.dashboard.model.Cliente;
import br.com.dashboard.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    public Page<Cliente> findAllPagination(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return new PageImpl<>(this.clienteRepository.findAll(), pageRequest, size);
    }

    public Page<Cliente> searchNome(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "nome");
        return this.clienteRepository.searchNome(searchTerm.toLowerCase(), pageRequest);
    }

    public Object searchCnpj(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "cnpj");
        return this.clienteRepository.searchCnpj(searchTerm.toLowerCase(), pageRequest);
    }

    public Page<Cliente> findPaginated(Pageable pageable) {

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<Cliente> list = this.clienteRepository.findAll();

        Page<Cliente> clientePage = new PageImpl<Cliente>(list, PageRequest.of(currentPage, pageSize), list.size());

        return clientePage;
    }
}