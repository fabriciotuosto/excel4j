package org.excel4j;

import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;

import org.excel4j.transform.Transformer;

public class ExcelRow implements ExcelLine
{
	private final Sheet sheet;
	private final int row;
	
	public ExcelRow(Sheet sheet, int row){
		this.sheet = sheet;
		this.row = row;
	}
	
	public String getColumn(char column)
	{
		return getColumn(String.valueOf(column));
	}
	
	public String getColumn(int colum){
		return sheet.getCell(colum, row).getContents();
	}
	
	public String getColumn(String colum){
		return sheet.getCell(CellReferenceHelper.getColumn(colum+row),row).getContents();
	}
	
	
	public <T> T getColumn(String colum,Transformer<T> trans){
		return trans.transform(getColumn(colum));
	}
	
	public String toString() {
		Cell colums[] = sheet.getRow(row-1);
		String result = null;
		for(int cont=0;cont<colums.length;cont++){
			if( result == null ){
				result = colums[cont].getContents();
			}else{
				result = result + "," + colums[cont].getContents();
			}
		}
		return result;
	}

	public ExcelCell getCell(int element) {
		return new ExcelCell(sheet.getCell(element,row));
	}
	
}
