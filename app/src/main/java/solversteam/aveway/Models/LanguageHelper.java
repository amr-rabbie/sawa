package solversteam.aveway.Models;

/**
 * Created by ahmed ezz on 28/02/2017.
 */

public class LanguageHelper {
    private int conversionrate,country;
    private String countryname,currencyname,currency;

    public LanguageHelper(int conversionrate, int country, String countryname, String currencyname, String currency) {
        this.conversionrate = conversionrate;
        this.country = country;
        this.countryname = countryname;
        this.currencyname = currencyname;
        this.currency = currency;
    }

    public int getConversionrate() {
        return conversionrate;
    }

    public void setConversionrate(int conversionrate) {
        this.conversionrate = conversionrate;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int language) {
        this.country = language;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setCurrencyname(String currencyname) {
        this.currencyname = currencyname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
