package negocio;

import java.util.ArrayList;
import java.util.Collections;

public class Instancia {

	private final ArrayList<Offer> objetos;

	public Instancia() {

		objetos = new ArrayList<>();
	}


	public void agregarObjeto(Offer obj)
	{
		objetos.add(obj);
		
		Collections.sort(objetos, Comparador.porPeso());
		Collections.reverse(objetos);
	}

	public Offer objeto(int i)
	{
		return objetos.get(i);
	}

	public int cantidadDeObjetos()
	{
		return objetos.size();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Offer> getObjetos()
	{
		return (ArrayList<Offer>) objetos.clone();
	}
}
