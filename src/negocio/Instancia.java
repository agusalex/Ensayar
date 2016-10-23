package negocio;

import java.util.ArrayList;
import java.util.Collections;

public class Instancia
{
	private final double capacidad;
	private final ArrayList<Objeto> objetos;

	public Instancia(double capacidadMax)
	{
		capacidad = capacidadMax;
		objetos = new ArrayList<>();
	}

	public double capacidad()
	{
		return capacidad;
	}

	public void agregarObjeto(Objeto obj)
	{
		objetos.add(obj);
		
		Collections.sort(objetos, Comparador.porPeso());
		Collections.reverse(objetos);
	}

	public Objeto objeto(int i)
	{
		return objetos.get(i);
	}

	public int cantidadDeObjetos()
	{
		return objetos.size();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Objeto> getObjetos()
	{
		return (ArrayList<Objeto>) objetos.clone();
	}
}
