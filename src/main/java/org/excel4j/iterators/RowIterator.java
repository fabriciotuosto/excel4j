package org.excel4j.iterators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import jxl.Sheet;

import org.excel4j.ExcelRow;

public class RowIterator implements Iterator<ExcelRow>
	{
		private AtomicInteger count;
		private final Sheet sheet;
		private final int maxValue;
		
		public RowIterator(Sheet mySheet,int maxValue) {
			sheet = mySheet;
			count = new AtomicInteger();
			this.maxValue = maxValue;
		}

		@Override
		public boolean hasNext() {
			return count.intValue() < maxValue;
		}

		@Override
		public ExcelRow next() {
			return new ExcelRow(sheet, count.getAndIncrement()+1);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
			
		}
	}
