/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poeamilambiko;

/**
 *
 * @author Student
 */
public class LoginClass {
    private String username;
    private String password;
    private String cellNum;
    
    private String firstName;
    private String lastName;
    
    
    public boolean checkUserName(String username) {
        if (username.contains("_")&& username.length()<=5) {
            return true;
        }
        return false;
    }
    
    
    public boolean checkPasswordComplexity(String password){
        boolean hasCapital=false;
        boolean hasNumber=false;
        boolean hasSpecial=false;
        
        if (password.length() >=8){
            for (int i=0; i< password.length(); i++){
                char ch= password.charAt(i);
                
                if (Character.isUpperCase(ch)){
                    hasCapital = true;
                }
                if (Character.isDigit(ch)){
                    hasNumber=true;
                }
                if (!Character.isLetterOrDigit(ch)){
                    hasSpecial=true;
                }
            }
        }
        if (hasCapital && hasNumber && hasSpecial){
            return true;
        }
        return false;
    }
    
    
    
    public boolean checkCellPhoneNumber(String cellNum){
        if (cellNum.startsWith("+27")&& cellNum.length()<=13){
            return true;
        }
        else {
            return false;
        }
    }
    
    public String registerUser(String firstName, String lastName,String username,String password,String cellNum){
        this.firstName=firstName;
        this.lastName=lastName;
        this.username=username;
        this.password=password;
        this.cellNum=cellNum;
        
        
        if (!checkUserName(username)){
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)){
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellNum)){
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        return "User registered successfully.";
    }
    
    
    public boolean loginUser(String username,String password){
        if (this.username.equals(username) && this.password.equals(password)){
            return true;
        }else{
            return false;
        }
    }
    
    public String returnLoginStatus(boolean loginStatus){
        if (loginStatus){
            return "Welcome "+ firstName+" "+ lastName+ " it is nice to see you again!";
        }else{
            return "Username or password are incorrect, please try again.";
        }
    }
}
