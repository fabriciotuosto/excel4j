package org.excel4j.iterators;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import jxl.Sheet;

import org.excel4j.ExcelRow;

import com.google.common.collect.AbstractIterator;

public class RowIterable implements Iterable<ExcelRow> {

    private final Sheet sheet;

    public RowIterable(Sheet mySheet) {
        this.sheet = mySheet;
    }

    @Override
    public Iterator<ExcelRow> iterator() {
        return new RowIterator(sheet);
    }

    private static class RowIterator extends AbstractIterator<ExcelRow> {

        private final Sheet sheet;
        private final int maxRow;
        private AtomicInteger index;

        private RowIterator(Sheet mySheet) {
            this.sheet = mySheet;
            maxRow = sheet.getRows();
            index = new AtomicInteger();

        }

        @Override
        protected ExcelRow computeNext() {
            if (maxRow == index.get()) {
                endOfData();
            }
            ExcelRow row = new ExcelRow(sheet, index.getAndIncrement());
            return row;
        }
    }
}
