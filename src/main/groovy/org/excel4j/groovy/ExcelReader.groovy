/**
 * 
 */
package org.excel4j.groovy

import com.google.inject.Inject
import com.google.inject.Guice
import org.excel4j.utils.Excel4JUtils
import org.excel4j.ExcelRepository

/**
 * @author fabricio
 *
 */
public class ExcelParser
{
	def final utils
	def final repo
	
	@Inject
	ExcelParser(Excel4JUtils utils,ExcelRepository repo)
	{
		this.utils = utils
		this.repo = repo
	}
	
	/**
	 * search a sheet with the given string name 
	 * the closure passed must received as parameter
	 * an ExcelRow, wich will be passed to the closure
	 * passing each row  
	 */
	def each_row(String sheetName,Closure action)
	{
		repo.getSheets(sheetName).each(
				{sheet -> sheet.each( {row -> action(row) })}
		)
	}
}