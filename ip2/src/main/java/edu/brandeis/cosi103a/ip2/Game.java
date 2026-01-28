package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game manages the overall game state and flow.
 * Handles players, the shared supply, and turn-based gameplay.
 */
public class Game {
    private List<Player> players;
    private GameSupply supply;
    private int currentPlayerIndex;
    private int roundNumber;
    private static final int HAND_SIZE = 5;
    private static final int STARTER_BITCOINS = 7;
    private static final int STARTER_METHODS = 3;

    public Game(String... playerNames) {
        this.players = new ArrayList<>();
        this.supply = new GameSupply();
        this.roundNumber = 0;

        for (String name : playerNames) {
            players.add(new Player(name));
        }

        // Initialize the game
        initializeGame();
    }

    /**
     * Initialize the game:
     * 1. Distribute starter decks (7 Bitcoin + 3 Method) to each player
     * 2. Shuffle each player's deck
     * 3. Deal initial hand of 5 cards
     * 4. Choose random starting player
     */
    private void initializeGame() {
        System.out.println("=== Initializing Game ===\n");

        // Distribute starter decks
        for (Player player : players) {
            List<Card> starterDeck = new ArrayList<>();

            // Get 7 Bitcoins
            List<Card> bitcoins = supply.getCards("Bitcoin", STARTER_BITCOINS);
            starterDeck.addAll(bitcoins);

            // Get 3 Methods
            List<Card> methods = supply.getCards("Method", STARTER_METHODS);
            starterDeck.addAll(methods);

            // Set and shuffle the starter deck
            player.setStarterDeck(starterDeck);
            System.out.println(player.getName() + " received starter deck: 7 Bitcoins + 3 Methods");
        }

        // Deal initial hand to each player
        for (Player player : players) {
            player.drawHand(HAND_SIZE);
            System.out.println(player.getName() + " drew initial hand of " + HAND_SIZE + " cards");
        }

        // Choose random starting player
        Random random = new Random();
        currentPlayerIndex = random.nextInt(players.size());
        System.out.println("\n" + getCurrentPlayer().getName() + " is chosen as the starting player!");
        System.out.println();
    }

    /**
     * Get the current player's turn.
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Get the game supply.
     * @return the GameSupply instance
     */
    public GameSupply getSupply() {
        return supply;
    }

    /**
     * Get a player by index.
     * @param index the player index
     * @return the player at that index
     */
    public Player getPlayer(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index);
        }
        return null;
    }

    /**
     * Get all players.
     * @return list of all players
     */
    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Execute buy phase: Player plays cryptocoins and purchases up to 1 card.
     * Only cryptocurrency cards are played to generate money.
     * Automation cards stay in hand and are not played.
     */
    public void buyPhase() {
        Player player = getCurrentPlayer();
        System.out.println("\n--- Buy Phase ---");
        player.printHand();

        // Play all cryptocurrency cards for money (in any order)
        System.out.println(player.getName() + " plays all cryptocurrency cards:");
        
        boolean hasPlayedCards = false;
        // Iterate backwards to avoid index issues when removing
        List<Card> hand = player.getHand();
        for (int i = hand.size() - 1; i >= 0; i--) {
            if (hand.get(i) instanceof CryptocurrencyCard) {
                player.playCard(i);
                hasPlayedCards = true;
            }
        }
        
        if (!hasPlayedCards) {
            System.out.println(player.getName() + " has no cryptocurrency cards to play.");
        }

        System.out.println("\n" + player.getName() + " has " + player.getMoney() + " money to spend.");
    }

    /**
     * Purchase a card for the current player.
     * @param cardName the name of the card to purchase
     * @return true if purchase was successful
     */
    public boolean purchaseCard(String cardName) {
        Player player = getCurrentPlayer();
        if (supply.hasCard(cardName)) {
            Card card = supply.getCard(cardName);
            return player.purchaseCard(card);
        } else {
            System.out.println("Card " + cardName + " is not available in the supply.");
            return false;
        }
    }

    /**
     * Execute cleanup phase: Discard hand, reshuffle if needed, deal new hand.
     */
    public void cleanupPhase() {
        Player player = getCurrentPlayer();
        System.out.println("\n--- Cleanup Phase ---");
        int handSize = 5;
        player.cleanupAndDeal(handSize);
        System.out.println(player.getName() + " dealt " + player.getHand().size() + " new cards.");
    }

    /**
     * Check if the game should end (all Framework cards purchased).
     * @return true if game should end
     */
    public boolean shouldGameEnd() {
        return supply.getQuantity("Framework") == 0;
    }

    /**
     * Move to the next player's turn.
     */
    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        if (currentPlayerIndex == 0) {
            roundNumber++;
        }
    }

    /**
     * Get the current round number.
     * @return round number
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Calculate final scores and declare the winner.
     */
    public void endGame() {
        System.out.println("\n=== Game Over ===");
        System.out.println("Final Scores (Total APs):\n");

        Player winner = null;
        int maxAP = -1;

        for (Player player : players) {
            int totalAP = player.calculateTotalAP();
            System.out.println(player.getName() + ": " + totalAP + " AP");
            if (totalAP > maxAP) {
                maxAP = totalAP;
                winner = player;
            }
        }

        System.out.println("\n🎉 " + winner.getName() + " wins with " + maxAP + " AP!");
    }

    /**
     * Print current game status including supply and all players.
     */
    public void printStatus() {
        System.out.println("\n=== Game Status ===");
        System.out.println("Round: " + roundNumber);
        System.out.println("Current Player: " + getCurrentPlayer().getName());
        supply.printSupply();
        
        System.out.println("\n=== Player Status ===");
        for (Player player : players) {
            System.out.println(player.getName() + " - AP: " + player.calculateTotalAP() + 
                             ", Deck Size: " + player.getDeckSize());
        }
    }

    /**
     * Print detailed turn status with hands and automation points.
     */
    public void printTurnStatus() {
        System.out.println("\n--- Supply Status ---");
        supply.printSupply();
        
        System.out.println("\n--- Players' Deck Status ---");
        for (Player player : players) {
            player.printDeckStatus();
            if (!player.getHand().isEmpty()) {
                System.out.println("  Cards in Hand:");
                for (int i = 0; i < player.getHand().size(); i++) {
                    System.out.println("    " + i + ": " + player.getHand().get(i));
                }
            }
            System.out.println();
        }
    }
}
