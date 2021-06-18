package solversteam.aveway.Models;

/**
 * Created by ahmed ezz on 24/02/2017.
 */

public class CurrencyModel {
    private String currency_name,currency_id;
    private int conversion_rate;

    public CurrencyModel(String currency_name, String currency_id, int conversion_rate) {
        this.currency_name = currency_name;
        this.currency_id = currency_id;
        this.conversion_rate = conversion_rate;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(String currency_id) {
        this.currency_id = currency_id;
    }

    public int getConversion_rate() {
        return conversion_rate;
    }

    public void setConversion_rate(int conversion_rate) {
        this.conversion_rate = conversion_rate;
    }
}
