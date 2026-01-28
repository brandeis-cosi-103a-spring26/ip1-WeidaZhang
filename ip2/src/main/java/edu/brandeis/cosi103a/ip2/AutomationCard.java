package edu.brandeis.cosi103a.ip2;

/**
 * Automation cards represent development automation tools.
 * They have a cost (in cryptocoins) and a value (in APs - automation points).
 */
public class AutomationCard extends Card {

    public AutomationCard(String name, int cost, int value) {
        super(name, cost, value);
    }

    /**
     * Get the AP value of this automation card.
     * @return the number of APs this card is worth at the end of the game
     */
    public int getAPValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Automation: " + super.toString();
    }
}
