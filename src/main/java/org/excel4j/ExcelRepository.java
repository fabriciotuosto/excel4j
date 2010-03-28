package org.excel4j;

import java.io.File;

import org.apache.commons.lang.Validate;
import org.excel4j.utils.Iterables;

import com.google.common.collect.LinkedListMultimap;
import com.google.inject.Singleton;
import jxl.Workbook;

@Singleton
public class ExcelRepository {

    private LinkedListMultimap<String, ExcelSheet> data;

    public ExcelRepository() {
        this.data = LinkedListMultimap.create();
    }

    public void add(String fileName) {
        Validate.notNull(fileName, "El Path no puede ser null");
        File file = new File(fileName);
        add(file);
    }

    public void add(File file) {
        try {
            Validate.notNull(file, "El Path no puede ser null");
            Validate.isTrue(file.exists(), "El path no existe");
            if (file.getName().toUpperCase().endsWith(".XLS")) {
                ExcelFile _excel = new ExcelFile(Workbook.getWorkbook(file));
                for (ExcelSheet _sheet : _excel.getExcelSheets()) {
                    data.put(_sheet.getName(), _sheet);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Iterable<ExcelSheet> getSheets(String sheetname) {
        Validate.notNull(sheetname, "");
        return data.get(sheetname);
    }

    public ExcelSheet getSheet(String sheetname) {
        return Iterables.fistItemOrDefault(data.get(sheetname), ExcelSheet.
                NULL_SHEET());
    }
}
