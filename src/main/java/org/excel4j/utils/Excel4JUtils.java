package org.excel4j.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.excel4j.ExcelRepository;
import org.excel4j.ExcelRow;
import org.excel4j.nulls.NullExcelRow;

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
		return Lists.newArrayList(Iterables.filter(repository.getSheet(sheet), new Predicate<ExcelRow>(){
			public boolean apply(ExcelRow input) {
				boolean isValid = true;
				for(Entry<String,String> column : parameters.entrySet())
				{
					isValid = isValid && column.getValue().equalsIgnoreCase(input.getColumn(column.getKey()));
				}
				return isValid;
			}}));
	}
	
	
	/**
	 * 
	 * @param parameters
	 * @param sheet
	 * @return
	 */
	public <T extends RowAdapter> List<T> findRowsInSheetByParameters (Map<String,String> parameters,String sheet,final Class<T> clazz)
	{
		return Lists.transform(findRowsInSheetByParameters(parameters, sheet), new Function<ExcelRow, T>() {
						public T apply(ExcelRow arg0) {
							try {
								return clazz.getConstructor(ExcelRow.class).newInstance(arg0);
							} catch (Exception e) {
								throw new Error(e);
							}
						}
			}
		);
	}
	/**
	 * 
	 * @param parameters
	 * @param sheet
	 * @return
	 */
	public ExcelRow findRowInSheetByParameters (Map<String,String> parameters,String sheet)
	{
		return getCollectionFistElement(findRowsInSheetByParameters(parameters, sheet),NullExcelRow.getInstance());
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
		return getCollectionFistElement(findRowsInSheetByParameters(parameters, sheet, clazz), null);
	}
	
	/**
	 * 
	 * @param <T>
	 * @param collection
	 * @param def
	 * @return
	 */
	private <T> T getCollectionFistElement(Iterable<T> collection,T def)
	{
		T result = Iterables.get(collection, 0);
		return result == null ? def : result;
	}
	
}
