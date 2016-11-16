package Data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static Data.Offer.generateRandomOffers;


public class DataBaseTest {
    private Random r;

    @Test
    public void save() throws Exception {
        DataBase db = DataBase.getDb();
        ArrayList<Offer> offers = Offer.generateRandomOffers();
        db.setOffers(offers);
        db.save();
    }

    @Test
    public void load() throws Exception {
        DataBase db = DataBase.getDb();
        db.load();
        System.out.println(DataBase.getOffers());
    }






}