package controladorqytetet;

import java.util.ArrayList;
import modeloqytetet.Qytetet;
import modeloqytetet.MetodoSalirCarcel;
import modeloqytetet.EstadoJuego;

public class ControladorQytetet {
    private ArrayList<String> nombreJugadores;
    private static final ControladorQytetet INSTANCE = new ControladorQytetet();

    private ControladorQytetet() {
        ArrayList<String> nombreJugadores = new ArrayList();
    }
    public static ControladorQytetet getInstance() {
        return INSTANCE;
    } 
    
    public void setNombreJugadores(ArrayList<String> nombreJugadores){
        this.nombreJugadores = nombreJugadores;
    }
        
    public ArrayList<Integer> obtenerOperacionesJuegoValidas(){
        ArrayList <Integer> resultado = new ArrayList();
        EstadoJuego estadoJuego = modeloqytetet.Qytetet.getInstance().getEstadoJuego();

        if (estadoJuego == null){
            resultado.add(OpcionMenu.INICIARJUEGO.ordinal());
            return resultado;
        }
        System.out.println("\n------------------------------------------------------");
        System.out.println("Jugador actual: " + modeloqytetet.Qytetet.getInstance().getJugadorActual().getNombre() + "\n");
        
        switch(estadoJuego){            
                
            case JA_CONSORPRESA:        
                resultado.add(OpcionMenu.APLICARSORPRESA.ordinal());
                resultado.add(OpcionMenu.TERMINARJUEGO.ordinal());
                resultado.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                break;
                
                
            case ALGUNJUGADORENBANCARROTA:
                // Este codigo no va aqui creo
                System.out.println("Algun jugador esta en bancarrota, se acaba el juego");
                Qytetet.getInstance().obtenerRanking();
                // Muestra los jugadores segun su ranking
                for(int i=0; i < Qytetet.getInstance().getJugadores().size(); i++)
                    System.out.println("Jugador nº " + (i+1) + ": " + Qytetet.getInstance().getJugadores().get(i));
                
                resultado.add(OpcionMenu.OBTENERRANKING.ordinal());
                resultado.add(OpcionMenu.TERMINARJUEGO.ordinal());
                resultado.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                break;
            
                
            case JA_PUEDECOMPRAROGESTIONAR:
                resultado.add(OpcionMenu.PASARTURNO.ordinal());
                resultado.add(OpcionMenu.COMPRARTITULOPROPIEDAD.ordinal());
                resultado.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                resultado.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal()); 
                resultado.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                resultado.add(OpcionMenu.EDIFICARCASA.ordinal());  
                resultado.add(OpcionMenu.EDIFICARHOTEL.ordinal()); 
                resultado.add(OpcionMenu.TERMINARJUEGO.ordinal());
                resultado.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                break;
                
                
            case JA_PUEDEGESTIONAR:
                resultado.add(OpcionMenu.PASARTURNO.ordinal());
                resultado.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
                resultado.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal()); 
                resultado.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
                resultado.add(OpcionMenu.EDIFICARCASA.ordinal());  
                resultado.add(OpcionMenu.EDIFICARHOTEL.ordinal()); 
                resultado.add(OpcionMenu.TERMINARJUEGO.ordinal());
                resultado.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                break;
                
                
            case JA_PREPARADO:
                resultado.add(OpcionMenu.JUGAR.ordinal());
                resultado.add(OpcionMenu.TERMINARJUEGO.ordinal());
                resultado.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                break;                
            
                
            case JA_ENCARCELADO:
                resultado.add(OpcionMenu.PASARTURNO.ordinal());
                resultado.add(OpcionMenu.TERMINARJUEGO.ordinal());
                resultado.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal());
                break;
                
                
            case JA_ENCARCELADOCONOPCIONDELIBERTAD:
                resultado.add(OpcionMenu.INTENTARSALIRCARCELPAGANDOLIBERTAD.ordinal());
                resultado.add(OpcionMenu.INTENTARSALIRCARCELTIRANDODADO.ordinal());
                resultado.add(OpcionMenu.TERMINARJUEGO.ordinal());
                resultado.add(OpcionMenu.MOSTRARTABLERO.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORES.ordinal());
                resultado.add(OpcionMenu.MOSTRARJUGADORACTUAL.ordinal()); 
                break;            
        }
        
        return resultado;
    }
    
    public boolean necesitaElegirCasilla(int opcionMenu){
        ArrayList <Integer> necesita = new ArrayList();
        necesita.add(OpcionMenu.VENDERPROPIEDAD.ordinal());
        necesita.add(OpcionMenu.HIPOTECARPROPIEDAD.ordinal());
        necesita.add(OpcionMenu.CANCELARHIPOTECA.ordinal());
        necesita.add(OpcionMenu.EDIFICARCASA.ordinal());
        necesita.add(OpcionMenu.EDIFICARHOTEL.ordinal());
        
        if (necesita.contains(opcionMenu))
            return true;
        else
            return false;
    }
    
    public ArrayList<Integer> obtenerCasillasValidas(int opcionMenu){
        ArrayList <Integer> casillasValidas = new ArrayList();
        OpcionMenu valor = OpcionMenu.values()[opcionMenu];
        
        switch(valor){
            case VENDERPROPIEDAD:
                casillasValidas = modeloqytetet.Qytetet.getInstance().obtenerPropiedadesJugador();
            break;
            
            case HIPOTECARPROPIEDAD:
                casillasValidas = modeloqytetet.Qytetet.getInstance().obtenerPropiedadesJugadorSegunEstadoHipoteca(false);
            break;
            
            case CANCELARHIPOTECA:
                casillasValidas = modeloqytetet.Qytetet.getInstance().obtenerPropiedadesJugadorSegunEstadoHipoteca(true);
            break;
            
            case EDIFICARCASA:
                casillasValidas = modeloqytetet.Qytetet.getInstance().obtenerPropiedadesJugador();
            break;
            
            case EDIFICARHOTEL:
                casillasValidas = modeloqytetet.Qytetet.getInstance().obtenerPropiedadesJugador();
            break;
            
        }
        
        return casillasValidas;
    }
    
    public String realizarOperacion(int opcionElegida, int casillaElegida){
        OpcionMenu opcion = OpcionMenu.values()[opcionElegida];
        String out = "";
        boolean result;
        
        switch(opcion){
            case INICIARJUEGO:
                modeloqytetet.Qytetet.getInstance().inicializarJuego(nombreJugadores);
                out = "Se ha inicializado el juego";
            break;
            
            case JUGAR:
                modeloqytetet.Qytetet.getInstance().jugar();
                out = "Se tira el dado, ha caido en la casilla: " + 
                      modeloqytetet.Qytetet.getInstance().obtenerCasillaJugadorActual().toString();
            break;
            
            case APLICARSORPRESA:
                modeloqytetet.Qytetet.getInstance().aplicarSorpresa();
                out = "Se ha aplicado una sorpresa";
            break;
            
            case INTENTARSALIRCARCELPAGANDOLIBERTAD:
                result = modeloqytetet.Qytetet.getInstance().intentarSalirCarcel(MetodoSalirCarcel.PAGANDOLIBERTAD);
                if(result)
                    out = "Has salido de la carcel";
                else
                    out = "No has salido de la carcel";
            break;
            
            case INTENTARSALIRCARCELTIRANDODADO:
                result = modeloqytetet.Qytetet.getInstance().intentarSalirCarcel(MetodoSalirCarcel.TIRANDODADO); 
                if(result)
                    out = "Has salido de la carcel";
                else
                    out = "No has salido de la carcel";
            break;
            
            case COMPRARTITULOPROPIEDAD:
                result = modeloqytetet.Qytetet.getInstance().comprarTituloPropiedad(); 
                if(result)
                    out = "Has comprado el titulo de propiedad";
                else
                    out = "No has comprado el titulo de propiedad";
            break;
            
            case HIPOTECARPROPIEDAD:
                modeloqytetet.Qytetet.getInstance().hipotecarPropiedad(casillaElegida); 

            break;
            
            case CANCELARHIPOTECA:
                result = modeloqytetet.Qytetet.getInstance().cancelarHipoteca(casillaElegida); 
                if(result)
                    out = "Has cancelado la hipoteca";
                else
                    out = "No has cancelado la hipoteca";
            break;
            
            case EDIFICARCASA:
                result = modeloqytetet.Qytetet.getInstance().edificarCasa(casillaElegida); 
                if(result)
                    out = "Has edificado una casa";
                else
                    out = "No has edificado una casa";
            break;
            
            case EDIFICARHOTEL:
                result = modeloqytetet.Qytetet.getInstance().edificarHotel(casillaElegida); 
                if(result)
                    out = "Has edificado un hotel";
                else
                    out = "No has edificado un hotel";
            break;
            
            case VENDERPROPIEDAD:
                result = modeloqytetet.Qytetet.getInstance().venderPropiedad(casillaElegida); 
                if(result)
                    out = "Has vendido la propiedad";
                else
                    out = "No has vendido la propiedad";
            break;
            
            case PASARTURNO:
                modeloqytetet.Qytetet.getInstance().siguienteJugador(); 
                out = "Has pasado turno";
            break;
            
            case OBTENERRANKING:
                System.out.println("Ranking:");
                modeloqytetet.Qytetet.getInstance().obtenerRanking();
                out = "";
            break;
            
            case TERMINARJUEGO:
                System.out.println("El juego ACABA");
                System.exit(0); 
                out = "";
            break;
            
            case MOSTRARJUGADORACTUAL:
                System.out.println("Jugador actual:");
                
                if(modeloqytetet.Qytetet.getInstance().getCartaActual() == null)
                    out = modeloqytetet.Qytetet.getInstance().getJugadorActual().toString()
                        + "\n Carta actual: null";
                else
                    out = modeloqytetet.Qytetet.getInstance().getJugadorActual().toString()
                        + "\n Carta actual: " + modeloqytetet.Qytetet.getInstance().getCartaActual().toString();
            break;
            
            case MOSTRARJUGADORES:
                System.out.println("Jugadores: ");
                out = modeloqytetet.Qytetet.getInstance().getJugadores().toString();
            break;
            
            case MOSTRARTABLERO:
                System.out.println("Tablero: ");
                out = modeloqytetet.Qytetet.getInstance().getTablero().toString();
            break;

        }
        return out;
    } 

}
