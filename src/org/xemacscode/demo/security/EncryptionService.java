/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author nikoa
 */
public class EncryptionService {

    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest messagedigest = MessageDigest.getInstance("SHA-512");
        messagedigest.update(password.getBytes());
        byte[] b = messagedigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b1 : b) {
            sb.append(Integer.toHexString(b1 & 0xff));
        }
        return sb.toString();
    }

}
