package org.excel4j;

import java.math.BigDecimal;
import java.text.Format;

import org.excel4j.transform.Transformer;

import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellType;
import jxl.format.CellFormat;

public class ExcelCell implements Cell
{

	private final Cell cell;
	
	public ExcelCell(Cell cell) {
		this.cell = cell;
	}

	public CellFeatures getCellFeatures() {
		return cell.getCellFeatures();
	}

	public CellFormat getCellFormat() {
		return cell.getCellFormat();
	}

	public int getColumn() {
		return cell.getColumn();
	}

	public String getContents() {
		return cell.getContents();
	}

	public int getRow() {
		return cell.getRow();
	}

	public CellType getType() {
		return cell.getType();
	}

	public boolean isHidden() {
		return cell.isHidden();
	}
	
	public BigDecimal getContentAsNumber()
	{
		return new BigDecimal(getContents());
	}

	public String getFormatedContet(Format format)
	{
		return format.format(getContents());
	}
	
	public <T> T getTransformedCell(Transformer<T> trans)
	{
		return trans.transform(getContents());
	}
}
