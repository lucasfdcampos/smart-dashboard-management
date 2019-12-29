package br.com.dashboard.service;

import br.com.dashboard.model.NFe;
import br.com.dashboard.repository.NFeRepository;
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
public class NFeService {

    @Autowired
    private NFeRepository nFeRepository;

    public NFeService() {
        super();
    }

    public NFeService(NFeRepository nFeRepository) {
        super();
        this.nFeRepository = nFeRepository;
    }

    public void save(NFe nFe) {
        this.nFeRepository.save(nFe);

        if (nFe.getNfeProdutos() != null) {
            nFe.getNfeProdutos()
                    .parallelStream()
                    .forEach(nFe::addProduto);
        }

        if (nFe.getNfeDuplicatas() != null) {
            nFe.getNfeDuplicatas()
                    .parallelStream()
                    .forEach(nFe::addDuplicata);
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
        NFe nFe = this.nFeRepository.getOne(id);
        return nFe;
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