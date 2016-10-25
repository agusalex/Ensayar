package negocio;

import Data.Offer;

import java.util.Random;

public class Main
{
	public static void main(String[] args)
	{
			Instancia instancia = aleatoria(10);
			SolverExacto solver = new SolverExacto();
			solver.resolver(instancia);
			System.out.print("n = " + 1);
			System.out.print(" - Hojas: " + solver.getHojas());
			System.out.println(" -Tiempo: " + solver.getTiempo());

	}

	private static Instancia aleatoria(int n)
	{
		Instancia instancia = new Instancia(2*n);
		Random random = new Random();

			for(int i=0; i<n; ++i) {
				Offer off=new Offer();
				off.random(random);
				System.out.println("Ofeer: "+i);
				System.out.println("Start Hour");
				System.out.println(off.getSchedule().getStart());
				System.out.println("Finishing  Hour");
				System.out.println(off.getSchedule().getEnd());
				System.out.println("");
				Objeto aleatorio = new Objeto( "Ofeer: "+i, off.getDuration(), off.getPrice());
				instancia.agregarObjeto(aleatorio);
	}

		return instancia;
	}
}
