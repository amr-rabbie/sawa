package solversteam.aveway.Models;

/**
 * Created by ahmed ezz on 05/03/2017.
 */

public class CartCLass {
    private int id_shop,id_lang,id_currency,id_product;
    private int quantity;
    private String totalmoney,id_customer;
     String name,img,price;

    public String getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setPrice(String price) {

        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalmoney() {
        return totalmoney;
    }

    public void setTotalmoney(String totalmoney) {
        this.totalmoney = totalmoney;
    }

    public CartCLass(int id_shop, int id_lang, int id_currency, String id_customer, int id_product, int quantity, String totalmoney, String price, String name, String img) {
        this.id_shop = id_shop;
        this.id_lang = id_lang;
        this.id_currency = id_currency;
        this.id_customer = id_customer;
        this.id_product = id_product;
        this.quantity = quantity;
        this.totalmoney=totalmoney;
        this.name=name;
        this.price=price;
        this.img=img;
    }

    public int getId_shop() {
        return id_shop;
    }

    public void setId_shop(int id_shop) {
        this.id_shop = id_shop;
    }

    public int getId_lang() {
        return id_lang;
    }

    public void setId_lang(int id_lang) {
        this.id_lang = id_lang;
    }

    public int getId_currency() {
        return id_currency;
    }

    public void setId_currency(int id_currency) {
        this.id_currency = id_currency;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
