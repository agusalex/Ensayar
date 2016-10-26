package negocio;

import java.util.Comparator;

public class Comparador
{
	public static Comparator<Offer> porPeso()
	{
		return new Comparator<Offer>()
		{
			@Override
			public int compare(Offer uno, Offer otro)
			{
				if (uno.getPeso() < otro.getPeso())
					return -1;
					
				if (uno.getPeso() > otro.getPeso())
					return 1;
					
				return 0;
			}
		};
	}
	
	public static Comparator<Offer> porBeneficio()
	{
		return new Comparator<Offer>()
		{
			public int compare(Offer uno, Offer otro)
			{
				if (uno.getBeneficio() < otro.getBeneficio())
					return 1;
					
				if (uno.getBeneficio() > otro.getBeneficio())
					return -1;
					
				return 0;
			}
		};
	}
	
	public static Comparator<Offer> porCociente()
	{
		return new Comparator<Offer>()
		{
			public int compare(Offer uno, Offer otro)
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
