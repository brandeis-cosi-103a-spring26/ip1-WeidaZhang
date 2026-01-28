package edu.brandeis.cosi103a.ip2;

/**
 * Cryptocurrency cards represent digital currencies.
 * They have a cost (in cryptocoins) and a value (in cryptocoins when played).
 */
public class CryptocurrencyCard extends Card {

    public CryptocurrencyCard(String name, int cost, int value) {
        super(name, cost, value);
    }

    /**
     * Get the cryptocurrency value of this card.
     * @return the number of cryptocoins this card is worth when played
     */
    public int getCryptoValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Cryptocurrency: " + super.toString();
    }
}
