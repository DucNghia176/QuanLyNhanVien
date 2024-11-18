/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Asus
 */
public class UserCache {
    private static String username;
    private static String password;
    private static String email;
    private static String phone;

    public static void saveUserInfo(String user, String pass, String mail, String phoneNum) {
        username = user;
        password = pass;
        email = mail;
        phone = phoneNum;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPhone() {
        return phone;
    }

    public static void clear() {
        username = null;
        password = null;
        email = null;
        phone = null;
    }
}
