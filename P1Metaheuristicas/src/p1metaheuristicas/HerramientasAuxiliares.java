/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1metaheuristicas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

/**
 *
 * @author juanf
 */
public class HerramientasAuxiliares {
    private static ArrayList<ArrayList<Integer>> matrizFlujos;
    private static ArrayList<ArrayList<Integer>> matrizDistancias;
    Integer tamano;
    
    /**
     * @description Esta función permite almacenar el valor del tamano.
     */
    
    public void setTamano(Integer Ntamano) {
        tamano = Ntamano;
    }
     /**
     * @description Esta función permite devolver el tamaño de las matrices.
     * @return tamano devuelve un Integer con el tamaño de las matrices de nuestro problema
     */
    
    public Integer getTamano() {
        return tamano;
    }
    
    /**
     * @param NmatrizFlujos nueva matriz de flujos para cambiar valor
     * @description Esta función permite almacenar el valor de la matrizFlujos.
     */
    
    public void setMatrizFlujos(ArrayList<ArrayList<Integer>> NmatrizFlujos) {
        matrizFlujos = NmatrizFlujos;
    }
    
    /**
     * @param NmatrizDistancias nueva matriz de distancias para cambiar valor
     * @description Esta función permite almacenar el valor de la matrizFlujos.
     */
    
    public void setMatrizDistancias(ArrayList<ArrayList<Integer>> NmatrizDistancias) {
        matrizDistancias = NmatrizDistancias;
    }
    
    /**
     * @description Esta función permite mostrar el contenido de la matriz de distancias por la salida estandar.
     */
    
    public void mostrarMatrizDistancias() {
        System.out.println("-------------Matriz de distancias ------");
        for (Integer i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                System.out.println(matrizDistancias.get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * @description Esta función permite mostrar el contenido de la matriz de flujos por la salida estandar.
     */
    public void mostrarMatrizFlujos() {
        System.out.println("-------------Matriz de flujos ------");
        for (Integer i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                System.out.println(matrizFlujos.get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * @param solucion Array de Integer que contiene la solución
     * @description Esta función permite realizar el calculo del coste de creación de dicha solución.
     * @return coste devuelve un Integer con el coste de la solución
     */
    
    public Integer costeTotal(ArrayList<Integer> Solucion) {
        int coste = 0;
        for (int i = 0; i < tamano-1; i++) {
            for (int j = 0; j < tamano-1; j++) {
                if (i != j)
                    coste += matrizFlujos.get(i).get(j) * matrizDistancias.get(Solucion.get(i)).get(Solucion.get(j));
            }
        }
        return coste;
    }
    
    /**
     * @param Solucion Array de Integer que contiene una solución a nuestro problema 
     * @param posicionA Integer que contiene una posición de la matriz
     * @param posicionB Integer que contiene una posición de la matriz
     * @param coste Integer que almacena el coste de la solucion de nuestro problema
     * @description Esta función recorre el contenido de las matrices de distancia y flujo y junto a la solución permite hallar el coste.
     * @return coste devuelve un Integer con el coste de la solución actual
     */
    public Integer costeFactorial(ArrayList<Integer> Solucion, Integer posicionA, Integer posicionB, Integer coste) {
        for (Integer i = 0; i < tamano; i++) {
            if (i != posicionA && i != posicionB) {
                coste += matrizFlujos.get(posicionA).get(i)*(matrizDistancias.get(Solucion.get(posicionB)).get(Solucion.get(i)) 
                        - matrizDistancias.get(Solucion.get(posicionA)).get(Solucion.get(i)) 
                        + matrizFlujos.get(posicionB).get(i)*(matrizDistancias.get(Solucion.get(posicionA)).get(Solucion.get(i)) 
                        - matrizDistancias.get(Solucion.get(posicionB)).get(Solucion.get(i))))
                        + matrizFlujos.get(i).get(posicionA)*(matrizDistancias.get(Solucion.get(i)).get(Solucion.get(posicionB)) 
                        - matrizDistancias.get(Solucion.get(i)).get(Solucion.get(posicionA))) 
                        + matrizFlujos.get(i).get(posicionB)*(matrizDistancias.get(Solucion.get(i)).get(Solucion.get(posicionA)) 
                        - matrizDistancias.get(Solucion.get(i)).get(Solucion.get(posicionB)));
            }
        }
        return coste;
    }
    
    
    /**
     * @param array Array de Integer 
     * @description Esta función rellena de valores auxiliares dentro de un rango nuestro array.
     */
    public void cargarVector(ArrayList<Integer> array) {
        for (int i = 0; i < tamano; i++) {
            array.add(i);
        }
        Integer auxiliar;
        for (int i = tamano - 1; i > 0; i--) {
            auxiliar = (int) (0 + (i - (0) * Math.random()));
            //intercambio de elementos
            Collections.swap(array,i,auxiliar);
        }
    }
}
