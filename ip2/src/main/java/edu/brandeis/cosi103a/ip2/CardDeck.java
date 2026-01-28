package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Initializes and manages the master deck of all 160 cards used in the game.
 * Card distribution: 14 Method, 8 Module, 8 Framework, 60 Bitcoin, 40 Ethereum, 30 Dogecoin
 */
public class CardDeck {
    private List<Card> cards;

    public CardDeck() {
        this.cards = new ArrayList<>();
        initializeCards();
    }

    private void initializeCards() {
        // Automation Cards: 14 Method + 8 Module + 8 Framework = 30 cards
        for (int i = 0; i < 14; i++) {
            cards.add(new AutomationCard("Method", 2, 1));
        }
        for (int i = 0; i < 8; i++) {
            cards.add(new AutomationCard("Module", 5, 3));
        }
        for (int i = 0; i < 8; i++) {
            cards.add(new AutomationCard("Framework", 8, 6));
        }

        // Cryptocurrency Cards: 60 Bitcoin + 40 Ethereum + 30 Dogecoin = 130 cards
        for (int i = 0; i < 60; i++) {
            cards.add(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        for (int i = 0; i < 40; i++) {
            cards.add(new CryptocurrencyCard("Ethereum", 3, 2));
        }
        for (int i = 0; i < 30; i++) {
            cards.add(new CryptocurrencyCard("Dogecoin", 6, 3));
        }

        // Total: 160 cards
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public int getSize() {
        return cards.size();
    }

    public Card getCard(String name) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getName().equals(name)) {
                return cards.remove(i);
            }
        }
        return null;
    }

    public List<Card> getCards(String name, int count) {
        List<Card> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Card card = getCard(name);
            if (card != null) {
                result.add(card);
            }
        }
        return result;
    }
}
