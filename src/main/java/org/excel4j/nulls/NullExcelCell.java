package org.excel4j.nulls;

import jxl.Cell;

import org.excel4j.ExcelCell;

public class NullExcelCell extends ExcelCell {

	private NullExcelCell(Cell cell) {
		super(cell);
	}

	public static ExcelCell getInstance() {
		return SingletonHolder.singleton;
	}

	
	private static class SingletonHolder
	{
		public static final NullExcelCell singleton = new NullExcelCell(null);
	}
}
