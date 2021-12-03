package br.com.vs.rangus.tables.service.interfaces;

import br.com.vs.rangus.tables.exceptions.MerchantNotFoundException;
import br.com.vs.rangus.tables.exceptions.TableNotFoundException;

import java.util.List;

public interface ITableService {

    String sit(String idTable, String user) throws TableNotFoundException;
    boolean getUp(String idTable) throws TableNotFoundException;

    List<String> findAllMerchants(String idMerchant) throws MerchantNotFoundException;
}
