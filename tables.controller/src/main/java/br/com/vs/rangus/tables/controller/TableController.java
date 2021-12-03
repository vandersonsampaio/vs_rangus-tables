package br.com.vs.rangus.tables.controller;

import br.com.vs.rangus.tables.service.interfaces.ITableService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/table", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TableController {

    private final ITableService service;

    public TableController(ITableService service) {
        this.service = service;
    }

    @PutMapping(value = "sit")
    public ResponseEntity<List<String>> sitTheTable(@RequestBody String idTable, String user)
            throws Exception {
        String mainMerchant = service.sit(idTable, user);
        return ResponseEntity.ok(service.findAllMerchants(mainMerchant));
    }
}
