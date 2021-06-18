package solversteam.aveway.Models;

/**
 * Created by ahmed ezz on 06/03/2017.
 */

public class CustomCountries {
    private String id_country,country_name,price,img,delay;
    private int idzone;

    public CustomCountries(String country_name, String id_country, int zone) {
        this.country_name = country_name;
        this.id_country = id_country;
        this.idzone=zone;
    }
    public CustomCountries(String country_name, String id_country, String price, String img , String delay) {
        this.country_name = country_name;
        this.id_country = id_country;
        this.price=price;
        this.img=img;
        this.delay=delay;
    }

    public int getIdzone() {
        return idzone;
    }

    public void setIdzone(int idzone) {
        this.idzone = idzone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getId_country() {
        return id_country;
    }

    public void setId_country(String id_country) {
        this.id_country = id_country;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
}
