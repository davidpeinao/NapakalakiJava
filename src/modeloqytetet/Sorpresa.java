package modeloqytetet;

public class Sorpresa {
    private String texto;
    private int valor;
    private TipoSorpresa tipo;
    
    Sorpresa (String texto, int valor, TipoSorpresa tipo){
        this.texto = texto;
        this.valor = valor;
        this.tipo = tipo;
    }
    
    String getTexto (){
        return texto;
    }
    
    int getValor (){
        return valor;
    }
    
    TipoSorpresa getTipo (){
        return tipo;
    }
    
    @Override
    public String toString (){
        return "Sorpresa {texto= " + texto + ", valor= " + 
                Integer.toString(valor) + ", tipo= " + tipo + "}";
    }
}
