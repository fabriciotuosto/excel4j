package org.excel4j.utils;

import org.excel4j.ExcelRow;

public abstract class RowAdapter {

    public RowAdapter(ExcelRow row) {
        super();
    }

    /**
     * @return the row
     */
    public abstract ExcelRow getRow();

    /**
     * Devuelve la columna con el nombre 
     * que se corresponde con la columna del Excel
     *
     * @param string
     * @return
     * @throws {@link IllegalStateException} if getRow() == null
     */
    public String getColumn(String string) {
        if (getRow() == null) {
            throw new IllegalStateException("An excel row was not set");
        }
        return getRow().getColumn(string);
    }
}
