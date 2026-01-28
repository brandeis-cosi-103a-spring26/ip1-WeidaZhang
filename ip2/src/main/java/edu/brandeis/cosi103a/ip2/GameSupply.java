package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the shared supply of purchaseable cards available to all players.
 */
public class GameSupply {
    private List<Card> templates;  // Template cards for creating new instances
    private Map<String, Integer> quantities;  // Track remaining quantity of each card type

    public GameSupply() {
        this.templates = new ArrayList<>();
        this.quantities = new HashMap<>();
        initializeSupply();
    }

    private void initializeSupply() {
        // Create template cards and track quantities
        // Automation Cards: 14 Method + 8 Module + 8 Framework = 30 cards
        templates.add(new AutomationCard("Method", 2, 1));
        quantities.put("Method", 14);
        
        templates.add(new AutomationCard("Module", 5, 3));
        quantities.put("Module", 8);
        
        templates.add(new AutomationCard("Framework", 8, 6));
        quantities.put("Framework", 8);

        // Cryptocurrency Cards: 60 Bitcoin + 40 Ethereum + 30 Dogecoin = 130 cards
        templates.add(new CryptocurrencyCard("Bitcoin", 0, 1));
        quantities.put("Bitcoin", 60);
        
        templates.add(new CryptocurrencyCard("Ethereum", 3, 2));
        quantities.put("Ethereum", 40);
        
        templates.add(new CryptocurrencyCard("Dogecoin", 6, 3));
        quantities.put("Dogecoin", 30);
    }

    public Card getCard(String name) {
        // Check if card type exists and has quantity remaining
        Integer qty = quantities.get(name);
        if (qty != null && qty > 0) {
            quantities.put(name, qty - 1);
            
            // Find template and create new instance
            for (Card card : templates) {
                if (card.getName().equals(name)) {
                    if (card instanceof AutomationCard) {
                        AutomationCard template = (AutomationCard) card;
                        return new AutomationCard(template.getName(), template.getCost(), template.getValue());
                    } else if (card instanceof CryptocurrencyCard) {
                        CryptocurrencyCard template = (CryptocurrencyCard) card;
                        return new CryptocurrencyCard(template.getName(), template.getCost(), template.getValue());
                    }
                }
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
            } else {
                break; // No more cards available
            }
        }
        return result;
    }

    public boolean hasCard(String name) {
        Integer qty = quantities.get(name);
        return qty != null && qty > 0;
    }

    public int getQuantity(String name) {
        Integer qty = quantities.get(name);
        return qty != null ? qty : 0;
    }

    public void printSupply() {
        System.out.println("=== Supply ===");
        System.out.println("Method: " + getQuantity("Method"));
        System.out.println("Module: " + getQuantity("Module"));
        System.out.println("Framework: " + getQuantity("Framework"));
        System.out.println("Bitcoin: " + getQuantity("Bitcoin"));
        System.out.println("Ethereum: " + getQuantity("Ethereum"));
        System.out.println("Dogecoin: " + getQuantity("Dogecoin"));
    }
}
