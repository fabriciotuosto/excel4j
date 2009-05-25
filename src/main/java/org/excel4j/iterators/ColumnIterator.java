package org.excel4j.iterators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import jxl.Sheet;

import org.excel4j.ExcelColumn;

public final class ColumnIterator implements Iterator<ExcelColumn> {
	private AtomicInteger count;
	private final Sheet sheet;
	private final int maxValue;

	public ColumnIterator(Sheet mySheet, int maxValue) {
		sheet = mySheet;
		count = new AtomicInteger();
		this.maxValue = maxValue;
	}

	@Override
	public boolean hasNext() {
		return count.intValue() < maxValue;
	}

	@Override
	public ExcelColumn next() {
		return new ExcelColumn(sheet, count.getAndIncrement() + 1);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();

	}
}