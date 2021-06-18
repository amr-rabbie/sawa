package solversteam.aveway.Models;

/**
 * Created by Ibrahim on 03/02/2017.
 */

public class CatModel {
    int url ;
    String name;

    public CatModel(int url, String name) {
        this.url = url;
        this.name = name;
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
