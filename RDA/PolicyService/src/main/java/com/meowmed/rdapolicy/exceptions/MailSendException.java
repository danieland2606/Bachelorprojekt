package com.meowmed.rdapolicy.exceptions;
/**
 * Die MailSendException wird ausgelöst, wenn das Versenden einer E-Mail fehlschlägt.
 */
public class MailSendException extends RuntimeException{
   
    /**
    * Konstruiert eine neue MailSendException ohne Fehlermeldung.
    */
    public MailSendException(){
    }
    
    /**
     * Konstruiert eine neue MailSendException mit der angegebenen Fehlermeldung
     * @param message die Fehlermeldung 
     */
    public MailSendException(String message) {
        super(message);
    }

    /**
     * gibt die Fehlermeldung der MailSendException zurück
     * @return die Fehlermeldung
     */
    public String getMessage(){
        return super.getMessage();
    }
    
}
