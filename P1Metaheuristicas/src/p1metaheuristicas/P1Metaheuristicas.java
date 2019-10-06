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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanf
 */
public class P1Metaheuristicas {

    private static Integer tamano;
    
    private static ArrayList<ArrayList<Integer>> matrizDistancias = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> matrizFlujos = new ArrayList<>();
    
    private static ArrayList<Integer> semillas = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     * @description Es la función principal de nuestra clase 
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
      LogText.init("Practica1Metaheuristica");
      StringBuilder str=new StringBuilder();
      long startTime;
      long endTime;
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
                   LogText.LogWriter("Has seleccionado la opción de cargar datos");
                   LogText.LogWriter("\r\n");
                   System.out.println("Has seleccionado la opción de cargar datos");
                   String fichero = seleccionFichero();
                   LogText.LogWriter("Has seleccionado el fichero " + fichero);
                   LogText.LogWriter("\r\n");
                   System.out.println("Has seleccionado el fichero " + fichero);
                   cargaDatos(fichero);
                   if (fichero.contains("9")){
                        matrizFlujos.remove(0);
                        ArrayList<ArrayList<Integer>> mf = new ArrayList<>(255);
                        for (int i=0; i<255; i++){
                            mf.add(matrizFlujos.get(i));
                        }
                        matrizFlujos.clear();
                        matrizFlujos = new ArrayList<>(255);
                        matrizFlujos.addAll(mf);
                    }
                   break;
                case '2':
                   LogText.LogWriter("Has seleccionado la opción de seleccionar semillas");
                   LogText.LogWriter("\r\n");
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
                   LogText.LogWriter("Las semillas seleccionadas son: ");
                   LogText.LogWriter("\r\n");
                   for (int i = 0; i<semillas.size(); i++){
                       LogText.LogWriter(semillas.get(i).toString());
                       LogText.LogWriter("\r\n");
                   }
                   
                   break;
                case '3':
                    if(matrizDistancias.isEmpty() || matrizFlujos.isEmpty()){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=(char)entradaIn.read();
                        if (opcion == 'S'){
                            fichero = seleccionFichero();
                            LogText.LogWriter("Has seleccionado el fichero " + fichero);
                            LogText.LogWriter("\r\n");
                            System.out.println("Has seleccionado el fichero " + fichero);
                            cargaDatos(fichero);
                        } else {
                            System.out.println("Sin datos no podemos lanzar el algoritmo, greedy.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }
                    LogText.LogWriter("Has seleccionado la opción del Algoritmo Greedy");
                    LogText.LogWriter("\r\n");
                    System.out.println("Has seleccionado la opción del Algoritmo Greedy");
                    startTime = System.currentTimeMillis();
                    AlgoritmoGreedy ag = new AlgoritmoGreedy();
                    HerramientasAuxiliares herramientas = new HerramientasAuxiliares();
                    herramientas.setTamano(tamano);
                    herramientas.setMatrizDistancias(matrizDistancias);
                    herramientas.setMatrizFlujos(matrizFlujos);
                    ag.setTamano(tamano);
                    ag.calculoFilasGreedy(matrizDistancias,matrizFlujos);
                    ArrayList<Integer> vectorSolucion = ag.AlgoritmoGreedy();
                    endTime = System.currentTimeMillis() - startTime;
                    LogText.LogWriter("Ha tardado " + endTime + " ms");
                    LogText.LogWriter("\r\n");
                    System.out.println("Ha tardado " + endTime + " ms");
                    LogText.LogWriter("Tiene de coste " + herramientas.costeTotal(vectorSolucion)+ ".");
                    LogText.LogWriter("\r\n");
                    System.out.println("Tiene de coste " + herramientas.costeTotal(vectorSolucion)+ ".");
                   break;
                 case '4':
                    if(matrizDistancias.size() == 0 || matrizFlujos.size() == 0){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=(char)entradaIn.read();
                        if (opcion == 'S'){
                             fichero = seleccionFichero();
                            LogText.LogWriter("Has seleccionado el fichero " + fichero);
                            LogText.LogWriter("\r\n");
                            System.out.println("Has seleccionado el fichero " + fichero);
                            cargaDatos(fichero);
                        } else {
                            System.out.println("Sin datos no podemos lanzar el algoritmo de búsqueda local.");
                            System.out.println("Lo sentimos");
                            break;
                        }
                    }  
                    
                    System.out.println("Has seleccionado la opción del Algoritmo Busqueda Local a traves de la solución Greedy");
                    startTime = System.currentTimeMillis();
                    ArrayList<Integer> valoresDistanciaBL = new ArrayList<>(tamano);
                    ArrayList<Integer> valoresFlujoBL = new ArrayList<>(tamano);
                    AlgoritmoGreedy alg = new AlgoritmoGreedy();
                    alg.setTamano(tamano);
                    alg.calculoFilasGreedy(matrizDistancias, matrizFlujos);
                    ArrayList<Integer> vectorSolucionBL = alg.AlgoritmoGreedy();
                    
                    BusquedaLocal busquedaLocal = new BusquedaLocal();
                    HerramientasAuxiliares herramientasAuxiliaresBL = new HerramientasAuxiliares();
                    herramientasAuxiliaresBL.setTamano(tamano);
                    herramientasAuxiliaresBL.setMatrizDistancias(matrizDistancias);
                    herramientasAuxiliaresBL.setMatrizFlujos(matrizFlujos);
                    
                    
                    busquedaLocal.setHerramientas(herramientasAuxiliaresBL);
                    busquedaLocal.setSolucionAnterior(vectorSolucionBL);
                    Integer coste = busquedaLocal.AlgoritmoBusquedaLocal();
                    System.out.println("Coste: " + coste);
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                   break;
                case '5':
                    ArrayList<Integer> Aleatorio = new ArrayList<>();
                    
                     if(matrizDistancias.size() == 0 || matrizFlujos.size() == 0){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=(char)entradaIn.read();
                        if (opcion == 'S'){
                             fichero = seleccionFichero();
                            LogText.LogWriter("Has seleccionado el fichero " + fichero);
                            LogText.LogWriter("\r\n");
                            System.out.println("Has seleccionado el fichero " + fichero);
                            cargaDatos(fichero);
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
                    startTime = System.currentTimeMillis();
                    while (c < semillas.size()) {
                        String semi = Integer.toString(semillas.get(c));
                        System.out.println("Búsqueda Local respecto a la semilla " + semillas.get(c));
                        Aleatorio.clear();
                        BusquedaLocal busquedaLocalAleatoria = new BusquedaLocal();
                        HerramientasAuxiliares herramientasAuxiliares = new HerramientasAuxiliares();
                        herramientasAuxiliares.setTamano(tamano);
                        herramientasAuxiliares.setMatrizDistancias(matrizDistancias);
                        herramientasAuxiliares.setMatrizFlujos(matrizFlujos);
                        herramientasAuxiliares.cargarVector(Aleatorio);
                        busquedaLocalAleatoria.setHerramientas(herramientasAuxiliares);
                        busquedaLocalAleatoria.setSolucionAnterior(Aleatorio);
                        Integer CosteUltimaSolucion = busquedaLocalAleatoria.AlgoritmoBusquedaLocal();
                        System.out.println("Coste: " + CosteUltimaSolucion);
                        c++;
                    }
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                    break;
                case '6':
                    ArrayList<Integer> AleatorioSem = new ArrayList<>();
                   if(matrizDistancias.size() == 0 || matrizFlujos.size() == 0){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=(char)entradaIn.read();
                        if (opcion == 'S'){
                             fichero = seleccionFichero();
                            LogText.LogWriter("Has seleccionado el fichero " + fichero);
                            LogText.LogWriter("\r\n");
                            System.out.println("Has seleccionado el fichero " + fichero);
                            cargaDatos(fichero);
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
                    
                    Integer IndexSemilla = (int) (0 + ((semillas.size()-1) - (0) * Math.random()));
                    ArrayList<Integer> AleatorioEG = new ArrayList<>();
                    startTime = System.currentTimeMillis();
                    for (int i = 0; i < semillas.size(); i++) {
                        AleatorioEG.clear();
                        String semi = Integer.toString(semillas.get(i));
                        System.out.println("Enfriamiento simulado Geometrico respecto a la semilla " + semillas.get(i));
                        AleatorioSem.clear();
                        EnfriamientoSimulado enfriamientoGeometrico = new EnfriamientoSimulado();
                        HerramientasAuxiliares herramientasAuxiliares = new HerramientasAuxiliares();
                        herramientasAuxiliares.setTamano(tamano);
                        herramientasAuxiliares.cargarVector(AleatorioSem);
                        herramientasAuxiliares.setMatrizDistancias(matrizDistancias);
                        herramientasAuxiliares.setMatrizFlujos(matrizFlujos);
                        herramientasAuxiliares.cargarVector(AleatorioEG);
                        enfriamientoGeometrico.setHerramientas(herramientasAuxiliares);
                        enfriamientoGeometrico.setSolucionActual(AleatorioEG);
                        Integer auxiliar=enfriamientoGeometrico.EnfriamientoSimulado(true);
                        String CosteUltimaSolucion=Integer.toString(auxiliar);
                        System.out.println("Coste: " + CosteUltimaSolucion);
                        IndexSemilla++;
                    }
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
                   break;
                case '7':
                   ArrayList<Integer> AleatorioSemBoltz = new ArrayList<>();
                   if(matrizDistancias.size() == 0 || matrizFlujos.size() == 0){
                        System.out.println("Los datos no estan cargados aún, ¿Desea cargarlos? (Responde con S o N)");
                        Reader entradaIn=new InputStreamReader(System.in);
                        opcion=(char)entradaIn.read();
                        if (opcion == 'S'){
                             fichero = seleccionFichero();
                            LogText.LogWriter("Has seleccionado el fichero " + fichero);
                            LogText.LogWriter("\r\n");
                            System.out.println("Has seleccionado el fichero " + fichero);
                            cargaDatos(fichero);
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
                    
                    Integer IndexsemillaBoltz = (int) (0 + ((semillas.size()-1) - (0) * Math.random()));
                    ArrayList<Integer> AleatorioEB = new ArrayList<>();
                    startTime = System.currentTimeMillis();
                    for (int i = 0; i < semillas.size(); i++) {
                        AleatorioEB.clear();
                        String semi = Integer.toString(semillas.get(i));
                         System.out.println("Enfriamiento simulado Boltzmann respecto a la semilla " + semillas.get(i));
                        AleatorioSemBoltz.clear();
                        EnfriamientoSimulado enfriamientoBoltz = new EnfriamientoSimulado();
                        HerramientasAuxiliares herramientasAuxiliaresBoltz = new HerramientasAuxiliares();
                        herramientasAuxiliaresBoltz.setTamano(tamano);
                        herramientasAuxiliaresBoltz.cargarVector(AleatorioSemBoltz);
                        herramientasAuxiliaresBoltz.setMatrizDistancias(matrizDistancias);
                        herramientasAuxiliaresBoltz.setMatrizFlujos(matrizFlujos);
                        herramientasAuxiliaresBoltz.cargarVector(AleatorioEB);
                        enfriamientoBoltz.setHerramientas(herramientasAuxiliaresBoltz);
                        enfriamientoBoltz.setSolucionActual(AleatorioEB);
                        Integer auxiliar=enfriamientoBoltz.EnfriamientoSimulado(false);
                        String CosteUltimaSolucion=Integer.toString(auxiliar);
                        System.out.println("Coste: " + CosteUltimaSolucion);
                        IndexsemillaBoltz++;
                    }
                    endTime = System.currentTimeMillis() - startTime;
                    System.out.println("Ha tardado " + endTime + " ms");
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
      matrizDistancias.clear();
      matrizFlujos.clear();
      String cadena;
      FileReader f = new FileReader(archivo);
      Boolean primeravez=true;
        try (BufferedReader b = new BufferedReader(f)) {
            while((cadena = b.readLine())!=null) {
                // Cogemos el primer caracter para representar el tamaño de la matriz (AXA)
                if (primeravez) {
                    System.out.println("tamaño de la matriz es:" + cadena + "X" + cadena);
                    tamano = new Integer(cadena);
                    matrizDistancias = new ArrayList<>(tamano);
                    matrizFlujos = new ArrayList<>(tamano);
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
    
    public static String seleccionFichero(){
        System.out.println("¿Que fichero desea seleccionar? (Seleccione un numero del 1 - 10)");
        String ruta = "";
        
        int opcion = -1;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader bf = new BufferedReader (isr);
        try {
             while (opcion < 0 || opcion >10){
                String lineaTeclado = bf.readLine();
                opcion = Integer.parseInt(lineaTeclado);
                System.out.println(opcion);
                if (opcion > 0 && opcion < 4 || opcion > 5 && opcion < 10){
                   ruta = "./archivos/cnf0" + opcion + ".dat";
                } else if (opcion >= 4 && opcion <= 5){
                   ruta = "./archivos/cnf0" + opcion + "dat.sec";
                } else if(opcion == 10){
                     ruta = "./archivos/cnf" + opcion + ".dat";
                } else {
                    ruta = "No es posible leer esta opcion, seleccione un numero valido";
                }
             }   
         } catch (IOException ex) {
             Logger.getLogger(P1Metaheuristicas.class.getName()).log(Level.SEVERE, null, ex);
         }
        return ruta;
    }
 }
