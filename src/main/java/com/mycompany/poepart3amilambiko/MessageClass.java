package com.mycompany.poepart3amilambiko;

import org.json.JSONObject;
import java.util.Random;

/**
 * Represents a single QuickChat message.
 * Extended from Part 2 to include: flag (Sent/Stored/Disregard),

 */
public class MessageClass {

    // ── fields ──────────────────────────────────────────────────────────────
    private String messageID;
    private int    messageNumber;
    private String sender;       // added for Part 3 (developer / sender number)
    private String recipient;
    private String messageText;
    private String flag;         // "Sent" | "Stored" | "Disregard"

    private static int totalMessages = 0;

    // ── constructors ─────────────────────────────────────────────────────────

    
    public MessageClass(int messageNumber, String sender,
                        String recipient, String messageText, String flag) {
        this.messageNumber = messageNumber;
        this.sender        = sender;
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.flag          = flag;
        generateMessageID();
        totalMessages++;
    }

    /**
     
     * Does NOT increment totalMessages (they were already counted when first created).
     */
    public MessageClass(String messageID, int messageNumber, String sender,
                        String recipient, String messageText, String flag) {
        this.messageID     = messageID;
        this.messageNumber = messageNumber;
        this.sender        = sender;
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.flag          = flag;
    }

    // ── ID / hash helpers ────────────────────────────────────────────────────

    /** Generates a random 10-digit message ID. */
    private void generateMessageID() {
        Random random = new Random();
        messageID = String.valueOf(1_000_000_000L + (long)(random.nextDouble() * 8_999_999_999L));
    }

    /** Returns true when the message ID is 10 characters or fewer. */
    public boolean checkMessageID() {
        return messageID.length() <= 10;
    }

    /** Returns true when the recipient cell number matches +27 format. */
    public boolean checkRecipientCell() {
        return recipient.matches("^\\+27\\d{1,10}$");
    }

    /** Creates a hash: first 2 chars of ID + ":" + messageNumber + ":" + firstWord + lastWord (uppercase). */
    public String createMessageHash() {
        String[] words    = messageText.trim().split("\\s+");
        String firstWord  = words[0];
        String lastWord   = words[words.length - 1];
        String hash       = messageID.substring(0, 2) + ":" + messageNumber + ":" + firstWord + lastWord;
        return hash.toUpperCase();
    }

    // ── send / store / disregard ─────────────────────────────────────────────

    /**
     * Returns a status string for menu choice:
     *  1 = Send, 2 = Disregard (store for deletion), 3 = Store
     */
    public String sentMessage(int choice) {
        return switch (choice) {
            case 1 -> "Message successfully sent.";
            case 2 -> "Press 0 to delete the message.";
            case 3 -> "Message successfully stored.";
            default -> "Invalid option.";
        };
    }

    // ── display helpers ──────────────────────────────────────────────────────

    /** One-line summary for menu display. */
    public String printMessages() {
        return "Message ID: %s | Hash: %s | Sender: %s | Recipient: %s | Message: %s | Flag: %s"
                .formatted(messageID, createMessageHash(), sender, recipient, messageText, flag);
    }

    /** Multi-line full report for a single message. */
    public String fullReport() {
        return """
                ──────────────────────────────────────────
                Message ID     : %s
                Message Number : %d
                Message Hash   : %s
                Sender         : %s
                Recipient      : %s
                Message        : %s
                Flag           : %s
                ──────────────────────────────────────────"""
                .formatted(messageID, messageNumber, createMessageHash(),
                           sender, recipient, messageText, flag);
    }

    // ── JSON serialisation ───────────────────────────────────────────────────

    
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("messageID",     messageID);
        obj.put("messageNumber", messageNumber);
        obj.put("sender",        sender);
        obj.put("recipient",     recipient);
        obj.put("messageText",   messageText);
        obj.put("flag",          flag);
        return obj;
    }

    /**
     
     * Used when reading messages back from the JSON file.
     */
    public static MessageClass fromJSON(JSONObject obj) {
        return new MessageClass(
            obj.getString("messageID"),
            obj.getInt("messageNumber"),
            obj.getString("sender"),
            obj.getString("recipient"),
            obj.getString("messageText"),
            obj.getString("flag")
        );
    }

    // ── validation ───────────────────────────────────────────────────────────

    /** Returns true when the message text is 250 characters or fewer. */
    public boolean checkMessageLength() {
        return messageText.length() <= 250;
    }

    // ── static counter ───────────────────────────────────────────────────────

    public static int returnTotalMessages() { return totalMessages; }

    // ── getters ──────────────────────────────────────────────────────────────

    public String getMessageID()     { return messageID;     }
    public int    getMessageNumber() { return messageNumber; }
    public String getSender()        { return sender;        }
    public String getRecipient()     { return recipient;     }
    public String getMessageText()   { return messageText;   }
    public String getFlag()          { return flag;          }
    public String getMessageHash()   { return createMessageHash(); }
}
