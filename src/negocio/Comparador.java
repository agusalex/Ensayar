package negocio;

import Data.Offer;

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
				if (uno.getDuration() < otro.getDuration())
					return -1;
					
				if (uno.getDuration() > otro.getDuration())
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
				if (uno.getPrice() < otro.getPrice())
					return 1;
					
				if (uno.getPrice() > otro.getPrice())
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
				double cocienteUno = uno.getPrice() / uno.getDuration();
				double cocienteOtro = otro.getPrice() / otro.getDuration();
						
				if (cocienteUno < cocienteOtro)
					return 1;
					
				if (cocienteUno > cocienteOtro)
					return -1;
					
				return 0;
			}
		};
	}
}
