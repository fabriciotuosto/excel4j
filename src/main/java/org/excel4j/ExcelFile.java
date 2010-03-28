package org.excel4j;

import java.util.Arrays;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ExcelFile {

    private final Workbook wb;
    private final List<ExcelSheet> excel_sheets;

    public ExcelFile(Workbook workbook) {
        try {
            Validate.notNull(workbook, "El path no puede ser null");
            wb = workbook;
            excel_sheets = ImmutableList.copyOf(Lists.transform(Arrays.asList(wb.
                    getSheets()), new TransformSheet()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ExcelSheet getExcelSheet(String name) {
        Validate.notNull(name, "El nombre de la hoja excel no puede ser null");
        for (ExcelSheet sheet : getExcelSheets()) {
            if (sheet.getName().equals(name)) {
                return sheet;
            }
        }
        return null;
    }

    public List<ExcelSheet> getExcelSheets() {
        return excel_sheets;
    }

    @Override
    public boolean equals(Object arg0) {
        if (arg0 == null) {
            return false;
        }
        if (this == arg0) {
            return true;
        }
        if (!(arg0 instanceof ExcelFile)) {
            return false;
        }
        ExcelFile dos = (ExcelFile) arg0;
        return new EqualsBuilder().append(wb, dos.wb).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(wb.hashCode()).append(31).toHashCode();
    }

    @Override
    public String toString() {
        return this.wb.toString();
    }

    private static final class TransformSheet implements
            Function<Sheet, ExcelSheet> {

        public ExcelSheet apply(Sheet arg0) {
            return new ExcelSheet(arg0);
        }
    }
}
