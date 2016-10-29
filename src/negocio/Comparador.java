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

				if(uno.getDurationInMin() == 0 && otro.getDurationInMin() == 0 ||
						uno.getDurationInMin() == 30 && otro.getDurationInMin() == 30) {

					if (uno.getDuration() < otro.getDuration())
						return 1;

					if (uno.getDuration() > otro.getDuration())
						return -1;

				}
				if(uno.getDurationInMin() == 30 && otro.getDurationInMin() == 0){
					if(uno.getDuration() >= otro.getDurationInMin())
						return -1;
					if(uno.getDuration() < otro.getDuration())
						return 1;
				}
				if(otro.getDurationInMin() == 30 && uno.getDurationInMin() == 0){
					if(otro.getDuration() >= uno.getDurationInMin())
						return -1;
					if(otro.getDuration() < uno.getDuration())
						return 1;
				}

				return 0;
			}
		};
	}
	
	public static Comparator<Offer> porBeneficio()
	{
		return new Comparator<Offer>()
		{
			@Override
			public int compare(Offer uno, Offer otro)
			{
				if (uno.getPrice() > otro.getPrice())
					return -1;
					
				if (uno.getPrice() < otro.getPrice())
					return 1;
					
				return 0;
			}
		};
	}
	
	
	//bases de datos especificacaion de softwarer/ingenieria-----> Laboratorio y especiicacion
	
	public static Comparator<Offer> porCociente()
	{
		return new Comparator<Offer>()
		{

			public int compare(Offer uno, Offer otro)
			{
				if(uno.getDuration() == 0 || otro.getDuration() == 0){
					double cocienteUno = uno.getPrice();
					double cocienteOtro = otro.getPrice();
					if (cocienteUno < cocienteOtro)
						return 1;

					if (cocienteUno > cocienteOtro)
						return -1;
				}

				double cocienteUno = uno.getPrice() / uno.getDuration();
				double cocienteOtro = otro.getPrice() / otro.getDuration();
						
				if (cocienteUno < cocienteOtro)
					return -1;
					
				if (cocienteUno > cocienteOtro)
					return 1;
					
				return 0;
			}
		};
	}
}
