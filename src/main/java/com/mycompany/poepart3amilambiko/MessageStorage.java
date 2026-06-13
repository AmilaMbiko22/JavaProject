package com.mycompany.poepart3amilambiko;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the five parallel arrays required for Part 3 and handles
 * all JSON file I/O (read stored messages from file, write stored messages to file).
 *
 * Parallel arrays:
 *   sentMessages        – messages flagged "Sent"
 *   disregardedMessages – messages flagged "Disregard"
 *   storedMessages      – messages flagged "Stored" (also persisted to JSON)
 *   messageHashes       – hash for every message processed
 *   messageIDs          – ID for every message processed
 */
public class MessageStorage {

    // ── JSON file path ───────────────────────────────────────────────────────
    private static final String JSON_FILE = "stored_messages.json";

    // ── parallel arrays (ArrayLists so they grow dynamically) ────────────────
    private final List<MessageClass> sentMessages        = new ArrayList<>();
    private final List<MessageClass> disregardedMessages = new ArrayList<>();
    private final List<MessageClass> storedMessages      = new ArrayList<>();
    private final List<String>       messageHashes       = new ArrayList<>();
    private final List<String>       messageIDs          = new ArrayList<>();

    // ── add a message to the correct array(s) ────────────────────────────────

    /**
     * Routes the message into the relevant parallel array based on its flag,
     * and always records its hash and ID.
     */
    public void addMessage(MessageClass msg) {
        messageHashes.add(msg.getMessageHash());
        messageIDs.add(msg.getMessageID());

        switch (msg.getFlag()) {
            case "Sent"      : sentMessages.add(msg);
            case "Stored"    : { storedMessages.add(msg); saveStoredMessagesToJSON(); }
            case "Disregard" : disregardedMessages.add(msg);
        }
    }

    // ── JSON persistence ─────────────────────────────────────────────────────

    /** Writes all currently stored messages to the JSON file. */
    public void saveStoredMessagesToJSON() {
        JSONArray array = new JSONArray();
        for (MessageClass msg : storedMessages) {
            array.put(msg.toJSON());
        }
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(JSON_FILE), StandardCharsets.UTF_8)) {
            writer.write(array.toString(2));   // pretty-print with indent 2
        } catch (IOException e) {
            System.out.println("Error writing JSON file: " + e.getMessage());
        }
    }

    /**
     * Reads stored messages from the JSON file and populates the storedMessages array.
     * Called once at application startup.
     */
    public void loadStoredMessagesFromJSON() {
        Path path = Paths.get(JSON_FILE);
        if (!Files.exists(path)) return;   // nothing to load yet

        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                MessageClass msg = MessageClass.fromJSON(obj);

                // Add to arrays without re-saving to avoid duplicate writes
                storedMessages.add(msg);
                messageHashes.add(msg.getMessageHash());
                messageIDs.add(msg.getMessageID());
            }
            System.out.println(storedMessages.size() + " stored message(s) loaded from file.");
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    // ── Feature a: display sender & recipient of all stored messages ──────────

    public void displayStoredSendersAndRecipients() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        System.out.println("\n=== Stored Messages – Sender & Recipient ===");
        for (MessageClass msg : storedMessages) {
            System.out.printf("Sender: %-15s  Recipient: %s%n",
                    msg.getSender(), msg.getRecipient());
        }
    }

    // ── Feature b: display the longest stored message ────────────────────────

    public void displayLongestStoredMessage() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }
        MessageClass longest = storedMessages.get(0);
        for (MessageClass msg : storedMessages) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        System.out.println("\n=== Longest Stored Message ===");
        System.out.println(longest.fullReport());
    }

    // ── Feature c: search by message ID, display recipient & message ──────────

    public void searchByMessageID(String id) {
        for (MessageClass msg : storedMessages) {
            if (msg.getMessageID().equalsIgnoreCase(id)) {
                System.out.println("\n=== Message Found ===");
                System.out.printf("Recipient : %s%nMessage   : %s%n",
                        msg.getRecipient(), msg.getMessageText());
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    // ── Feature d: search all stored messages for a particular recipient ──────

    public void searchByRecipient(String recipient) {
        boolean found = false;
        System.out.println("\n=== Stored Messages for Recipient: " + recipient + " ===");
        for (MessageClass msg : storedMessages) {
            if (msg.getRecipient().equalsIgnoreCase(recipient)) {
                System.out.println(msg.fullReport());
                found = true;
            }
        }
        if (!found) System.out.println("No stored messages found for that recipient.");
    }

    // ── Feature e: delete a stored message by its hash ───────────────────────

    public void deleteByHash(String hash) {
        String upperHash = hash.toUpperCase();
        boolean removed  = storedMessages.removeIf(
                msg -> msg.getMessageHash().equals(upperHash));

        if (removed) {
            // Keep the hash/ID arrays consistent
            messageHashes.remove(upperHash);
            saveStoredMessagesToJSON();
            System.out.println("Message successfully deleted.");
        } else {
            System.out.println("Hash not found. No message deleted.");
        }
    }

    // ── Feature f: full report of all stored messages ────────────────────────

    public void displayStoredMessagesReport() {
        if (storedMessages.isEmpty()) {
            System.out.println("No stored messages to report.");
            return;
        }
        System.out.println("\n========== STORED MESSAGES REPORT ==========");
        System.out.printf("Total stored messages: %d%n", storedMessages.size());
        for (MessageClass msg : storedMessages) {
            System.out.println(msg.fullReport());
        }
        System.out.println("============================================");
    }

    // ── Getters for the arrays (used by unit tests) ───────────────────────────

    public List<MessageClass> getSentMessages()        { return sentMessages;        }
    public List<MessageClass> getDisregardedMessages() { return disregardedMessages; }
    public List<MessageClass> getStoredMessages()      { return storedMessages;      }
    public List<String>       getMessageHashes()       { return messageHashes;       }
    public List<String>       getMessageIDs()          { return messageIDs;          }
}
