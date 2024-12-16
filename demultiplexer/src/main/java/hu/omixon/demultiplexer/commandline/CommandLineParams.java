package hu.omixon.demultiplexer.commandline;

import hu.omixon.demultiplexer.configuration.Allignment;

public record CommandLineParams(String sequenceSampleFilePath, String configFilePath,
                                String outputFilePrefix, Allignment allignmentName) {


    public static CommandLineParams fromArgs(String[] args) {

        if (args.length < 4) {
            throw new IllegalArgumentException("""
                    All 4 command-line parameters must be provided:
                        * sequencing file path
                        * config file path
                        * prefix for output file generation
                        * allignment to run
                    """);
        }

        return new CommandLineParams(args[0], args[1], args[2], Allignment.findByName(args[3]));


    }

}
