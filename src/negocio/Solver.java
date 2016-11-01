package negocio;

public interface Solver
{
	public Subconjunto resolver(Instancia inst);


	double getTiempo();
}
