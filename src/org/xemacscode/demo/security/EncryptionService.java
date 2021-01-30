package org.xemacscode.demo.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author nikoa
 */
public class EncryptionService {

    /**
     * hashes a password based on SHA-512
     * 
     * @param password as string
     * @return string The encrypted password
     * @throws NoSuchAlgorithmException 
     */
    public String hashPassword(String password) throws NoSuchAlgorithmException {

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
