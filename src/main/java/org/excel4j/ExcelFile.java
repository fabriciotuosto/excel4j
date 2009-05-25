package org.excel4j;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ExcelFile implements Iterable<ExcelSheet>{
	private final File file;
	private final Workbook wb;
	private final List<ExcelSheet> excel_sheets;
	
	public ExcelFile(String filepath){
		try {
			Validate.notNull(filepath,"El path no puede ser null");
			file = new File(filepath);
			Validate.isTrue(file.exists(), "El archivo no existe");
			wb = Workbook.getWorkbook(file);
			excel_sheets = ImmutableList.copyOf(Lists.transform(Lists.newArrayList(wb.getSheets()), new Function<Sheet, ExcelSheet>(){
																public ExcelSheet apply(Sheet arg0) 
																{
																	return new ExcelSheet(arg0);
																}
		}));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public ExcelSheet getExcelSheet(String name)
	{
		Validate.notNull(name,"El nombre de la hoja excel no puede ser null");
		for (ExcelSheet sheet : getExcelSheets())
		{
			if (sheet.getName().equals(name))
			{
				return sheet;
			}
		}
		return null;
	}
	
	public List<ExcelSheet> getExcelSheets(){
		return excel_sheets;
	}
	
	public String getPath(){
		return file.getPath();
	}
	
	public String getName(){
		return file.getName();
	}
	
	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null) return false;
		if (this == arg0) return true;
		if(!( arg0 instanceof ExcelFile)){
			return false;
		}
		ExcelFile dos = (ExcelFile) arg0;
		return new EqualsBuilder().
				append(this.getPath(), dos.getPath()).
				isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().
					append(getPath()).
					append(31).
					toHashCode();
	}
	
	@Override
	public String toString() {
		return this.getName();
	}

	public Iterator<ExcelSheet> iterator() {
		return getExcelSheets().iterator();
	}
}
