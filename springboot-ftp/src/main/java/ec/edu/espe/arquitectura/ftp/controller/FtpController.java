/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ftp.controller;

import ec.edu.espe.arquitectura.ftp.exceptions.FTPErrors;
import ec.edu.espe.arquitectura.ftp.service.FtpService;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Windows Boo
 */
@RestController
public class FtpController {

    @Autowired
    private FtpService ftpService;

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public void upload() {
        try {
            ftpService.connectToFTP("192.168.0.7", "user1", "1234");
            //ftpService.uploadFileToFTP(new File("Correos.txt"), "admin/", "correo.txt");
            //ftpService.downloadFileFromFTP("admin/correo.txt", "copia.txt");
            ftpService.disconnectFTP();

        } catch (FTPErrors ftpErrors) {
            //System.out.println(ftpErrors.getMessage());
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download() {
        try {
            ftpService.connectToFTP("192.168.0.7", "user1", "1234");
            //ftpService.uploadFileToFTP(new File("Correos.txt"), "admin/", "correo.txt");
            ftpService.downloadFileFromFTP("admin/correo.txt", "copia.txt");
            ftpService.disconnectFTP();
        } catch (FTPErrors ftpErrors) {
            //System.out.println(ftpErrors.getMessage());
        }
    }
}
