package br.com.dashboard.service;

import br.com.dashboard.model.NFeDuplicatas;
import br.com.dashboard.repository.NFeDuplicatasRepository;
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
public class NFeDuplicatasService {

    @Autowired
    private NFeDuplicatasRepository nFeDuplicatasRepository;

    @Autowired
    private NFeService nFeService;

    public NFeDuplicatasService() {
        super();
    }

    public NFeDuplicatasService(NFeDuplicatasRepository nFeDuplicatasRepository, NFeService nFeService) {
        super();
        this.nFeDuplicatasRepository = nFeDuplicatasRepository;
        this.nFeService = nFeService;
    }

    public void save(Long idNFe, NFeDuplicatas nFeDuplicatas) {
        nFeDuplicatas.setNfe(this.nFeService.findById(idNFe));
        this.nFeDuplicatasRepository.save(nFeDuplicatas);
    }

    public void update(Long idNFe, Long idNFeDuplicatas, NFeDuplicatas nFeDuplicatas) {
        nFeDuplicatas.setNfe(this.nFeService.findById(idNFe));
        nFeDuplicatas.setId(idNFeDuplicatas);
        this.nFeDuplicatasRepository.save(nFeDuplicatas);
    }

    public void delete(Long idNFe, Long idNFeDuplicatas) {
        this.nFeDuplicatasRepository.delete(this.findByIdNFeDuplicataAndIdNFe(idNFeDuplicatas, idNFe));
    }

    public NFeDuplicatas findByIdNFeDuplicataAndIdNFe(Long idNFeDuplicata, Long idNFe) {
        return this.nFeDuplicatasRepository.findByIdNFeDuplicataAndIdNFe(idNFeDuplicata, idNFe);
    }

    public List<NFeDuplicatas> findAllByNFe(Long idNFe) {
        return this.nFeDuplicatasRepository.findAllByNFe(idNFe);
    }

    public Page<NFeDuplicatas> findAllPaginationByNFe(Long idNFe) {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "numero");
        return new PageImpl<>(this.nFeDuplicatasRepository.findAllByNFe(idNFe), pageRequest, size);
    }

    public Page<NFeDuplicatas> searchByNFe(Long idNFe, String searchTerm, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "numero");
        return this.nFeDuplicatasRepository.search(idNFe, searchTerm.toLowerCase(), pageRequest);
    }
}