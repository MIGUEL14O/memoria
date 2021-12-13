package Todo;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Juan sebastian
 */


public class Juego {
    
    // Definifion de atributos 
    JFrame ventana; 
    JPanel panelPresentacion;
    JLabel fondoPresentacion;
    JLabel botonJugar;
    //jugar
    JPanel panelJuego;
    JLabel fondoJuego;
    JLabel matriz[][];
    int mat[][];
    int matAux [][];
    String Jugador;
    Random aleatorio;
    JLabel nombreJugador;
    //cronometro del juego 
    Timer tiempo ;
    JLabel contador;
    int min;
    int seg;
    int contar;
    Timer tiempoEspera;
    int contSegEspera;
    Timer tiempoEspera1;
    int bandera;
    int bandera1;
    
    int numeroCartAnt;
    int antX;
    int antY;
    int actualNumeroCart;
    int actualX;
    int actualY;
    
    public Juego(){
        ventana = new JFrame("Juego de memoria");
        ventana.setSize(650,700);// tamaño de la ventana 
        ventana.setLayout(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setResizable(false);
        
        
        //presentacion
        panelPresentacion = new JPanel();
        panelPresentacion.setSize(ventana.getWidth(), ventana.getHeight());
        panelPresentacion.setLocation(0,0);
        panelPresentacion.setLayout(null);
        panelPresentacion.setVisible(true);
        ventana.add(panelPresentacion,0);
        
        //fondo de presentacion 
        fondoPresentacion = new JLabel();
        fondoPresentacion.setSize(ventana.getWidth(), ventana.getHeight());
        fondoPresentacion.setLocation(0, 0);
        fondoPresentacion.setIcon(new ImageIcon("imagenes/fondo.jpg"));//imagen de fondo del juego inicio
        fondoPresentacion.setVisible(true);
        panelPresentacion.add(fondoPresentacion,0);
        ventana.add(panelPresentacion);
        
        //boton de jugar 
        botonJugar = new JLabel();
        botonJugar.setSize(332,75);//ancho y alto  
        botonJugar.setLocation(150, 220); // x, y 
        botonJugar.setIcon(new ImageIcon("imagenes/boton-jugar.png"));
        botonJugar.setVisible(true);
        panelPresentacion.add(botonJugar,0);
        
        //Panel  juego
        panelJuego = new JPanel();
        panelJuego.setSize(ventana.getWidth(), ventana.getHeight());  
        panelJuego.setLocation(0, 0);
        panelJuego.setLayout(null);
        panelJuego.setVisible(true);
        
        
        //fondo del juego
        fondoJuego = new JLabel();
        fondoJuego.setSize(ventana.getWidth(), ventana.getHeight());
        fondoJuego.setLocation(0, 0);
        fondoJuego.setIcon(new ImageIcon("imagenes/fondo-juego.png"));//imagen fondo de juego con cartas
        fondoJuego.setVisible(true);
        panelJuego.add(fondoJuego,0);
        
        
        //nombre del jugador 
        
        nombreJugador = new JLabel();
        nombreJugador.setSize(150,20);
        nombreJugador.setLocation(30, 60);
        nombreJugador.setForeground(Color.white);
        nombreJugador.setVisible(true);
        panelJuego.add(nombreJugador,0);
        
        // tiempo
        contador = new JLabel();
        contador.setSize(150,20);
        contador.setLocation(ventana.getWidth()-200, 10);
        contador.setForeground(Color.white);
        contador.setVisible(true);
        panelJuego.add(contador,0);
        
        
   
        //matriz logica  
        //20 imagenes para cambiar el tamaño de los tarjetas cambiar numero de columnas 
        mat = new int [4][5];
        matAux = new int [4][5];
        aleatorio = new Random();
        this.nuemrosAletorios();
        
        
         //matriz de imagenes 
        matriz = new JLabel[4][5];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                matriz [i][j] = new JLabel();
                matriz [i][j].setBounds( 90+(j*100), 90+(i*120) , 80, 100); // i alto j ancho
                matriz[i][j].setIcon(new ImageIcon("imagenes/"+ matAux[i][j]+".png"));
                matriz[i][j].setVisible(true);
               panelJuego.add(matriz[i][j],0); 
            }
        }
        
        // tiempo o contador
        
        min = 0;
        seg = 0;
        
        tiempo =  new Timer (1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                seg ++;
                if (seg == 60) {
                    min++;
                    seg=0;
                }
                contador.setText("Tiempo: "+min+":"+seg);
            }
        } 
        );
        
        // tiempo de espera entre carta y carta 
        
        contSegEspera =0;
        tiempoEspera =  new Timer (1000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               contSegEspera ++;
            }
        } 
        );
        tiempoEspera.start();
        tiempoEspera.stop();
        contSegEspera = 0;
        bandera = 0;
        bandera1 = 0;
        
        
        
        // click cartas evento para las tarjetas 
        contar = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                matriz [i][j].addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        for (int k = 0; k < 4; k++) {
                            for (int l = 0; l < 5; l++) {
                                if (e.getSource()== matriz[k][l]) {
                                    //System.out.println(k+" "+l);
                                    
                                    if (matAux[k][l]==0 && contar !=2) {
                                    matAux[k][l]= mat [k][l];
                                    matriz[k][l].setIcon(new ImageIcon("imagenes/"+ matAux[k][l]+".png"));  
                                    contar ++;
                                    actualNumeroCart = mat[k][l];
                                    actualX = k;
                                    actualY = l;
                                    if (contar == 1) {
                                           numeroCartAnt= mat [k][l];
                                            antX = k;
                                            antY = l ; 
                                        }
                     
                                    tiempoEspera1 =  new Timer (1000, new ActionListener()
                                    {
                                        public void actionPerformed(ActionEvent e)
                                        {
                                         
                                         
                                            if (contar == 2 && bandera1 == 0 ) {
                                               tiempoEspera.restart();
                                               bandera1 = 1;
                                            }
                                        if (contar == 2 && contSegEspera ==2 ) {
                                            tiempoEspera.stop();
                                            contSegEspera = 0;
                                            if (matAux[actualX][actualY]== matAux[antX][antY]) {
                                                matAux[actualX][actualY]= -1;
                                                matAux [antX][antY] = -1;
                                                matriz[actualX][actualY].setIcon(new ImageIcon("imagenes/"+ matAux[actualX][actualY]+".png"));  
                                                matriz[antX][antY].setIcon(new ImageIcon("imagenes/"+ matAux[antX][antY]+".png"));
                                                contar=0;
                                                // ganer 
                                                int acumulador = 0;
                                                for (int m = 0; m < 4; m++) {
                                                    for (int n = 0; n < 5; n++) {
                                                        if (matAux[m][n]== -1) 
                                                         acumulador ++;   
                                                        }
                                                }
                                                if(acumulador == 20)
                                                {
                                                    tiempo.stop();
                                                    JOptionPane.showInputDialog(ventana, "FELICIDADES HAS GANADO CON UN TIEMPO DE : "+min+": "+seg);
                                                    
                                                    for (int m = 0; m < 4; m++) {
                                                        for (int n = 0; n < 5; n++) {
                                                             mat[m][n]= 0;
                                                             matAux[m][n]= 0;
                                                             matriz[m][n].setIcon(new ImageIcon("imagenes/"+ matAux[m][n]+".png")); 
                                                        }
                                                    }
                                                    nuemrosAletorios();
                                                    min=0;
                                                    seg=0;
                                                    tiempo.restart();
                                                    
                                                }
                                                
                                            }
                                            for (int m = 0; m < 4; m++) {
                                                for (int n = 0; n < 5; n++) {
                                                    if (matAux[m][n]!=0 && matAux[m][n]!=-1) {
                                                       matAux[m][n]= 0;
                                                       matriz[m][n].setIcon(new ImageIcon("imagenes/"+ matAux[m][n]+".png")); 
                                                       contar=0;
                                                    }
                                                }
  
                                            }
                                            tiempoEspera1.stop();
                                            bandera1=0;
                                        }
                                       }}); 
                                        if (bandera == 0) {
                                            tiempoEspera1.start();
                                            bandera= 1;
                                        }
                                        if(contar==2)
                                         tiempoEspera1.restart();
                                        }
                                    
                                    
                                }
                            }
                        }
                    }
                });
            }
        }
        
        
        
        
        
        
        
        
        
        // funcionalidad del boton 
        botonJugar.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                Jugador = JOptionPane.showInputDialog(ventana,"Nombre del jugador", "Escribe aqui");
                while(Jugador == null || Jugador.compareTo("Escribe aqui")==0 || Jugador.compareTo("")==0){
                    Jugador = JOptionPane.showInputDialog(ventana,"Nombre del jugador", "Escribe aqui");
                }
                
                
                //System.out.println("Funciona");
                nombreJugador.setText("Jugador: " + Jugador );
                tiempo.start();
                panelPresentacion.setVisible(false);
                ventana.add(panelJuego);
                panelJuego.setVisible(true);
            }
        });
               
        
        
        
        
        ventana.add(panelPresentacion);
        ventana.setVisible(true);
    }
    
    public void nuemrosAletorios(){
       int acumular = 0 ; 
        
        for (int i = 0; i < 4; i++) 
            for (int j = 0; j < 5; j++) {
                mat [i][j]= 0;
                matAux [i][j]= 0; 
                }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                mat[i][j] = aleatorio.nextInt(10)+1;
                do{
                    acumular = 0;
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 5; l++) {
                            if(mat[i][j]==mat[k][l]){
                                acumular += 1;

                            }
                        } 
                    }
                    if(acumular == 3 )
                    {
                       mat[i][j] = aleatorio.nextInt(10)+1; 
                    }
                }while(acumular == 3);
                
            }
        }
        /*for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(mat[i][j]+"  ");
            }
            System.out.println("");
        }*/
    }
    
    
}
