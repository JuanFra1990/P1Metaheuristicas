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
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author juanf
 */
public class P1Metaheuristicas {

    int Semilla;
    private static Integer tamano;
    
    private static final ArrayList<ArrayList<Integer>> matrizDistancias = new ArrayList<>();
    private static final ArrayList<ArrayList<Integer>> matrizFlujos = new ArrayList<>();
    /**
     * @param args the command line arguments
     * @description Es la función principal de nuestra clase 
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
       StringBuilder str=new StringBuilder();
       char opcion = '0';
       while (opcion != '4') {
           System.out.println("---------------Menú Practica 1 -------------");
           System.out.println("--- 1. Carga de datos ----------------------");
           System.out.println("--- 2. Seleccion de semilla ----------------");
           System.out.println("--- 3. Seleccion de algoritmo greedy--------");
           System.out.println("--- 4. Finalizar Programa ------------------");
          // System.out.println("--------------------------------------------");
           System.out.println("Introduce opción: ");
           Reader entrada=new InputStreamReader(System.in);
           opcion=(char)entrada.read();
           
            switch (opcion){
                case '1':
                   System.out.println("Has seleccionado la opción de cargar datos");
                   cargaDatos("./archivos/cnf02.dat");
                   break;
                case '2':
                   System.out.println("Has seleccionado la opción de seleccionar semilla");
                   break;
                case '3':
                    if(matrizDistancias.size() == 0 || matrizFlujos.size() == 0){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=(char)entradaIn.read();
                        if (opcion == 'S'){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el algoritmo, greedy.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    } 
                    
                    System.out.println("Has seleccionado la opción del Algoritmo Greedy");
                    ArrayList<Integer> valoresDistancia = new ArrayList<>(tamano);
                    ArrayList<Integer> valoresFlujo = new ArrayList<>(tamano);
                    calculoFilasGreedy(valoresDistancia, valoresFlujo);
                    ArrayList<Integer> vectorSolucion = AlgoritmoGreedy(valoresDistancia, valoresFlujo);

                    for (Integer item : vectorSolucion) {
                        System.out.println(item);
                    }
                   
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
    
    public static void cargaDatos(String archivo) throws FileNotFoundException, IOException {
      String cadena;
      FileReader f = new FileReader(archivo);
      Boolean primeravez=true;
        try (BufferedReader b = new BufferedReader(f)) {
            while((cadena = b.readLine())!=null) {
                // Cogemos el primer caracter para representar el tamaño de la matriz (AXA)
                if (primeravez) {
                    System.out.println("tamaño de la matriz es:" + cadena + "X" + cadena);
                    tamano = new Integer(cadena);
                    primeravez = false;
                } else {
                    //A partir de hay nos queda coger los numeros de cada matriz y meterlos en una EEDD, una matriz es buena
                    //Ya que conociendo el tamaño podemos separar sus filas y sus columnas
                    if (!cadena.isEmpty()){
                        if (matrizDistancias.isEmpty()){
                             matrizDistancias.add(new ArrayList<>(tamano));
                        }
                        
                        if (matrizFlujos.isEmpty()){
                               matrizFlujos.add(new ArrayList<>(tamano));
                        }
                        
                        if (matrizFlujos.size() <= tamano){
                            String[] cadfila = cadena.split(" ");
                            for (String cadfila1 : cadfila) {
                                Integer num = new Integer(cadfila1);
                                matrizFlujos.get(matrizFlujos.size()-1).add(num);
                            }
                            matrizFlujos.add(new ArrayList<>(tamano));
                            
                        } else {
                            String[] cadfila = cadena.split(" ");
                            for (String cadfila1 : cadfila) {
                                Integer num = new Integer(cadfila1);
                                matrizDistancias.get(matrizDistancias.size()-1).add(num);
                            }
                            matrizDistancias.add(new ArrayList<>(tamano));   
                        }
                    }
                }
            }
        } catch(Exception e) {
            System.out.println("Excepcion leyendo fichero "+ archivo + ": " + e);
        }
        System.out.println("Carga realizada de manera correcta");
        int tamDistancias = matrizDistancias.size() -1;
        int tamFlujos = matrizFlujos.size() -1;
        System.out.println("El tamaño de la matriz de flujos es: " + tamFlujos);
        System.out.println("El tamaño de la matriz de distancias es: " + tamDistancias);
    }
    
    /**
     * @param valoresDistancia ArrayList de entrada y salida para obtener la suma de las filas de la matriz Distancia
     * @param valoresFlujo ArrayList de entrada y salida para obtener la suma de las filas de la matriz Flujo
     * @description En esta funcion tan solo hacemos el calculo de las filas de las diferentes matrices, este es un apoyo
     * para nuestro algoritmo Greedy.
     */
    
    public static void calculoFilasGreedy(ArrayList<Integer> valoresDistancia, ArrayList<Integer> valoresFlujo ){
        Integer resultadoFilaDistancia = 0;
        
        for (int i=0; i < matrizDistancias.size(); i++){
            for (int j=0; j < matrizDistancias.get(i).size(); j++){
                resultadoFilaDistancia += matrizDistancias.get(i).get(j);
            }
            valoresDistancia.add(resultadoFilaDistancia);
            resultadoFilaDistancia = 0;
        }
        
        Integer resultadoFilaFlujos = 0;
        for (int i=0; i < matrizFlujos.size(); i++){
            for (int j=0; j < matrizFlujos.get(i).size(); j++){
                resultadoFilaFlujos += matrizFlujos.get(i).get(j);
            }
            valoresFlujo.add(resultadoFilaFlujos);
            resultadoFilaFlujos = 0;
        }
    }
    
    /**
     * @param valoresDistancia ArrayList de entrada para obtener la suma de las filas de la matriz Distancia
     * @param valoresFlujo ArrayList de entrada para obtener la suma de las filas de la matriz Flujo
     * @description En esta funcion el objetivo es obtener la solución greedy mediante nuestros parametros de entrada, calculados previamente
     * y con distintas variables apoyo como son vectorIndice1, vectorIndice2, posicion, flujoMaximo y distanciaMaxima, el calculo consiste en
     * recorrer cada Array y comparar tanto la distancia minima como el flujo maximo, de superar los umbrales marcados se actualiza el valor y
     * la posicion del Array y se almacena en nuestros vectoresIndice, que más tarde la unión de estos saldra nuestro vectorSolucion.
     */
    public static ArrayList<Integer> AlgoritmoGreedy(ArrayList<Integer> valoresDistancia, ArrayList<Integer> valoresFlujo){
        ArrayList<Integer> vectorSolucion = new ArrayList<>(tamano);
        ArrayList<Integer> vectorIndice1 = new ArrayList<>(tamano);
        ArrayList<Integer> vectorIndice2 = new ArrayList<>(tamano);
        Integer posicion = 0;
        Integer flujoMaximo = -10;
        Integer distanciaMinima = 999999999;
        for (int i = 0; i< tamano; i++){
            for (int j = 0; j< tamano; j++){
                if (valoresDistancia.get(i) < distanciaMinima){
                    distanciaMinima = valoresDistancia.get(i);
                    posicion = i;
                }
            }
            distanciaMinima = 999999999;
            valoresDistancia.set(i, 999999999);
            vectorIndice1.add(posicion);
        }
        
        for (int i = 0; i< tamano; i++){
            for (int j = 0; j< tamano; j++){
                if (valoresFlujo.get(i) > flujoMaximo){
                    flujoMaximo = valoresFlujo.get(i);
                    posicion = i;
                }
            }
            flujoMaximo = -10;
            valoresFlujo.set(i, -10);
            vectorIndice2.add(posicion);
        }
        
        for (int i = 0; i < tamano; i++) {
            System.out.println("En la posicion: " + vectorIndice1.get(i) + " El numero " + vectorIndice2.get(i));
            vectorSolucion.add(vectorIndice1.get(i), vectorIndice2.get(i));
        }
       
        return vectorSolucion;
    }
    
    //************************************** Búsqueda Local************************************** //
     /**
     * @param array ArrayList de entrada y salida donde vamos a cambiar las posiciones
     * @param origen Integer que indica la posicion del elemento origen
     * @param destino Integer que indica la posicion del elemento destino
     * @description Funcion de apoyo para la Búsqueda Local, que permite hacer intercambio de elementos de diferentes posiciones,
     * dado el Array y su posicion origen y destino.
     */
    
    public static void intercambioPosiciones(ArrayList<Integer> array, Integer origen, Integer destino ){
        Integer auxiliar = array.get(origen);
        array.set(origen, array.get(destino));
        array.set(destino, auxiliar);
    }
    
     /**
     * @param valoresDistancia ArrayList de entrada para obtener la suma de las filas de la matriz Distancia
     * @param valoresFlujo ArrayList de entrada para obtener la suma de las filas de la matriz Flujo
     * @description En esta funcion el objetivo es obtener la solución greedy mediante nuestros parametros de entrada, calculados previamente
     * y con distintas variables apoyo como son vectorIndice1, vectorIndice2, posicion, flujoMaximo y distanciaMaxima, el calculo consiste en
     * recorrer cada Array y comparar tanto la distancia minima como el flujo maximo, de superar los umbrales marcados se actualiza el valor y
     * la posicion del Array y se almacena en nuestros vectoresIndice, que más tarde la unión de estos saldra nuestro vectorSolucion.
     */
    public static ArrayList<Integer> AlgoritmoBusquedaLocal(ArrayList<Integer> valoresDistancia, ArrayList<Integer> valoresFlujo){
        ArrayList<Integer> array = new ArrayList<>();
        return array;
    }
    

 }
