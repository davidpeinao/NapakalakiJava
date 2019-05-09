package modeloqytetet;

public class TituloPropiedad {
    private int alquilerBase;
    private float factorRevalorizacion;
    private int hipotecaBase;   
    private boolean hipotecada;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private int precioCompra;
    private int precioEdificar;
    
    private Jugador propietario = null;
    
    
    
    public TituloPropiedad (String nombre, int precioCompra, int alquilerBase,
            float factorRevalorizacion, int hipotecaBase, int precioEdificar) {
        this.nombre = nombre;
        this.hipotecada = false;
        this.precioCompra = precioCompra;
        this.alquilerBase = alquilerBase;
        this.factorRevalorizacion = factorRevalorizacion;
        this.hipotecaBase = hipotecaBase;
        this.precioEdificar = precioEdificar;
        this.numHoteles = 0;
        this.numCasas = 0;
    }
    
    int calcularCosteCancelar(){
        int coste = (int) (calcularCosteHipotecar() * 1.1);
        return coste;
    }
    
    int calcularCosteHipotecar(){
        int coste =  (int) (hipotecaBase + numCasas * 0.5 * hipotecaBase + numHoteles * hipotecaBase);
        return coste;
    }
    
    int calcularImporteAlquiler(){
        int coste =(alquilerBase + 1 + (int)(numCasas * 0.5 + numHoteles * 2));
        return coste;
    }
    
    int calcularPrecioVenta(){
        int coste = (int) (precioCompra + (numCasas + numHoteles) * precioEdificar * factorRevalorizacion);
        return coste;
    }
    
    void cancelarHipoteca(){
        this.hipotecada = false;
    }
    
    void edificarCasa(){
        numCasas += 1;
    }
    
    void edificarHotel(){
        numHoteles += 1;
        numCasas -= 4;
    }
  
    String getNombre (){
        return nombre;
    }
    
    boolean getHipotecada (){
        return hipotecada;
    }
    
    int getPrecioCompra (){
        return precioCompra;
    }
    
    int getAlquilerBase (){
        return alquilerBase;
    }
    
    float getFactorRevalorizacion (){
        return factorRevalorizacion;
    }
    
    int getHipotecaBase (){
        return hipotecaBase;
    }
    
    int getPrecioEdificar (){
        return precioEdificar;
    }
    
    int getNumHoteles (){
        return numHoteles;
    }
    
    int getNumCasas (){
        return numCasas;
    }
    
    Jugador getPropietario (){
        return propietario;
    }
  
    int hipotecar(){
        this.hipotecada = true;
        System.out.println("Se ha hipotecado la propiedad");
        return this.calcularCosteHipotecar();
    }
    
    int pagarAlquiler(){
        int costeAlquiler = this.calcularImporteAlquiler();
        this.getPropietario().modificarSaldo(costeAlquiler);
        return costeAlquiler;
    }
    
    boolean propietarioEncarcelado(){
        return propietario.getEncarcelado();
    }

    void setHipotecada (boolean val){
        hipotecada = val;
    }
    
    void setPropietario (Jugador jug){
        propietario = jug;
    }
    
    boolean tengoPropietario(){
        return (propietario != null);
    }

    @Override
    public String toString (){
        return "TituloPropiedad:" + " Nombre: " + nombre + "," + 
                " hipotecada: " + hipotecada + 
                ", precio: " + precioCompra + 
                ", alquiler: " + alquilerBase + 
                ", factor de rev: " + factorRevalorizacion + 
                ", hipoteca base: " + hipotecaBase + 
                ", precio edif.: " + precioEdificar + 
                ", num de hoteles: " + numHoteles + 
                ", num de casas:" + numCasas;
    }
}