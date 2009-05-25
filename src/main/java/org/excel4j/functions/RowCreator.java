package org.excel4j.functions;

import jxl.Sheet;

import org.excel4j.ExcelRow;

import com.google.common.base.Function;

public class RowCreator implements Function<Integer, ExcelRow> {

	public final Sheet sheet;
	
	public RowCreator(Sheet sheet) {
		this.sheet = sheet;
	}
	@Override
	public ExcelRow apply(Integer from) {
		return new ExcelRow(sheet,from+1);
	}

}
