package org.excel4j;

import java.util.Iterator;
import java.util.List;

import jxl.Sheet;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.NumberRange;
import org.excel4j.functions.ColumnCreator;
import org.excel4j.functions.RowCreator;
import org.excel4j.utils.LazyList;

import com.google.common.collect.Lists;

public class ExcelSheet implements Iterable<ExcelRow>
{
	private final Sheet mySheet ;
	private final List<ExcelRow> rows;
	private final List<ExcelColumn> columns;
	private final NumberRange validRows;
	private final NumberRange validColumns;
	

	public ExcelSheet(Sheet sheet){
		mySheet      = sheet;
		validRows    = new NumberRange(0,mySheet.getRows()   );
		validColumns = new NumberRange(0,mySheet.getColumns());		
		rows         = LazyList.newLazyList(new RowCreator(mySheet)    ,mySheet.getRows()-1   );
		columns      = LazyList.newLazyList(new ColumnCreator(mySheet) ,mySheet.getColumns()-1);
	}
	

	public String getName(){
		return mySheet.getName();
	}
	
	public List<ExcelRow> getRows(){
		return rows;
	}
	
	public List<ExcelRow> getRows(int start){
		return getRows(start,getRows().size());
	}
	
	public List<ExcelRow> getRows(int start,int end){
		return getSubList(start, end, validRows, getRows());
	}

	public ExcelRow getRow(int row){
		Validate.isTrue(validRows.containsNumber(row),"Array index out of bounds");
		return getRows().get(row);
	}
	
	private <T> List<T> getSubList(int start,int end,NumberRange range,List<T> list)
	{
		Validate.isTrue(start <= end ,"El inicio debe ser anterior o igual al final");
		Validate.isTrue(range.containsNumber(start)&& range.containsNumber(end),"Inicio y fin debe estar en el rango "+range);
		return list.subList(start, end);		
	}
	
	public List<ExcelColumn> getColumns(){
		return Lists.newArrayList(columns);
	}
	
	public List<ExcelColumn> getColumns(int start){
		return getColumns(0,getRows().size());
	}
	
	public List<ExcelColumn> getColumns(int start,int end){
		return getSubList(start, end, validColumns, getColumns());
	}
	
	public ExcelColumn getColumn(int colum){
		Validate.isTrue(validColumns.containsNumber(colum),"Array index out of bounds");
		return getColumns().get(colum);
	}
	
	public int getRowsAmount(){
		return mySheet.getRows();
	}
	
	public int getColumnsAmount(){
		return mySheet.getColumns();
	}

	public Iterator<ExcelRow> iterator() {
		return rows.iterator();
	}
		
}
