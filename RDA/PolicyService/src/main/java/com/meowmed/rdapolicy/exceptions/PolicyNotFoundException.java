package com.meowmed.rdapolicy.exceptions;
/**
 * Die PolicyNotFoundException wird ausgelöst, wenn keine Policy gefunden wird.
 */
public class PolicyNotFoundException extends RuntimeException{
    
    /**
    * Konstruiert eine neue PolicyNotFoundException ohne Fehlermeldung.
    */
    public PolicyNotFoundException(){
    }

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
