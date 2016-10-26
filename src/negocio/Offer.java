package negocio;

public class Offer
{
	private final String nombre;
	private final double peso;
	private final double beneficio;

	public Offer(String n, double p, double b)
	{
		nombre = n;
		peso = p;
		beneficio = b;
	}

	public String getNombre()
	{
		return nombre;
	}

	public double getPeso()
	{
		return peso;
	}

	public double getBeneficio()
	{
		return beneficio;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;
		
		Offer otro = (Offer) obj;
		return this.getNombre() == otro.getNombre() &&
			   this.getBeneficio() == otro.getBeneficio() &&
			   this.getPeso() == otro.getPeso();
	}

	public String toString(){
		return "Nombre: "+ this.getNombre()+" Peso= "+this.getPeso()+ " Beneficio=  "+ this.getBeneficio();
	}

}
