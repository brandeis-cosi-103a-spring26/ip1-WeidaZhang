package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GameSupply manages the shared supply of cards available for purchase.
 * Players can buy cards from the supply to add to their personal decks.
 */
public class GameSupply {
    private Map<String, List<Card>> supply;
    private CardDeck masterDeck;

    public GameSupply() {
        this.supply = new HashMap<>();
        this.masterDeck = new CardDeck();
        initializeSupply();
    }

    /**
     * Initialize the supply with cards from the master deck.
     */
    private void initializeSupply() {
        for (Card card : masterDeck.getCards()) {
            String cardName = card.getName();
            supply.putIfAbsent(cardName, new ArrayList<>());
            supply.get(cardName).add(card);
        }
    }

    /**
     * Get a card from the supply by name, if available.
     * @param cardName the name of the card to purchase
     * @return the card if available, null otherwise
     */
    public Card getCard(String cardName) {
        List<Card> cards = supply.get(cardName);
        if (cards != null && cards.size() > 0) {
            return cards.remove(0);
        }
        return null;
    }

    /**
     * Get multiple cards of the same type from supply.
     * @param cardName the name of the card
     * @param quantity the number of cards needed
     * @return list of cards, or empty list if not enough available
     */
    public List<Card> getCards(String cardName, int quantity) {
        List<Card> result = new ArrayList<>();
        List<Card> cards = supply.get(cardName);
        if (cards != null && cards.size() >= quantity) {
            for (int i = 0; i < quantity; i++) {
                result.add(cards.remove(0));
            }
        }
        return result;
    }

    /**
     * Check if a card is available in the supply.
     * @param cardName the name of the card
     * @return true if at least one copy is available
     */
    public boolean hasCard(String cardName) {
        List<Card> cards = supply.get(cardName);
        return cards != null && cards.size() > 0;
    }

    /**
     * Get the quantity available for a specific card.
     * @param cardName the name of the card
     * @return number of copies available
     */
    public int getQuantity(String cardName) {
        List<Card> cards = supply.get(cardName);
        return cards != null ? cards.size() : 0;
    }

    /**
     * Print the current supply status.
     */
    public void printSupply() {
        System.out.println("=== Game Supply ===");
        System.out.println("Automation Cards:");
        System.out.println("  - Method: " + getQuantity("Method") + " available");
        System.out.println("  - Module: " + getQuantity("Module") + " available");
        System.out.println("  - Framework: " + getQuantity("Framework") + " available");
        System.out.println("Cryptocurrency Cards:");
        System.out.println("  - Bitcoin: " + getQuantity("Bitcoin") + " available");
        System.out.println("  - Ethereum: " + getQuantity("Ethereum") + " available");
        System.out.println("  - Dogecoin: " + getQuantity("Dogecoin") + " available");
    }
}
