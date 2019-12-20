package br.com.dashboard.service;

import br.com.dashboard.model.ContasPagar;
import br.com.dashboard.repository.ContasPagarRepository;
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
public class ContasPagarService {

    @Autowired
    private ContasPagarRepository contasPagarRepository;

    public ContasPagarService() {
        super();
    }

    public ContasPagarService(ContasPagarRepository contasPagarRepository) {
        super();
        this.contasPagarRepository = contasPagarRepository;
    }

    public void save(ContasPagar contasPagar) {
        this.contasPagarRepository.save(contasPagar);
    }

    public void update(Long id, ContasPagar contasPagar) {
        contasPagar.setId(id);
        this.contasPagarRepository.save(contasPagar);
    }

    public void delete(Long id) {
        this.contasPagarRepository.deleteById(id);
    }

    public ContasPagar findById(Long id) {
        return this.contasPagarRepository.getOne(id);
    }

    public List<ContasPagar> findAll() {
        return this.contasPagarRepository.findAll();
    }

    public List<ContasPagar> findAllByFornecedor(Long idFornecedor) {
        return this.contasPagarRepository.findAllByFornecedor(idFornecedor);
    }

    public List<ContasPagar> findAllByTipoPagamento(Long idTipoPagamento) {
        return this.contasPagarRepository.findAllByTipoPagamento(idTipoPagamento);
    }

    public Page<ContasPagar> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "data_vencimento");
        return new PageImpl<>(this.contasPagarRepository.findAll(), pageRequest, size);
    }

    public Page<ContasPagar> search(Long idFornecedor, String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "data_vencimento");
        return this.contasPagarRepository.search(idFornecedor, searchTerm.toLowerCase(), pageRequest);
    }
}