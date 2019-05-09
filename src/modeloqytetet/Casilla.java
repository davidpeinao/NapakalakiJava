package modeloqytetet;

public abstract class Casilla {
    
    
    protected int numeroCasilla;
    protected int coste;
        
    public Casilla(int numero, int cost) {
        numeroCasilla = numero;
        coste = cost;
    }

    int getCoste() {
        return coste;
    }
    
    int getNumeroCasilla() {
        return numeroCasilla;
    }
    
    protected abstract TipoCasilla getTipo();
        
    protected abstract TituloPropiedad getTitulo();
    
    public void setCoste(int coste){
        this.coste = coste;
    }
    
    protected abstract boolean soyEdificable();
   

    @Override
    public String toString() {
        return "Casilla -> " + numeroCasilla + ", coste=" + coste + ", ";
    }
}
