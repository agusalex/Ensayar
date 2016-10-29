package negocio;

import Data.Offer;

import java.util.ArrayList;
import java.util.Collections;

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
		Collections.sort(offers,Comparador.porBeneficio());
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
		return this.getOffers().size() != 0;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Offer> getOffers()
	{
		return (ArrayList<Offer>) offers.clone();
	}
}
