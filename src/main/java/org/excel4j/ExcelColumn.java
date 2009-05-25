package org.excel4j;

import org.apache.commons.lang.Validate;

import jxl.Cell;
import jxl.Sheet;

public class ExcelColumn implements ExcelLine
{
	private final Sheet sheet;
	private final int column;
	
	public ExcelColumn(Sheet sheet, int column)
	{
		Validate.notNull(sheet);
		this.sheet = sheet;
		this.column = column;
	}

	public String getRow(int row){
		return sheet.getCell(column, row).getContents();
	}

	
	public String toString() {
		Cell rows[] = sheet.getColumn(column-1);
		String result = null;
		for(int cont=0;cont<rows.length;cont++){
			if( result == null ){
				result = rows[cont].getContents();
			}else{
				result = result + "," + rows[cont].getContents();
			}
		}
		return result;
	}

	public ExcelCell getCell(int element) {
		return new ExcelCell(sheet.getCell(column,element));
	}
	
}
