package com.meowmed.rdapolicy.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * Die PolicyNotFoundException wird ausgelöst, wenn keine Policy gefunden wird.
 */
public class PolicyNotFoundException extends NestedRuntimeException{
    /**
    * Konstruiert eine neue PolicyNotFoundException mit der angegebenen Fehlermeldung
    * @param message die Fehlermeldung 
    */
    public PolicyNotFoundException(String message) {
        super(message);
    }

    /**
     * gibt die Fehlermeldung der PolicyNotFoundException zurück
     * @return die Fehlermeldung
     */
    public String getMessage(){
        return super.getMessage();
    }
}
