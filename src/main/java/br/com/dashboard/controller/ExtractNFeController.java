package br.com.dashboard.controller;

import br.com.dashboard.service.ExtractNFeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/extract-nfes")
public class ExtractNFeController {

    @Autowired
    private ExtractNFeService extractNFeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> extractNFe() {
        try {
            Integer qtNfesImportado = this.extractNFeService.sweepDirectory();
            String response = qtNfesImportado.toString();
            return new ResponseEntity<String>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }
}
