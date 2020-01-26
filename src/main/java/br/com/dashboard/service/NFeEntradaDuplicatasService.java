package br.com.dashboard.service;

import br.com.dashboard.model.NFeEntradaDuplicatas;
import br.com.dashboard.repository.NFeEntradaDuplicatasRepository;
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
public class NFeEntradaDuplicatasService {

    @Autowired
    private NFeEntradaDuplicatasRepository nFeEntradaDuplicatasRepository;

    @Autowired
    private NFeEntradaService nFeEntradaService;

    public NFeEntradaDuplicatasService() {
        super();
    }

    public NFeEntradaDuplicatasService(NFeEntradaDuplicatasRepository nFeEntradaDuplicatasRepository,
                                       NFeEntradaService nFeEntradaService) {
        super();
        this.nFeEntradaDuplicatasRepository = nFeEntradaDuplicatasRepository;
        this.nFeEntradaService = nFeEntradaService;
    }

    public void save(Long idNFeEntrada, NFeEntradaDuplicatas nFeEntradaDuplicatas) {
        nFeEntradaDuplicatas.setNfeEntrada(this.nFeEntradaService.findById(idNFeEntrada));
        this.nFeEntradaDuplicatasRepository.save(nFeEntradaDuplicatas);
    }

    public void update(Long idNFeEntrada, Long idNFeEntradaDuplicatas, NFeEntradaDuplicatas nFeEntradaDuplicatas) {
        nFeEntradaDuplicatas.setNfeEntrada(this.nFeEntradaService.findById(idNFeEntrada));
        nFeEntradaDuplicatas.setId(idNFeEntradaDuplicatas);
        this.nFeEntradaDuplicatasRepository.save(nFeEntradaDuplicatas);
    }

    public void delete(Long idNFeEntrada, Long idNFeEntradaDuplicatas) {
        this.nFeEntradaDuplicatasRepository
                .delete(this.findByIdNFeEntradaDuplicataAndIdNFeEntrada(idNFeEntradaDuplicatas, idNFeEntrada));
    }

    public NFeEntradaDuplicatas findByIdNFeEntradaDuplicataAndIdNFeEntrada(Long idNFeEntradaDuplicatas,
                                                                           Long idNFeEntrada) {
        return this.nFeEntradaDuplicatasRepository.
                findByIdNFeEntradaDuplicataAndIdNFeEntrada(idNFeEntradaDuplicatas, idNFeEntrada);
    }

    public List<NFeEntradaDuplicatas> findAllByNFeEntrada(Long idNFeEntrada) {
        return this.nFeEntradaDuplicatasRepository.findAllByNFeEntrada(idNFeEntrada);
    }

    public Page<NFeEntradaDuplicatas> findAllPaginationByNFeEntrada(Long idNFeEntrada) {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "numero");
        return new PageImpl<>(this.nFeEntradaDuplicatasRepository.findAllByNFeEntrada(idNFeEntrada), pageRequest, size);
    }

    public Page<NFeEntradaDuplicatas> searchByNFeEntrada(Long idNFeEntrada, String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "numero");
        return this.nFeEntradaDuplicatasRepository.search(idNFeEntrada, searchTerm.toLowerCase(), pageRequest);
    }
}