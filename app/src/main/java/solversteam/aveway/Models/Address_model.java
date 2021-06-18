package solversteam.aveway.Models;

/**
 * Created by mosta on 3/16/2017.
 */

public class Address_model {
    String name,city,address,mobile,id,idzone;

    public Address_model(String name, String city, String address, String mobile, String id, String idzone)
    {
        this.address= address;
        this.city=city;
        this.mobile=mobile;
        this.name=name;
        this.id=id;
        this.idzone=idzone;
    }

    public String getIdzone() {
        return idzone;
    }

    public void setIdzone(String idzone) {
        this.idzone = idzone;
    }

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
