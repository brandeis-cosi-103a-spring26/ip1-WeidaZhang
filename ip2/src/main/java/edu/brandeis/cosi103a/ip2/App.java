package edu.brandeis.cosi103a.ip2;

/**
 * Main application that runs automated 2-player Dominion-style card game.
 * Players automatically play their hands and make card purchases until Framework cards are depleted.
 */
public class App {
    public static void main(String[] args) {
        Game game = new Game("Alice", "Bob");
        
        int turnCount = 0;
        int maxTurns = 1000;  // Increased to allow game to finish properly
        
        System.out.println("=== Starting Game ===\n");
        
        while (!game.shouldGameEnd() && turnCount < maxTurns) {
            Player currentPlayer = game.getCurrentPlayer();
            
            // Display turn information
            game.printTurnStatus();
            System.out.println("\n" + currentPlayer.getName() + "'s Turn:");
            
            // Execute turn phases
            game.buyPhase();
            
            // Choose and purchase card based on strategy
            if (currentPlayer.getMoney() > 0) {
                String cardToBuy = chooseBestCard(game);
                System.out.println(currentPlayer.getName() + " buys: " + cardToBuy);
                game.purchaseCard(cardToBuy);
            }
            
            game.cleanupPhase();
            game.nextPlayer();
            turnCount++;
        }
        
        game.endGame();
    }
    
    /**
     * Simple AI strategy: prioritize Framework > Module > Ethereum > Method > Bitcoin
     */
    private static String chooseBestCard(Game game) {
        Player player = game.getCurrentPlayer();
        GameSupply supply = game.getSupply();
        
        // Card costs: Framework=8, Module=5, Ethereum=3, Method=2, Bitcoin=0
        int[] costs = {8, 5, 3, 2, 0};
        String[] priorityCards = {"Framework", "Module", "Ethereum", "Method", "Bitcoin"};
        
        for (int i = 0; i < priorityCards.length; i++) {
            String cardName = priorityCards[i];
            int cost = costs[i];
            if (supply.hasCard(cardName) && player.getMoney() >= cost) {
                return cardName;
            }
        }
        return "Bitcoin"; // Default fallback
    }
}
