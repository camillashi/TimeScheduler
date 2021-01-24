/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xemacscode.demo;

/**
 *
 * @author nikoa
 */
public class UserProvider {
    
    private static int id;
    private static String email;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UserProvider.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserProvider.email = email;
    }
    
}
