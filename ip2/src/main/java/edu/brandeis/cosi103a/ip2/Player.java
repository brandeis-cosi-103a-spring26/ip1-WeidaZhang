package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a player's personal deck and hand in the game.
 * Players draw cards from their deck to form a hand, play cards for money,
 * and purchase cards to add to their deck.
 */
public class Player {
    private String name;
    private List<Card> deck;
    private List<Card> hand;
    private List<Card> discardPile;
    private int money;

    public Player(String name) {
        this.name = name;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.money = 0;
    }

    /**
     * Initialize player's starter deck with 7 Bitcoin and 3 Method cards.
     * These should be provided from the game supply.
     */
    public void setStarterDeck(List<Card> starterCards) {
        this.deck = new ArrayList<>(starterCards);
        shuffleDeck();
    }

    /**
     * Shuffle the player's deck.
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * Draw a hand of 5 cards from the player's deck.
     * If the deck has fewer than 5 cards, reshuffle the discard pile into the deck.
     */
    public void drawHand(int handSize) {
        hand.clear();
        money = 0;

        // If not enough cards in deck, reshuffle discard pile
        if (deck.size() < handSize) {
            deck.addAll(discardPile);
            discardPile.clear();
        }

        // Draw cards for hand
        for (int i = 0; i < handSize && deck.size() > 0; i++) {
            hand.add(deck.remove(0));
        }
    }

    /**
     * Play a card from hand to gain money (for cryptocurrency cards).
     * @param index the index of the card in hand to play
     */
    public void playCard(int index) {
        if (index < 0 || index >= hand.size()) {
            System.out.println("Invalid card index!");
            return;
        }

        Card card = hand.remove(index);
        
        // Only cryptocurrency cards generate money when played
        if (card instanceof CryptocurrencyCard) {
            money += card.getValue();
            System.out.println(name + " played " + card.getName() + " and gained " + card.getValue() + " money!");
        } else {
            System.out.println(card.getName() + " is an Automation card and doesn't generate money.");
        }

        discardPile.add(card);
    }

    /**
     * Purchase a card from the supply and add it to the discard pile.
     * @param card the card to purchase
     * @return true if purchase was successful, false otherwise
     */
    public boolean purchaseCard(Card card) {
        if (money >= card.getCost()) {
            money -= card.getCost();
            discardPile.add(card);
            System.out.println(name + " purchased " + card.getName() + " for " + card.getCost() + " money. Remaining money: " + money);
            return true;
        } else {
            System.out.println("Not enough money to purchase " + card.getName() + ". Need " + card.getCost() + ", have " + money);
            return false;
        }
    }

    /**
     * End turn: move remaining hand cards to discard pile.
     */
    public void endTurn() {
        discardPile.addAll(hand);
        hand.clear();
    }

    /**
     * Cleanup phase: Reshuffle discard pile if needed and deal new hand.
     * @param handSize the number of cards to deal
     */
    public void cleanupAndDeal(int handSize) {
        // Add remaining hand cards to discard pile
        discardPile.addAll(hand);
        hand.clear();

        // If draw pile is empty, reshuffle discard pile
        if (deck.isEmpty() && !discardPile.isEmpty()) {
            deck.addAll(discardPile);
            discardPile.clear();
            shuffleDeck();
        }

        // Draw new hand
        for (int i = 0; i < handSize && deck.size() > 0; i++) {
            hand.add(deck.remove(0));
        }

        // Reset money for next turn
        money = 0;
    }

    /**
     * Calculate total AP value of all cards in the deck.
     * Only Automation cards contribute APs.
     * @return total AP value
     */
    public int calculateTotalAP() {
        int totalAP = 0;
        List<Card> allCards = new ArrayList<>(deck);
        allCards.addAll(hand);
        allCards.addAll(discardPile);

        for (Card card : allCards) {
            if (card instanceof AutomationCard) {
                totalAP += card.getValue();
            }
        }
        return totalAP;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public List<Card> getHand() {
        return new ArrayList<>(hand);
    }

    public int getDeckSize() {
        return deck.size() + hand.size() + discardPile.size();
    }

    public void printHand() {
        System.out.println(name + "'s hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("  " + i + ": " + hand.get(i));
        }
    }

    public void printDeckStatus() {
        System.out.println(name + "'s Deck Status:");
        System.out.println("  Hand: " + hand.size() + " cards");
        System.out.println("  Draw Pile: " + deck.size() + " cards");
        System.out.println("  Discard Pile: " + discardPile.size() + " cards");
        System.out.println("  Total in Collection: " + getDeckSize() + " cards");
        System.out.println("  Automation Points (AP): " + calculateTotalAP());
    }
}
