package org.excel4j;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.Validate;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.inject.Singleton;

@Singleton
public class ExcelRepository
{

	private List<ExcelFile> excels = null;
	
	public ExcelRepository(){
		this.excels = Lists.newArrayListWithCapacity(10);
	}
	
	
	public void add(String filepath){
		Validate.notNull(filepath,"El Path no puede ser null");
		File file = new File(filepath);
		Validate.isTrue(file.exists(),"El path no existe");
		if (file.isDirectory())
		{
			addDir(file);
		}else{
			addOneExcel(file);
		}
	}
	
	public void addDir(File dir)
	{
		Validate.notNull(dir,"El Path no puede ser null");
		Validate.isTrue(dir.exists(),"El path no existe");
		Validate.isTrue(dir.isDirectory(),"El path no es un directorio");
		for (File file : dir.listFiles())
		{
			addOneExcel(file);
		}
	}
	
	public void addOneExcel(File file)
	{
		Validate.notNull(file,"El Path no puede ser null");
		Validate.isTrue(file.exists(),"El path no existe");
		if (file.getName().toUpperCase().endsWith(".XLS"))
			this.excels.add(new ExcelFile(file.getAbsolutePath()));
	}
	
	public List<ExcelSheet> getSheets(String sheetname)
	{
		List<ExcelSheet> sheets = Lists.newArrayList();
		for(ExcelFile excel : excels)
		{
			sheets.add(getSheet(excel,sheetname));
		}
		return sheets;
	}
	
	public ExcelSheet getSheet(String sheetname){
		for(ExcelFile excel : excels)
		{
			ExcelSheet sheet = getSheet(excel,sheetname);
			if (sheet != null) return sheet;
		}
		return null;
	}
	
	public ExcelSheet getSheet (String sheetName , String fileName)
	{
		ExcelFile file = findExcelFile(fileName);
		if (file != null) return getSheet(file,sheetName);
		return null;
	}
	
	private ExcelFile findExcelFile(final String fileName) {
		return Iterators.filter(excels.iterator(), new Predicate<ExcelFile>(){
			public boolean apply(ExcelFile input) {
				return input.getName().equalsIgnoreCase(fileName);
		}}).next();
	}

	private ExcelSheet getSheet(ExcelFile excel,String sheetname)
	{
		return excel.getExcelSheet(sheetname);
			
	}
	
	public void remove(ExcelFile file){
		this.excels.remove(file);
	}
	
	public Iterator<ExcelFile> iterator(){
		return excels.iterator();
	}
	
}
