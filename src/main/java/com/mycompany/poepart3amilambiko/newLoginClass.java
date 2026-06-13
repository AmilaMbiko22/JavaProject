package com.mycompany.poepart3amilambiko;

/**
 * Handles user registration and login validation.
 * Carried over and unchanged from Part 2.
 */
public class newLoginClass {

    private String username;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;

    public newLoginClass(String firstName, String lastName,String username, String password, String phoneNumber) {
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.username   = username;
        this.password   = password;
        this.phoneNumber = phoneNumber;
    }

    /** Username must contain '_' and be 5 characters or fewer. */
    public boolean checkUsername() {
        return username.contains("_") && username.length() <= 5;
    }

    /** Password must be ≥8 chars and contain an uppercase, digit, and special character. */
    public boolean checkPassword() {
        boolean hasUpper   = false;
        boolean hasNumber  = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))        hasUpper   = true;
            if (Character.isDigit(c))            hasNumber  = true;
            if (!Character.isLetterOrDigit(c))   hasSpecial = true;
        }
        return password.length() >= 8 && hasUpper && hasNumber && hasSpecial;
    }

    /** Phone must start with +27 followed by up to 10 digits. */
    public boolean checkPhoneNumber() {
        return phoneNumber.matches("^\\+27\\d{1,10}$");
    }

    /** Returns true when the supplied credentials match the registered ones. */
    public boolean loginUser(String user, String pass) {
        return user.equals(username) && pass.equals(password);
    }

    /** Returns a welcome message on success or an error message on failure. */
    public String returnLoginStatus(boolean status) {
        if (status) {
            return "Welcome " + firstName + " " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}
