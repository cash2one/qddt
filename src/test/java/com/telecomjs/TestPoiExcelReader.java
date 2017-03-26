package com.telecomjs;

import com.telecomjs.utils.PoiExcelReader;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by zark on 17/3/4.
 */
public class TestPoiExcelReader {

    @Test()
    public void testExcel() throws FileNotFoundException {
        InputStream is = new FileInputStream("/Users/zark/Downloads/temp/doc2.xls");
        PoiExcelReader reader = new PoiExcelReader(is,(short)0);
        reader.open();
        String[] titles = reader.readExcelTitle();
        System.out.printf( "[%s %s %s ... ]",titles[0],titles[1],titles[2]);
        Iterator iterator = reader.iterator();
        int row=1;
        while (iterator.hasNext()) {
            String[] ss = (String[]) iterator.next();
            System.out.printf("INFO ROW[%d]: ",row++);
            for (String str : ss)
                System.out.printf(" col_=%s ",str);
            System.out.println();
        }
        reader.close();
    }
}
