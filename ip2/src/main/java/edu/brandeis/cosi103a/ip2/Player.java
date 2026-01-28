package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages individual player state including deck, hand, discard pile, and resources.
 */
public class Player {
    private String name;
    private List<Card> drawPile;
    private List<Card> hand;
    private List<Card> discardPile;
    private int money;

    public Player(String name) {
        this.name = name;
        this.drawPile = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.money = 0;
    }

    public String getName() {
        return name;
    }

    public void setStarterDeck(List<Card> starterCards) {
        drawPile.addAll(starterCards);
        shuffleDeck();
    }

    public void shuffleDeck() {
        Collections.shuffle(drawPile);
    }

    public void drawHand(int cardCount) {
        hand.clear();
        for (int i = 0; i < cardCount && drawPile.size() > 0; i++) {
            hand.add(drawPile.remove(drawPile.size() - 1));
        }
        // If we couldn't draw enough cards, try reshuffling from discard
        while (hand.size() < cardCount && discardPile.size() > 0) {
            drawPile.addAll(discardPile);
            discardPile.clear();
            shuffleDeck();
            if (drawPile.size() > 0) {
                hand.add(drawPile.remove(drawPile.size() - 1));
            }
        }
    }

    public List<Card> getHand() {
        return hand;
    }

    public void playCard(int index) {
        if (index >= 0 && index < hand.size()) {
            Card card = hand.get(index);
            if (card instanceof CryptocurrencyCard) {
                CryptocurrencyCard crypto = (CryptocurrencyCard) card;
                money += crypto.getCryptoValue();
                // Card stays in hand - it will be discarded during cleanup phase
            }
        }
    }

    public int getMoney() {
        return money;
    }

    public void purchaseCard(Card card) {
        if (money >= card.getCost()) {
            money -= card.getCost();
            discardPile.add(card);
        }
    }

    public void cleanupAndDeal(int handSize) {
        // Add hand to discard pile
        discardPile.addAll(hand);
        hand.clear();
        
        // If draw pile is empty, reshuffle from discard
        if (drawPile.size() == 0 && discardPile.size() > 0) {
            drawPile.addAll(discardPile);
            discardPile.clear();
            shuffleDeck();
        }
        
        // Deal new hand - handle case where fewer cards than handSize available
        for (int i = 0; i < handSize; i++) {
            if (drawPile.size() > 0) {
                hand.add(drawPile.remove(drawPile.size() - 1));
            } else if (discardPile.size() > 0) {
                // Emergency reshuffle if still out of cards
                drawPile.addAll(discardPile);
                discardPile.clear();
                shuffleDeck();
                if (drawPile.size() > 0) {
                    hand.add(drawPile.remove(drawPile.size() - 1));
                }
            }
        }
        
        // Reset money for next turn
        money = 0;
    }

    public int calculateTotalAP() {
        int ap = 0;
        // Count AP from all Automation cards in entire collection
        for (Card card : drawPile) {
            if (card instanceof AutomationCard) {
                ap += ((AutomationCard) card).getAPValue();
            }
        }
        for (Card card : hand) {
            if (card instanceof AutomationCard) {
                ap += ((AutomationCard) card).getAPValue();
            }
        }
        for (Card card : discardPile) {
            if (card instanceof AutomationCard) {
                ap += ((AutomationCard) card).getAPValue();
            }
        }
        return ap;
    }

    public int getDeckSize() {
        return drawPile.size() + hand.size() + discardPile.size();
    }

    public void printDeckStatus() {
        System.out.println(name + "'s Deck Status:");
        System.out.println("  Hand (" + hand.size() + "): " + getHandNames());
        System.out.println("  Draw Pile: " + drawPile.size());
        System.out.println("  Discard Pile: " + discardPile.size());
        System.out.println("  Total Collection: " + getDeckSize());
        System.out.println("  AP Score: " + calculateTotalAP());
    }

    private String getHandNames() {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
            sb.append(card.getName()).append(" ");
        }
        return sb.toString();
    }
}
