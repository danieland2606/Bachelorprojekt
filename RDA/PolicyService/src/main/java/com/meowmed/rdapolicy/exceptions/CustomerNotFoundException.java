package com.meowmed.rdapolicy.exceptions;
/**
 * Exception wird ausgelöst, ein Kunde nicht gefunden werden kann. 
 * Dies kann passieren, wenn die Kundendaten falsche eingegeben wurden oder wenn der Kunde nicht in der Datenbank vorhanden ist. 
 * 
 */
public class CustomerNotFoundException extends RuntimeException{

    /*+
     * konstruiert eine CustomerNotFoundException ohne Fehlermeldung
     */
    public CustomerNotFoundException(){
    }

    /**
     * konstruiert eine CustomerNotFoundException und übergibt eine Fehlermeldung als Parameter
     * @param message die Fehlermeldung
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
    /*+
     * gibt die Fehlermeldung der CustomerNotFoundException zurück
     * @return die Fehlermeldung
     */
    public String getMessage(){
        return super.getMessage();
    }

}
