package com.meowmed.rdapolicy.exceptions;

import org.springframework.core.NestedRuntimeException;

/**
 * Die MailSendException wird ausgelöst, wenn das Versenden einer E-Mail fehlschlägt.
 */
public class MailSendException extends NestedRuntimeException{
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
