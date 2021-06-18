package solversteam.aveway.Models;

/**
 * Created by Ibrahim on 06/02/2017.
 */

public class CatModel2 {
    int url ;
    String name , price , prevprice , offer;

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public CatModel2(int url, String name, String price, String prevprice, String offer) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.prevprice = prevprice;
        this.offer = offer;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrevprice() {
        return prevprice;
    }

    public void setPrevprice(String prevprice) {
        this.prevprice = prevprice;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
