package Data;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;


@SuppressWarnings("deprecation")
public class OfferTest {
    private Random r;

    private Offer instanceOffer() {
        Schedule schedule = new Schedule(4, 30, 8, 0);
        ArrayList<Offer.Instruments> chosen = new ArrayList<>();
        chosen.add(Offer.Instruments.GUITARRA);
        chosen.add(Offer.Instruments.BAJO);
        chosen.add(Offer.Instruments.BATERIA);
        Client cl = new Client("Joseph");
        Offer offer=new Offer(chosen, schedule, cl);
        offer.setAvailableTomorrow();
        return offer;

    }

    @Test
    public void getPrice() throws Exception {
        Offer offer = instanceOffer();
        assertEquals(275, offer.getPrice());
    }


    @Test
    public void getClient() throws Exception {
        Offer offer = instanceOffer();
        assertEquals("Joseph", offer.getClient().getName());
    }

    @Test
    public void getDateAvailable() throws Exception {
        Offer offer = instanceOffer();
        LocalDate c = offer.getDateAvailable();
        LocalDate a=LocalDate.now();
        a=a.plusDays(1);
        assertEquals(a.getDayOfMonth(), c.getDayOfMonth());
        assertEquals(a.getMonth(),c.getMonth());
    }


    @Test
    public void conflictsWith() throws Exception {
        Offer offer = instanceOffer();
        Offer offer2 = instanceOffer();
        assertTrue(offer.conflictsWith(offer2));

        ArrayList<Offer.Instruments> defaultInst = defaultInstruments();
        ArrayList<Offer.Instruments> offerInstruments = chosenRandomInstruments(2, defaultInst);
        Schedule sc = new Schedule(8, 0, 9, 30);
        Offer offer3 = new Offer(offerInstruments, sc, new Client("Juan"));
        assertFalse(offer2.conflictsWith(offer3));
    }


    @Test
    public void getDuration() throws Exception {
        Offer offer = instanceOffer();
        assertEquals(3, offer.getDuration());

    }

    @Test
    public void getDurationInMin() throws Exception {
        Offer offer = instanceOffer();
        assertEquals(30, offer.getDurationInMin());
    }

    @Test
    public void getInstruments() throws Exception {
        Offer offer = instanceOffer();
        ArrayList<Offer.Instruments> chosen = new ArrayList<>();
        chosen.add(Offer.Instruments.GUITARRA);
        chosen.add(Offer.Instruments.BAJO);
        chosen.add(Offer.Instruments.BATERIA);
        assertEquals(chosen, offer.getInstruments());

        ArrayList<Offer.Instruments> chosen2 = new ArrayList<>();
        chosen2.add(Offer.Instruments.BAJO);
        chosen2.add(Offer.Instruments.MICROFONO);
        chosen2.add(Offer.Instruments.TECLADO);
        assertFalse(chosen2.equals(offer.getInstruments()));

    }

    @Test
    public void getSchedule() throws Exception {
        Offer offer = instanceOffer();
        Schedule schedule = new Schedule(4, 30, 8, 0);
        assertEquals(schedule, offer.getSchedule());
    }

    @Test
    public void equals() throws Exception {
        Offer offer = instanceOffer();
        Offer offer2 = instanceOffer();
        assertEquals(offer,offer2);
    }


    @Test
    public void showOffers() {
        ArrayList<Offer> offers = generateRandomOffers();
        offers.forEach(System.out::println);
    }

    private ArrayList<Offer> generateRandomOffers() {
        r = new Random();
        Offer offer;
        Client client;
        ArrayList<Offer> ret = new ArrayList<>();
        Schedule schedule;
        ArrayList<Client> clients = generateRandomClients();
        ArrayList<Offer.Instruments> defaultInst = defaultInstruments();
        ArrayList<Offer.Instruments> offerInstruments;
        int Low = 1;
        int High = 5;
        int randomClient;
        int randomHour, randomMin;
        int cantInst;
        int[] min = {0, 30};

        for (int i = 0; i < 5; i++) {
            cantInst = r.nextInt(defaultInst.size() - 1) + 1;
            offerInstruments = chosenRandomInstruments(cantInst, defaultInst);

            randomHour = r.nextInt(High - Low) + Low;
            randomMin = min[r.nextInt(2)];
            schedule = new Schedule(randomHour, randomMin, randomHour + High, min[r.nextInt(2)]);

            randomClient = r.nextInt(clients.size() - 1);
            client = clients.get(randomClient);

            offer = new Offer(offerInstruments, schedule, client);
            ret.add(offer);
        }

        return ret;
    }

    private ArrayList<Offer.Instruments> chosenRandomInstruments(int n, ArrayList<Offer.Instruments> defaultInst) {
        ArrayList<Offer.Instruments> ret = new ArrayList<>();
        Random rand = new Random();
        int randomIndex;
        for (int i = 0; i < n; i++) {
            randomIndex = rand.nextInt(n);
            ret.add(defaultInst.get(randomIndex));
        }
        return ret;
    }

    private ArrayList<Offer.Instruments> defaultInstruments() {
        ArrayList<Offer.Instruments> inst = new ArrayList<>();
        inst.add(Offer.Instruments.BATERIA);
        inst.add(Offer.Instruments.GUITARRA);
        inst.add(Offer.Instruments.TECLADO);
        inst.add(Offer.Instruments.BAJO);
        inst.add(Offer.Instruments.MICROFONO);
        return inst;
    }

    private ArrayList<Client> generateRandomClients() {
        String[] names = {"Gerardo", "Agustin", "Emmanuel", "Fede", "Roberto", "Juan", "Pustilnik", "Maxi", "Roberto"
                , "Maria", "Josefina", "Claudia", "Alberto", "Esteban"};
        String[] IDs = {"1", "2", "3", "4", "5", "6", "7", "8", "9"
                , "10", "11", "12", "13", "14"};
        ArrayList<Client> clients = new ArrayList<>();

        r = new Random();
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