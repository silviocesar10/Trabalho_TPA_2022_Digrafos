/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifes.ci.si;

import java.util.ArrayList;

/**
 *
 * @author silvio
 */
public class AlgoritimoTopAutores {
    private ArrayList<Vertice> listaV;
    private int numV;
    
    public AlgoritimoTopAutores(Digrafo Dg){
        this.listaV = Dg.getLv();
        this.numV = Dg.V();
    }
    
    public void printaTopAutores(){
        int[] autores = new int[numV];
        //preenche o vetor de autores com 0 inicialmente
        for(int j =0; j < numV; j++){
            autores[j] = 0;
        }

        /**
         * Como você referencia os autores pelos indices de autores[], ao interar a lista de artigos, você soma um artigo ao autor.
          */
        for(Vertice v : listaV){
            autores[v.getAutor()]++;
        }
        
        //for para printar o vetor de autores atualizados
        for(int k =0; k < numV;){
            if(autores[k] == 0){
                k++;
            }else{
                System.out.printf("%d: %d", k, autores[k]);
                k++;
            }
        }
    }
}
