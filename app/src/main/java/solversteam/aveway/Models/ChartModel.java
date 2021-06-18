package solversteam.aveway.Models;

/**
 * Created by Ibrahim on 03/02/2017.
 */

public class ChartModel {
    String Name , Seller ;
    int Price , prevPrice , offer ;
    String ImageUrl,item_id;
    public ChartModel(String ImageUrl ,String name, String seller, int price, int prevPrice , int offer) {
        Name = name;
        Seller = seller;
        Price = price;
        this.prevPrice = prevPrice;
        this.offer = offer;
        this.ImageUrl = ImageUrl;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public ChartModel(String ImageUrl , String name, int price, int prevPrice , int offer, String item_id) {
        Name = name;
        Price = price;
        this.prevPrice = prevPrice;
        this.offer = offer;
        this.ImageUrl = ImageUrl;
        this.item_id=item_id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getPrevPrice() {
        return prevPrice;
    }

    public void setPrevPrice(int prevPrice) {
        this.prevPrice = prevPrice;
    }
}
