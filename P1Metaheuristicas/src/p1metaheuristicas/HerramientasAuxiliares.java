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
public class HerramientasAuxiliares {
    
    /**
     * @param solucion Array de Integer que contiene la solución
     * @param tamano Integer que indica el tamaño del array de la solucion y de las matrices
     * @param matrizDistancias Matriz de integer que contiene la informacion de distancias de nuestro problema
     * @param matrizFlujos Matriz de integer que contiene la información de flujo de nuestro problema
     * @description Esta función permite realizar el calculo del coste de creación de dicha solución.
     * @return coste devuelve un Integer con el coste de la solución
     */
    
    public Integer costeTotal(ArrayList<Integer> solucion, Integer tamano,  
            ArrayList<ArrayList<Integer>> matrizDistancias, ArrayList<ArrayList<Integer>> matrizFlujos) {
        Integer coste = 0;
        for (Integer i = 0; i < tamano; i++) {
            for (Integer j = 0; j < tamano; j++) {
                if (i != j)
                    coste += matrizFlujos.get(i).get(j) * matrizDistancias.get(solucion.get(i)).get(solucion.get(j));
            }
        }
        return coste;
    }
}
