package logica;

public class Aristas {
    private String inicio;
    private String fin;
    private int peso;

    public Aristas(String inicio, String fin, int peso) {
        this.inicio = inicio;
        this.fin = fin;
        this.peso = peso;
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }

    public int getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return inicio + " -> " + fin + " (" + peso + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Aristas arista = (Aristas) obj;
        return inicio.equals(arista.inicio) && fin.equals(arista.fin);
    }

    @Override
    public int hashCode() {
        return inicio.hashCode() + fin.hashCode();
    }
}