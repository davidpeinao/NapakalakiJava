package modeloqytetet;

public class OtraCasilla extends Casilla{
    
    private TipoCasilla tipo;
    
    public OtraCasilla(int numeroCasilla, int coste, TipoCasilla tipo) {
        super(numeroCasilla, coste);
        this.tipo = tipo;
    }
    
    @Override
    protected boolean soyEdificable(){
        return false;
    }
    
    protected TipoCasilla getTipo(){
        return tipo;
    }
    
    @Override
    protected TituloPropiedad getTitulo(){
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + getTipo().toString();
    }
    
    
}
