package com.capgemini;

import com.capgemini.pearson.PearsonManualLogged;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;

public class PearsonTest {
    @Test
    public void testGeneratedLoggedClassAuto(){
        HashMap<LocalDate, Integer> weighings = new HashMap<>();
        final LocalDate firstDate = LocalDate.of(2021, 8, 20);
        weighings.put(firstDate,80);
        PearsonManualLogged pearsonLogged = new PearsonManualLogged("Francisco", weighings);
        Assert.assertFalse(pearsonLogged.addWheight(firstDate.plusMonths(1),82));
        Assert.assertEquals(
                "Pearson{name='Francisco', weighings={2021-08-20=80, 2021-09-20=82}}",
                pearsonLogged.toString()
        );
    }
    @Test
    public void testGeneratedLoggedClassManual(){
        PearsonManualLogged pearsonLogged = new PearsonManualLogged("Manuel");
        Assert.assertFalse(
                pearsonLogged.addWheight(LocalDate.of(2021, 8, 20),82)
        );
        Assert.assertEquals(
                "Pearson{name='Manuel', weighings={2021-08-20=82}}",
                pearsonLogged.toString()
        );
    }
}
