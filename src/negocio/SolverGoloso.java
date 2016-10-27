package negocio;

import Data.Offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SolverGoloso implements Solver
{
	private Comparator<Offer> comparador;

	// El comparador es para ordenar los objetos en el algoritmo goloso
	public SolverGoloso(Comparator<Offer> comp)
	{
		comparador = comp;
	}
	
	@Override
	public Subconjunto resolver(Instancia inst)
	{
		Subconjunto ret = new Subconjunto();
		for(Offer obj: objetosOrdenados(inst))
		{
			ret.agregar(obj);
		}
		
		return ret;
	}

	ArrayList<Offer> objetosOrdenados(Instancia inst)
	{
		ArrayList<Offer> objetos = inst.getOffers();
		Collections.sort(objetos, comparador);
		
		return objetos;
	}
}
