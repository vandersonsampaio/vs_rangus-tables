package br.com.vs.rangus.tables.service.interfaces;

import br.com.vs.rangus.tables.dto.response.ResponseMerchantDTO;
import br.com.vs.rangus.tables.dto.response.ResponseTableDTO;
import br.com.vs.rangus.tables.exceptions.MerchantNotFoundException;
import br.com.vs.rangus.tables.exceptions.TableNotFoundException;

public interface ITableService {

    String sit(String idTable, String user) throws TableNotFoundException;
    ResponseTableDTO getUp(String idTable) throws TableNotFoundException;

    ResponseMerchantDTO findAllMerchants(String idMerchant) throws MerchantNotFoundException;
}
