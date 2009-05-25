package org.excel4j.utils;

import org.apache.commons.lang.Validate;
import org.excel4j.ExcelRow;

public abstract class RowAdapter {

	public RowAdapter(ExcelRow row) {
		super();
	}
	

	private ExcelRow row;
	/**
	 * @return the row
	 */
	public ExcelRow getRow() {
		return row;
	}
	/**
	 * Guarda la fila de excel que se esta adaptando a la aplicacion
	 * 
	 * @param {@see {@link ExcelRow}} fila de Excel 
	 * @exception IllegalArgumentException si el parametro Row es null
	 */
	protected void setRow(ExcelRow row) {
		Validate.notNull(row,"La fila excel no puede ser null");
		this.row = row;
	}
	
	/**
	 * Devuelve la columna con el nombre 
	 * que se corresponde con la columna del Excel 
	 * 
	 * @param string
	 * @return
	 */
	public String getColumn(String string) {
		return getRow().getColumn(string);
	}
}
