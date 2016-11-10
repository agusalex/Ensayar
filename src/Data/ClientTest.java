package Data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class ClientTest {

    @Test
    public void getIDTest() throws Exception {
        Client cl = new Client("Roberto");
        assertNull(cl.getID());
        cl.setID("12345612345");
        assertEquals(cl.getID(), "12345612345");
    }

    @Test
    public void getNameTest() throws Exception {
        Client cl = new Client("Roberto");
        assertEquals(cl.getName(), "Roberto");
        cl.setName("Juan");
        assertEquals(cl.getName(), "Juan");
    }

    @Test
    public void getMobileTest() throws Exception {
        Client cl = new Client("Roberto");
        assertNull(cl.getMobile());
        cl.setMobile("1534768940");
        assertEquals(cl.getMobile(), "1534768940");
    }


    @Test
    public void showClients() {
        ArrayList<Client> clients = generateRandomClients();
        clients.forEach(System.out::println);
    }


    private ArrayList<Client> generateRandomClients() {
        String[] names = {"Gerardo", "Agustin", "Emmanuel", "Fede", "Roberto", "Juan", "Pustilnik", "Matzi", "Roberto"
                , "Maria", "Josefina", "Claudia", "Alberto", "Esteban"};
        String[] IDs = {"1", "2", "3", "4", "5", "6", "7", "8", "9"
                , "10", "11", "12", "13", "14"};
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
            client.setID(IDs[randomIndex]);
            mobile = Integer.toString(r.nextInt(1599999999 - 1500000000) + 1500000000);
            client.setMobile(mobile);
            clients.add(client);
        }

        return clients;


    }

}