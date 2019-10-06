/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p1metaheuristicas;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author juanf
 */
public class AlgoritmoGreedy {
    Integer tamano;
    ArrayList<Integer> final1;
    ArrayList<Integer> final2;
    ArrayList<Integer> solucion;
    
    /**
     * @param t Integer que permite modificar el tamaño de nuestros Arrays
     * @description Esta función es un set de nuestra propiedad tamaño.
     */
    
    public void setTamano(Integer t){
        tamano = t;
    }
    
    /**
     * @param _final1 vector auxiliar
     * @param _final2 vector auxiliar
     * @param _solucion vector auxiliar
     * @description Permite cambiar el valor de las dos matrices de nuestro problema.
     */

    public void setArrays(ArrayList<Integer>  _final1, ArrayList<Integer> _final2, ArrayList<Integer> _solucion){
        final1 = _final1;
        final2 = _final2;
        solucion = _solucion;
    }
    
     /**
     * @param _final1 vector auxiliar
     * @description Permite cambiar el valor de las dos matrices de nuestro problema.
     */

    public void setFinal1(ArrayList<Integer> _final1){
        final1 = _final1;
    }
    
    /**
     * @param _final2 vector auxiliar
     * @description Permite cambiar el valor de las dos matrices de nuestro problema.
     */

    public void setFinal2(ArrayList<Integer> _final2){
        final2 = _final2;
    }
    
    /**
     * @param _solucion vector auxiliar
     * @description Permite cambiar el valor de las dos matrices de nuestro problema.
     */

    public void setSolucion(ArrayList<Integer> _solucion){
        solucion = _solucion;
    }
    
     /**
     * @return ArrayList obtener vector final1
     * @description Permite obtener el vector final1 de nuestro problema.
     */

    public ArrayList<Integer> getFinal1(){
        return final1;
    }
    
    /**
     * @return ArrayList obtener vector final2
     * @description Permite obtener el vector final2 de nuestro problema.
     */

    public ArrayList<Integer> getFinal2(){
        return final2;
    }
    
    /**
     * @return ArrayList obtener vector Solucion
     * @description Permite obtener el vector solucion de nuestro problema.
     */

    public ArrayList<Integer> getSolucion(){
        return solucion;
    }
    
    /**
     * @param md ArrayList de entrada y salida para obtener la suma de las filas de la matriz Distancia
     * @param mf ArrayList de entrada y salida para obtener la suma de las filas de la matriz Flujo
     * @description En esta funcion tan solo hacemos el calculo de las filas de las diferentes matrices, este es un apoyo
     * para nuestro algoritmo Greedy.
     */
    
    public void calculoFilasGreedy(ArrayList<ArrayList<Integer>> md, ArrayList<ArrayList<Integer>> mf){
        final1 = new ArrayList<>((int)tamano);
        final2 = new ArrayList<>((int)tamano);
        
        for(int i=0; i<tamano; i++){
            final1.add(i);
            final2.add(i);
        }
        
        int resultado = 0;
        
        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                resultado += md.get(i).get(j);
            }
            final1.set(i, resultado);
            resultado = 0;
        }

        for (int i = 0; i < tamano; i++) {
            for (int j = 0; j < tamano; j++) {
                resultado += mf.get(i).get(j);
            }
            final2.set(i, resultado);
            resultado = 0;
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
    public ArrayList<Integer> AlgoritmoGreedy() throws IOException{
        ArrayList<Integer> vectorSolucion = new ArrayList<>(tamano);
        ArrayList<Integer> vectorIndice1 = new ArrayList<>(tamano);
        ArrayList<Integer> vectorIndice2 = new ArrayList<>(tamano);
        Integer posicion = 0;
        Integer flujoMaximo = -10;
        Integer distanciaMinima = 999999999;
        //CREACIÓN DE VECTOR DE INDICES ORDENADOS DE MAYOR A MENOR POR POSICIÓN   (FLUJOS))
        for (int i = 0; i< tamano; i++){
            for (int j = 0; j< tamano; j++){
                if (final2.get(i) < distanciaMinima){
                    distanciaMinima = final2.get(i);
                    posicion = i;
                }
            }
            distanciaMinima = 999999999;
            final2.set(i, 999999999);
            vectorIndice1.add(posicion);
        }
        
        for (int i = 0; i< tamano; i++){
            for (int j = 0; j< tamano; j++){
                if (final1.get(i) > flujoMaximo){
                    flujoMaximo = final1.get(i);
                    posicion = i;
                }
            }
            flujoMaximo = -10;
            final1.set(i, -10);
            vectorIndice2.add(posicion);
        }
        
        for (int i = 0; i < tamano; i++) {
            LogText.LogWriter("En la posicion: " + vectorIndice1.get(i) + " El numero " + vectorIndice2.get(i));
            LogText.LogWriter("\r\n");
            System.out.println("En la posicion: " + vectorIndice1.get(i) + " El numero " + vectorIndice2.get(i));
            vectorSolucion.add(vectorIndice1.get(i), vectorIndice2.get(i));
        }
       
        return vectorSolucion;
    }
    
}
