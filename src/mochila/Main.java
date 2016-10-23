package mochila;

import java.util.Random;

public class Main
{
	public static void main(String[] args)
	{
		for(int n=10; n<=40; ++n)
		{
			Instancia instancia = aleatoria(n);
			SolverExacto solver = new SolverExacto();
			solver.resolver(instancia);

			System.out.print("n = " + n);
			System.out.print(" - Hojas: " + solver.getHojas());
			System.out.println(" -Tiempo: " + solver.getTiempo());
		}
	}

	private static Instancia aleatoria(int n)
	{
Instancia instancia = new Instancia(2*n);
		Random random = new Random(0); // Semilla fija!

		for(int i=0; i<n; ++i)
		{
			int peso = random.nextInt(10) + 1;
			int benef  = random.nextInt(10) + 1;
			
			Objeto aleatorio = new Objeto("Obj " + i, peso, benef);
			instancia.agregarObjeto(aleatorio);
		}
		
		return instancia;
	}
}
