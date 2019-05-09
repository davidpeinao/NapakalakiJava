
package modeloqytetet;
import java.util.ArrayList;
/**
 *
 * @author acano
 */
public class Jugador implements Comparable {
    private ArrayList<TituloPropiedad> propiedades = new ArrayList <>();
    private Casilla casillaActual;
    private Sorpresa cartaLibertad;
    
    private boolean encarcelado;
    private String nombre;
    private int saldo = 7500;
    
    Jugador (String nombre){
        this.nombre = nombre;
        this.cartaLibertad = null;
        this.casillaActual = null;
        this.encarcelado = false;
    }
   
    protected Jugador (Jugador otroJugador){
        this.nombre = otroJugador.nombre;
        this.cartaLibertad = otroJugador.cartaLibertad;
        this.casillaActual = otroJugador.casillaActual;
        this.encarcelado = otroJugador.encarcelado;
        this.propiedades = otroJugador.propiedades;
        this.saldo = otroJugador.saldo;
    }
    
    boolean cancelarHipoteca (TituloPropiedad titulo){
        titulo.cancelarHipoteca();
        int costeCancelar = titulo.calcularCosteCancelar();
        this.modificarSaldo(-costeCancelar);
        return true;
    }
    
    boolean comprarTituloPropiedad (){
        int costeCompra = getCasillaActual().getCoste();
        if(costeCompra < getSaldo()){
            TituloPropiedad titulo = ((Calle) getCasillaActual()).asignarPropietario(this);
            this.casillaActual.getTitulo().setPropietario(this);
            getPropiedades().add(titulo);
            modificarSaldo(-costeCompra);
            return true;
        }
        return false;
    }
    
    protected Especulador convertirme(int fianza){
        Especulador e = new Especulador(this,fianza);
        return e;
    }
    
    int cuantasCasasHotelesTengo (){
        int temp = 0;
        for (TituloPropiedad propiedad : propiedades){
            temp = propiedad.getNumCasas() + propiedad.getNumHoteles();
        }
        return temp;
    }
    
    protected boolean deboIrACarcel(){
        return !tengoCartaLibertad();
    }
    
    boolean deboPagarAlquiler (){
        TituloPropiedad titulo = ((Calle) casillaActual).getTitulo();
        boolean esDeMiPropiedad = this.esDeMiPropiedad(titulo);
        //esDeMiPropiedad devuelve false si no es de mi propiedad
        if(!esDeMiPropiedad){
            boolean tienePropietario = titulo.tengoPropietario();
            if(tienePropietario){
                boolean encarcelado = titulo.propietarioEncarcelado();
                if(!encarcelado){
                    boolean estaHipotecada = titulo.getHipotecada();
                    if(!estaHipotecada)
                        return true;
                }
            }
        }
        return false;
    }
    
    Sorpresa devolverCartaLibertad (){
        Sorpresa c = cartaLibertad;
        cartaLibertad = null;
        return c;
    }
    
    boolean edificarCasa (TituloPropiedad titulo){
        if(puedoEdificarCasa(titulo)){
            int costeEdificarCasa = titulo.getPrecioEdificar();
            boolean tengoSaldo = tengoSaldo(costeEdificarCasa);
            
            if(tengoSaldo){
                titulo.edificarCasa();
                modificarSaldo(-costeEdificarCasa);
                return true;                
            }           
        }
        return false;
    }
    
    boolean edificarHotel (TituloPropiedad titulo){
        if(puedoEdificarHotel(titulo)){
            int costeEdificarCasa = titulo.getPrecioEdificar();
            boolean tengoSaldo = tengoSaldo(costeEdificarCasa);
                    
            if(tengoSaldo){
                titulo.edificarHotel();
                modificarSaldo(-costeEdificarCasa);
                return true;                
            }           
        }
        return false;
    }
    
    private void eliminarDeMisPropiedades (TituloPropiedad titulo){
        propiedades.remove(titulo);
        titulo.setPropietario(null);
    }
    
    private boolean esDeMiPropiedad (TituloPropiedad titulo){
        return propiedades.contains(titulo);
    }

    Sorpresa getCartaLibertad (){   
        return cartaLibertad;
    }
    
    Casilla getCasillaActual (){
        return casillaActual;
    }
    
    boolean getEncarcelado (){
        return encarcelado;
    }

    public String getNombre (){
        return nombre;
    }
    
    ArrayList<TituloPropiedad> getPropiedades (){
        return propiedades;
    }
    
    public int getSaldo (){
        return saldo;
    }

    boolean hipotecarPropiedad (TituloPropiedad titulo){
        boolean sePuedeHipotecar = true;
        
        int costeHipoteca = titulo.hipotecar();
        this.modificarSaldo(costeHipoteca);

        return sePuedeHipotecar;
    }
    
    void irACarcel (Casilla casilla){
        setCasillaActual(casilla);
        setEncarcelado(true);
    }
    
    int modificarSaldo (int cantidad){
        return saldo += cantidad;
    }
    
    int obtenerCapital (){
        int valorPropiedades = 0;
        
        for (TituloPropiedad propiedad : this.propiedades){
            
            valorPropiedades += propiedad.getPrecioCompra()+ 
            (propiedad.getNumCasas() + propiedad.getNumHoteles()) * propiedad.getPrecioEdificar();

            if (propiedad.getHipotecada())
                valorPropiedades -= propiedad.getHipotecaBase();
        }
        

        return valorPropiedades + this.saldo;
        
    }
 
    ArrayList<TituloPropiedad> obtenerPropiedades (boolean hipotecada){
        ArrayList<TituloPropiedad> propiedades = new ArrayList();
        
        for (TituloPropiedad p : this.propiedades){
            if (p.getHipotecada() == hipotecada)
                propiedades.add(p);
        }
        return propiedades;
    }
    
    void pagarAlquiler (){
        int costeAlquiler = ((Calle) casillaActual).pagarAlquiler();
        modificarSaldo(-costeAlquiler);        
    }
    
    protected void pagarImpuesto (){
        modificarSaldo(-casillaActual.getCoste());
    }
    
    void pagarLibertad (int cantidad){
        boolean tengoSaldo = this.tengoSaldo(cantidad);
        if(tengoSaldo){
            setEncarcelado(false);
            this.modificarSaldo(-cantidad);
        }
    }

    protected boolean puedoEdificarCasa(TituloPropiedad titulo){
        if(titulo.getNumCasas() < 4)
            return true;
        return false;
    }
    
    protected boolean puedoEdificarHotel(TituloPropiedad titulo){
        if((titulo.getNumHoteles() < 4) && (titulo.getNumCasas()  == 4))
            return true;
        return false;
    }
    
    void setCartaLibertad (Sorpresa carta){
        this.cartaLibertad = carta;
    }
    
    void setCasillaActual (Casilla casilla){
        this.casillaActual = casilla;
    }
    
    void setEncarcelado (boolean encarcelado){
        this.encarcelado = encarcelado;
    }

    boolean tengoCartaLibertad (){
        return cartaLibertad != null;
    }
    
    protected boolean tengoSaldo (int cantidad){
        return saldo > cantidad;
    }
    
    boolean venderPropiedad (Casilla casilla){
        TituloPropiedad titulo = casilla.getTitulo();
        this.eliminarDeMisPropiedades(titulo);
        int precioVenta = titulo.calcularPrecioVenta();
        this.modificarSaldo(precioVenta);
        // esto igual, que returnea?
        return true;
    }


    @Override
    public String toString() {
        return "\nJugador | Nombre= " + getNombre() + ", saldo=" + getSaldo() + ", capital=" + obtenerCapital() + ", CartaLibertad =" + getCartaLibertad() + ", Encarcelado=" + getEncarcelado() + ", CasillaActual=" + getCasillaActual() + ",\n\t Propiedades={" + getPropiedades() + "}";
    }

    @Override
    public int compareTo(Object otroJugador) {
        int otroCapital = ((Jugador) otroJugador).obtenerCapital();
        return otroCapital - this.obtenerCapital();
    }
    
}