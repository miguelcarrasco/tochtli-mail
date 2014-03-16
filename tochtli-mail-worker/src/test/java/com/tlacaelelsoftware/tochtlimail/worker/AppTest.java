package com.tlacaelelsoftware.tochtlimail.worker;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.StandardErrorStreamLog;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class AppTest {

    @Rule
    public final StandardErrorStreamLog errLog = new StandardErrorStreamLog();

    @Rule
    public final StandardOutputStreamLog outLog = new StandardOutputStreamLog();

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Test
    public void invalidArgumentsTest() {

        exit.expectSystemExitWithStatus(1);
        App.main(new String[]{"invalid-argument"});
        assertThat(errLog.getLog(), containsString("No argument is allowed: invalid-argument"));
    }

    @Test
    public void versionInfoTest() {
        exit.expectSystemExitWithStatus(0);
        App.main(new String[]{"-v"});
        assertEquals(outLog, "Version: " + App.VERSION);
    }

    @Test
    public void versionInfoLargeTest() {
        exit.expectSystemExitWithStatus(0);
        App.main(new String[]{"--version"});
        assertEquals(outLog, "Version: " + App.VERSION);
    }
}
