package com.mycompany.poepart3amilambiko;

import java.util.Scanner;

/**
 * QuickChat – Part 3 main entry point.
 *
 * Main menu:
 *   1) Send Messages
 *   2) Show recently sent messages
 *   3) Quit
 *   4) Stored Messages  ← NEW in Part 3
 */
public class POEPart3amilambiko {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // ── Pre-load stored messages from JSON file ──────────────────────────
        MessageStorage storage = new MessageStorage();
        storage.loadStoredMessagesFromJSON();

        // ── Registration ─────────────────────────────────────────────────────
        System.out.println("=== REGISTER ===");
        System.out.print("First name: ");
        String firstName = sc.nextLine();

        System.out.print("Last name: ");
        String lastName = sc.nextLine();

        System.out.print("Username (max 5 chars, must contain '_'): ");
        String username = sc.nextLine();

        System.out.print("Password (≥8 chars, uppercase, digit, special): ");
        String password = sc.nextLine();

        System.out.print("Phone number (+27...): ");
        String phone = sc.nextLine();

        newLoginClass login = new newLoginClass(firstName, lastName, username, password, phone);

        System.out.println(login.checkUsername()
                ? "Username successfully captured."
                : "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");

        System.out.println(login.checkPassword()
                ? "Password successfully captured."
                : "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");

        System.out.println(login.checkPhoneNumber()
                ? "Cell phone number successfully added."
                : "Cell phone number incorrectly formatted or does not contain international dialling code.");

        // ── Login ─────────────────────────────────────────────────────────────
        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter username: ");
        String loginUser = sc.nextLine();

        System.out.print("Enter password: ");
        String loginPass = sc.nextLine();

        boolean status = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(status));

        if (!status) {
            System.out.println("Login failed. Exiting.");
            sc.close();
            return;
        }

        // ── Message count ─────────────────────────────────────────────────────
        System.out.println("\nWelcome to QuickChat.");
        System.out.print("How many messages do you want to send? ");
        int numMessages = sc.nextInt();
        sc.nextLine();

        // ── Main menu loop ────────────────────────────────────────────────────
        int choice;
        do {
            printMainMenu();
            System.out.print("Choose option: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // ── 1: Send messages ─────────────────────────────────────────
                case 1 -> {
                    for (int i = 1; i <= numMessages; i++) {
                        System.out.println("\n--- Message " + i + " ---");

                        System.out.print("Sender (your number): ");
                        String sender = sc.nextLine();

                        System.out.print("Recipient (+27...): ");
                        String recipient = sc.nextLine();

                        System.out.print("Message: ");
                        String text = sc.nextLine();

                        // Ask what to do with the message
                        System.out.println("""
                                What would you like to do with this message?
                                  1) Send
                                  2) Disregard
                                  3) Store""");
                        System.out.print("Choice: ");
                        int flagChoice = sc.nextInt();
                        sc.nextLine();

                        String flag = switch (flagChoice) {
                            case 1 -> "Sent";
                            case 2 -> "Disregard";
                            case 3 -> "Stored";
                            default -> "Disregard";
                        };

                        MessageClass msg = new MessageClass(i, sender, recipient, text, flag);

                        if (!msg.checkMessageLength()) {
                            System.out.println("Please enter a message of less than 250 characters.");
                            i--;   // redo this slot
                            continue;
                        }

                        if (!msg.checkRecipientCell() && flag.equals("Sent")) {
                            System.out.println("Recipient cell number invalid (+27 format required).");
                            i--;
                            continue;
                        }

                        storage.addMessage(msg);
                        System.out.println(msg.sentMessage(flagChoice));
                        System.out.println("Message Hash: " + msg.getMessageHash());
                        System.out.println("Message ID  : " + msg.getMessageID());
                    }
                }

                // ── 2: Show recently sent messages ───────────────────────────
                case 2 -> {
                    if (storage.getSentMessages().isEmpty()) {
                        System.out.println("No sent messages yet.");
                    } else {
                        System.out.println("\n=== Sent Messages ===");
                        for (MessageClass m : storage.getSentMessages()) {
                            System.out.println(m.printMessages());
                        }
                    }
                }

                // ── 3: Quit ──────────────────────────────────────────────────
                case 3 -> System.out.println("Goodbye.");

                // ── 4: Stored Messages submenu ───────────────────────────────
                case 4 -> storedMessagesMenu(sc, storage);

                default -> System.out.println("Invalid option, please try again.");
            }

        } while (choice != 3);

        System.out.println("\nTotal messages processed: " + MessageClass.returnTotalMessages());
        sc.close();
    }

    // ── Stored Messages sub-menu ─────────────────────────────────────────────

    private static void storedMessagesMenu(Scanner sc, MessageStorage storage) {

        int sub;
        do {
            System.out.println("""

                    === STORED MESSAGES MENU ===
                    a) Display sender & recipient of all stored messages
                    b) Display the longest stored message
                    c) Search by Message ID
                    d) Search messages for a particular recipient
                    e) Delete a message using its hash
                    f) Display full stored messages report
                    0) Back to main menu
                    """);
            System.out.print("Choose option: ");
            String input = sc.nextLine().trim().toLowerCase();

            switch (input) {

                case "a" -> storage.displayStoredSendersAndRecipients();

                case "b" -> storage.displayLongestStoredMessage();

                case "c" -> {
                    System.out.print("Enter Message ID to search: ");
                    String id = sc.nextLine().trim();
                    storage.searchByMessageID(id);
                }

                case "d" -> {
                    System.out.print("Enter recipient number to search: ");
                    String rec = sc.nextLine().trim();
                    storage.searchByRecipient(rec);
                }

                case "e" -> {
                    System.out.print("Enter Message Hash to delete: ");
                    String hash = sc.nextLine().trim();
                    storage.deleteByHash(hash);
                }

                case "f" -> storage.displayStoredMessagesReport();

                case "0" -> System.out.println("Returning to main menu...");

                default  -> System.out.println("Invalid option.");
            }

            sub = input.equals("0") ? 0 : -1;   // 0 exits the sub-menu loop

        } while (sub != 0);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static void printMainMenu() {
        System.out.println("""

                === QUICKCHAT MAIN MENU ===
                1) Send Messages
                2) Show recently sent messages
                3) Quit
                4) Stored Messages
                """);
    }
}
