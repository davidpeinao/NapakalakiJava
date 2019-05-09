
package vistatextualqytetet;

import controladorqytetet.*;
import java.util.ArrayList;
import java.util.Scanner;

//
//
//
//
//
//
//  
//
//
//
//






public class VistaTextualQytetet {
    
    public ArrayList<String> obtenerNombreJugadores(){
        ArrayList<String> nombres = new ArrayList();
        Scanner in = new Scanner(System.in);
        
        int n = 0;
        do{
            System.out.println("Introduzca numero de jugadores, de 2 a 4: ");
            n = in.nextInt();
            in.nextLine();
        }while(n < 2 || n > 4);
        
        for (int i=0; i<n; i++){
            System.out.println("Escriba el nombre del jugador " + (i+1) + " :");
            nombres.add(in.next());
        }
        return nombres;
    }
    
    public int elegirCasilla(int opcionMenu){
        ArrayList<Integer> casillasValidas = ControladorQytetet.getInstance().obtenerCasillasValidas(opcionMenu);
        ArrayList<String> casillasValidasString = new ArrayList();
        
        if (casillasValidas.isEmpty())
            return -1;
        
        for(int i=0; i < casillasValidas.size(); i++){
            casillasValidasString.add(casillasValidas.get(i).toString());
        }
        // al devolver leerValorCorrecto un string, se le hace Integer.parseInt 
        // para ponerlo como int
        // esto devuelve la casilla que se ha seleccionado por teclado
        return Integer.parseInt(leerValorCorrectoCasilla(casillasValidasString));
    }
    
    public String leerValorCorrectoCasilla(ArrayList<String> valoresCorrectos){
        Scanner in = new Scanner(System.in);
        String result = "";
        boolean erroneo = true;
        do{
            System.out.println("Introduzca casilla correcta: ");
            for (int i = 0; i < valoresCorrectos.size(); i++){
                System.out.println("Posibilidades:  " + valoresCorrectos.get(i));
            }
            result = in.next();
            if(valoresCorrectos.contains(result))
                erroneo = false;
            in.nextLine();           
        }while(erroneo);
        
        return result;
    }
    
    public int elegirOperacion(){
        ArrayList<Integer> operacionesValidas = ControladorQytetet.getInstance().obtenerOperacionesJuegoValidas();
        ArrayList<String> operacionesValidasString = new ArrayList();
        for(int i=0; i < operacionesValidas.size(); i++){
            operacionesValidasString.add(operacionesValidas.get(i).toString());
        }      
        // al devolver leerValorCorrecto un string, se le hace Integer.parseInt 
        // para ponerlo como int
        // esto devuelve la operacion que se ha seleccionado por teclado
        return Integer.parseInt(leerValorCorrectoOperacion(operacionesValidasString));
    }
    public String leerValorCorrectoOperacion(ArrayList<String> valoresCorrectos){
        Scanner in = new Scanner(System.in);
        String result = "";
        boolean erroneo = true;
        do{
            System.out.println("Valores posibles: ");
            for (int i = 0; i < valoresCorrectos.size(); i++){
                System.out.println("Posibilidad:  " + valoresCorrectos.get(i) + " --> "+ OpcionMenu.values()[Integer.parseInt(valoresCorrectos.get(i))]);
            }
            System.out.println("Introduzca un valor posible: ");
            result = in.next();
            if(valoresCorrectos.contains(result))
                erroneo = false;
            in.nextLine();           
        }while(erroneo);
        
        return result;
    }
    
    public static void main(String args[]) {
        VistaTextualQytetet ui = new VistaTextualQytetet();
        ControladorQytetet.getInstance().setNombreJugadores(ui.obtenerNombreJugadores());
        int operacionElegida, casillaElegida=0;
        boolean necesitaElegirCasilla;
        
        
        do {
            operacionElegida = ui.elegirOperacion();           
            necesitaElegirCasilla = ControladorQytetet.getInstance().necesitaElegirCasilla(operacionElegida);
            if (necesitaElegirCasilla) 
                casillaElegida = ui.elegirCasilla(operacionElegida);                  
            if (!necesitaElegirCasilla || casillaElegida >= 0)
                System.out.println(ControladorQytetet.getInstance().realizarOperacion(operacionElegida, casillaElegida));          
        } while (true);
    }
}
