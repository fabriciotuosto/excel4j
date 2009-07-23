package org.excel4j;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.google.common.collect.LinkedListMultimap;
import com.google.inject.Singleton;

@Singleton
public class ExcelRepository
{
	private LinkedListMultimap<String,ExcelSheet> data;
	
	public ExcelRepository(){
		this.data = LinkedListMultimap.create();
	}
	
	public void add(String fileName) {
		Validate.notNull(fileName,"El Path no puede ser null");
		File file = new File(fileName);
		add(file);
	}
	
	public void add(File file) {
		Validate.notNull(file,"El Path no puede ser null");
		Validate.isTrue(file.exists(),"El path no existe");
		if (file.getName().toUpperCase().endsWith(".XLS")){
			ExcelFile _excel = new ExcelFile(file.getAbsolutePath());
			for(ExcelSheet _sheet : _excel.getExcelSheets()){
				data.put(_sheet.getName(),_sheet);
			}
		}
	}
	
	public Collection<ExcelSheet> getSheets(String sheetname)
	{
		Validate.notNull(sheetname,"");
		return data.get(sheetname);
	}
	
	public ExcelSheet getSheet(String sheetname){
		List<ExcelSheet> _sheets = data.get(sheetname);
		return _sheets.isEmpty() ? 
				ExcelSheet.NULL_SHEET() : _sheets.iterator().next() ;
	}

}
