package br.com.vs.rangus.tables.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMerchantDTO {

    private String idMerchant;

    private String name;

    private ResponseMerchantDTO sons;
}
