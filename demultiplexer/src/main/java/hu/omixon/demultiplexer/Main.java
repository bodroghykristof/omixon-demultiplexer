package hu.omixon.demultiplexer;

import hu.omixon.demultiplexer.service.DemultiplexerService;
import hu.omixon.demultiplexer.service.IOService;

public class Main {

    public static void main(String[] args) {

        IOService ioService = new IOService();
        DemultiplexerService demultiplexerService = new DemultiplexerService(ioService);
        demultiplexerService.run();

    }

}