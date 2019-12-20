package br.com.dashboard.service;

import br.com.dashboard.model.NFeProdutos;
import br.com.dashboard.repository.NFeProdutosRepository;
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
public class NFeProdutosService {

    @Autowired
    private NFeProdutosRepository nFeProdutosRepository;

    @Autowired
    private NFeService nFeService;

    public NFeProdutosService() {
        super();
    }

    public NFeProdutosService(NFeProdutosRepository nFeProdutosRepository, NFeService nFeService) {
        super();
        this.nFeProdutosRepository = nFeProdutosRepository;
        this.nFeService = nFeService;
    }

    public void save(Long idNFe, NFeProdutos nFeProdutos) {
        nFeProdutos.setNfe(nFeService.findById(idNFe));
        this.nFeProdutosRepository.save(nFeProdutos);
    }

    public void update(Long idNFe, Long idNFeProdutos, NFeProdutos nFeProdutos) {
        nFeProdutos.setNfe(nFeService.findById(idNFe));
        nFeProdutos.setId(idNFeProdutos);
        this.nFeProdutosRepository.save(nFeProdutos);
    }

    public void delete(Long idNFe, Long idNFeProdutos) {
        this.nFeProdutosRepository.delete(this.findByIdNFeProdutoAndIdNFe(idNFeProdutos, idNFe));
    }

    public NFeProdutos findByIdNFeProdutoAndIdNFe(Long idNFeProdutos, Long idNFe) {
        return this.nFeProdutosRepository.findByIdNFeProdutoAndIdNFe(idNFeProdutos, idNFe);
    }

    public List<NFeProdutos> findAllByNFe(Long idNFe) {
        return this.nFeProdutosRepository.findAllByNFe(idNFe);
    }

    public Page<NFeProdutos> findAllPaginationByNFe(Long idNFe) {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "produto");
        return new PageImpl<>(this.nFeProdutosRepository.findAllByNFe(idNFe), pageRequest, size);
    }

    public Page<NFeProdutos> searchByNFe(Long idNFe, String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "produto");
        return this.nFeProdutosRepository.search(idNFe, searchTerm.toLowerCase(), pageRequest);
    }
}