/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ftp.service;

import ec.edu.espe.arquitectura.ftp.exceptions.ErrorMessage;
import ec.edu.espe.arquitectura.ftp.exceptions.FTPErrors;
import java.io.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

/**
 *
 * @author Windows Boo
 */
@Service
@Slf4j
public class FtpService {

    FTPClient ftpconnection;

    public void connectToFTP(String host, String user, String pass) throws FTPErrors {

        ftpconnection = new FTPClient();
        ftpconnection.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;

        try {
            ftpconnection.connect(host);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-1, "No fue posible conectarse al FTP a través del host=" + host);
            log.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }

        reply = ftpconnection.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {

            try {
                ftpconnection.disconnect();
            } catch (IOException e) {
                ErrorMessage errorMessage = new ErrorMessage(-2, "No fue posible conectarse al FTP, el host=" + host + " entregó la respuesta=" + reply);
                log.error(errorMessage.toString());
                throw new FTPErrors(errorMessage);
            }
        }
        try {
            ftpconnection.login(user, pass);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-3, "El usuario=" + user + ", y el pass=**** no fueron válidos para la autenticación.");
            log.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
        try {
            ftpconnection.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-4, "El tipo de dato para la transferencia no es válido.");
            log.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
        ftpconnection.enterLocalPassiveMode();
    }

    public void uploadFileToFTP(File file, String ftpHostDir, String serverFilename) throws FTPErrors {
        try {
            InputStream input = new FileInputStream(file);
            this.ftpconnection.storeFile(ftpHostDir + serverFilename, input);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-5, "No se pudo subir el archivo al servidor.");
            log.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
    }

    public void downloadFileFromFTP(String ftpRelativePath, String copytoPath) throws FTPErrors {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(copytoPath);
        } catch (FileNotFoundException e) {
            ErrorMessage errorMessage = new ErrorMessage(-6, "No se pudo obtener la referencia a la carpeta relativa donde guardar, verifique la ruta y los permisos.");
            log.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
        try {
            this.ftpconnection.retrieveFile(ftpRelativePath, fos);
        } catch (IOException e) {
            ErrorMessage errorMessage = new ErrorMessage(-7, "No se pudo descargar el archivo.");
            log.error(errorMessage.toString());
            throw new FTPErrors(errorMessage);
        }
    }

    public void disconnectFTP() throws FTPErrors {
        if (this.ftpconnection.isConnected()) {
            try {
                this.ftpconnection.logout();
                this.ftpconnection.disconnect();
            } catch (IOException f) {
                throw new FTPErrors(new ErrorMessage(-8, "Ha ocurrido un error al realizar la desconexión del servidor FTP"));
            }
        }
    }
}
