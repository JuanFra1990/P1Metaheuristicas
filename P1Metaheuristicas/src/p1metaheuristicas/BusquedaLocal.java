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
public class BusquedaLocal {
    
    
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
