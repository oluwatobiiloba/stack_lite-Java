package com.stacklite.dev.stacklite_clone.Handlers;

public class DuplicateEntryHandler extends RuntimeException {
    public DuplicateEntryHandler(String message) {
        super(message);
    }
}
