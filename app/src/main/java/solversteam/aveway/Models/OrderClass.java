package solversteam.aveway.Models;

import java.util.ArrayList;

/**
 * Created by ahmed ezz on 05/03/2017.
 */

public class OrderClass {
    private ArrayList<CartCLass>cartCLasses;
    private int id_cart;

    public OrderClass(ArrayList<CartCLass> cartCLasses, int id_cart) {
        this.cartCLasses = cartCLasses;
        this.id_cart = id_cart;
    }

    public ArrayList<CartCLass> getCartCLasses() {
        return cartCLasses;
    }

    public void setCartCLasses(ArrayList<CartCLass> cartCLasses) {
        this.cartCLasses = cartCLasses;
    }

    public int getId_cart() {
        return id_cart;
    }

    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
    }
}
