package Data;

/**
 * Created by Agus on 23/10/2016.
 */
public class Client {
    private String ID;
    private String Name;
    private String Mobile;

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

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (ID != null && client.ID != null)
            return ID.equals(client.ID);
        if (Name != null && client.Name != null)
            return Name.equals(client.Name);
        if(Mobile != null && client.getMobile() != null)
            return Mobile.equals(client.Mobile);
        if(ID == null && client.ID == null)
            return true;
        if (Name == null && client.Name == null)
            return true;
        if(Mobile == null && client.getMobile() == null)
            return true;
        return false;
    }

    public String toString(){
        return "Nombre: "+ this.Name+ " \nDNI: "
                + this.ID+ " \nTelefono: "+this.Mobile+"\n";
    }

}
