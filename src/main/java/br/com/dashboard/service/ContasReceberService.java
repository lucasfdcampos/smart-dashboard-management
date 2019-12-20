package br.com.dashboard.service;

import br.com.dashboard.model.ContasReceber;
import br.com.dashboard.repository.ContasReceberRepository;
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
public class ContasReceberService {

    @Autowired
    private ContasReceberRepository contasReceberRepository;

    public ContasReceberService() {
        super();
    }

    public ContasReceberService(ContasReceberRepository contasReceberRepository) {
        super();
        this.contasReceberRepository = contasReceberRepository;
    }

    public void save(ContasReceber contasReceber) {
        this.contasReceberRepository.save(contasReceber);
    }

    public void update(Long id, ContasReceber contasReceber) {
        contasReceber.setId(id);
        this.contasReceberRepository.save(contasReceber);
    }

    public void delete(Long id) {
        this.contasReceberRepository.deleteById(id);
    }

    public ContasReceber findById(Long id) {
        return this.contasReceberRepository.getOne(id);
    }

    public List<ContasReceber> findAll() {
        return this.contasReceberRepository.findAll();
    }

    public List<ContasReceber> findAllByCliente(Long idCliente) {
        return this.contasReceberRepository.findAllByCliente(idCliente);
    }

    public List<ContasReceber> findAllByTipoPagamento(Long idTipoPagamento) {
        return this.contasReceberRepository.findAllByTipoPagamento(idTipoPagamento);
    }

    public Page<ContasReceber> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "data_vencimento");
        return new PageImpl<>(this.contasReceberRepository.findAll(), pageRequest, size);
    }

    public Page<ContasReceber> search(Long idCliente, String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "data_vencimento");
        return this.contasReceberRepository.search(idCliente, searchTerm.toLowerCase(), pageRequest);
    }
}