/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poeamilambiko;

/**
 *
 * @author Student
 */
import java.util.Scanner;
public class POEamilambiko {
   
  public static void main(String[] args) {
     Scanner sc=new Scanner(System.in);
     
     LoginClass login=new LoginClass();
     String firstName;
     String lastName;
     String username;
     String password;
     String cellNum;
     
     System.out.println("===== Registration =====");
     
     System.out.print("Enter First Name: ");
     firstName=sc.nextLine();
     
     System.out.print("Enter Last Name: ");
     lastName=sc.nextLine();
     
     System.out.print("Enter Username: ");
     username=sc.nextLine();
     
     System.out.print("Enter Password: ");
     password=sc.nextLine();
     
     System.out.print("Enter your cellphone number.(+27): ");
     cellNum=sc.nextLine();
     
     
     System.out.println(login.registerUser(firstName, lastName, username, password, cellNum));
     System.out.println("/n==== Login ====");
     System.out.print("Enter Username: ");
     username=sc.nextLine();
     
             
     System.out.print("Enter password: ");
     password= sc.nextLine();
     
     boolean loginStatus= login.loginUser(username, password);
     
     System.out.println(login.returnLoginStatus(loginStatus));
    }
}
