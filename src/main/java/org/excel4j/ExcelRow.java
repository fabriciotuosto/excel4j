package org.excel4j;

import com.google.common.collect.Lists;
import java.util.List;
import jxl.Cell;
import jxl.CellReferenceHelper;
import jxl.Sheet;

import org.apache.commons.lang.Validate;
import org.excel4j.transform.Transformer;

public class ExcelRow {

    private final Sheet sheet;
    private final int row;
    private String toString;

    public ExcelRow(Sheet sheet, int row) {
        Validate.notNull(sheet);
        this.sheet = sheet;
        this.row = row;
    }

    /**
     * Default behavior for generating null ExcelSheet
     */
    private ExcelRow() {
        sheet = null;
        row = -1;
    }

    public static ExcelRow NULL_ROW() {
        return SingletonHolder.singleton;
    }

    private void computeToString() {
        Cell columns[] = sheet.getRow(row);
        List<String> contents = Lists.newArrayListWithExpectedSize(sheet.getColumns());
        for (Cell cell : columns) {
            contents.add(cell.getContents());
        }
        toString = contents.toString();
    }

    private static class SingletonHolder {

        public static final ExcelRow singleton = new NullExcelRow();
    }

    // / end null object
    public String getColumn(char column) {
        return getColumn(String.valueOf(column));
    }

    public String getColumn(int colum) {
        return sheet.getCell(colum, row).getContents();
    }

    public String getColumn(String colum) {
        return sheet.getCell(CellReferenceHelper.getColumn(colum + row), row).getContents();
    }

    public <T> T getColumn(String colum, Transformer<T> trans) {
        return trans.transform(getColumn(colum));
    }

    public String toString() {
        if (toString == null) {
            computeToString();
        }
        return toString();
    }

    private static final class NullExcelRow extends ExcelRow {

        private static final String NULL = "<NULL>";

        @Override
        public String getColumn(char column) {
            return NULL;
        }

        @Override
        public String getColumn(int colum) {
            return NULL;
        }

        @Override
        public <T> T getColumn(String colum, Transformer<T> trans) {
            return trans.transform(NULL);
        }

        @Override
        public String getColumn(String colum) {
            return NULL;
        }

        @Override
        public String toString() {
            return "Null ExcelRow Object";
        }
    }
}
