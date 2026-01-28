package edu.brandeis.cosi103a.ip2;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the Card Game (Dominion-style).
 * Verifies all game mechanics and rules.
 */
public class AppTest {
    private Game game;
    private Player alice;
    private Player bob;

    @Before
    public void setUp() {
        game = new Game("Alice", "Bob");
        // Get players from game
        List<Player> players = game.getPlayers();
        alice = players.get(0);
        bob = players.get(1);
    }

    // ===== CARD TESTS =====
    @Test
    public void testAutomationCardProperties() {
        AutomationCard method = new AutomationCard("Method", 2, 1);
        assertEquals("Method", method.getName());
        assertEquals(2, method.getCost());
        assertEquals(1, method.getValue());
        assertEquals(1, method.getAPValue());
    }

    @Test
    public void testCryptocurrencyCardProperties() {
        CryptocurrencyCard bitcoin = new CryptocurrencyCard("Bitcoin", 0, 1);
        assertEquals("Bitcoin", bitcoin.getName());
        assertEquals(0, bitcoin.getCost());
        assertEquals(1, bitcoin.getValue());
        assertEquals(1, bitcoin.getCryptoValue());
    }

    // ===== DECK TESTS =====
    @Test
    public void testCardDeckInitialization() {
        CardDeck deck = new CardDeck();
        // 14 Method + 8 Module + 8 Framework + 60 Bitcoin + 40 Ethereum + 30 Dogecoin = 160
        assertEquals(160, deck.getSize());
    }

    @Test
    public void testCardDeckCorrectCounts() {
        CardDeck deck = new CardDeck();
        List<Card> cards = deck.getCards();
        
        int methodCount = 0;
        int moduleCount = 0;
        int frameworkCount = 0;
        int bitcoinCount = 0;
        int ethereumCount = 0;
        int dogecoinCount = 0;
        
        for (Card card : cards) {
            if (card.getName().equals("Method")) methodCount++;
            else if (card.getName().equals("Module")) moduleCount++;
            else if (card.getName().equals("Framework")) frameworkCount++;
            else if (card.getName().equals("Bitcoin")) bitcoinCount++;
            else if (card.getName().equals("Ethereum")) ethereumCount++;
            else if (card.getName().equals("Dogecoin")) dogecoinCount++;
        }
        
        assertEquals(14, methodCount);
        assertEquals(8, moduleCount);
        assertEquals(8, frameworkCount);
        assertEquals(60, bitcoinCount);
        assertEquals(40, ethereumCount);
        assertEquals(30, dogecoinCount);
    }

    // ===== SETUP TESTS =====
    @Test
    public void testStarterDeckDistribution() {
        // Each player should have 10 cards total (7 Bitcoin + 3 Method)
        assertEquals(10, alice.getDeckSize());
        assertEquals(10, bob.getDeckSize());
    }

    @Test
    public void testInitialHandSize() {
        // Initial hand should be 5 cards
        assertEquals(5, alice.getHand().size());
        assertEquals(5, bob.getHand().size());
    }

    @Test
    public void testRandomStartingPlayer() {
        // Starting player should be either Alice or Bob
        Player startingPlayer = game.getCurrentPlayer();
        assertTrue(startingPlayer.getName().equals("Alice") || startingPlayer.getName().equals("Bob"));
    }

    // ===== GAMEPLAY TESTS =====
    @Test
    public void testPlayCryptocurrencyCard() {
        Player player = game.getCurrentPlayer();
        int initialMoney = player.getMoney();
        assertEquals(0, initialMoney);
        
        // Find a cryptocurrency card and play it
        List<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i) instanceof CryptocurrencyCard) {
                player.playCard(i);
                assertTrue(player.getMoney() > initialMoney);
                return;
            }
        }
    }

    @Test
    public void testAutomationCardsNotPlayed() {
        Player player = game.getCurrentPlayer();
        
        // Count automation cards in hand
        List<Card> hand = player.getHand();
        int automationCount = 0;
        for (Card card : hand) {
            if (card instanceof AutomationCard) {
                automationCount++;
            }
        }
        
        // After buy phase, automation cards should still be in hand
        game.buyPhase();
        List<Card> handAfter = player.getHand();
        int automationCountAfter = 0;
        for (Card card : handAfter) {
            if (card instanceof AutomationCard) {
                automationCountAfter++;
            }
        }
        
        assertEquals(automationCount, automationCountAfter);
    }

    @Test
    public void testPurchaseCard() {
        Player player = game.getCurrentPlayer();
        
        // Give player money
        game.buyPhase();
        
        int deckSizeBefore = player.getDeckSize();
        
        if (player.getMoney() > 0) {
            game.purchaseCard("Bitcoin");
            
            // Card should be added to deck
            assertEquals(deckSizeBefore + 1, player.getDeckSize());
        }
    }

    @Test
    public void testGameEndCondition() {
        assertFalse(game.shouldGameEnd());
        
        // Get initial Framework count
        int initialFrameworks = game.getSupply().getQuantity("Framework");
        
        // Purchase all Framework cards
        Player currentPlayer = game.getCurrentPlayer();
        for (int i = 0; i < initialFrameworks; i++) {
            // Set up player with enough money
            if (currentPlayer.getMoney() < 8) {
                game.buyPhase();
            }
            game.purchaseCard("Framework");
        }
        
        assertTrue(game.shouldGameEnd());
    }

    @Test
    public void testAutomationPointsCalculation() {
        // Initial AP should come only from Method cards (3 methods x 1 AP each)
        assertEquals(3, alice.calculateTotalAP());
        assertEquals(3, bob.calculateTotalAP());
    }

    @Test
    public void testTwoPlayers() {
        // Game should have 2 players
        assertEquals("Alice", alice.getName());
        assertEquals("Bob", bob.getName());
        assertNotEquals(alice.getName(), bob.getName());
    }

    // ===== TURN PHASE TESTS =====
    @Test
    public void testCleanupPhaseClearsHand() {
        Player player = game.getCurrentPlayer();
        
        int handSizeBefore = player.getHand().size();
        assertTrue(handSizeBefore > 0);
        
        game.cleanupPhase();
        
        // New hand should be dealt, size should be 5 again
        assertEquals(5, player.getHand().size());
    }

    @Test
    public void testMoneyResetAfterCleanup() {
        Player player = game.getCurrentPlayer();
        
        game.buyPhase();
        assertTrue(player.getMoney() >= 0);
        
        game.cleanupPhase();
        
        // Money should reset after cleanup
        assertEquals(0, player.getMoney());
    }
}

