/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1metaheuristicas;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Vector;

/**
 *
 * @author juanf
 */
public class P1Metaheuristicas {

    int Semilla;
    private static Integer tamano;
    private static Vector<Vector<Integer>> matrizFlujos;
    private static Vector<Vector<Integer>> matrizDistancias;
    /**
     * @param args the command line arguments
     * @description Es la función principal de nuestra clase 
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
       StringBuffer str=new StringBuffer();
       char opcion = '0';
       while (opcion != '4') {
           System.out.println("---------------Menú Practica 1 -------------");
           System.out.println("--- 1. Carga de datos ----------------------");
           System.out.println("--- 2. Seleccion de semilla ----------------");
           System.out.println("--- 3. Seleccion de algoritmo---------------");
           System.out.println("--- 4. Finalizar Programa ------------------");
           System.out.println("--------------------------------------------");
           System.out.println("Introduce opción: ");
           Reader entrada=new InputStreamReader(System.in);
           opcion=(char)entrada.read();
           
            switch (opcion){
                case '1':
                   System.out.println("Has seleccionado la opción de cargar datos");
                   muestraContenido("./archivos/ejemplo1.txt");
                   break;
                case '2':
                   System.out.println("Has seleccionado la opción de seleccionar semilla");
                   break;
                case '3':
                   System.out.println("Has seleccionado la opción de seleccionar algoritmo");
                   break;
                case '4':
                   System.out.println("Ha decidido salir del programa, muchisimas gracias por usarlo.");
                   System.out.println("Hasta pronto!");
                   break;
                default:
                   System.out.println("Esta opción no esta contemplada.");
                   System.out.println("Elija otra. ¡Gracias!.");
                   break;
           }
               
       }
    }
    
    
     /**
     * @param archivo (ruta del fichero que vamos a cargar)
     * @referenced https://geekytheory.com/como-leer-un-fichero-en-java
     * @description Le pasamos la ruta del fichero como parametro y va leyendo cada
     * fila y las va mostrando por la salida estandar del sistema y almacenando en el parametro
     * propio de la clase matrizDistancias, donde almacenamos
     * @throws FileNotFoundException
     * @throws IOException 
     */
    
    public static void muestraContenido(String archivo) throws FileNotFoundException, IOException {
      String cadena;
      FileReader f = new FileReader(archivo);
      Boolean primeravez=true;
        try (BufferedReader b = new BufferedReader(f)) {
            while((cadena = b.readLine())!=null) {
                // Cogemos el primer caracter para representar el tamaño de la matriz (AXA)
                if (primeravez) {
                    //System.out.println("tamaño de la matriz es:" + cadena + "X" + cadena);
                    tamano = new Integer(cadena);
                    primeravez = false;
                } else {
                    //A partir de hay nos queda coger los numeros de dicha matriz y meterlos en una EEDD, una matriz es buena
                    //Ya que conociendo el tamaño podemos separar sus filas y sus columnas
                    if (cadena.isEmpty()){
                        matrizFlujos.addElement(new Vector<>(tamano*tamano));
                        //System.out.println("Matriz " + columna + ":");
                    } else {
                        //System.out.println("Fila numero:" + fila);
                        String[] cadfila = cadena.split(" ");
                        for (int q=0; q<cadfila.length; q++){
                            //System.out.println("Numero " + q + ":" + cadfila[q]);
                            Integer num = new Integer(cadfila[q]);
                            //System.out.println("Insertar numero: " + num + " en la posicion " + columna + " " + fila);
                            matrizFlujos.get(matrizFlujos.size()-1).add(num);
                        }
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Excepcion leyendo fichero "+ archivo + ": " + e);
        }
    }
    

 }
