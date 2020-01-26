package br.com.dashboard.service;

import br.com.dashboard.model.NFe;
import br.com.dashboard.model.NFeDuplicatas;
import br.com.dashboard.model.NFeProdutos;
import br.com.dashboard.repository.NFeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class NFeService {

    @Autowired
    private NFeRepository nFeRepository;

    @Autowired
    private NFeProdutosService nFeProdutosService;

    @Autowired
    private NFeDuplicatasService nFeDuplicatasService;

    public NFeService() {
        super();
    }

    public NFeService(NFeRepository nFeRepository) {
        super();
        this.nFeRepository = nFeRepository;
    }

    public void save(NFe nFe) {
        this.nFeRepository.save(nFe);

        Iterator<NFeProdutos> nFeProdutosIterator = nFe.getNfeProdutos().iterator();
        while (nFeProdutosIterator.hasNext()) {
            NFeProdutos nFeProdutos = nFeProdutosIterator.next();
            nFeProdutos.setNfe(nFe);

            nFeProdutosService.save(nFe.getId(), nFeProdutos);
        }

        Iterator<NFeDuplicatas> nFeDuplicatasIterator = nFe.getNfeDuplicatas().iterator();
        while (nFeDuplicatasIterator.hasNext()) {
            NFeDuplicatas nFeDuplicatas = nFeDuplicatasIterator.next();
            nFeDuplicatas.setNfe(nFe);

            nFeDuplicatasService.save(nFe.getId(), nFeDuplicatas);
        }
    }

    public void update(Long id, NFe nFe) {
        nFe.setId(id);
        this.nFeRepository.save(nFe);
    }

    public void delete(Long id) {
        this.nFeRepository.deleteById(id);
    }

    public NFe findById(Long id) {
        return this.nFeRepository.getOne(id);
    }

    public NFe findByChave(String chave) {
        return this.nFeRepository.findByChave(chave);
    }

    public NFe findByNumero(String numero) {
        return this.nFeRepository.findByNumero(numero);
    }

    public List<NFe> findAll() {
        return this.nFeRepository.findAll();
    }

    public List<NFe> findAllByCliente(Long idCliente) {
        return this.nFeRepository.findAllByCliente(idCliente);
    }

    public Page<NFe> findAllPagination() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "numero");
        return new PageImpl<>(this.nFeRepository.findAll(), pageRequest, size);
    }

    public Page<NFe> search(String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "numero");
        return this.nFeRepository.search(searchTerm.toLowerCase(), pageRequest);
    }
}