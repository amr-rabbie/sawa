package solversteam.aveway.Models;

/**
 * Created by Ibrahim on 17/02/2017.
 */

public class Country_Language_Model {
    String Name , Image;
    static Boolean flag;

    public static Boolean getFlag() {
        return flag;
    }

    public static void setFlag(Boolean flag) {
        Country_Language_Model.flag = flag;
    }

    public Country_Language_Model(String name, String image , Boolean flag) {
        Name = name;
        Image = image;
        this.flag = flag;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
