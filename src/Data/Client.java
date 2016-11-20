package Data;


import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("ALL")
public class Client {
    private String ID;
    private String Name;
    private String Mobile;

    public Client(String name) {
        this.Name = name;
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
        if (Mobile != null && client.getMobile() != null)
            return Mobile.equals(client.Mobile);
        if (ID == null && client.ID == null)
            return true;
        return Name == null && client.Name == null || Mobile == null && client.getMobile() == null;
    }

    public static ArrayList<Client> generateRandomClients() {
        String[] names = {"Gerardo", "Agustin", "Emmanuel", "Fede", "Roberto", "Juan", "Pustilnik", "Maxi", "Roberto"
                , "Maria", "Josefina", "Claudia", "Alberto", "Esteban"};
        String[] IDs = {"12345678"};
        ArrayList<Client> clients = new ArrayList<>();

        Random r = new Random();
        Client client;
        String mobile;
        int Low = 0;
        int High = names.length - 1;
        int randomIndex;

        for (int i = 0; i < High; i++) {
            client = new Client("");
            randomIndex = r.nextInt(High - Low) + Low;
            client.setName(names[randomIndex]);
            client.setID(IDs[0]);
            mobile = Integer.toString(r.nextInt(1599999999 - 1500000000) + 1500000000);
            client.setMobile(mobile);
            clients.add(client);
        }

        return clients;


    }


    public String toString() {
        return "Nombre: " + this.Name + " \nDNI: "
                + this.ID + " \nTelefono: " + this.Mobile + "\n";
    }

}
