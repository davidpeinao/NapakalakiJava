package modeloqytetet;
import java.util.ArrayList;
import java.util.Collections;

public class Qytetet {
    
//    
//    
//Hola,
//
//Os paso a escribir y explicar los fragmentos de código que resuelven el examen sobre las prácticas P4 y P5.
//
//
//* Enumerado   OpcionMenu
//
//Solo había que añadir un valor más al enumerado.  Por ejemplo, REGALO.
//
//
//* Clase ControladorQytetet
//
//- Método  obtenerOperacionesJuegoValidas
//  Añadir una opción más en el caso de que esté el juego en el estado  JA_PUEDEGESTIONAR
//  operacionesJuego.add(OpcionMenu.REGALO.ordinal());
//  
//- Método   realizarOperacion
//  Añadir y un case más para la nueva operación. Importante, el controlador se comunica con el modelo a través de su fachada, Qytetet. 
//  Estaría mal enviarle el mensaje a un objeto de otra clase, como Jugador. Y todavía peor intentar resolver todo el examen en el controlador
//  case REGALO:
//    modelo.regalo();
//    // Se puede añadir código para informar al usuario de lo que se ha hecho
//    break;
//    
//    
//* Clase Qytetet
//  
//  Se declara e implementa el método  public boolean regalo() 
//  La clase responsable de gestionar las propiedades de un jugador es la clase Jugador.
//  Cada clase es responsable de gestionar sus atributos. Por tanto, Qytetet solo debe calcular el jugador siguiente al actual y TODA la gestión 
//  sobre las propiedades debe hacerse en la clase Jugador y/o Especulador.
//  
//  public void regalo () {   // Visibilidad publica, es accesible fuera del modelo, desde el controlador
//    Jugador siguiente = jugadores.get((jugadores.indexOf(jugadorActual)+1)%jugadores.size());
//    // Para la siguiente línea hay dos opciones válidas
//    // O bien,
//    siguiente.regaloRecibir(jugadorActual.regaloDar());
//    // Aunque también es válido
//    jugadorActual.regaloDar (siguiente); // Obviamente se implementaría distinto
//  }
//  
//  
//* Clase Jugador
//
//  Aquí es el único sitio donde es correcto gestionar las propiedades del jugador para realizar y recibir el regalo. Para ello, se usan los métodos que ya existen. 
//  No sería correcto volver a implementar algo que ya está implementado en un método.
//  TituloPropiedad regaloDar () {
//      TituloPropiedad aEntregar = null;
//      ArrayList<TituloPropiedad> hipotecadas = obtenerPropiedades(true);
//      if (!hipotecadas.isEmpty()) {
//        aEntregar = propiedades.get(0);
//        eliminarDeMisPropiedades (aEntregar);
//      }
//      return aEntregar;
//    }
//    
//    void regaloRecibir (TituloPropiedad titulo) {
//      if (titulo != null) {
//        propiedades.add(titulo);
//        titulo.setPropietario(this);  // Hay que actualizar el propietario del título
//      }
//    }
//
//    
//* Clase Especulador
//
//  Solo hay que redefinir regaloRecibir para que además, se intente cancelar la hipoteca.
//    @Override
//    void regaloRecibir (TituloPropiedad titulo) {
//      if (titulo != null) {
//        super.regaloRecibir(titulo);
//        cancelarHipoteca(titulo);   // El método cancelarHipoteca de Jugador
//      }
//    }
//  
//  Sería del todo incorrecto resolver el ejercicio haciendo consulta explícita de tipos, ya sea de manera explícita mediante instance of   como con cualquier estrategia isEspeculador, 
//  tieneFianza, etc. que nos permita consultar cuál es el tipo dinámico de un jugador. ¡¡¡HAY QUE USAR POLIMORFISMO!!!
//    
//    
//    
//    
    
    
    
    public static int MAX_JUGADORES = 4;
    static int NUM_SORPRESAS = 10;
    public static int NUM_CASILLAS = 20;
    static int PRECIO_LIBERTAD = 200;
    static int SALDO_SALIDA = 1000;
            
    private static final Qytetet INSTANCE = new Qytetet();
    private Qytetet() {
        dado = Dado.getInstance();
        jugadorActual = null;
        cartaActual = null;
        indiceJugadorActual = 0;
    }

    private ArrayList<Sorpresa> mazo = new ArrayList<>();
    private Tablero tablero;
    private Dado dado;
    private Jugador jugadorActual;
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private Sorpresa cartaActual;
    private EstadoJuego estadoJuego;
    private int indiceJugadorActual;
    
    public static Qytetet getInstance() {
        return INSTANCE;
    }    

    void actuarSiEnCasillaEdificable(){
        boolean deboPagar = jugadorActual.deboPagarAlquiler();
        if (deboPagar){
            jugadorActual.pagarAlquiler();
            if(jugadorActual.getSaldo() <= 0)
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
        } 
        
        Casilla calle = obtenerCasillaJugadorActual();
        boolean tengoPropietario = ((Calle) calle).tengoPropietario();
        
        if(estadoJuego != EstadoJuego.ALGUNJUGADORENBANCARROTA){
            if (tengoPropietario)
                setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
            else
                setEstadoJuego(EstadoJuego.JA_PUEDECOMPRAROGESTIONAR);
        }
        
    }
    
    void actuarSiEnCasillaNoEdificable(){
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        Casilla casillaActual= jugadorActual.getCasillaActual();
        
        if(casillaActual.getTipo()==TipoCasilla.IMPUESTO){
            jugadorActual.pagarImpuesto();
            if(jugadorActual.getSaldo() <= 0)
                this.setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);
        }
        if(casillaActual.getTipo()==TipoCasilla.JUEZ)             
            encarcelarJugador();

        if(casillaActual.getTipo()==TipoCasilla.SORPRESA){
            setCartaActual(mazo.get(0));
            mazo.remove(0);
            Collections.shuffle(mazo);
            setEstadoJuego(EstadoJuego.JA_CONSORPRESA);
        }       
    }
    
    public void aplicarSorpresa(){
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        if(cartaActual.getTipo() == TipoSorpresa.SALIRCARCEL)
            jugadorActual.setCartaLibertad(cartaActual);
        else 
            mazo.add(cartaActual);

        if(cartaActual.getTipo() == TipoSorpresa.PAGARCOBRAR){
            jugadorActual.modificarSaldo(cartaActual.getValor());
            
            if(jugadorActual.getSaldo() <= 0)
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);               
        }
        
        if(cartaActual.getTipo() == TipoSorpresa.IRACASILLA){
            int valor = cartaActual.getValor();
            boolean esCasillaCarcel = tablero.esCasillaCarcel(valor);
            if(esCasillaCarcel)
                encarcelarJugador();
            else
                mover(valor);
          
        }
        
        if(cartaActual.getTipo() == TipoSorpresa.PORCASAHOTEL){
            int cantidad = cartaActual.getValor();
            int numeroTotal = jugadorActual.cuantasCasasHotelesTengo();
            jugadorActual.modificarSaldo(cantidad*numeroTotal);
            
            if(jugadorActual.getSaldo() < 0)
                setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);   
        }
            
        if(cartaActual.getTipo() == TipoSorpresa.PORJUGADOR){
            for(Jugador j : jugadores){
                if(j != jugadorActual){
                    j.modificarSaldo(-cartaActual.getValor());
                    jugadorActual.modificarSaldo(cartaActual.getValor());
                }
                if(j.getSaldo() < 0)
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);  
                
                if(jugadorActual.getSaldo() < 0)
                    setEstadoJuego(EstadoJuego.ALGUNJUGADORENBANCARROTA);   
            }
        }
        if(cartaActual.getTipo() == TipoSorpresa.CONVERTIRME){
            int indice = jugadores.indexOf(jugadorActual);
            jugadorActual = jugadorActual.convertirme(cartaActual.getValor());
            jugadores.set(indice,jugadorActual);
        }
        
    }
    
    
    public boolean cancelarHipoteca(int numeroCasilla){
        TituloPropiedad titulo = tablero.obtenerCasillaNumero(numeroCasilla).getTitulo();
        if(titulo.getHipotecada()){
            jugadorActual.cancelarHipoteca(titulo);
            return true;
        }       
        return false;
    }
    
    public boolean comprarTituloPropiedad(){
        boolean comprado = jugadorActual.comprarTituloPropiedad();    
        if(comprado)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return comprado;
    }
    
    public boolean edificarCasa(int numeroCasilla){
        boolean edificada = false;    
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        edificada = jugadorActual.edificarCasa(titulo);
        
        if(edificada)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return edificada;
    }
        
    public boolean edificarHotel(int numeroCasilla){
        boolean edificada = false;    
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        edificada = jugadorActual.edificarHotel(titulo);
        
        if(edificada)
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
        return edificada;
    }
    
    private void encarcelarJugador(){
        if(!jugadorActual.tengoCartaLibertad() && jugadorActual.deboIrACarcel()){
            Casilla casillaCarcel = tablero.getCarcel();
            jugadorActual.irACarcel(casillaCarcel);
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        }
        else{
            Sorpresa carta = jugadorActual.devolverCartaLibertad();
            this.mazo.add(carta);
            setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        }
    }
    
    public Sorpresa getCartaActual(){
        return this.cartaActual;
    }
    
    Dado getDado(){
        return this.dado;
    }
    
    public Jugador getJugadorActual(){
        return jugadorActual;
    }

    public EstadoJuego getEstadoJuego() {
        return estadoJuego;
    }
    
    public ArrayList<Jugador> getJugadores(){
        return this.jugadores;
    }
    
    ArrayList<Sorpresa> getMazo(){
        return mazo;
    }
    
    public Tablero getTablero() {
        return tablero;
    }
    
    public int getValorDado(){
        return dado.getValor();
    }
    
    public void hipotecarPropiedad(int numeroCasilla){
        Casilla casilla = tablero.obtenerCasillaNumero(numeroCasilla);
        TituloPropiedad titulo = casilla.getTitulo();
        jugadorActual.hipotecarPropiedad(titulo);
        this.setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        
    }
    
    private void inicializarCartasSorpresa (){
        mazo.add(new Sorpresa("Te conviertes en especulador", 3000, TipoSorpresa.CONVERTIRME));
        mazo.add(new Sorpresa("Te han pillado, al trullo", tablero.getCarcel().getNumeroCasilla(), TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Descubres mágicamente el teletransporte y apareces en la casilla 7", 7, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Hace mucho viento, tanto que te lleva hasta la casilla 19", 19, TipoSorpresa.IRACASILLA));
        mazo.add(new Sorpresa("Tienes suerte, con esta carta podrás salir de la cárcel", 0, TipoSorpresa.SALIRCARCEL));
        mazo.add(new Sorpresa("Estás de suerte, te llevas 450e", 450, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Un etniano te ha pedido un leuro premoh, como no te has ido del lugar, te ha quitado el leuro y 299 más", -300, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Te conviertes en Montoro, cada jugador te tiene que pagar 125e", 125, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("Moroso, le debias 50e a cada jugador y no me lo has dicho, ahora pagas el doble", -100, TipoSorpresa.PAGARCOBRAR));
        mazo.add(new Sorpresa("El IBI ha subido, paga 20e por cada casa y hotel que tengas", -20, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Tus propiedades se confunden con unas de la Iglesia, así que no pagas IBI, recibe 15e por cada casa y hotel que tengas", 15, TipoSorpresa.PORCASAHOTEL));
        mazo.add(new Sorpresa("Te conviertes en especulador", 5000, TipoSorpresa.CONVERTIRME));
        Collections.shuffle(mazo);
    }    
    
    public void  inicializarJuego(ArrayList nombres){
        inicializarJugadores(nombres);
        inicializarTablero();
        inicializarCartasSorpresa();
        salidaJugadores();
    }
    
    private void inicializarJugadores(ArrayList<String> nombres){
        Jugador player;
        for (String jugador : nombres){
            player = new Jugador(jugador);
            jugadores.add(player);
        }
    }
    
    private void inicializarTablero(){
        tablero = new Tablero(); 
        tablero.inicializar();
    }
 
    public boolean intentarSalirCarcel(MetodoSalirCarcel metodo){        
        if(metodo == MetodoSalirCarcel.TIRANDODADO){
            int resultado = dado.tirar();
            if(resultado >= 5)
                jugadorActual.setEncarcelado(false);
        }
        if(metodo == MetodoSalirCarcel.PAGANDOLIBERTAD){
            jugadorActual.pagarLibertad(PRECIO_LIBERTAD);
        }
        boolean encarcelado = jugadorActual.getEncarcelado();
        
        if(encarcelado)
            setEstadoJuego(EstadoJuego.JA_ENCARCELADO);
        else
            setEstadoJuego(EstadoJuego.JA_PREPARADO);
        
        return !encarcelado;
    }
    
    public boolean jugadorActualEnCalleLibre(){
        return ((jugadorActual.getCasillaActual().soyEdificable()) 
             && (jugadorActual.getCasillaActual().getTitulo().tengoPropietario() == false));
    }
    
    public boolean jugadorActualEncarcelado(){
        return jugadorActual.getEncarcelado();
    }
    
    public void jugar(){
        int valor = this.tirarDado();
        Casilla casilla = this.getTablero().obtenerCasillaFinal(this.jugadorActual.getCasillaActual(), valor);
        this.mover(casilla.getNumeroCasilla());
    }

    public Casilla obtenerCasillaJugadorActual(){
        return jugadorActual.getCasillaActual();
    }
    
    public ArrayList<Casilla> obtenerCasillasTablero(){
        return tablero.getCasillas();
    }
        
    public ArrayList<Integer> obtenerPropiedadesJugador(){
        ArrayList<Integer> array = new ArrayList();
        for (Casilla c : tablero.getCasillas()){
            if (c.soyEdificable() && (c.getTitulo().getPropietario() == jugadorActual))
                array.add(c.getNumeroCasilla());
        }      
        return array;
    }
    
    public ArrayList<Integer> obtenerPropiedadesJugadorSegunEstadoHipoteca(boolean estadoHipoteca){
        ArrayList<TituloPropiedad> props = new ArrayList<>();
        ArrayList<Integer> posiciones = new ArrayList<>();
        
        props = jugadorActual.getPropiedades();
        
        for (Casilla c: tablero.getCasillas()){
            for (TituloPropiedad p: props){
                if (c.getTipo() == TipoCasilla.CALLE){
                    if (c.getTitulo().getNombre() == p.getNombre()
                        && p.getHipotecada() == estadoHipoteca)
                            posiciones.add(c.getNumeroCasilla());
                }
            }
        }
        
        return posiciones;        
    }
    
    void mover(int casilla){
        Casilla casillaFinal = tablero.obtenerCasillaNumero(casilla);
        Casilla casillaInicial = jugadorActual.getCasillaActual();
        jugadorActual.setCasillaActual(casillaFinal);
        
        if(casillaFinal.getNumeroCasilla() < casillaInicial.getNumeroCasilla())
            jugadorActual.modificarSaldo(SALDO_SALIDA);
        
        if(casillaFinal.soyEdificable())
            actuarSiEnCasillaEdificable();
        else
            actuarSiEnCasillaNoEdificable();      
    }
     
    
    public void obtenerRanking(){
        Collections.sort(jugadores);
    }
    
    public int obtenerSaldoJugadorActual(){
        return jugadorActual.getSaldo();
    }

    private void salidaJugadores(){
        for(Jugador j : jugadores)
            j.setCasillaActual(tablero.obtenerCasillaNumero(0));
        
        int numero = (int) (Math.random() * jugadores.size());
        jugadorActual = jugadores.get(numero);
        
        this.setEstadoJuego(EstadoJuego.JA_PREPARADO);
    }
    
    private void setCartaActual(Sorpresa cartaActual){
        this.cartaActual = cartaActual;
    }

    public void setEstadoJuego(EstadoJuego estadoJuego) {
        this.estadoJuego = estadoJuego;
    }
    
    
    public void siguienteJugador(){
        int pos = 0;

        while (jugadores.get(pos).getNombre() != jugadorActual.getNombre()) {
            ++pos;
        }

        pos = (pos +1)%jugadores.size();
        jugadorActual = jugadores.get(pos);

            //this.intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO);
        if(jugadorActual.getEncarcelado()){
            estadoJuego = EstadoJuego.JA_ENCARCELADOCONOPCIONDELIBERTAD;
        }
        else{
            estadoJuego = EstadoJuego.JA_PREPARADO;
        }
    }

    
    int tirarDado(){
        return dado.tirar();
    }
    
    public boolean venderPropiedad(int numeroCasilla){
        Casilla casilla = this.tablero.obtenerCasillaNumero(numeroCasilla);
        jugadorActual.venderPropiedad(casilla);
        setEstadoJuego(EstadoJuego.JA_PUEDEGESTIONAR);
        //esto devuelve true siempre o que?
        return true;
    }

}