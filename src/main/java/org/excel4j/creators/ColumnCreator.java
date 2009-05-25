package org.excel4j.creators;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import jxl.Sheet;

import org.excel4j.ExcelColumn;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

public class ColumnCreator implements Function<Integer, ExcelColumn>{

	private final Sheet sheet;
	private ConcurrentMap<Integer,ExcelColumn> map = new MapMaker()
															.weakKeys()
															.weakValues()
															.expiration(30, TimeUnit.SECONDS)
															.makeMap();
	
	public ColumnCreator(Sheet sheet) {
		this.sheet = sheet;
	}
	
	
	public ExcelColumn apply(Integer arg0) {
		ExcelColumn column = map.get(arg0);
		if (column == null)
		{
			map.putIfAbsent(arg0,new ExcelColumn(sheet, arg0+1));
		}
		return map.get(arg0);
	}

}
