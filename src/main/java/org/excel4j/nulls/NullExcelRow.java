package org.excel4j.nulls;

import jxl.Sheet;

import org.excel4j.ExcelCell;
import org.excel4j.ExcelRow;
import org.excel4j.transform.Transformer;

public class NullExcelRow extends ExcelRow{

	private NullExcelRow(Sheet sheet, int row) {
		super(sheet, row);
	}

	@Override
	public ExcelCell getCell(int element) {
		return NullExcelCell.getInstance();
	}

	@Override
	public String getColumn(char column) {
		return "null";
	}

	@Override
	public String getColumn(int colum) {
		return "null";
	}

	@Override
	public <T> T getColumn(String colum, Transformer<T> trans) {
		return trans.transform("null");
	}

	@Override
	public String getColumn(String colum) {
		return "null";
	}

	@Override
	public String toString() {
		return "null";
	}

	public static NullExcelRow getInstance() {
		return SingletonHolder.singleton;
	}

	
	private static class SingletonHolder
	{
		public static final NullExcelRow singleton = new NullExcelRow(null,0);
	}
}
