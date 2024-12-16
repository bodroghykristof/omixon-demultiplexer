package hu.omixon.demultiplexer;

import hu.omixon.demultiplexer.commandline.CommandLineParams;
import hu.omixon.demultiplexer.service.DataConversionService;
import hu.omixon.demultiplexer.service.DemultiplexerService;
import hu.omixon.demultiplexer.service.IOService;

public class Main {

    public static void main(String[] args) {

        CommandLineParams params = CommandLineParams.fromArgs(args);

        IOService ioService = new IOService();
        DataConversionService dataConversionService = new DataConversionService(ioService);
        DemultiplexerService demultiplexerService = new DemultiplexerService(dataConversionService);

        demultiplexerService.run(
                params.sequenceSampleFilePath(),
                params.configFilePath(),
                params.outputFilePrefix(),
                params.allignmentName()
        );

    }

}