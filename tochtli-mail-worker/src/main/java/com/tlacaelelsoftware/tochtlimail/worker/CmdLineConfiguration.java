package com.tlacaelelsoftware.tochtlimail.worker;

import org.kohsuke.args4j.Option;

public class CmdLineConfiguration {

    @Option(name = "-v", aliases = {"--version"}, usage = "print tochtli mail version and exit")
    private boolean printVersion = false;

    @Option(name = "-c", aliases = {"--config-file"}, usage = "configuration file (app.conf is default)")
    private String configFileName = "app.conf";

    @Option(name = "-h", aliases = {"--help"}, usage = "print this message")
    private boolean printHelp = false;


    public boolean isPrintVersion() {
        return printVersion;
    }

    public String getConfigFileName() {
        return configFileName;
    }

    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }

    public boolean isPrintHelp() {
        return printHelp;
    }

    public void setPrintHelp(boolean printHelp) {
        this.printHelp = printHelp;
    }
}
