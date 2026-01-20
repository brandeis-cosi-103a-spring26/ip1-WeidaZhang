package edu.brandeis.cosi103a.ip1;

import java.util.Scanner;
import java.util.Random;

/**
 * Two-Player Dice Game
 * 
 * Players take turns rolling a 6-sided die and accumulating points.
 * Each player gets up to 2 re-rolls per turn and can choose when to end their turn.
 * Each player plays 10 turns, and the player with the most points wins.
 */
public class App 
{
    private static final int NUM_PLAYERS = 2;
    private static final int TURNS_PER_PLAYER = 10;
    private static final int MAX_REROLLS = 2;
    private static final int DIE_SIDES = 6;
    
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        System.out.println("Welcome to the Two-Player Dice Game!");
        System.out.println("======================================\n");
        
        int[] scores = new int[NUM_PLAYERS];
        String[] playerNames = getPlayerNames();
        
        // Play 10 turns for each player
        for (int turn = 0; turn < TURNS_PER_PLAYER; turn++) {
            // Player 1's turn
            playTurn(0, playerNames[0], scores, turn + 1);
            
            // Player 2's turn
            playTurn(1, playerNames[1], scores, turn + 1);
            
            System.out.println("\n--- Scores after Turn " + (turn + 1) + " ---");
            displayScores(playerNames, scores);
            System.out.println();
        }
        
        // Determine winner
        displayFinalResults(playerNames, scores);
    }
    
    /**
     * Get names of both players from command line input
     */
    private static String[] getPlayerNames() {
        String[] names = new String[NUM_PLAYERS];
        for (int i = 0; i < NUM_PLAYERS; i++) {
            System.out.print("Enter name for Player " + (i + 1) + ": ");
            names[i] = scanner.nextLine().trim();
            if (names[i].isEmpty()) {
                names[i] = "Player " + (i + 1);
            }
        }
        System.out.println();
        return names;
    }
    
    /**
     * Execute one player's turn
     */
    private static void playTurn(int playerIndex, String playerName, int[] scores, int turnNumber) {
        System.out.println("\n" + playerName + "'s Turn " + turnNumber);
        System.out.println("------------------------");
        
        int currentDieValue = rollDie();
        int rerollsUsed = 0;
        
        System.out.println("You rolled: " + currentDieValue);
        
        while (rerollsUsed < MAX_REROLLS) {
            System.out.print("Do you want to re-roll? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            
            if (choice.equals("yes") || choice.equals("y")) {
                currentDieValue = rollDie();
                rerollsUsed++;
                System.out.println("You rolled: " + currentDieValue);
                System.out.println("Re-rolls used: " + rerollsUsed + "/" + MAX_REROLLS);
            } else if (choice.equals("no") || choice.equals("n")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
        
        scores[playerIndex] += currentDieValue;
        System.out.println(playerName + " ends their turn with a score of " + currentDieValue);
        System.out.println("Total score: " + scores[playerIndex]);
    }
    
    /**
     * Roll a 6-sided die
     */
    private static int rollDie() {
        return random.nextInt(DIE_SIDES) + 1;
    }
    
    /**
     * Display current scores
     */
    private static void displayScores(String[] playerNames, int[] scores) {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            System.out.println(playerNames[i] + ": " + scores[i] + " points");
        }
    }
    
    /**
     * Display final results and determine winner
     */
    private static void displayFinalResults(String[] playerNames, int[] scores) {
        System.out.println("\n===============================");
        System.out.println("GAME OVER!");
        System.out.println("===============================");
        System.out.println("\nFinal Scores:");
        displayScores(playerNames, scores);
        
        if (scores[0] > scores[1]) {
            System.out.println("\n🎉 " + playerNames[0] + " wins with " + scores[0] + " points!");
        } else if (scores[1] > scores[0]) {
            System.out.println("\n🎉 " + playerNames[1] + " wins with " + scores[1] + " points!");
        } else {
            System.out.println("\n🎲 It's a tie! Both players have " + scores[0] + " points!");
        }
    }
}
