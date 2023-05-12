package com.meowmed.rdapolicy.exceptions;

public class CatNotFoundException extends RuntimeException{
    
    /*+
     * konstruiert eine CustomerNotFoundException ohne Fehlermeldung
     */
    public CatNotFoundException(){
    }

    /**
     * konstruiert eine CustomerNotFoundException und übergibt eine Fehlermeldung als Parameter
     * @param message die Fehlermeldung
     */
    public CatNotFoundException(String message) {
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
