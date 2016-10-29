package negocio;

import Data.Offer;

public class SolverExacto implements Solver
{
	private Instancia instancia;
	private Subconjunto subconjunto;
	private Subconjunto mejorEncontrado; // incumbent
	
	// Estadisticas
	private int hojas;
	private double tiempo; // sg

	@Override
	public Subconjunto resolver(Instancia inst)
	{
		instancia = inst;
		subconjunto = new Subconjunto();
		mejorEncontrado = null;
		hojas = 0;

		long inicio = System.currentTimeMillis();
		generarDesde(0);
		long fin = System.currentTimeMillis();
		
		tiempo = (fin - inicio) / 1000.0;
		return mejorEncontrado;
	}

	// Genera y eval�a todos los subconjuntos a partir del k-�simo offerAt
	private void generarDesde(int k)
	{
		// Caso base: Recorrimos todos los objetos (estamos en una hoja)
		if (k == instancia.offersSize()) {
			analizarActual();
			return;
		}

		// Caso recursivo
		Offer oferta2 = instancia.offerAt(k);
		for(Offer oferta1 : subconjunto.getOffers()){
			if(oferta1.conflictsWith(oferta2))
				return;
		}

		subconjunto.agregar(oferta2);
		generarDesde(k+1);

		subconjunto.sacar(oferta2);
		generarDesde(k+1);
	}

	private void analizarActual()
	{
		++hojas;

		if (subconjunto.tieneMayorBeneficioQue(mejorEncontrado))
			mejorEncontrado = subconjunto.clonar();
	}


	public int getHojas()
	{
		return hojas;
	}
	
	public double getTiempo()
	{
		return tiempo;
	}
}
