package Data;

/**
 * Created by Agus on 23/10/2016.
 */
public class Client {
    String ID;
    String Name;
    String Surname;

    public Client(String name){
        this.Name=name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    String Mobile;




}
