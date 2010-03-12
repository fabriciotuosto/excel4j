package org.excel4j;

import java.util.Collections;
import java.util.Iterator;

import jxl.Sheet;

import org.apache.commons.lang.Validate;
import org.excel4j.iterators.ColumnIterable;
import org.excel4j.iterators.RowIterable;

public class ExcelSheet implements Iterable<ExcelRow> {

    private final Sheet mySheet;
    private final Iterable<ExcelRow> rows;
    private final Iterable<ExcelColumn> columns;
    private final int maxRows;
    private final int maxColumns;

    public ExcelSheet(Sheet sheet) {
        Validate.notNull(sheet);
        mySheet = sheet;
        rows = new RowIterable(mySheet);
        columns = new ColumnIterable(mySheet);
        maxRows = mySheet.getRows();
        maxColumns = mySheet.getColumns();
    }

    /**
     * Default behavior for generating null ExcelSheet
     */
    private ExcelSheet() {
        mySheet = null;
        rows = Collections.emptyList();
        columns = Collections.emptyList();
        maxRows = 0;
        maxColumns = 0;
    }

    public static ExcelSheet NULL_SHEET() {
        return SingletonHolder.singleton;
    }

    private static class SingletonHolder {

        public static final ExcelSheet singleton = new ExcelSheet();
    }

    /// end null object
    public String getName() {
        return mySheet.getName();
    }

    public Iterable<ExcelRow> getRows() {
        return rows;
    }

    public Iterable<ExcelColumn> getColumns() {
        return columns;
    }

    public int getRowsAmount() {
        return maxRows;
    }

    public int getColumnsAmount() {
        return maxColumns;
    }

    @Override
    public Iterator<ExcelRow> iterator() {
        return rows.iterator();
    }
}
