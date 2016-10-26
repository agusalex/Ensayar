package negocio;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;

public class SolverGolosoTest
{
	@Test
	public void objetosOrdenadosTest()
	{
		Instancia instancia = peque(25);
		
		SolverGoloso solver = new SolverGoloso(Comparador.porPeso());
		ArrayList<Offer> objetos = solver.objetosOrdenados(instancia);
		
		assertEquals(3, objetos.size());
		assertEquals("B", objetos.get(0).getNombre());
		assertEquals("C", objetos.get(1).getNombre());
		assertEquals("A", objetos.get(2).getNombre());		
	}

	@Test
	public void golosoPorPesoTest()
	{
		testear(peque(25), Comparador.porPeso(), new int[] {1, 2});
	}

	@Test
	public void solucionVaciaTest()
	{
		testear(peque(2), Comparador.porPeso(), new int[] {});
	}

	@Test
	public void solucionCompletaTest()
	{
		testear(peque(31), Comparador.porPeso(), new int[] {1, 2, 0});
	}

	@Test
	public void golosoPorBeneficioTest()
	{
		testear(peque(25), Comparador.porBeneficio(), new int[] {0, 2});
	}

	@Test
	public void golosoPorCocienteTest()
	{
		testear(peque(25), Comparador.porCociente(), new int[] {1, 2});
	}
	
	private void testear(Instancia instancia, Comparator<Offer> comparador, int[] esperados)
	{
		SolverGoloso solver = new SolverGoloso(comparador);
		Subconjunto solucion = solver.resolver(instancia);
		
		Subconjunto esperado = new Subconjunto();
		for(Integer i: esperados)
			esperado.agregar(instancia.objeto(i));

		assertEquals(esperado, solucion);
	}

	private Instancia peque(double capacidad)
	{
		Instancia instancia = new Instancia();
		instancia.agregarObjeto(new Offer("A", 20, 10));
		instancia.agregarObjeto(new Offer("B",  5,  5));
		instancia.agregarObjeto(new Offer("C",  6,  7));
		return instancia;
	}
}
