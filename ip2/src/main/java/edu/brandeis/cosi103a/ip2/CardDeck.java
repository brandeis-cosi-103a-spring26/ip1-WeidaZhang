package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.List;

/**
 * CardDeck manages the collection of all cards available in the game.
 * Includes both Automation cards and Cryptocurrency cards with their quantities.
 */
public class CardDeck {
    private List<Card> cards;

    public CardDeck() {
        this.cards = new ArrayList<>();
        initializeCards();
    }

    /**
     * Initialize all cards in the game deck.
     */
    private void initializeCards() {
        // Automation Cards
        for (int i = 0; i < 14; i++) {
            cards.add(new AutomationCard("Method", 2, 1));
        }
        for (int i = 0; i < 8; i++) {
            cards.add(new AutomationCard("Module", 5, 3));
        }
        for (int i = 0; i < 8; i++) {
            cards.add(new AutomationCard("Framework", 8, 6));
        }

        // Cryptocurrency Cards
        for (int i = 0; i < 60; i++) {
            cards.add(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        for (int i = 0; i < 40; i++) {
            cards.add(new CryptocurrencyCard("Ethereum", 3, 2));
        }
        for (int i = 0; i < 30; i++) {
            cards.add(new CryptocurrencyCard("Dogecoin", 6, 3));
        }
    }

    /**
     * Get all cards in the deck.
     * @return list of all cards
     */
    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Get the total number of cards in the deck.
     * @return size of the deck
     */
    public int getSize() {
        return cards.size();
    }

    /**
     * Print information about all cards in the deck.
     */
    public void printDeck() {
        System.out.println("=== Game Deck ===");
        System.out.println("Total cards: " + getSize());
        System.out.println("\nAutomation Cards:");
        System.out.println("  - Method x14 (cost: 2, value: 1)");
        System.out.println("  - Module x8 (cost: 5, value: 3)");
        System.out.println("  - Framework x8 (cost: 8, value: 6)");
        System.out.println("\nCryptocurrency Cards:");
        System.out.println("  - Bitcoin x60 (cost: 0, value: 1)");
        System.out.println("  - Ethereum x40 (cost: 3, value: 2)");
        System.out.println("  - Dogecoin x30 (cost: 6, value: 3)");
    }
}
