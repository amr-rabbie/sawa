package solversteam.aveway.Models;

/**
 * Created by Ibrahim on 20/02/2017.
 */

public class WishListModel {
    private String Name , Price , PrevPrice,item_id;
    private String Image;
    private int idcard;



    public int getIdcard() {
        return idcard;
    }

    public void setIdcard(int idcard) {
        this.idcard = idcard;
    }

    public WishListModel(String name, String price, String prevPrice, String image, String item_id) {
        Name = name;
        Price = price;
        PrevPrice = prevPrice;
        Image = image;
        this.item_id=item_id;

    }

    public WishListModel(String name, String price, String prevPrice, String image,String item_id,int idcard) {
        Name = name;
        Price = price;
        PrevPrice = prevPrice;
        Image = image;
        this.item_id=item_id;
        this.idcard=idcard;

    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getPrevPrice() {
        return PrevPrice;
    }

    public void setPrevPrice(String prevPrice) {
        PrevPrice = prevPrice;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
