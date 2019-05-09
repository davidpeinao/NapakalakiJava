package modeloqytetet;

public class Dado {
    
    private static final Dado INSTANCE = new Dado();
    
    private Dado() {}
    private int valor;
    
    public static Dado getInstance() {
        return INSTANCE;
    }
    
    int tirar() {  
        int numero = (int) (Math.random() * 6) + 1;
        valor = numero;
        return numero;
    }

    public int getValor() {
        return valor;
    }
    
    
}
