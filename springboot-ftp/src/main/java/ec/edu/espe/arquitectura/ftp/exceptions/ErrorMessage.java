/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ftp.exceptions;

/**
 *
 * @author Windows Boo
 */
public class ErrorMessage {

    private int errorcode;
    private String errormessage;

    public ErrorMessage(int errorcode, String errormessage) {
        this.errorcode = errorcode;
        this.errormessage = errormessage;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public String getErrormessage() {
        return errormessage;
    }

    @Override
    public String toString() {
        return "ErrorMessage{"
                + "errorcode=" + errorcode
                + ", errormessage='" + errormessage + '\''
                + '}';
    }
}
