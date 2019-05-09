package modeloqytetet;

public class Calle extends Casilla{
    
    private TituloPropiedad titulo;
    
    public Calle(int numeroCasilla, TituloPropiedad title) {
        super(numeroCasilla, title.getPrecioCompra());
        setTitulo(title);
    }
    
    public TituloPropiedad asignarPropietario(Jugador jugador){
        this.titulo.setPropietario(jugador);
        return this.titulo;
    }
     
    @Override
    protected TituloPropiedad getTitulo() {
        return titulo;
    }
    
    @Override
    protected TipoCasilla getTipo(){
        return TipoCasilla.CALLE;
    }
    
    
    
    int pagarAlquiler(){
        return titulo.pagarAlquiler();
    }
    
    private void setTitulo (TituloPropiedad titulo){
        this.titulo = titulo;
    }

    @Override
    protected boolean soyEdificable (){
        return true;
    }
    
    public boolean tengoPropietario (){
        return titulo.tengoPropietario();
    }

    @Override
    public String toString() {
        if (tengoPropietario()){
            return super.toString() + " - nombre del propietario: " + getTitulo().getPropietario().getNombre() + "\n -> " + getTitulo().toString();
        } else {
            return super.toString() + "\n -> " + getTitulo().toString();
        }
            
    }
    
    
}
