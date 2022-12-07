/*******************************************************************************
 *  Compilação:        javac Digrafo.java
 *  Execução:          java Digrafo dados.txt
 *  Dependências:      Aresta.java
 *  Arquivos de dados: Digrafo1.txt
 *  Link dos dados:    https://drive.google.com/open?id=0B3q56TwNCeXoc2tlbllCRmo1MTQ
 *
 *  Um dígrafo implementado utilizando listas de adjacências.
 *
 *  % java Digrafo Digrafo1.txt
 *  13 22
 *  0: 1  5  
 *  1: 
 *  2: 3  0  
 *  3: 2  5  
 *  4: 2  3  
 *  5: 4  
 *  6: 0  8  4  9  
 *  7: 9  6  
 *  8: 6  
 *  9: 10  11  
 *  10: 12  
 *  11: 12  4  
 *  12: 9  
 *
 ******************************************************************************/

package br.edu.ifes.ci.si;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe implementa a representação do dígrafo com lista de adjacências.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/42digraph">Section 4.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Digrafo {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;         // número de vértices no dígrafo
    private int A;               // número de arestas no dígrafo
    private List<Aresta>[] referencias;  // adj[v1] = lista de adjacência do vértice v1
    private ArrayList<Vertice> artigos; // Lista de vertices do digrafo
    
    /**
     * Inicializa um dígrafo com V vertices e 0 arestas.
     * @param  V o número de vértices
     * @throws IllegalArgumentException se V < 0
     */
    public Digrafo(int V) {
        if (V < 0) throw new IllegalArgumentException("Número de vértices no dígrafo deve ser não negativo");
        this.V = V;
        this.A = 0;
        referencias = new ArrayList[V];
        for (int v = 0; v < V; v++)
            referencias[v] = new ArrayList<>();
    }


    /**  
     * Inicializa um dígrafo à partir de um arquivo de dados.
     * O formato é o número de vértices V e o número de arestas A
     * seguido por pares de pares de vértices.
     * @param  in o arquivo de entrada de dados
     * @throws IndexOutOfBoundsException se os pontos finais de qualquer borda estão fora da área prescrita
     * @throws IllegalArgumentException se o número de vértices ou arestas for negativo
     */
    public Digrafo(In in) {
        this(in.readInt());
        int A = in.readInt();
        if (A < 0) throw new IllegalArgumentException("Número de arestas deve ser não negativo");
        for(int line = 0; line < this.V; line++){
            int artigo = in.readInt();
            int autor = in.readInt();
            this.artigos.add(new Vertice(artigo, autor));
        }
        for (int i = 0; i < A; i++) {
            int v1 = in.readInt();
            int v2 = in.readInt();
            if (v1 < 0 || v1 >= V) throw new IndexOutOfBoundsException("vértice " + v1 + " não está entre 0 e " + (V-1));
            if (v2 < 0 || v2 >= V) throw new IndexOutOfBoundsException("vértice " + v2 + " não está entre 0 e " + (V-1));
            addAresta(v1, v2);//Peso igual a zero para aresta (dígrafo não ponderado)
        }
    }

    /**
     * Retorna o número de vértices do dígrafo.
     * @return o número de vértices do dígrafo
     */
    public int V() {
        return V;
    }

    /**
     * Retorna o número de arestas do dígrafo.
     * @return o número de arestas do dígrafo
     */
    public int A() {
        return A;
    }

    /**
     * Valida vértice do dígrafo.
     * @throws IndexOutOfBoundsException caso v não seja 0 <= v < V
     */
    private void validaVertice(int v) {
        if (v < 0 || v >= V)
            throw new IndexOutOfBoundsException("vértice " + v + " não está entre 0 e " + (V-1));
    }

    /**
     * Adiciona aresta direcionada a no dígrafo.
     * @param  a a aresta
     * @throws IndexOutOfBoundsException caso extremidades não estejam entre 0 e V-1
     */
    public void addAresta(int v1, int v2) {
        validaVertice(v1);
        validaVertice(v2);
        Aresta a = new Aresta(getVertice(v1), getVertice(v2));
        referencias[v1].add(a);

    }


    /**
     * Retorna as arestas incidentes no vértice v1.
     * @param  v o vértice
     * @return as arestas incidentes no vértice v1 como um Iterable
     * @throws IndexOutOfBoundsException caso v não seja 0 <= v < V
     */
    public List<Aresta> adj(int v) {
        validaVertice(v);
        return referencias[v];
    }
    
    public ArrayList<Vertice> getLv(){
        return this.artigos;
    }


    /**
     * Retorna todas as arestas neste dígrafo.
     * @return todas as arestas neste dígrafo, como um Iterable
     */
    public List<Aresta> arestas() {
        List<Aresta> lista = new ArrayList();
        for (int v = 0; v < V; v++) {
            for (Aresta a : adj(v)) {
                lista.add(a);
            }
        }
        return lista;
    } 

    /**
     * Retorna uma representação String deste dígrafo.
     * @return uma representação String deste dígrafo
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + A + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Aresta a : referencias[v]) {
                s.append(a + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     *
     * @param artigo: inteiro que identifica um artigo.
     * @return o vertice que representa o artigo identificado.
     */
    public Vertice getVertice(int artigo){
        for (Vertice v: this.artigos){
            if(artigo == v.getArtigo()){
                return v;
            }
        }
        return null;
    }

}