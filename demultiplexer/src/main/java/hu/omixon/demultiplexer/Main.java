package hu.omixon.demultiplexer;

import hu.omixon.demultiplexer.configuration.Allignment;
import hu.omixon.demultiplexer.service.DemultiplexerService;
import hu.omixon.demultiplexer.service.DataConversionService;
import hu.omixon.demultiplexer.service.IOService;

public class Main {

    public static void main(String[] args) {

        String sequenceSampleFilePath = args[0];
        String configFilePath = args[1];
        String allignmentName = args[2];
        String outputFilePrefix = args[3];

        IOService ioService = new IOService();
        DataConversionService dataConversionService = new DataConversionService(ioService);
        DemultiplexerService demultiplexerService = new DemultiplexerService(dataConversionService);

        demultiplexerService.run(sequenceSampleFilePath, configFilePath, Allignment.findByName(allignmentName), outputFilePrefix);

    }

}