package org.excel4j.utils;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.excel4j.ExcelRepository;
import org.excel4j.ExcelRow;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public final class Excel4JUtils
{
	private final ExcelRepository repository;
	
	@Inject
	public Excel4JUtils(ExcelRepository repo)
	{
		repository = repo;
	}
	
	public void add(String filepath){
		Validate.notNull(filepath,"El Path no puede ser null");
		File file = new File(filepath);
		Validate.isTrue(file.exists(),"El path no existe");
		if (file.isDirectory())
		{
			addDir(file);
		}else{
			loadExcels(file);
		}
	}
	
	private void addDir(File dir)
	{
		Validate.notNull(dir,"El Path no puede ser null");
		Validate.isTrue(dir.exists(),"El path no existe");
		Validate.isTrue(dir.isDirectory(),"El path no es un directorio");
		for (File file : dir.listFiles()){
			loadExcels(file);
		}
	}
	
	/**
	 * 
	 * @param paths
	 */
	public void loadExcels(File... paths)
	{
		for (File path : paths)
		{
				repository.add(path);
		}
	}	
	
	/**
	 * 
	 * @param paths
	 */
	public void loadExcels(String... paths)
	{
		for (String path : paths)
		{
				repository.add(path);
		}
	}

	
	/**
	 * 
	 * @param parameters
	 * @param sheet
	 * @return
	 */
	public List<ExcelRow> findRowsInSheetByParameters (final Map<String,String> parameters,String sheet)
	{
		return Lists.newArrayList(Iterables.filter(repository.getSheet(sheet), new ValidateRow(parameters)));
	}
	
	
	/**
	 * 
	 * @param parameters
	 * @param sheet
	 * @return
	 */
	public <T extends RowAdapter> List<T> findRowsInSheetByParameters (Map<String,String> parameters,String sheet,final Class<T> clazz)
	{
		return Lists.transform(findRowsInSheetByParameters(parameters, sheet), new AdaptExcelRow<T>(clazz));
	}
	/**
	 * 
	 * @param parameters
	 * @param sheet
	 * @return
	 */
	public ExcelRow findRowInSheetByParameters (Map<String,String> parameters,String sheet)
	{
		return org.excel4j.utils.Iterables.fistItemOrDefault(findRowsInSheetByParameters(parameters, sheet),ExcelRow.NULL_ROW());
	}

	/**
	 * 
	 * @param <T>
	 * @param parameters
	 * @param sheet
	 * @param clazz
	 * @return
	 */
	public <T extends RowAdapter> T findRowInSheetByParameters(Map<String,String> parameters,String sheet,Class<T> clazz)
	{
		return org.excel4j.utils.Iterables.fistItemOrDefault(findRowsInSheetByParameters(parameters, sheet, clazz), null);
	}
	
	
	/**
	 * 
	 * @author tuosto
	 *
	 */
	private static class ValidateRow implements Predicate<ExcelRow>{
		private final Map<String,String> parameters;
		
		public ValidateRow(Map<String, String> parameters) {
			this.parameters = parameters;
		}

		/**
		 * 
		 */
		public boolean apply(ExcelRow input) {
			boolean isValid = true;
			for(Entry<String,String> column : parameters.entrySet())
			{
				isValid = isValid && column.getValue().equalsIgnoreCase(input.getColumn(column.getKey()));
			}
			return isValid;
		}
	}
	
	/**
	 * 
	 * @author tuosto
	 *
	 * @param <T>
	 */
	private static class AdaptExcelRow<T> implements Function<ExcelRow, T>{
		
		private final Class<T> clazz;
		public AdaptExcelRow(Class<T> clazz) {
			this.clazz = clazz;
		}
		
		/**
		 * 
		 */
		public T apply(ExcelRow arg0) {
			try {
				return clazz.getConstructor(ExcelRow.class).newInstance(arg0);
			} catch (Exception e) {
				throw new Error(e);
			}
		}
	}
}
