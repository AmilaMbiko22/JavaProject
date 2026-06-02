/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.poepart2amilambiko;

/**
 *
 * @author Student
 */
import java.util.Scanner;

public class POEPart2amilambiko {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // REGISTER
        System.out.println("=== REGISTER ===");

        System.out.print("First name: ");
        String firstName = sc.nextLine();

        System.out.print("Last name: ");
        String lastName = sc.nextLine();

        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Phone number: ");
        String phone = sc.nextLine();

        newLoginClass login = new newLoginClass(
                firstName,
                lastName,
                username,
                password,
                phone);

        // Validation messages
        System.out.println(
                login.checkUsername()
                        ? "Username successfully captured."
                        : "Username is not correctly formatted.");

        System.out.println(
                login.checkPassword()
                        ? "Password successfully captured."
                        : "Password is not correctly formatted.");

        System.out.println(
                login.checkPhoneNumber()
                        ? "Cell phone number successfully added."
                        : "Cell phone number incorrectly formatted.");

        // LOGIN
        System.out.println("\n=== LOGIN ===");

        System.out.print("Enter username: ");
        String loginUser = sc.nextLine();

        System.out.print("Enter password: ");
        String loginPass = sc.nextLine();

        boolean status = login.loginUser(loginUser, loginPass);

        System.out.println(login.returnLoginStatus(status));

        // ONLY continue if login successful
        if (status) {
            System.out.println("\nWelcome to QuickChat.");

            System.out.print("How many messages do you want to send? ");

            int numMessages = sc.nextInt();
            sc.nextLine();

            int choice;

            do {

                System.out.println("""  
                        1) Send Messages
                        2) Show recently sent messages
                        3) Quit
                        """);

                System.out.print("Choose option: ");
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:

                        for (int i = 1;i <= numMessages;i++) {

                            System.out.println("\nMessage " + i);

                            System.out.print( "Recipient: ");

                            String recipient =sc.nextLine();

                            System.out.print("Message: ");

                            String text =sc.nextLine();

                            MessageClass msg =new MessageClass( i,recipient,text);

                            // Message length validation
                            if (!msg.checkMessageLength()) {

                                System.out.println("Please enter a message of less than 250 characters.");

                                continue;
                            }

                            System.out.println("Message sent.");

                            System.out.println(msg.printMessages());

                            System.out.println("Message Hash: "+ msg.createMessageHash());
                        }

                        break;

                    case 2:

                        System.out.println("Coming soon.");

                        break;

                    case 3:

                        System.out.println("Goodbye.");

                        break;

                    default:

                        System.out.println("Invalid option.");
                }

            } while (choice != 3);

            System.out.println("\nTotal messages sent: "+ MessageClass.returnTotalMessages());
        }

        sc.close();
    }
}
