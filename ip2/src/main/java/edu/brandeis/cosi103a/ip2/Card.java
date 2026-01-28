package edu.brandeis.cosi103a.ip2;

/**
 * Abstract base class for all cards in the Dominion-style card game.
 * Cards can be either Automation Cards (contribute to final AP score) or Cryptocurrency Cards (generate money).
 */
public abstract class Card {
    protected String name;
    protected int cost;
    protected int value;

    public Card(String name, int cost, int value) {
        this.name = name;
        this.cost = cost;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }
}
