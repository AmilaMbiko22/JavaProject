/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepart2amilambiko;

/**
 *
 * @author Student
 */
public class newLoginClass {

    private String username;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;

    public newLoginClass(String firstName, String lastName,String username, String password, String phoneNumber) 
    {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    // Username validation
    public boolean checkUsername() {
        return username.contains("_") && username.length() <= 5;
    }

    // Password validation
    public boolean checkPassword() {

        boolean hasUpper = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {

            if (Character.isUpperCase(c))
                hasUpper = true;
 
            if (Character.isDigit(c))
                hasNumber = true;

            if (!Character.isLetterOrDigit(c))
                hasSpecial = true;
        }

        return password.length() >= 8 && hasUpper&& hasNumber && hasSpecial;
    }

    // Phone validation
    public boolean checkPhoneNumber() {
        return phoneNumber.matches("^\\+27\\d{1,10}$");
    }

    // Login validation
    public boolean loginUser(String user, String pass) {
        return user.equals(username)&& pass.equals(password);
    }

    // Login status message
    public String returnLoginStatus(boolean status) {

        if (status) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}
    

