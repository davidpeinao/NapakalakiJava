package modeloqytetet;


public class Especulador extends Jugador{
    
    protected int factorEspeculador = 2;
    private int fianza;
    
    public Especulador(Jugador j, int fianza) {
        super(j);
        this.fianza = fianza;      
    }
    
    @Override
    protected void pagarImpuesto(){
        modificarSaldo(-(getCasillaActual().getCoste())/2);
    }
    
    @Override
    protected boolean deboIrACarcel(){
        return super.deboIrACarcel() && !pagarFianza();
    }
    
    @Override
    protected Especulador convertirme(int f){
        return this;
    }
    
    private boolean pagarFianza(){
        if (getSaldo() > fianza){
            modificarSaldo(-fianza);
            return true;
        }

        return false;
    }
    
    @Override
    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        if(titulo.getNumCasas() < 8)
            return true;
        
        return false;
    }
    
    @Override
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        if((titulo.getNumHoteles() < 8) && (titulo.getNumCasas() >= 4))
            return true;
        
        return false;
    }
    
    @Override
    public String toString() {
        return super.toString() + " Soy especulador";
    }
}