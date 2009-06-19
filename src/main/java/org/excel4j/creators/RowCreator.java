package org.excel4j.creators;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;

import org.excel4j.ExcelRow;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

public class RowCreator implements Function<Integer, ExcelRow>{

	private final Sheet sheet;

	private ConcurrentMap<Integer,ExcelRow> map = new MapMaker()
														.weakKeys()
														.weakValues()
														.expiration(30, TimeUnit.SECONDS)
														.makeMap();
	public RowCreator(Sheet sheet) {
		this.sheet = sheet;
	}
	
	
	public ExcelRow apply(Integer arg0) {
		ExcelRow column = map.get(arg0);
		if (column == null)
		{
			map.putIfAbsent(arg0,new ExcelRow(sheet, arg0+1));
		}
		return map.get(arg0);
	}

}
