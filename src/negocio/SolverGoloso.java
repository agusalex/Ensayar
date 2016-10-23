package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SolverGoloso implements Solver
{
	private Comparator<Objeto> comparador;

	// El comparador es para ordenar los objetos en el algoritmo goloso
	public SolverGoloso(Comparator<Objeto> comp)
	{
		comparador = comp;
	}
	
	@Override
	public Subconjunto resolver(Instancia inst)
	{
		Subconjunto ret = new Subconjunto();
		for(Objeto obj: objetosOrdenados(inst))
		{
			if( ret.peso() + obj.getPeso() <= inst.capacidad() )
				ret.agregar(obj);
		}
		
		return ret;
	}

	ArrayList<Objeto> objetosOrdenados(Instancia inst)
	{
		ArrayList<Objeto> objetos = inst.getObjetos();
		Collections.sort(objetos, comparador);
		
		return objetos;
	}
}
