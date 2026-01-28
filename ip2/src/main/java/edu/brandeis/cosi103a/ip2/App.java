package edu.brandeis.cosi103a.ip2;

/**
 * Card Game Application (Dominion-style Deck Building)
 * Two players automatically play to build their decks and maximize Automation Points (APs).
 * 
 * Game Mechanics:
 * - Each player has their own deck (cards they own)
 * - Draw Pile: Cards waiting to be drawn
 * - Hand: Cards currently played from
 * - Discard Pile: Cards played this turn or purchased
 * - When draw pile is empty, discard pile reshuffles into draw pile
 * - Players use money from cards to purchase new cards
 * - Goal: Build a deck with the most Automation Points
 * 
 * Game ends when all Framework cards are purchased.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("=== Dominion-Style Card Game ===\n");
        System.out.println("Game Goal: Build the most valuable deck by maximizing Automation Points (AP)\n");

        // Create a game with 2 players
        Game game = new Game("Alice", "Bob");

        // Simulate multiple turns until game ends
        System.out.println("\n=== Game Started ===");
        
        int turnCount = 0;
        while (!game.shouldGameEnd() && turnCount < 100) {
            turnCount++;
            System.out.println("\n" + "=".repeat(70));
            System.out.println("TURN " + turnCount + " - " + game.getCurrentPlayer().getName() + "'s Turn");
            System.out.println("=".repeat(70));
            
            // Display current game state
            game.printTurnStatus();
            
            // Buy Phase
            System.out.println("\n--- Buy Phase ---");
            game.buyPhase();
            
            // Make a purchase (automated AI strategy)
            String cardToBuy = chooseBestCard(game);
            if (cardToBuy != null) {
                System.out.println(game.getCurrentPlayer().getName() + " purchases: " + cardToBuy);
                game.purchaseCard(cardToBuy);
            } else {
                System.out.println(game.getCurrentPlayer().getName() + " cannot afford any cards.");
            }
            
            // Cleanup Phase
            System.out.println("\n--- Cleanup Phase ---");
            game.cleanupPhase();
            System.out.println(game.getCurrentPlayer().getName() + " cleaned up and dealt new hand.");
            
            // Move to next player
            game.nextPlayer();
            
            // Check if game should end
            if (game.shouldGameEnd()) {
                System.out.println("\n*** All Framework cards have been purchased! ***");
                System.out.println("*** Game is ending... ***");
                break;
            }
        }

        // Show final game status
        System.out.println("\n" + "=".repeat(70));
        System.out.println("GAME OVER");
        System.out.println("=".repeat(70));
        game.printStatus();

        // End game and show results
        game.endGame();
    }

    /**
     * Automated AI strategy to choose best card to buy.
     * Prioritizes: Framework > Module > Ethereum > Method > Bitcoin
     * Only attempts to buy if player has enough money.
     */
    private static String chooseBestCard(Game game) {
        String[] cardPriority = {"Framework", "Module", "Ethereum", "Method", "Bitcoin"};
        Player player = game.getCurrentPlayer();
        
        for (String cardName : cardPriority) {
            // Create a dummy card to check cost
            Card dummyCard = null;
            if (cardName.equals("Framework")) {
                dummyCard = new AutomationCard("Framework", 8, 6);
            } else if (cardName.equals("Module")) {
                dummyCard = new AutomationCard("Module", 5, 3);
            } else if (cardName.equals("Ethereum")) {
                dummyCard = new CryptocurrencyCard("Ethereum", 3, 2);
            } else if (cardName.equals("Method")) {
                dummyCard = new AutomationCard("Method", 2, 1);
            } else if (cardName.equals("Bitcoin")) {
                dummyCard = new CryptocurrencyCard("Bitcoin", 0, 1);
            }
            
            // Try to buy if we have money and card is available
            if (dummyCard != null && player.getMoney() >= dummyCard.getCost() && 
                game.getSupply().hasCard(cardName)) {
                return cardName;
            }
        }
        return null;
    }
}
