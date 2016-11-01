package negocio;

import Data.Offer;

import java.util.Collections;
import java.util.Comparator;

public class SolverGoloso implements Solver
{
	private Comparator<Offer> comparador;
	private Criterios criterio;
	private double tiempo;

	// El comparador es para ordenar los objetos en el algoritmo goloso

	public SolverGoloso(Criterios criterio){setCriterio(criterio);}

	public enum Criterios{PRECIO, HORARIO, COCIENTE, COMBO;}

	@Override
	public Subconjunto resolver(Instancia inst) {
		if(criterio == Criterios.PRECIO)
			comparador = Comparador.porBeneficio();
		else if(criterio == Criterios.HORARIO)
			comparador = Comparador.porPeso();
		else if(criterio == Criterios.COCIENTE)
			comparador = Comparador.porCociente();

		//el comparador esta seteado para ordenar  de mayor a menor asi se tiene a los mejores al principio
		Collections.sort(inst.getOffers(),comparador);

		long inicio = System.currentTimeMillis();
		Subconjunto ret = Solve(inst);
		long fin = System.currentTimeMillis();

		tiempo = (fin - inicio) / 1000.0;


		return ret;
	}

	@Override
	public double getTiempo() {
		return tiempo;
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


	private Subconjunto Solve(Instancia instance){
		Subconjunto solucion = new Subconjunto();
		//solucion
		boolean conflicto;
		for(Offer oferta :instance.getOffers()) {
			conflicto=false;
			for(Offer  offer2 : solucion.getOffers()){
				if(oferta.conflictsWith(offer2)) {
					conflicto=true;
				}
			}
			if(!conflicto)
				solucion.agregar(oferta);
		}
		return solucion;
	}


}
