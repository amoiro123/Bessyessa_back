package com.bessy.productservice.exc;

public class NotAvailableException  extends RuntimeException {
    public NotAvailableException(String reference) {
        super(reference + " is already loaned.");
    }
}
