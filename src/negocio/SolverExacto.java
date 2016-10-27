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
		Offer obj = instancia.offerAt(k);
		for(Offer of : subconjunto.getOffers()){
			System.out.println(of.conflictsWith(obj));
			if(of.conflictsWith(obj))
				return;
		}

		subconjunto.agregar(obj);
		generarDesde(k+1);

		subconjunto.sacar(obj);
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
