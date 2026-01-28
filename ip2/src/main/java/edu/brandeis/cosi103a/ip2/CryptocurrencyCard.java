package edu.brandeis.cosi103a.ip2;

/**
 * Represents a Cryptocurrency Card that generates money during the buy phase.
 * These are currency cards like Bitcoin, Ethereum, and Dogecoin.
 */
public class CryptocurrencyCard extends Card {
    private int cryptoValue;

    public CryptocurrencyCard(String name, int cost, int cryptoValue) {
        super(name, cost, cryptoValue);
        this.cryptoValue = cryptoValue;
    }

    public int getCryptoValue() {
        return cryptoValue;
    }
}
