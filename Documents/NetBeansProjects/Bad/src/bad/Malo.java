package bad;

import java.awt.Image;


public class Malo extends Base{
    
    private int conteo; // variable de conteo
    private boolean entrada;// variable de entrada
    
    public Malo(int posX,int posY,Image image, Animacion anim){ //constructor
		super(posX,posY,image,anim);
               
    }

  
    public boolean getEntrada(){
        return entrada; // regresa el valor booleando de entrada
    }
    
    public int getConteo(){
        return conteo; // regresa el valor entero de conteo
    }
    
    public void setConteo(int conteo){
        this.conteo=conteo;
    }
    
}
