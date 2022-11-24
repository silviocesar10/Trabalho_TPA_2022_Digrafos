/*******************************************************************************
 *  Compilação:        javac DigrafoPonderado.java
 *  Execução:          java DigrafoPonderado dados.txt
 *  Dependências:      Aresta.java
 *  Arquivos de dados: DigrafoPonderado1.txt
 *  Link dos dados:    https://drive.google.com/open?id=0B3q56TwNCeXoc08yZ0JHZW1aZWs
 *
 *  Um dígrafo ponderado implementado utilizando listas de adjacências.
 *
 *  % java Digrafo DigrafoPonderado1.txt
 *  8 15
 *  0: 0-4 0,38000  0-2 0,26000  
 *  1: 1-3 0,29000  
 *  2: 2-7 0,34000  
 *  3: 3-6 0,52000  
 *  4: 4-5 0,35000  4-7 0,37000  
 *  5: 5-4 0,35000  5-7 0,28000  5-1 0,32000  
 *  6: 6-2 0,40000  6-0 0,58000  6-4 0,93000  
 *  7: 7-5 0,28000  7-3 0,39000  
 *
 ******************************************************************************/

package br.edu.ifes.ci.si;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe implementa a representação do dígrafo ponderado com lista de adjacências.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/44sp/">Section 4.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class DigrafoPonderado {
    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;          // número de vértices no dígrafo
    private int A;                // número de arestas no dígrafo
    private List<Aresta>[] adj;   // adj[v1] = lista de adjacência do vértice v1
    
    /**
     * Inicializa um dígrafo com V vertices e 0 arestas.
     * @param  V o número de vértices
     * @throws IllegalArgumentException se V < 0
     */
    public DigrafoPonderado(int V) {
        if (V < 0) throw new IllegalArgumentException("Número de vértices no dígrafo deve ser não negativo");
        this.V = V;
        this.A = 0;
        adj = new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<>();
    }


    /**  
     * Inicializa um dígrafo à partir de um arquivo de dados.
     * O formato é o número de vértices V e o número de arestas A
     * seguido por pares de pares de vértices.
     * @param  in o arquivo de entrada de dados
     * @throws IndexOutOfBoundsException se os pontos finais de qualquer borda estão fora da área prescrita
     * @throws IllegalArgumentException se o número de vértices ou arestas for negativo
     */
    public DigrafoPonderado(In in) {
        this(in.readInt());
        int A = in.readInt();
        if (A < 0) throw new IllegalArgumentException("Número de arestas deve ser não negativo");
        //esse for deve fazer a leitura do arquivo, ainda nao foi modificado para o padrao novo
        for (int i = 0; i < A; i++) {
            int v1 = in.readInt();
            int v2 = in.readInt();
            if (v1 < 0 || v1 >= V) throw new IndexOutOfBoundsException("vértice " + v1 + " não está entre 0 e " + (V-1));
            if (v2 < 0 || v2 >= V) throw new IndexOutOfBoundsException("vértice " + v2 + " não está entre 0 e " + (V-1));
            double peso = in.readDouble();
            //no lugar dos 0 deveria estar o numero que representa o autor, esse for precisa ser refeito
            addAresta(new Aresta(new Vertice(v1,0), new Vertice(v2,0), peso));
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
    public void addAresta(Aresta a) {
        int v1 = a.getV1().getVertice();
        int v2 = a.getV2().getVertice();
        validaVertice(v1);
        validaVertice(v2);
        adj[v1].add(a);
        A++;
    }


    /**
     * Retorna as arestas incidentes no vértice v1.
     * @param  v o vértice
     * @return as arestas incidentes no vértice v1 como um Iterable
     * @throws IndexOutOfBoundsException caso v não seja 0 <= v < V
     */
    public List<Aresta> adj(int v) {
        validaVertice(v);
        return adj[v];
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
            for (Aresta a : adj[v]) {
                s.append(a + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    /**
     * Testa a classe DigrafoPonderado.
     */
    /*
    public static void main(String[] args) {
        In in = new In(args[0]);
        DigrafoPonderado G = new DigrafoPonderado(in);
        System.out.println(G);
    }
    */

}
