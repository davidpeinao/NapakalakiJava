package modeloqytetet;
import java.util.ArrayList;

public class Tablero {
    private ArrayList<Casilla> casillas;
    private Casilla carcel;

    public Tablero (){
    }
    
    ArrayList<Casilla> getCasillas (){
        return casillas;
    }
    
    Casilla getCarcel (){
        return carcel;
    }
    
    void inicializar (){
        this.casillas = new ArrayList<>();
        
        // Casillas que son calles
        int i = 0;
        ArrayList<TituloPropiedad> titulosCalles = new ArrayList();
        titulosCalles.add(new TituloPropiedad("Calle Java", 500, 50, -0.2f, 150, 250));
        titulosCalles.add(new TituloPropiedad("Calle Python", 500, 55, -0.15f, 200, 250));
        titulosCalles.add(new TituloPropiedad("Calle C", 550, 60, -0.10f, 250, 300));
        titulosCalles.add(new TituloPropiedad("Calle C++", 600, 65, -0.05f, 300, 350));
        titulosCalles.add(new TituloPropiedad("Calle Ruby", 650, 70, 0.00f, 350, 400));
        titulosCalles.add(new TituloPropiedad("Calle JavaScript", 700, 75, 0.00f, 450, 450));
        titulosCalles.add(new TituloPropiedad("Calle XML", 750, 80, 0.00f, 550, 500));
        titulosCalles.add(new TituloPropiedad("Calle PHP", 800, 85, 0.05f, 650, 550));
        titulosCalles.add(new TituloPropiedad("Calle SQL", 850, 90, 0.10f, 750, 600));
        titulosCalles.add(new TituloPropiedad("Calle MATLAB", 900, 95, 0.15f, 850, 650));
        titulosCalles.add(new TituloPropiedad("Calle R", 950, 95, 0.20f, 950, 700));
        titulosCalles.add(new TituloPropiedad("Calle Ensamblador", 1000, 100, 0.20f, 1000, 750));
        
        this.casillas.add(new OtraCasilla(0, 0, TipoCasilla.SALIDA));
        this.casillas.add(new Calle(1, titulosCalles.get(i++)));
        this.casillas.add(new Calle(2, titulosCalles.get(i++)));
        this.casillas.add(new Calle(3, titulosCalles.get(i++)));
        this.casillas.add(new Calle(4, titulosCalles.get(i++)));
        this.casillas.add(new OtraCasilla(5, 0, TipoCasilla.CARCEL));
        
        // inicializamos atributo carcel
        this.carcel = this.casillas.get(5);
        
        this.casillas.add(new OtraCasilla(6, 0, TipoCasilla.SORPRESA));
        this.casillas.add(new Calle(7, titulosCalles.get(i++)));
        this.casillas.add(new Calle(8, titulosCalles.get(i++)));
        this.casillas.add(new Calle(9, titulosCalles.get(i++)));
        this.casillas.add(new OtraCasilla(10, 500, TipoCasilla.IMPUESTO));
        this.casillas.add(new Calle(11, titulosCalles.get(i++)));
        this.casillas.add(new OtraCasilla(12, 0, TipoCasilla.SORPRESA));
        this.casillas.add(new Calle(13, titulosCalles.get(i++)));
        this.casillas.add(new OtraCasilla(14, 0, TipoCasilla.PARKING));
        this.casillas.add(new OtraCasilla(15, 0, TipoCasilla.JUEZ));
        this.casillas.add(new Calle(16, titulosCalles.get(i++)));
        this.casillas.add(new Calle(17, titulosCalles.get(i++)));
        this.casillas.add(new OtraCasilla(18, 0, TipoCasilla.SORPRESA));
        this.casillas.add(new Calle(19, titulosCalles.get(i++)));
    }
    
    boolean esCasillaCarcel(int numeroCasilla) {
        return carcel.getNumeroCasilla() == numeroCasilla;
    }
    
    Casilla obtenerCasillaNumero(int numeroCasilla) {
        return casillas.get(numeroCasilla);
    }
    
    Casilla obtenerCasillaFinal(Casilla casilla, int desplazamiento) {
        int i = (casilla.getNumeroCasilla() + desplazamiento) % casillas.size();
        return casillas.get(i);
  }

    @Override
    public String toString() {
        String result = "";
        for (Casilla c : casillas){
            result += ("\n " + c.toString());
        }
        return result;
    }
   
    
}