package Data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Max on 10/26/2016.
 */
public class OfferTest {
    private Random r;
    @Test
    public void getPrice() throws Exception {

    }

    @Test
    public void getClient() throws Exception {

    }

    @Test
    public void getDateAvailable() throws Exception {

    }


    @Test
    public void conflictsWith() throws Exception {

    }

    @Test
    public void getDuration() throws Exception {

    }

    @Test
    public void getDurationInMin() throws Exception {

    }

    @Test
    public void getInstruments() throws Exception {

    }

    @Test
    public void getSchedule() throws Exception {

    }

    @Test
    public void equals() throws Exception {

    }


    @Test
    public void showOffers(){
        ArrayList<Offer> offers = generateRandomOffers(5);
        for(Offer of : offers)
            System.out.println(of);
    }

    private ArrayList<Offer> generateRandomOffers(int n){
        r = new Random();
        Offer offer;
        Client client;
        ArrayList<Offer> ret = new ArrayList<Offer>();
        Schedule schedule;
        ArrayList<Client> clients =  generateRandomClients();
        ArrayList<Offer.Instruments> defaultInst = defaultInstruments();
        ArrayList<Offer.Instruments> offerInstruments = new ArrayList<Offer.Instruments>();
        int Low = 1;
        int High = 5;
        int randomIndex;
        int randomHour, randomMin;
        int cantInst;
        int[] min = {0,30};

        for(int i = 0; i< n; i++){
            cantInst = r.nextInt(defaultInst.size()-1)+1;
            offerInstruments = chosenRandomInstruments(cantInst,defaultInst);
            randomHour = r.nextInt(High-Low)+Low;
            randomMin = min[r.nextInt(1)];
            schedule = new Schedule(randomHour,randomMin,randomHour+High, randomMin);
            randomIndex = r.nextInt(clients.size()-1);
            client = clients.get(randomIndex);
            offer = new Offer(offerInstruments,schedule,client);
            ret.add(offer);
        }

        return ret;
    }

    private ArrayList<Offer.Instruments> chosenRandomInstruments(int n, ArrayList<Offer.Instruments> defaultInst){
        ArrayList<Offer.Instruments> inst = new ArrayList<Offer.Instruments>();
        Random rand = new Random();
        int randomIndex;
        for (int i =0; i< n; i++){
            randomIndex = rand.nextInt(n);
            inst.add(defaultInst.get(randomIndex));
        }
        return inst;
    }

    private ArrayList<Offer.Instruments> defaultInstruments(){
        ArrayList<Offer.Instruments> inst = new ArrayList<Offer.Instruments>();
        inst.add(Offer.Instruments.BATERIA);
        inst.add(Offer.Instruments.GUITARRA);
        inst.add(Offer.Instruments.TECLADO);
        inst.add(Offer.Instruments.BAJO);
        inst.add(Offer.Instruments.MICROFONO);
        return inst;
    }


    private ArrayList <Client> generateRandomClients(){
        String [] names = {"Gerardo", "Agustin", "Emmanuel","Fede", "Roberto", "Juan", "Pustilnik","Maxi", "Roberto"
                ,"Maria", "Josefina", "Claudia", "Alberto", "Esteban"};
        String [] IDs = {"1", "2", "3","4", "5", "6", "7","8", "9"
                ,"10", "11", "12", "13", "14"};
        ArrayList <Client> clients = new ArrayList<Client>();

        r = new Random();
        Client client = null;
        String mobile = "";
        int Low = 0;
        int High = names.length-1;
        int randomIndex ;

        for(int i = 0; i< High; i++){
            client = new Client("");
            randomIndex = r.nextInt(High-Low) + Low;
            client.setName(names[randomIndex]);
            client.setID(IDs[randomIndex]);
            mobile = Integer.toString(r.nextInt(1599999999-1500000000)+1500000000);
            client.setMobile(mobile);
            clients.add(client);
        }

        return clients;


    }


}