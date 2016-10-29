package negocio;

import Data.Offer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SolverGoloso implements Solver
{
	private Comparator<Offer> comparador;
	private Criterios criterio;

	// El comparador es para ordenar los objetos en el algoritmo goloso

	public SolverGoloso(Criterios criterio){
		setCriterio(criterio);
	}
	public enum Criterios{
		PRECIO, HORARIO, COCIENTE, COMBO;
	}

	@Override
	public Subconjunto resolver(Instancia inst) {
		if(criterio == Criterios.PRECIO)
			comparador = Comparador.porBeneficio();
		else if(criterio == Criterios.HORARIO)
			comparador = Comparador.porPeso();
		else if(criterio == Criterios.COCIENTE)
			comparador = Comparador.porCociente();

		Collections.sort(inst.getInstanceOffers(),comparador);

		Subconjunto ret = Solve(inst);

		return ret;
	}



	public void setCriterio(Criterios criterio){
		switch (criterio){
			case PRECIO: this.criterio = Criterios.PRECIO;break;
			case HORARIO: this.criterio = Criterios.HORARIO;break;
			case COCIENTE: this.criterio = Criterios.COCIENTE;break;
			case COMBO: this.criterio = Criterios.COMBO;break;
			default: return;
		}
	}


	private Subconjunto Solve(Instancia inst){
		Subconjunto ret = new Subconjunto();
		int horas = 0, index = 0;
		int size = inst.getInstanceOffers().size();

		//agrega un primer elemento al subconjunto solucion
		Offer offer =  inst.offerAt(index);
		ret.agregar(offer);
		horas += offer.getDuration();
		index++;


		while(horas <= 24 && inst.hasAviableOffers() && !areAllColisions(ret.getOffers(), inst.getInstanceOffers()) && index < size){
			offer =  inst.offerAt(index);
			if(!collition(offer,ret.getOffers())) {
				ret.agregar(offer);
				horas += offer.getDuration();
				if (horas > 24) //la oferta agregada supera el limite la elimina y se termina
					ret.sacar(offer);
			}
			index++;
		}
		return ret;
	}

	//funcion para verificar si en un punto todas las ofertas de un subconjunto colisionan con las de instancia,
	//esto genera que no se puedan agregar mas ofertas
	private static boolean areAllColisions(ArrayList<Offer> offers, ArrayList<Offer> ret){
		boolean retu = true;
		for(Offer of : offers){
			for(Offer of1 : ret) {
				retu = retu && of.conflictsWith(of1);
			}
		}
		return retu;
	}
	//verifica si una oferta choca con las del conjunto a devolver
	private static  boolean collition(Offer of, ArrayList<Offer> offers){
		for(Offer of1 : offers){
			if(of.conflictsWith(of1))
				return true;
		}
		return false;
	}
}
