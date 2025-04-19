package logica;


public class Aristas {
	private Estacion inicio;
    private Estacion fin;
    private int peso;

    public Aristas(Estacion inicio, Estacion fin, int peso) {
        this.inicio = inicio;
        this.fin = fin;
        this.peso = peso;
    }

    public Estacion getInicio() {
        return inicio;
    }

    public Estacion getFin() {
        return fin;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return inicio.getNombre() + " -> " + fin.getNombre() + " (" + peso + ")";
    }
}
