package negocio;

import Data.Offer;

import java.util.ArrayList;
import java.util.Collections;

public class Instancia {

	private final ArrayList<Offer> offers;

	public Instancia() {

		offers = new ArrayList<>();
	}


	public void agregarObjeto(Offer obj)
	{
		offers.add(obj);
		
		Collections.sort(offers, Comparador.porPeso());
		Collections.reverse(offers);
	}

	public Offer offerAt(int i)
	{
		return offers.get(i);
	}

	public int offersSize()
	{
		return offers.size();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Offer> getOffers()
	{
		return (ArrayList<Offer>) offers.clone();
	}
}
