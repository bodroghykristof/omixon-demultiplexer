package hu.omixon.demultiplexer.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DemultiplexerService {

    private final IOService ioService;

    public void run() {
        // TODO run operation
        System.out.println("Omixon Demultiplexer");
    }

}
