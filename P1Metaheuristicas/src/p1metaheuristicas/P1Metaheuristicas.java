/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1metaheuristicas;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author juanf
 */
public class P1Metaheuristicas {

    int Semilla;
    /**
     * @param args the command line arguments
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
 }
