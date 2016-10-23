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
		Random random = new Random(0); // Semilla fija!

			for(int i=0; i<n; ++i)
			{
				/*int peso = random.nextInt(10) + 1;
				int benef  = random.nextInt(10) + 1;*/


				Offer off=new Offer();
				off.random(random);
				Objeto aleatorio = new Objeto(off.hashCode() +" "+ i, off.getDuration(), off.getPrice());
				System.out.println(aleatorio);
		instancia.agregarObjeto(aleatorio);
	}

		return instancia;
	}
}
