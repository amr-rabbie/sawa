package solversteam.aveway.Models;

import java.io.Serializable;

/**
 * Created by mosta on 3/19/2017.
 */

public class Order_model implements Serializable {
String order_id,name,orederstatenumber,delveery_date,price,totalprice,shiping,type,order_status;

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public Order_model(String name, String orederstatenumber, String delveery_date, String price, String totalprice, String shiping, String type, String order_status, String order_id) {
        this.name = name;
        this.orederstatenumber = orederstatenumber;
        this.delveery_date = delveery_date;
        this.price = price;
        this.totalprice = totalprice;
        this.type=type;
        this.order_status=order_status;

        this.shiping = shiping;
        this.order_id = order_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrederstatenumber() {
        return orederstatenumber;
    }

    public void setOrederstatenumber(String invoice_date) {
        this.orederstatenumber = invoice_date;
    }

    public String getDelveery_date() {
        return delveery_date;
    }

    public void setDelveery_date(String delveery_date) {
        this.delveery_date = delveery_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public String getShiping() {
        return shiping;
    }

    public void setShiping(String shiping) {
        this.shiping = shiping;
    }
}
