package negocio;

import java.util.Comparator;

public class Comparador
{
	public static Comparator<Objeto> porPeso()
	{
		return new Comparator<Objeto>()
		{
			@Override
			public int compare(Objeto uno, Objeto otro)
			{
				if (uno.getPeso() < otro.getPeso())
					return -1;
					
				if (uno.getPeso() > otro.getPeso())
					return 1;
					
				return 0;
			}
		};
	}
	
	public static Comparator<Objeto> porBeneficio()
	{
		return new Comparator<Objeto>()
		{
			public int compare(Objeto uno, Objeto otro)
			{
				if (uno.getBeneficio() < otro.getBeneficio())
					return 1;
					
				if (uno.getBeneficio() > otro.getBeneficio())
					return -1;
					
				return 0;
			}
		};
	}
	
	public static Comparator<Objeto> porCociente()
	{
		return new Comparator<Objeto>()
		{
			public int compare(Objeto uno, Objeto otro)
			{
				double cocienteUno = uno.getBeneficio() / uno.getPeso();
				double cocienteOtro = otro.getBeneficio() / otro.getPeso();
						
				if (cocienteUno < cocienteOtro)
					return 1;
					
				if (cocienteUno > cocienteOtro)
					return -1;
					
				return 0;
			}
		};
	}
}
