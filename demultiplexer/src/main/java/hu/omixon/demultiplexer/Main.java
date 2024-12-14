package hu.omixon.demultiplexer;

import hu.omixon.demultiplexer.service.DemultiplexerService;
import hu.omixon.demultiplexer.service.IOService;

public class Main {

    public static void main(String[] args) {

        String sequenceSampleFilePath = args[0];

        IOService ioService = new IOService();
        DemultiplexerService demultiplexerService = new DemultiplexerService(ioService);

        demultiplexerService.run(sequenceSampleFilePath);

    }

}