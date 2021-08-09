package com.capgemini.db;

import com.capgemini.db.SampleDB;
import org.junit.Assert;
import org.junit.Test;

public class SampleDBTest {
    @Test
    public void sampleDBTest(){
        Assert.assertEquals(
                "insert into my_table (column_a, column_b) values (?, ?)",
                SampleDB.INSERT
        );
        Assert.assertEquals(
                "select column_a, column_b from my_table",
                SampleDB.SELECT_ALL
        );

    }
}
