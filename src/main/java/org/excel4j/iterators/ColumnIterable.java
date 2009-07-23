package org.excel4j.iterators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import jxl.Sheet;

import org.excel4j.ExcelColumn;

import com.google.common.collect.AbstractIterator;

public class ColumnIterable implements Iterable<ExcelColumn> {

	private final Sheet sheet;

	public ColumnIterable(Sheet mySheet) {
		this.sheet = mySheet;
	}

	@Override
	public Iterator<ExcelColumn> iterator() {
		return new ColumnIterator(sheet);
	}

	private static class ColumnIterator extends AbstractIterator<ExcelColumn> {
		private final Sheet sheet;
		private final int maxColumn;
		private AtomicInteger index;

		public ColumnIterator(Sheet mySheet) {
			this.sheet = mySheet;
			maxColumn = mySheet.getColumns();
			index = new AtomicInteger();
		}

		@Override
		protected ExcelColumn computeNext() {
			if (maxColumn == index.get()) {
				endOfData();
			}
			return new ExcelColumn(sheet, index.getAndIncrement());
		}
	}
}
