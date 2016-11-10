package negocio;

import Data.Offer;

import java.util.ArrayList;
import java.util.Collections;

public class Subconjunto
{
	private final ArrayList<Offer> objetos;
	private double peso;
	private double beneficio;

	Subconjunto()
	{
		objetos = new ArrayList<>();
		peso = 0;
		beneficio = 0;
	}

	@SuppressWarnings("unused")
	public double peso()
	{
		return peso;
	}

	private double beneficio()
	{
		return beneficio;
	}

	void agregar(Offer obj)
	{
		objetos.add(obj);
		peso += obj.getDuration();
		beneficio += obj.getPrice();
	}

	void sacar(Offer obj)
	{
		objetos.remove(obj);
		peso -= obj.getDuration();
		beneficio -= obj.getPrice();
	}

	Subconjunto clonar()
	{
		Subconjunto s = new Subconjunto();
		objetos.forEach(s::agregar);

		return s;
	}

	boolean tieneColisiones(){
		for(Offer offer1:  this.getOffers()){
			for(Offer offer2:  this.getOffers()){
				if(offer1!=offer2){
					if(offer1.conflictsWith(offer2)){

					return true;
				}
				}
			}
		}
		return false;
	}

	@Override
	public String toString()
	{

		Collections.sort(objetos,Comparador.porHorario());

		String s = "{";
		for (Offer o: objetos)
			s += o.toString() + "\n";
		
		return s + "}";
	}

	boolean tieneMayorBeneficioQue(Subconjunto s)
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
		
		for(Offer objeto: objetos) if (otro.contiene(objeto))
			return false;
		
		return true;
	}

	public boolean contiene(Offer objeto)
	{
		return !objetos.contains(objeto);
	}

	private int cantidadDeObjetos()
	{
		return objetos.size();
	}

	public ArrayList<Offer> getOffers(){return this.objetos;}
}
