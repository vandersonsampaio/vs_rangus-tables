package br.com.vs.rangus.tables.exceptions;

public class TableNotFoundException extends Exception {

    public TableNotFoundException(String idTable) {
        super("Table not found. ID: " + idTable);
    }
}
