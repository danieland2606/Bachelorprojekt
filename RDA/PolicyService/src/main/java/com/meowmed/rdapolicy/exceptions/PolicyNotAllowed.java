package com.meowmed.rdapolicy.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * Die PolicyNotAllowed wird ausgelöst, wenn keine Policy gefunden wird.
 */
public class PolicyNotAllowed extends NestedRuntimeException{
    /**
    * Konstruiert eine neue PolicyNotAllowed mit der angegebenen Fehlermeldung
    * @param message die Fehlermeldung 
    */
    public PolicyNotAllowed(String message) {
        super(message);
    }

    /**
     * gibt die Fehlermeldung der PolicyNotAllowed zurück
     * @return die Fehlermeldung
     */
    public String getMessage(){
        return super.getMessage();
    }
}
