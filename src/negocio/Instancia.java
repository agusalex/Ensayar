package negocio;

import Data.Offer;

import java.util.ArrayList;

public class Instancia {

	private final ArrayList<Offer> offers;



	public enum Sorting{
		DURACION,PRECIO,COCIENTE;
	}

	public Instancia(){

		offers = new ArrayList<>();
	}


	public void agregarObjeto(Offer obj){
		offers.add(obj);
	}


	public Offer offerAt(int i)
	{
		return offers.get(i);
	}

	public int offersSize()
	{
		return offers.size();
	}

	public boolean hasAviableOffers(){
		return this.getInstanceOffers().size() != 0;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Offer> cloneOffers()
	{
		return (ArrayList<Offer>) offers.clone();
	}

	public ArrayList<Offer> getInstanceOffers(){
		return this.offers;
	}
}
