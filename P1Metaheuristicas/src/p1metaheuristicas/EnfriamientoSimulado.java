/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1metaheuristicas;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author juanf
 */
public class EnfriamientoSimulado {
    private HerramientasAuxiliares herramientas;
    private ArrayList<Integer> solucionActual;
    private ArrayList<Integer> solucionESimulado; 
    
    /**
     * @param SolucionActual ArrayList que contiene la solucion actual
     * @description Esta funcion nos permite modificar el valor de nuestro parametro solucionActual.
     */
    
    public void setSolucionActual(ArrayList<Integer> SolucionActual){
        solucionActual = SolucionActual;
    }
    
     /**
     * @param SolucionESimulado ArrayList que contiene la solucion del enfriamiento simulado
     * @description Esta funcion nos permite modificar el valor de nuestro parametro SolucionESimulado.
     */
    
    public void setSolucionESimulado(ArrayList<Integer> SolucionESimulado){
        solucionESimulado = SolucionESimulado;
    }
    
     /**
     * @param herramientasAux Clase Herramientas que nos permitirá hacer el calculo del Enfriamiento Simulado
     * @description Esta funcion nos permite modificar el valor de nuestro parametro herramientas.*/
    
    public void setHerramientas(HerramientasAuxiliares herramientasAux){
        herramientas = herramientasAux;
    }
    
    /**
     * 
     * @param num Double al cual queremos calcularle el logaritmo
     * @param base Int para saber en que base vamos a realizar el logaritmo al parametro num
     * @description Funcion para realizar el logaritmo en base X a un numero 
     */
    private static Double log(double num, int base) {
      return (Math.log10(num) / Math.log10(base));
    }
    
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
     * 
     * @param tipoTemperatura este parametro es utilizado para saber qué tipo de temperatura es
     * si es temperatura geometrica el parametro tiene valor true
     * si el parametro tiene valor false es de Boltzmann
     * @description Funcion para calcular el enfriamiento simulado mediante un algoritmo
     * de búsqueda por entornos con un criterio probabilístico 
     * de aceptación de soluciones basados en la Termodinámica
     */
    public Integer EnfriamientoSimulado(boolean tipoTemperatura){
        Integer coste = herramientas.costeTotal(solucionActual);
        Integer mejorCoste = coste;
        Integer tamanoSolActual = solucionActual.size();
        solucionESimulado = solucionActual;
        ArrayList<Integer> dlb = new ArrayList<>(tamanoSolActual);
        Integer contador = 0;
        boolean mejora = true;
        Double temperatura = 1.5*coste;
        Double temperaturaInicial = temperatura;
        Double temperaturaFinal = temperatura*0.05;
        Integer i = 0;
        Integer j = 0;
        double rand = new Random().nextDouble();
        
        while (temperatura > temperaturaFinal && contador < 50000){
            mejora = false;
            contador++;
            if (i==tamanoSolActual || j==tamanoSolActual){
                i=0;
                j=0;
            }
            
            for (i=0; i<tamanoSolActual && !mejora; i++){
                if (dlb.get(i) == 0){
                    boolean parada = false;
                    for (j=0;j<tamanoSolActual && !mejora; j++){
                        if(i!=j){
                            Integer costeFactorial = herramientas.costeFactorial(solucionActual, i, j, coste);
                            Integer diferencia = costeFactorial - coste;
                            Double enfriamiento = (-diferencia/temperatura);
                            if((diferencia<0) || (rand <= Math.exp(enfriamiento))){
                                intercambioPosiciones(solucionActual,i,j);
                                coste = costeFactorial;
                                dlb.set(i, 0);
                                dlb.set(j, 0);
                                parada=true;
                                mejora=true;
                                if (costeFactorial < mejorCoste){
                                    mejorCoste = coste;
                                    solucionESimulado = solucionActual;
                                }
                            }
                        }
                    }
                    
                    if (parada == false){
                        dlb.set(i, 1);
                    }
                }
            }
            
            if(tipoTemperatura == true){
                temperatura = temperatura * 0.90;
            }else{
                temperatura = (temperaturaInicial/(1+log(contador,10)));
            }
            
        }
        return coste;
    }
    
     /**
     * @description Esta funcion nos permite convertir todos los elementos de un array en un string.
     */
    public String ConversorArrayString(){
        String Palabra = "";
        for (Integer i = 0; i < herramientas.getTamano(); i++) {
            String auxiliar= Integer.toString(solucionESimulado.get(i));
            Palabra+=" "+auxiliar;
        }
        return Palabra;
    }
}
