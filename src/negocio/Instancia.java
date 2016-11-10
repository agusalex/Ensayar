package negocio;

import Data.Offer;

import java.util.ArrayList;

public class Instancia {

    private final ArrayList<Offer> offers;

    public Instancia() {

        offers = new ArrayList<>();
    }


    public void agregarObjeto(Offer obj) {
        offers.add(obj);

    }


    Offer offerAt(int i) {
        return offers.get(i);
    }

    int offersSize() {
        return offers.size();
    }


    ArrayList<Offer> getOffers() {
        return this.offers;
    }
}
