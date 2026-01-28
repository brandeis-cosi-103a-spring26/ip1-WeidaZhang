package edu.brandeis.cosi103a.ip2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Orchestrates the overall game flow, managing turns, phases, and game state.
 */
public class Game {
    private List<Player> players;
    private GameSupply supply;
    private int currentPlayerIndex;
    private int round;

    public Game(String player1Name, String player2Name) {
        this.players = new ArrayList<>();
        players.add(new Player(player1Name));
        players.add(new Player(player2Name));
        this.supply = new GameSupply();
        this.round = 1;
        
        initializeGame();
    }

    private void initializeGame() {
        // Distribute starter decks: 7 Bitcoin + 3 Method per player
        for (Player player : players) {
            List<Card> starterCards = new ArrayList<>();
            starterCards.addAll(supply.getCards("Bitcoin", 7));
            starterCards.addAll(supply.getCards("Method", 3));
            player.setStarterDeck(starterCards);
        }
        
        // Deal initial 5-card hands
        for (Player player : players) {
            player.drawHand(5);
        }
        
        // Randomly select starting player
        Random rand = new Random();
        currentPlayerIndex = rand.nextInt(2);
    }

    public void buyPhase() {
        Player player = getCurrentPlayer();
        List<Card> hand = player.getHand();
        
        // Play all cryptocurrency cards (iterate backwards to avoid index issues)
        for (int i = hand.size() - 1; i >= 0; i--) {
            Card card = hand.get(i);
            if (card instanceof CryptocurrencyCard) {
                player.playCard(i);
            }
        }
    }

    public void purchaseCard(String cardName) {
        Player player = getCurrentPlayer();
        Card card = supply.getCard(cardName);
        if (card != null) {
            player.purchaseCard(card);
        }
    }

    public void cleanupPhase() {
        Player player = getCurrentPlayer();
        player.cleanupAndDeal(5);
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        if (currentPlayerIndex == 0) {
            round++;
        }
    }

    public boolean shouldGameEnd() {
        return supply.getQuantity("Framework") == 0;
    }

    public void endGame() {
        System.out.println("\n=== GAME OVER ===");
        System.out.println("Final Automation Points:");
        Player winner = null;
        int maxAP = 0;
        for (Player player : players) {
            int ap = player.calculateTotalAP();
            System.out.println(player.getName() + ": " + ap + " AP");
            if (ap > maxAP) {
                maxAP = ap;
                winner = player;
            }
        }
        System.out.println("\nWinner: " + winner.getName() + " with " + maxAP + " AP!");
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public GameSupply getSupply() {
        return supply;
    }

    public int getRound() {
        return round;
    }

    public void printStatus() {
        System.out.println("\n=== Round " + round + " ===");
        System.out.println("Current Player: " + getCurrentPlayer().getName());
        supply.printSupply();
        for (Player player : players) {
            System.out.println(player.getName() + " AP: " + player.calculateTotalAP() + " Deck Size: " + player.getDeckSize());
        }
    }

    public void printTurnStatus() {
        System.out.println("\n--- Round " + round + " ---");
        supply.printSupply();
        for (Player player : players) {
            player.printDeckStatus();
        }
    }
}
