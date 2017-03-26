package com.telecomjs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
/**
 * Created by zark on 17/3/4.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestPoiExcelWriter.class,
        TestPoiExcelReader.class
})
public class TestSuitClass {
}
