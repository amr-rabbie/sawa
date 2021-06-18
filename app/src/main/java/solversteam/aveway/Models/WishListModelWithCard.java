package solversteam.aveway.Models;

/**
 * Created by ahmed ezz on 23/02/2017.
 */

public class WishListModelWithCard {
    private WishListModel wishListModel;
    private int idcard;

    public WishListModelWithCard(WishListModel wishListModel, int idcard) {
        this.wishListModel = wishListModel;
        this.idcard = idcard;
    }

    public int getIdcard() {
        return idcard;
    }

    public void setIdcard(int idcard) {
        this.idcard = idcard;
    }

    public WishListModel getWishListModel() {
        return wishListModel;
    }

    public void setWishListModel(WishListModel wishListModel) {
        this.wishListModel = wishListModel;
    }
}
