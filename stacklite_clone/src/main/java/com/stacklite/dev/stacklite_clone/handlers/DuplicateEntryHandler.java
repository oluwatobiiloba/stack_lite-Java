package com.stacklite.dev.stacklite_clone.handlers;

public class DuplicateEntryHandler extends RuntimeException {
    public DuplicateEntryHandler(String message) {
        super(message);
    }
}
