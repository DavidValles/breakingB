
package bad;
import javax.swing.JFrame;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
/**
 *
 * @author David
 */
public class Breaking extends JFrame implements Runnable, KeyListener{
        public Breaking() throws IOException {
        init();
        start();
    }   
        private Graphics dbg;               //Objeto tipo Graphics
        private Image dbImage;              //Imagen para el doblebuffer 
        private boolean pausa;              // boolean para pausa
        private Animacion animP;
        private Animacion animB;
        private Animacion animM;
        private int direX;
        private int direY;
         private SoundClip intro;          //audio para el intro
              
        private boolean gameStart;
        
        //Variables de control de tiempo de la animación
        private long tiempoActual;
        private long tiempoInicial;
        
         private Bueno hank; //objeto bueno
         private Malo meth; // objeto malo
         private Bueno bola; //objeto buenp
         private int direccion; // variable utilizada para el movimiento
         private int nivel;
         private boolean vida;
         private boolean arma;
         private boolean takeon;
         private boolean gameOver;
         
         private int posX;
         private int posY;
         
         private int vx;
         private int vy;
         
         private int posMx;
         private int posMy;
         
         private boolean instrucciones;    //te muestra las instrucciones del juego  
         private Image ins;    //imagen de las instrucciones
         private Image bg; //imagen del background
    
         private LinkedList<Malo> lista; // Lista
         private String st; // string para desplegar score
        
        
        public void init() throws IOException {
            this.setSize(1000, 570); // tamano del applet
            posX=50;
            posY=500;
            direX=1;
            direY=1;
            posMx=50;
            posMy=80;
            gameOver=false;
            addKeyListener(this); //utilizada para los metodos de KeyBoard
             URL iURL = this.getClass().getResource("images/inicio.png");
             ins = Toolkit.getDefaultToolkit().getImage(iURL);
             URL uURL = this.getClass().getResource("images/bg2.png");
             bg = Toolkit.getDefaultToolkit().getImage(uURL);
             Image pallet = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/pallet.png"));
             Image circulo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/ball.png"));
             Image mal1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/meth1.png"));
             Image mal2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/meth2.png"));
             
             animP = new Animacion();
             animP.sumaCuadro(pallet, 100);
             
             animB = new Animacion();
             animB.sumaCuadro(circulo,100);
             
             hank = new Bueno(posX,posY, pallet, animP); // se crea hank
             bola = new Bueno(100, 100, circulo, animB);
             pausa=true;
             
             intro = new SoundClip("sounds/Breaking Bad Theme Song.wav");
             
             gameStart=false;
             
             lista= new LinkedList<Malo>();
             for(int j=0; j<5; j++){
              for(int i=0;i<10;i++){
                  
                  animM = new Animacion();
                  animM.sumaCuadro(mal1,100);
                  posMx=posMx+ 70;
                  posMy=posMy;
                  
                  meth = new Malo(posMx,posMy,mal1,animB); //se crea una nueva bomba
                  meth.setPosX(meth.getPosX()+20); //posicion en x
                  meth.setPosY(meth.getPosY()-20);
                  lista.add(meth);
                 
              
                }
                posMx=50;
                posMy+=60;
             }
        }
        
         public void start() {

        //Crea el thread
        Thread hilo = new Thread(this);
        //Inicializa el thread
        hilo.start();
    }
         
         public void stop() {

    }
          public void destroy() {

    }
           public void run() {
               
               //Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();

        //Ciclo principal del Applet. Actualiza y despliega en pantalla la animación hasta que el Applet sea cerrado
        while (true) {

            //sigue mientras pausa sea true
            if(gameStart){
            if (pausa) {
                try {
                    //Actualiza la animación
                    actualiza();
                } catch (IOException ex) {
                    Logger.getLogger(Breaking.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Checa colision
                checaColision();

                //Manda a llamar al método paint() para mostrar en pantalla la animación
                repaint();
            }
        }
            //Hace una pausa de 200 milisegundos
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
            }
        }
               
    }
            public void actualiza() throws IOException {
                
            
                
            //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
            long tiempoTranscurrido
                    = System.currentTimeMillis() - tiempoActual;

            //Guarda el tiempo actual
            tiempoActual += tiempoTranscurrido;
            
             //Actualiza la animación en base al tiempo transcurrido
         animP.actualiza(tiempoTranscurrido);
         animB.actualiza(tiempoTranscurrido);
         
         for(Malo meth:lista){
              animM.actualiza(tiempoTranscurrido);
         }
            
            if(direX==0){
                bola.setPosX(bola.getPosX()-20);
            }
            else{
                bola.setPosX(bola.getPosX()+20);
            }
            
            if(direY==0){
                bola.setPosY(bola.getPosY()-20);
            }
            else{
                bola.setPosY(bola.getPosY()+20);
            }
            
            switch (direccion) {
                    case 1: {
                        hank.setPosY(hank.getPosY()-20);

                    }

                    break;    //se mueve hacia arriba

                    case 2: {
                        hank.setPosY(hank.getPosY()+20);

                    }

                    break;    //se mueve hacia abajo

                    case 3: {
                        hank.setPosX(hank.getPosX() - 20);

                    }

                    break;    //se mueve hacia izquierda

                    case 4: {
                        hank.setPosX(hank.getPosX() + 20);

                    }

                    break;    //se mueve hacia derecha	

                }
                  

    }
            public void checaColision() {
               
                if(hank.getPosX()<0){
                hank.setPosX(hank.getPosX()+20);
                }
                
                if(hank.getPosX()>(1000-hank.getAncho())){
                 hank.setPosX(hank.getPosX()-20);  
                }
                
               if(hank.intersecta2(bola)){
                   direY=0;
               }
               
               
               if(bola.getPosY()<20){
                   direY=1;
               }
               
               if(bola.getPosX()>(1000-bola.getAncho())){
                   direX=0;
               }
               
               if(bola.getPosX()<0){
                   direX=1;
               }
                
    }
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {//Presiono flecha arriba
            direccion = 0;
            

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {    //Presiono flecha abajo
            direccion = 0;
            

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha izquierda
            direccion = 3;
            

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha derecha
            direccion = 4;
            

        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            if (pausa) {
                pausa = false;
            } else {
                pausa = true;
            }
        }
        
          if (e.getKeyCode() == KeyEvent.VK_S) {
             if(!gameStart){gameStart=true;}
        }
        
        
            
    }
            public void keyTyped(KeyEvent e) {

    }
             public void keyReleased(KeyEvent e) {
    }
            
            public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
         
         
        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }      
      
            public void paint1(Graphics g) {
              if(!gameStart)  {g.drawImage(ins,0,0,this);}
              if(gameStart){ g.drawImage(bg, 0, 0, this);
              for(Malo meth:lista){   
             if(animP!=null){
            
             g.drawImage(animP.getImagen(), hank.getPosX(), hank.getPosY(), this);
             g.drawImage(animB.getImagen(),bola.getPosX(), bola.getPosY(), this);
             g.drawImage(animM.getImagen(), meth.getPosX(), meth.getPosY(),this);
             }
            }
           }
    }
            
}
