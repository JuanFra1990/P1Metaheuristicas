/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1metaheuristicas;

import java.util.ArrayList;

/**
 *
 * @author juanf
 */
public class AlgoritmoGreedy {
    int Semilla;
    Integer tamano;
    
    ArrayList<ArrayList<Integer>> matrizDistancias = new ArrayList<>();
    ArrayList<ArrayList<Integer>> matrizFlujos = new ArrayList<>();
    
    /**
     * @param t Integer que permite modificar el tamaño de nuestros Arrays
     * @description Esta función es un set de nuestra propiedad tamaño.
     */
    
    public void setTamano(Integer t){
        tamano = t;
    }
    
    /**
     * @param MD matriz distancias de nuestro problema
     * @param MF matriz flujo de nuestro problema
     * @description Permite cambiar el valor de las dos matrices de nuestro problema.
     */

    public void setArrays(ArrayList<ArrayList<Integer>>  MD, ArrayList<ArrayList<Integer>> MF){
        matrizDistancias = MD;
        matrizFlujos = MF;
    }
    
    /**
     * @param valoresDistancia ArrayList de entrada y salida para obtener la suma de las filas de la matriz Distancia
     * @param valoresFlujo ArrayList de entrada y salida para obtener la suma de las filas de la matriz Flujo
     * @description En esta funcion tan solo hacemos el calculo de las filas de las diferentes matrices, este es un apoyo
     * para nuestro algoritmo Greedy.
     */
    
    public void calculoFilasGreedy(ArrayList<Integer> valoresDistancia, ArrayList<Integer> valoresFlujo ){
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
    public ArrayList<Integer> AlgoritmoGreedy(ArrayList<Integer> valoresDistancia, ArrayList<Integer> valoresFlujo){
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
    
}
