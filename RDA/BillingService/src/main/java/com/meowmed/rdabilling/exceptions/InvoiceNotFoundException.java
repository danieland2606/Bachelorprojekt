package com.meowmed.rdabilling.exceptions;

import org.springframework.core.NestedRuntimeException;

public class InvoiceNotFoundException extends NestedRuntimeException{
    /**
     * konstruiert eine CatNotFoundException und übergibt eine Fehlermeldung als Parameter
     * @param message die Fehlermeldung
     */
    public InvoiceNotFoundException(String message) {
        super(message);
    }
    /*+
     * gibt die Fehlermeldung der CatNotFoundException zurück
     * @return die Fehlermeldung
     */
    public String getMessage(){
        return super.getMessage();
    }
}
