package br.com.vs.rangus.tables.client.merchant;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Merchant", url = "${vs.rangus.merchant.url}")
public interface MerchantClient {

    @GetMapping(value = "/merchant/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<String>> findAll(@PathVariable("id") String id);
}
