package org.excel4j;

import java.util.Collections;
import java.util.Iterator;

import jxl.Sheet;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.NumberRange;
import org.excel4j.iterators.ColumnIterable;
import org.excel4j.iterators.RowIterable;

public class ExcelSheet implements Iterable<ExcelRow>
{
	private final Sheet mySheet ;
	private final Iterable<ExcelRow> rows;
	private final Iterable<ExcelColumn> columns;
	private final NumberRange validRows;
	private final NumberRange validColumns;
	
	
	public ExcelSheet(Sheet sheet){
		Validate.notNull(sheet);
		mySheet      = sheet;
		validRows    = new NumberRange(0,mySheet.getRows());
		validColumns = new NumberRange(0,mySheet.getColumns());		
		rows         = new RowIterable(mySheet);
		columns      = new ColumnIterable(mySheet);
	}
	
	/**
	 * Default behavior for generating null ExcelSheet
	 */
	private ExcelSheet(){
		mySheet      = null;
		rows         = Collections.emptyList();
		columns      = Collections.emptyList();
		validRows    = new NumberRange(0,0);
		validColumns = validRows;				
	}

	public static ExcelSheet NULL_SHEET() {
		return SingletonHolder.singleton;
	}
	
	private static class SingletonHolder {
		public static final ExcelSheet singleton = new ExcelSheet();
	}

	
	/// end null object
	
	public String getName(){
		return mySheet.getName();
	}
	
	public Iterable<ExcelRow> getRows(){
		return rows;
	}
	
	public Iterable<ExcelColumn> getColumns(){
		return columns;
	}
			
	public int getRowsAmount(){
		return validRows.getMaximumInteger();
	}
	
	public int getColumnsAmount(){
		return validColumns.getMaximumInteger();
	}

	public Iterator<ExcelRow> iterator() {
		return rows.iterator();
	}
		
}
