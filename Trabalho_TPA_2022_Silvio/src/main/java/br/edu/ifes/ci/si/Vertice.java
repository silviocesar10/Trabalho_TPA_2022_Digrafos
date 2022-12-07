/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifes.ci.si;



/**
 *
 * @author silvio
 */
public class Vertice {
    private int artigo;
    private int autor;
   
    
    
    public Vertice(int artigo, int autor) {
        this.artigo = artigo;
        this.autor = autor;
    }

    
    public int getArtigo() {
        return artigo;
    }

    public void setArtigo(int artigo) {
        this.artigo = artigo;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }
    
    
    
}
