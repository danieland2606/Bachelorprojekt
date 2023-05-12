package com.meowmed.rdapolicy.exceptions;
/**
 * Die PolicyNotAllowed wird ausgelöst, wenn keine Policy gefunden wird.
 */
public class PolicyNotAllowed extends RuntimeException{
    /**
    * Konstruiert eine neue PolicyNotAllowed ohne Fehlermeldung.
    */
    public PolicyNotAllowed(){
    }

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
