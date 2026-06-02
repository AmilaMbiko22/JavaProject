/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poepart2amilambiko;

/**
 *
 * @author Student
 */
import java.util.Random;

public class MessageClass {

    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;

    private static int totalMessages = 0;

    // Constructor
    public MessageClass(int messageNumber,String recipient,String messageText) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;

        generateMessageID();

        totalMessages++;
    }

    // Generate random 10-digit ID
    private void generateMessageID() {

        Random random = new Random();

        messageID = String.valueOf(1000000000L+ (long)(random.nextDouble()* 8999999999L));
    }

    // Check message ID
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    // Check recipient cell number
    public boolean checkRecipientCell() {
        return recipient.matches("^\\+27\\d{1,10}$");
    }

    // Create message hash
    public String createMessageHash() {

        String[] words = messageText.split(" ");

        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        String hash = messageID.substring(0, 2)+ ":"+ messageNumber+ ":"+ firstWord+ lastWord;

        return hash.toUpperCase();
    }

    // Send/store/disregard
    public String sentMessage(int choice) {

        switch (choice) {

            case 1:
                return "Message successfully sent.";

            case 2:
                return "Press 0 to delete the message.";

            case 3:
                return "Message successfully stored.";

            default:
                return "Invalid option.";
        }
    }

    // Print message details
    public String printMessages() {

        return """
                Message ID: %s Message Hash: %s Recipient: %s Message: %s""".formatted(messageID,createMessageHash(),recipient, messageText);
    }

    // Total messages
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // Store message (basic version)
    public void storeMessage() {
        System.out.println("Message stored in JSON format.");
    }

    // Message length check
    public boolean checkMessageLength() {
        return messageText.length() <= 250;
    }
}
