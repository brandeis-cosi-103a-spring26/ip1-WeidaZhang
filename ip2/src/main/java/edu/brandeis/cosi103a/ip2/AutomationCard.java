package edu.brandeis.cosi103a.ip2;

/**
 * Represents an Automation Card that contributes to the final Automation Points (AP) score.
 * These are development tools like Method, Module, and Framework.
 */
public class AutomationCard extends Card {
    private int apValue;

    public AutomationCard(String name, int cost, int apValue) {
        super(name, cost, apValue);
        this.apValue = apValue;
    }

    public int getAPValue() {
        return apValue;
    }
}
