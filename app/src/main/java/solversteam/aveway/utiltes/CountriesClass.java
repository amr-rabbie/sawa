package solversteam.aveway.utiltes;

/**
 * Created by ahmed ezz on 22/02/2017.
 */

public class CountriesClass {
    private String country,Currency;

    public CountriesClass(String country, String currency) {
        this.country = country;
        Currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }
}
