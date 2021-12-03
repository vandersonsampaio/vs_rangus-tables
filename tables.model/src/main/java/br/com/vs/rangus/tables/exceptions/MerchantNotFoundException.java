package br.com.vs.rangus.tables.exceptions;

public class MerchantNotFoundException extends Exception {

    public MerchantNotFoundException(String idMerchant) {
        super("Merchant not found. ID: " + idMerchant);
    }
}
