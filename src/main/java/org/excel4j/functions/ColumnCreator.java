package org.excel4j.functions;

import jxl.Sheet;

import org.excel4j.ExcelColumn;

import com.google.common.base.Function;

public class ColumnCreator implements Function<Integer,ExcelColumn> {
	public final Sheet sheet;
	
	public ColumnCreator(Sheet sheet) {
		this.sheet = sheet;
	}
	@Override
	public ExcelColumn apply(Integer from) {
		return new ExcelColumn(sheet,from+1);
	}
}
