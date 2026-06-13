package com.mycompany.poepart3amilambiko;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for Part 3 – covers MessageClass and MessageStorage.
 * Uses the test data specified in the assignment brief.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageClassTest {

    // ── shared storage instance ───────────────────────────────────────────────
    private static MessageStorage storage;

    // ── test data from the assignment brief ──────────────────────────────────
    private static MessageClass msg1;
    private static MessageClass msg2;
    private static MessageClass msg3;
    private static MessageClass msg4;
    private static MessageClass msg5;

    @BeforeAll
    static void setUp() {
        storage = new MessageStorage();

        msg1 = new MessageClass(1, "0800000000",  "+27834557896", "Did you get the cake?",                                    "Sent");
        msg2 = new MessageClass(2, "0800000000",  "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        msg3 = new MessageClass(3, "0800000000",  "+27834484567", "Yohoooo, I am at your gate.",                              "Disregard");
        msg4 = new MessageClass(4, "0838884567",  "+27838884567", "It is dinner time !",                                      "Sent");
        msg5 = new MessageClass(5, "0800000000",  "+27838884567", "Ok, I am leaving without you.",                            "Stored");

        storage.addMessage(msg1);
        storage.addMessage(msg2);
        storage.addMessage(msg3);
        storage.addMessage(msg4);
        storage.addMessage(msg5);
    }

    // ── MessageClass tests ───────────────────────────────────────────────────

    @Test @Order(1)
    @DisplayName("Message ID is 10 characters")
    void testMessageIDLength() {
        assertTrue(msg1.checkMessageID(), "Message ID should be exactly 10 digits");
    }

    @Test @Order(2)
    @DisplayName("Valid recipient cell number (+27 format)")
    void testValidRecipientCell() {
        assertTrue(msg1.checkRecipientCell(), "+27834557896 should be a valid cell number");
    }

    @Test @Order(3)
    @DisplayName("Invalid recipient cell number (no +27)")
    void testInvalidRecipientCell() {
        MessageClass bad = new MessageClass(99, "0800000000", "0838884567", "Test message", "Sent");
        assertFalse(bad.checkRecipientCell(), "Number without +27 should be invalid");
    }

    @Test @Order(4)
    @DisplayName("Message hash format is correct")
    void testMessageHashFormat() {
        String hash = msg1.createMessageHash();
        // Hash must be uppercase and contain ':'
        assertTrue(hash.contains(":"), "Hash should contain ':' separator");
        assertEquals(hash, hash.toUpperCase(), "Hash should be fully uppercase");
    }

    @Test @Order(5)
    @DisplayName("Message length within 250 characters")
    void testMessageLengthValid() {
        assertTrue(msg1.checkMessageLength(), "Short message should pass length check");
    }

    @Test @Order(6)
    @DisplayName("Message over 250 characters fails length check")
    void testMessageLengthInvalid() {
        String longText = "a".repeat(251);
        MessageClass longMsg = new MessageClass(10, "0800000000", "+27834557896", longText, "Sent");
        assertFalse(longMsg.checkMessageLength(), "251-char message should fail length check");
    }

    // ── Parallel array population tests ──────────────────────────────────────

    @Test @Order(7)
    @DisplayName("Sent messages array contains only Sent messages")
    void testSentMessagesArray() {
        List<MessageClass> sent = storage.getSentMessages();
        assertEquals(2, sent.size(), "Should have 2 sent messages (msg1, msg4)");
        sent.forEach(m -> assertEquals("Sent", m.getFlag()));
    }

    @Test @Order(8)
    @DisplayName("Disregarded messages array contains only Disregard messages")
    void testDisregardedMessagesArray() {
        List<MessageClass> disregarded = storage.getDisregardedMessages();
        assertEquals(1, disregarded.size(), "Should have 1 disregarded message (msg3)");
        assertEquals("Disregard", disregarded.get(0).getFlag());
    }

    @Test @Order(9)
    @DisplayName("Stored messages array contains only Stored messages")
    void testStoredMessagesArray() {
        List<MessageClass> stored = storage.getStoredMessages();
        assertEquals(2, stored.size(), "Should have 2 stored messages (msg2, msg5)");
        stored.forEach(m -> assertEquals("Stored", m.getFlag()));
    }

    @Test @Order(10)
    @DisplayName("Message hashes array has an entry for every message")
    void testMessageHashesArray() {
        assertEquals(5, storage.getMessageHashes().size(),
                "Hashes array should contain one entry per message");
    }

    @Test @Order(11)
    @DisplayName("Message IDs array has an entry for every message")
    void testMessageIDsArray() {
        assertEquals(5, storage.getMessageIDs().size(),
                "IDs array should contain one entry per message");
    }

    // ── Feature tests ────────────────────────────────────────────────────────

    @Test @Order(12)
    @DisplayName("Longest stored message is msg2")
    void testLongestStoredMessage() {
        List<MessageClass> stored = storage.getStoredMessages();
        MessageClass longest = stored.get(0);
        for (MessageClass m : stored) {
            if (m.getMessageText().length() > longest.getMessageText().length()) longest = m;
        }
        assertEquals(msg2.getMessageText(), longest.getMessageText(),
                "msg2 has the longest stored message text");
    }

    @Test @Order(13)
    @DisplayName("Search stored messages by recipient finds correct messages")
    void testSearchByRecipient() {
        long count = storage.getStoredMessages().stream()
                .filter(m -> m.getRecipient().equals("+27838884567"))
                .count();
        assertEquals(2, count, "Both msg2 and msg5 are for +27838884567");
    }

    @Test @Order(14)
    @DisplayName("Delete stored message by hash removes it from array")
    void testDeleteByHash() {
        String hashToDelete = msg2.getMessageHash();
        int before = storage.getStoredMessages().size();
        storage.deleteByHash(hashToDelete);
        int after = storage.getStoredMessages().size();
        assertEquals(before - 1, after, "Stored messages count should decrease by 1 after deletion");
    }

    @Test @Order(15)
    @DisplayName("sentMessage() returns correct status strings")
    void testSentMessageMethod() {
        assertEquals("Message successfully sent.",   msg1.sentMessage(1));
        assertEquals("Press 0 to delete the message.", msg1.sentMessage(2));
        assertEquals("Message successfully stored.", msg1.sentMessage(3));
        assertEquals("Invalid option.",              msg1.sentMessage(99));
    }
}
