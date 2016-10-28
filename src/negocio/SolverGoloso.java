package negocio;

import Data.Offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SolverGoloso implements Solver
{
	private Comparator<Offer> comparador;

	// El comparador es para ordenar los objetos en el algoritmo goloso

	@Override
	public Subconjunto resolver(Instancia inst)
	{
		Subconjunto ret = new Subconjunto();
		for(Offer obj: objetosOrdenados(inst,Comparador.porCociente()))
		{
			ret.agregar(obj);
		}
		
		return ret;
	}

	ArrayList<Offer> objetosOrdenados(Instancia inst,Comparator<Offer> comp)
	{
		ArrayList<Offer> objetos = inst.getOffers();
		Collections.sort(objetos, comp);
		
		return objetos;
	}
	

}
