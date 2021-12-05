package br.com.vs.rangus.tables.controller;

import br.com.vs.rangus.tables.dto.request.RequestTableDTO;
import br.com.vs.rangus.tables.dto.response.ResponseMerchantDTO;
import br.com.vs.rangus.tables.dto.response.ResponseTableDTO;
import br.com.vs.rangus.tables.exceptions.MerchantNotFoundException;
import br.com.vs.rangus.tables.exceptions.TableNotFoundException;
import br.com.vs.rangus.tables.service.interfaces.ITableService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/table", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TableController {

    private final ITableService service;

    public TableController(ITableService service) {
        this.service = service;
    }

    @PutMapping(value = "sit")
    public ResponseEntity<ResponseMerchantDTO> sitTheTable(@RequestBody RequestTableDTO tableDTO)
            throws TableNotFoundException, MerchantNotFoundException {
        String mainMerchant = service.sit(tableDTO.getIdTable(), tableDTO.getIdUser());
        return ResponseEntity.ok(service.findAllMerchants(mainMerchant));
    }

    @PostMapping(value = "get-up/{idTable}")
    public ResponseEntity<ResponseTableDTO> getUpTable(@PathVariable String idTable)
            throws TableNotFoundException {
        return ResponseEntity.ok(service.getUp(idTable));
    }
}
