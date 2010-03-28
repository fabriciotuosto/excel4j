package org.excel4j;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.google.common.collect.Lists;

import jxl.Cell;
import jxl.Sheet;

public class ExcelColumn {

    private final Sheet sheet;
    private final int column;
    private String toString;
    
    public ExcelColumn(Sheet sheet, int column) {
        Validate.notNull(sheet);
        Validate.isTrue(column > -1);
        this.sheet = sheet;
        this.column = column;
    }

    public String getRow(int row) {
        return sheet.getCell(column, row).getContents();
    }

    @Override
    public String toString() {
        if(toString != null){
            calculateToString();
        }
        return toString;
    }

    private void calculateToString() {
        Cell rows[] = sheet.getColumn(column);
        List<String> contents = Lists.newArrayListWithExpectedSize(sheet.getRows());
        for (Cell cell : rows) {
            contents.add(cell.getContents());
        }
        toString = contents.toString();
    }
}
