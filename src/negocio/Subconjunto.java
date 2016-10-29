package negocio;

import Data.Offer;

import java.util.ArrayList;

public class Subconjunto
{
	private final ArrayList<Offer> objetos;
	private double peso;
	private double beneficio;

	public Subconjunto()
	{
		objetos = new ArrayList<Offer>();
		peso = 0;
		beneficio = 0;
	}

	public double peso()
	{
		return peso;
	}

	public double beneficio()
	{
		return beneficio;
	}

	public void agregar(Offer obj)
	{
		objetos.add(obj);
		peso += obj.getDuration();
		beneficio += obj.getPrice();
	}

	public void sacar(Offer obj)
	{
		objetos.remove(obj);
		peso -= obj.getDuration();
		beneficio -= obj.getPrice();
	}

	public Subconjunto clonar()
	{
		Subconjunto s = new Subconjunto();
		for (Offer o: objetos)
			s.agregar(o);

		return s;
	}

	@Override
	public String toString()
	{
		String s = "{";
		for (Offer o: objetos)
			s += o.toString() + "\n";
		
		return s + "}";
	}

	public boolean tieneMayorBeneficioQue(Subconjunto s)
	{
		return s == null || this.beneficio() >= s.beneficio();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		Subconjunto otro = (Subconjunto) obj;
		if (objetos.size() != otro.cantidadDeObjetos())
			return false;
		
		for(Offer objeto: objetos) if (otro.contiene(objeto) == false)
			return false;
		
		return true;
	}

	public boolean contiene(Offer objeto)
	{
		return objetos.contains(objeto);
	}

	public int cantidadDeObjetos()
	{
		return objetos.size();
	}

	public ArrayList<Offer> getOffers(){return this.objetos;}
}
