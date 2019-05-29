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

/**
 *
 * @author juanf
 */
public class P1Metaheuristicas {

    int Semilla;
    private static Integer tamano;
    
    private static final ArrayList<ArrayList<Integer>> matrizDistancias = new ArrayList<>();
    private static final ArrayList<ArrayList<Integer>> matrizFlujos = new ArrayList<>();
    
    ArrayList<Integer> semillas = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     * @description Es la función principal de nuestra clase 
     * @throws java.io.IOException
     */
    public void main(String[] args) throws IOException {
       StringBuilder str=new StringBuilder();
       char opcion = '0';
       while (opcion != '7') {
           System.out.println("---------------Menú Practica 1 -----------------------------------------");
           System.out.println("--- 1. Carga de datos --------------------------------------------------");
           System.out.println("--- 2. Seleccion de semilla --------------------------------------------");
           System.out.println("--- 3. Seleccion de algoritmo greedy------------------------------------");
           System.out.println("--- 4. Seleccion de algoritmo busqueda Local (Algoritmo Greedy)---------");
           System.out.println("--- 5. Seleccion de algoritmo busqueda Local (Aleatoria)----------------");
           System.out.println("--- 6. Seleccion de algoritmo Enfriamiento Simulado --------------------");
           System.out.println("--- 7. Finalizar Programa ----------------------------------------------");
           System.out.println("------------------------------------------------------------------------");
           System.out.println("Introduce opción: ");
           Reader entrada=new InputStreamReader(System.in);
           opcion=(char)entrada.read();
           
            switch (opcion){
                case '1':
                   System.out.println("Has seleccionado la opción de cargar datos");
                   cargaDatos("./archivos/cnf02.dat");
                   break;
                case '2':
                   System.out.println("Has seleccionado la opción de seleccionar semillas");
                   System.out.println("¿Cuantas semillas desea introducir?");
                   Reader entradaNumeroSemillas=new InputStreamReader(System.in);
                   opcion=(char)entradaNumeroSemillas.read();
                   Integer contador = 1;
                   while ((int)opcion > 0){
                        System.out.println("Introduzca la semilla numero " + contador);
                        Reader entradaSemillas=new InputStreamReader(System.in);
                        opcion=(char)entradaSemillas.read();
                        semillas.add((int)opcion);
                   }
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
                    AlgoritmoGreedy ag = new AlgoritmoGreedy();
                    ag.setTamano(tamano);
                    ag.setArrays(matrizDistancias, matrizFlujos);
                    ag.calculoFilasGreedy(valoresDistancia, valoresFlujo);
                    ArrayList<Integer> vectorSolucion = ag.AlgoritmoGreedy(valoresDistancia, valoresFlujo);                   
                   break;
                 case '4':
                    if(matrizDistancias.size() == 0 || matrizFlujos.size() == 0){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=(char)entradaIn.read();
                        if (opcion == 'S'){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el algoritmo de búsqueda local.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }  
                    
                   System.out.println("Has seleccionado la opción del Algoritmo Busqueda Local a traves de la solución Greedy");
                    ArrayList<Integer> valoresDistanciaBL = new ArrayList<>(tamano);
                    ArrayList<Integer> valoresFlujoBL = new ArrayList<>(tamano);
                    AlgoritmoGreedy alg = new AlgoritmoGreedy();
                    alg.setTamano(tamano);
                    alg.setArrays(matrizDistancias, matrizFlujos);
                    alg.calculoFilasGreedy(valoresDistanciaBL, valoresFlujoBL);
                    ArrayList<Integer> vectorSolucionBL = alg.AlgoritmoGreedy(valoresDistanciaBL, valoresFlujoBL);
                    
                    BusquedaLocal busquedaLocal = new BusquedaLocal();
                    busquedaLocal.setSolucionAnterior(vectorSolucionBL);
                    Integer coste = busquedaLocal.AlgoritmoBusquedaLocal();
                    System.out.println(busquedaLocal.ConversorArrayString());
                    System.out.println("Coste: " + coste);
                   break;
                case '5':
                    ArrayList<Integer> Aleatorio = new ArrayList<>();
                    if (semillas.size() == 0){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=(char)entradaNumeroSemillasR.read();
                        Integer contadorR = 1;
                        while ((int)opcion > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=(char)entradaSemillas.read();
                            semillas.add((int)opcion);
                        }
                    }
                    Integer c = 0;
                    while (c < semillas.size()) {
                        String semi = Integer.toString(semillas.get(c));
                        System.out.println("Búsqueda Local respecto a la semilla " + semillas.get(c));
                        Aleatorio.clear();
                        BusquedaLocal busquedaLocalAleatoria = new BusquedaLocal();
                        HerramientasAuxiliares herramientasAuxiliares = new HerramientasAuxiliares();
                        herramientasAuxiliares.cargarVector(Aleatorio);
                        busquedaLocalAleatoria.setHerramientas(herramientasAuxiliares);
                        busquedaLocalAleatoria.setSolucionAnterior(Aleatorio);
                        Integer CosteUltimaSolucion = busquedaLocalAleatoria.AlgoritmoBusquedaLocal();
                        System.out.println("Coste: " + CosteUltimaSolucion);
                        c++;
                    }
                case '6':
                   System.out.println("El algoritmo de enfriamiento simulado aún no esta implementado.");
                   System.out.println("Elija otra. ¡Gracias!.");
                   break;
                case '7':
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
 }
