package com.example.renderfarm.server.command.CommandException;


public class IncorrectCommandException extends IllegalStateException {

    public IncorrectCommandException() {
        super("");
    }

    public IncorrectCommandException(String e) {
        super(e);
    }
}
