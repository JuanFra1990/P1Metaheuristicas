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

    private static Integer tamano;
    
    private static final ArrayList<ArrayList<Integer>> matrizDistancias = new ArrayList<>();
    private static final ArrayList<ArrayList<Integer>> matrizFlujos = new ArrayList<>();
    
    private static ArrayList<Integer> semillas = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     * @description Es la función principal de nuestra clase 
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
       StringBuilder str=new StringBuilder();
       char opcion = '0';
       while (opcion != '8') {
           System.out.println("---------------Menú Practica 1 -----------------------------------------");
           System.out.println("--- 1. Carga de datos --------------------------------------------------");
           System.out.println("--- 2. Seleccion de semilla --------------------------------------------");
           System.out.println("--- 3. Seleccion de algoritmo greedy------------------------------------");
           System.out.println("--- 4. Seleccion de algoritmo busqueda Local (Algoritmo Greedy)---------");
           System.out.println("--- 5. Seleccion de algoritmo busqueda Local (Aleatoria)----------------");
           System.out.println("--- 6. Seleccion de algoritmo Enfriamiento Simulado Geometrico----------");
           System.out.println("--- 7. Seleccion de algoritmo Enfriamiento Simulado Boltzmann-----------");
           System.out.println("--- 8. Finalizar Programa ----------------------------------------------");
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
                   Integer tamanoSemilla = Character.getNumericValue(opcion);
                   Integer contador = 1;
                   while (tamanoSemilla > 0){
                        System.out.println("Introduzca la semilla numero " + contador);
                        Reader entradaSemillas=new InputStreamReader(System.in);
                        opcion=(char)entradaSemillas.read();
                        semillas.add(Character.getNumericValue(opcion));
                        tamanoSemilla--;
                        contador++;
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
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=(char)entradaNumeroSemillasR.read();
                        Integer tamanoSemillaBL = Character.getNumericValue(opcion);
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaBL > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=(char)entradaSemillas.read();
                            semillas.add(Character.getNumericValue(opcion));
                            tamanoSemillaBL--;
                            contadorR++;
                        }
                        
                        if (opcion == 0){
                            System.out.println("Sin semilla no podemos lanzar el algoritmo de búsqueda local.");
                            System.out.println("Lo sentimos");
                            break;
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
                    ArrayList<Integer> AleatorioSem = new ArrayList<>();
                   if(matrizDistancias.size() == 0 || matrizFlujos.size() == 0){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=(char)entradaIn.read();
                        if (opcion == 'S'){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el algoritmo de Enfriamiento simulado Geometrico.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }  
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=(char)entradaNumeroSemillasR.read();
                        Integer tamanoSemillaES = Character.getNumericValue(opcion);
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaES > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=(char)entradaSemillas.read();
                            semillas.add(Character.getNumericValue(opcion));
                            tamanoSemillaES--;
                            contadorR++;
                        }
                        
                        if (opcion == 0){
                            System.out.println("Sin semilla no podemos lanzar el algoritmo de Enfriamiento simulado Geometrico.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    Integer IndexSemilla = (0 + ((semillas.size()-1)-(0) * (int)Math.random()));
                    for (int i = 0; i < semillas.size(); i++) {
                        
                        String semi = Integer.toString(semillas.get(IndexSemilla));
                        System.out.println("Enfriamiento simulado Geometrico respecto a la semilla " + semillas.get(IndexSemilla));
                        AleatorioSem.clear();
                        EnfriamientoSimulado enfriamientoGeometrico = new EnfriamientoSimulado();
                        HerramientasAuxiliares herramientasAuxiliares = new HerramientasAuxiliares();
                        herramientasAuxiliares.cargarVector(AleatorioSem);
                        enfriamientoGeometrico.setHerramientas(herramientasAuxiliares);
                        
                        Integer auxiliar=enfriamientoGeometrico.EnfriamientoSimulado(true);
                        String CosteUltimaSolucion=Integer.toString(auxiliar);
                        System.out.println("Coste: " + CosteUltimaSolucion);
                        IndexSemilla++;
                    }
                   break;
                case '7':
                   ArrayList<Integer> AleatorioSemBoltz = new ArrayList<>();
                   if(matrizDistancias.size() == 0 || matrizFlujos.size() == 0){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=(char)entradaIn.read();
                        if (opcion == 'S'){
                            cargaDatos("./archivos/cnf02.dat");
                        } else {
                            System.out.println("Sin datos no podemos lanzar el algoritmo de Enfriamiento simulado Boltzmann.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }  
                    
                    if (semillas.isEmpty()){
                        System.out.println("¿Cuantas semillas desea introducir?");
                        Reader entradaNumeroSemillasR=new InputStreamReader(System.in);
                        opcion=(char)entradaNumeroSemillasR.read();
                        Integer tamanoSemillaESB =  Character.getNumericValue(opcion);;
                        Integer contadorR = 0;
                        while ((int)tamanoSemillaESB > 0){
                            System.out.println("Introduzca la semilla numero " + contadorR);
                            Reader entradaSemillas=new InputStreamReader(System.in);
                            opcion=(char)entradaSemillas.read();
                            semillas.add(Character.getNumericValue(opcion));
                            tamanoSemillaESB--;
                            contadorR++;
                        }
                        
                        if (opcion == 0){
                            System.out.println("Sin semilla no podemos lanzar el algoritmo de Enfriamiento simulado Boltzmann.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    
                    Integer IndexsemillaBoltz = (0 + ((semillas.size()-1)-(0) * (int)Math.random()));
                    for (int i = 0; i < semillas.size(); i++) {
                        String semi = Integer.toString(semillas.get(IndexsemillaBoltz));
                        System.out.println("Enfriamiento simulado Boltzmann respecto a la semilla " + semillas.get(IndexsemillaBoltz));
                        AleatorioSemBoltz.clear();
                        EnfriamientoSimulado enfriamientoGeometrico = new EnfriamientoSimulado();
                        HerramientasAuxiliares herramientasAuxiliares = new HerramientasAuxiliares();
                        herramientasAuxiliares.cargarVector(AleatorioSemBoltz);
                              
                        Integer auxiliar=enfriamientoGeometrico.EnfriamientoSimulado(false);
                        String CosteUltimaSolucion=Integer.toString(auxiliar);
                        System.out.println("Coste: " + CosteUltimaSolucion);
                       IndexsemillaBoltz++;
                    }
    
                   break;
                case '8':
                    System.out.println("Muchas gracias por usar nuestra aplicación.");
                    System.out.println("Te esperamos pronto. ¡Gracias!.");
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
