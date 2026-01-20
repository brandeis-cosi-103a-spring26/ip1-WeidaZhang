package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Test;
import java.lang.reflect.Method;

/**
 * Unit tests for the Two-Player Dice Game.
 */
public class AppTest 
{
    /**
     * Test that die roll returns valid values (1-6)
     */
    @Test
    public void testDieRollReturnsValidValue()
    {
        try {
            Method rollDieMethod = App.class.getDeclaredMethod("rollDie");
            rollDieMethod.setAccessible(true);
            
            // Test multiple rolls to ensure they're all in valid range
            for (int i = 0; i < 100; i++) {
                int result = (int) rollDieMethod.invoke(null);
                assertTrue("Die roll should be >= 1", result >= 1);
                assertTrue("Die roll should be <= 6", result <= 6);
            }
        } catch (Exception e) {
            fail("Failed to test die roll: " + e.getMessage());
        }
    }
    
    /**
     * Test that die roll produces variety of outcomes
     */
    @Test
    public void testDieRollDistribution()
    {
        try {
            Method rollDieMethod = App.class.getDeclaredMethod("rollDie");
            rollDieMethod.setAccessible(true);
            
            boolean[] seen = new boolean[7];
            
            // Roll die many times to check we get variety
            for (int i = 0; i < 600; i++) {
                int result = (int) rollDieMethod.invoke(null);
                seen[result] = true;
            }
            
            // We should see all values 1-6 with high probability
            for (int i = 1; i <= 6; i++) {
                assertTrue("Should see die value " + i + " in 600 rolls", seen[i]);
            }
        } catch (Exception e) {
            fail("Failed to test die distribution: " + e.getMessage());
        }
    }
    
    /**
     * Test that die roll is not always the same value
     */
    @Test
    public void testDieRollVariability()
    {
        try {
            Method rollDieMethod = App.class.getDeclaredMethod("rollDie");
            rollDieMethod.setAccessible(true);
            
            int firstRoll = (int) rollDieMethod.invoke(null);
            boolean hasDifferentValue = false;
            
            // Roll 20 times and check if we get a different value
            for (int i = 0; i < 20; i++) {
                int result = (int) rollDieMethod.invoke(null);
                if (result != firstRoll) {
                    hasDifferentValue = true;
                    break;
                }
            }
            
            assertTrue("Die rolls should vary, not always return same value", hasDifferentValue);
        } catch (Exception e) {
            fail("Failed to test die variability: " + e.getMessage());
        }
    }
    
    /**
     * Test that constants are correctly defined
     */
    @Test
    public void testGameConstants()
    {
        try {
            // Test NUM_PLAYERS = 2
            java.lang.reflect.Field numPlayersField = App.class.getDeclaredField("NUM_PLAYERS");
            numPlayersField.setAccessible(true);
            assertEquals("Should have 2 players", 2, numPlayersField.getInt(null));
            
            // Test TURNS_PER_PLAYER = 10
            java.lang.reflect.Field turnsField = App.class.getDeclaredField("TURNS_PER_PLAYER");
            turnsField.setAccessible(true);
            assertEquals("Each player should get 10 turns", 10, turnsField.getInt(null));
            
            // Test MAX_REROLLS = 2
            java.lang.reflect.Field maxRerollsField = App.class.getDeclaredField("MAX_REROLLS");
            maxRerollsField.setAccessible(true);
            assertEquals("Should allow up to 2 rerolls", 2, maxRerollsField.getInt(null));
            
            // Test DIE_SIDES = 6
            java.lang.reflect.Field dieSidesField = App.class.getDeclaredField("DIE_SIDES");
            dieSidesField.setAccessible(true);
            assertEquals("Should be a 6-sided die", 6, dieSidesField.getInt(null));
        } catch (Exception e) {
            fail("Failed to test game constants: " + e.getMessage());
        }
    }
}
