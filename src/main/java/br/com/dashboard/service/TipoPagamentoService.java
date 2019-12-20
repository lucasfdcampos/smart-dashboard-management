package br.com.dashboard.service;

import br.com.dashboard.model.TipoPagamento;
import br.com.dashboard.repository.TipoPagamentoRepository;
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
public class TipoPagamentoService {

    @Autowired
    private TipoPagamentoRepository tipoPagamentoRepository;

    public TipoPagamentoService() {
        super();
    }

    public TipoPagamentoService(TipoPagamentoRepository tipoPagamentoRepository) {
        super();
        this.tipoPagamentoRepository = tipoPagamentoRepository;
    }

    public void save(TipoPagamento tipoPagamento) {
        this.tipoPagamentoRepository.save(tipoPagamento);
    }

    public void update(Long id, TipoPagamento tipoPagamento) {
        tipoPagamento.setId(id);
        this.tipoPagamentoRepository.save(tipoPagamento);
    }

    public void delete(Long id) {
        this.tipoPagamentoRepository.deleteById(id);
    }

    public TipoPagamento findById(Long id) {
        return this.tipoPagamentoRepository.getOne(id);
    }

    public TipoPagamento findByDescricao(String descricao) {
        return this.tipoPagamentoRepository.findByDescricao(descricao);
    }

    public List<TipoPagamento> findAll() {
        return this.tipoPagamentoRepository.findAll();
    }

    public Page<TipoPagamento> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "descricao");
        return new PageImpl<>(this.tipoPagamentoRepository.findAll(), pageRequest, size);
    }

    public Page<TipoPagamento> search(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "descricao");
        return this.tipoPagamentoRepository.search(searchTerm.toLowerCase(), pageRequest);
    }
}
