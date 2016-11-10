package negocio;

public interface Solver {
    Subconjunto resolver(Instancia inst);


    @SuppressWarnings("unused")
    double getTiempo();
}
