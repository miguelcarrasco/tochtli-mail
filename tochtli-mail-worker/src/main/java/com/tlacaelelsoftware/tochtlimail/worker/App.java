package com.tlacaelelsoftware.tochtlimail.worker;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public class App {
    private static CmdLineConfiguration cmdLineConfig = new CmdLineConfiguration();
    public static final String VERSION = "1.0-SNAPSHOT";

    private static CmdLineParser parser;

    public static void main(String[] args) {
        parseCmdLineArgs(args);

        try {
            Properties properties = getPropertiesFromFile(cmdLineConfig.getConfigFileName());
            new MailWorker(properties).start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void parseCmdLineArgs(String[] args) {
        parser = new CmdLineParser(cmdLineConfig);

        //The character number width to print usage in cli.
        parser.setUsageWidth(80);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());

            printUsage(parser, System.err);

            System.exit(1);
        }

        if (cmdLineConfig.isPrintHelp()) {
            printUsage(parser, System.out);
            System.exit(0);
        }

        if (cmdLineConfig.isPrintVersion()) {
            System.out.println("Version: " + VERSION);
            System.exit(0);
        }
    }

    private static Properties getPropertiesFromFile(String filename) throws IOException {
        Properties prop = new Properties();
        InputStream input = new FileInputStream(filename);
        prop.load(input);
        return prop;
    }

    private static void printUsage(CmdLineParser parser, PrintStream printStream) {
        printStream.println("java -jar tochtli-mail.jar [options...]");
        // print the list of available options
        parser.printUsage(printStream);
        printStream.println();
        printStream.println("Example java -jar tochtli-mail.jar -c app.conf");
    }
}
