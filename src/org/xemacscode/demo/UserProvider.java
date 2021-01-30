package org.xemacscode.demo;

/**
 * Provides user Information across the application
 *
 * @author nikoa
 */
public class UserProvider {
    
    private static int id;
    private static String email;

    /**
     * @return id of the user as int
     */
    public static int getId() {
        return id;
    }

    /**
     * Sets the user id
     *
     * @param id 
     */
    public static void setId(int id) {
        UserProvider.id = id;
    }

    /**
     * @return email of the user as string
     */
    public static String getEmail() {
        return email;
    }

    /**
     * Sets the user email
     *
     * @param email 
     */
    public static void setEmail(String email) {
        UserProvider.email = email;
    }
    
}
