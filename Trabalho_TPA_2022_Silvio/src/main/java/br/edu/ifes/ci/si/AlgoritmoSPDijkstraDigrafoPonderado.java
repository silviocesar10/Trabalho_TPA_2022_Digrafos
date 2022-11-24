/**
 * ****************************************************************************
 *  Compilação:         javac AlgoritmoSPDijkstraDigrafoPonderado.java
 *  Execução:           java AlgoritmoSPDijkstraDigrafoPonderado dados.txt vo
 *  Dependências:       DigrafoPonderado.java FilaPrioridadeMinIndex.java Pilha.java Aresta.java
 *  Arquivo de dados:   DigrafoPonderado1.txt
 *  Link dos dados:
 *
 *  Algoritmo Dijkstra. Calcula a árvore que representa o menor caminho de um vértice de origem para todos os outros.
 *  Assume todos os pesos das arestas como não negativos.
 *
 *  % java AlgoritmoSPDijkstraDigrafoPonderado DigrafoPonderado1.txt 0
 *  0 to 0 (0.00)
 *  0 to 1 (1.05)  0->4  0.38   4->5  0.35   5->1  0.32
 *  0 to 2 (0.26)  0->2  0.26
 *  0 to 3 (0.99)  0->2  0.26   2->7  0.34   7->3  0.39
 *  0 to 4 (0.38)  0->4  0.38
 *  0 to 5 (0.73)  0->4  0.38   4->5  0.35
 *  0 to 6 (1.51)  0->2  0.26   2->7  0.34   7->3  0.39   3->6  0.52
 *  0 to 7 (0.60)  0->2  0.26   2->7  0.34
 *****************************************************************************
 */
package br.edu.ifes.ci.si;

/**
 * Esta classe implementa o caminho mínimo utilizando o algoritmo de Dijkstra.
 * Para documentação adicional, acesse:
 * <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a>
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class AlgoritmoSPDijkstraDigrafoPonderado {

    private double[] distanciaPara;          // distanciaPara[v1] = menor distância entre de um vértice origem e v1: vo->v1
    private Aresta[] arestaPara;           // arestaPara[v1] = última aresta no menor caminho vo->v1
    private FilaPrioridadeMinIndex<Double> filaPrioridade; // fila de prioridade dos vértices

    /**
     * Calcula o caminho mais curto a partir de um vértice origem para cada um dos outros vértices do dígrafo ponderado
     * @param G o dígrafo ponderado
     * @param vo o vértice origem
     * @throws IllegalArgumentException se a aresta tiver peso negativo
     */
    public AlgoritmoSPDijkstraDigrafoPonderado(DigrafoPonderado G, int vo) {
        for (Aresta a : G.arestas()) {
            if (a.peso() < 0) {
                throw new IllegalArgumentException("aresta " + a + " tem peso negativo");
            }
        }

        distanciaPara = new double[G.V()];
        arestaPara = new Aresta[G.V()];
        for (int v = 0; v < G.V(); v++) {
            distanciaPara[v] = Double.POSITIVE_INFINITY;
        }
        distanciaPara[vo] = 0.0;

        // relaxa vertices em ordem de acordo com a distância de s
        filaPrioridade = new FilaPrioridadeMinIndex<Double>(G.V());
        filaPrioridade.insere(vo, distanciaPara[vo]);
        while (!filaPrioridade.isEmpty()) {
            int v = filaPrioridade.removeMin();
            for (Aresta a : G.adj(v)) {
                relaxa(a);
            }
        }

        // checa as condições de otimização
        assert checa(G, vo);
    }

    // 
    /**
     * Relaxa aresta e atualiza a filaPrioridade, se alterada
     * @param a a aresta
     */
    private void relaxa(Aresta a) {
        int v1 = a.getV1().getVertice(), v2 = a.getV2().getVertice();
        if (distanciaPara[v2] > distanciaPara[v1] + a.peso()) {
            distanciaPara[v2] = distanciaPara[v1] + a.peso();
            arestaPara[v2] = a;
            if (filaPrioridade.contem(v2)) {
                filaPrioridade.diminuiChave(v2, distanciaPara[v2]);
            } else {
                filaPrioridade.insere(v2, distanciaPara[v2]);
            }
        }
    }

    /**
     * Retorna o tamanho do menor caminho do vértice ordem para o vértice v
     * @param v o vértice de destino
     * @return retorna o tamanho do menor caminho do vértice origem para o vértice v, ou Double.POSITIVE_INFINITYse não existir caminho
     */
    public double distanciaPara(int v) {
        return distanciaPara[v];
    }

    /**
     * Retorna verdadeiro se existe um caminho do vértice origem para o vértice v
     * @param v o vértice destino
     * @return verdadeiro se existe um caminho do vértice origem para o vértice v, ou falso, caso contrário
     */
    public boolean temCaminhoPara(int v) {
        return distanciaPara[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Retorna o menor caminho do vértice origem para o vértice v
     * @param v o vértice destino
     * @return o menor caminho do vértice origem para o vértice v como um iterable de arestas e null, se não existir caminho
     */
    public Iterable<Aresta> caminhoPara(int v) {
        if (!temCaminhoPara(v)) {
            return null;
        }
        Pilha<Aresta> path = new Pilha<Aresta>();
        for (Aresta a = arestaPara[v]; a != null; a = arestaPara[a.getV1().getVertice()]) {
            path.empilha(a);
        }
        return path;
    }

    // checa optimality conditions:
    // (i)  for all arestas e:            distanciaPara[e.to()] <= distanciaPara[e.from()] + e.peso()
    // (ii) for all edge e on the SPT: distanciaPara[e.to()] == distanciaPara[e.from()] + e.peso()
    /**
     * Checar as condições de otimização
     * @param G o dígrafo ponderado
     * @param vo o vértice origem
     * @return verdadeiro caso sejam satisfeitas as condições
     */
    private boolean checa(DigrafoPonderado G, int vo) {

        // checa se as arestas possuem peso negativa
        for (Aresta a : G.arestas()) {
            if (a.peso() < 0) {
                System.err.println("detectada aresta com peso negativo");
                return false;
            }
        }

        // checa se distanciaPara[vo] e arestaPara[vo] são consistentes
        if (distanciaPara[vo] != 0.0 || arestaPara[vo] != null) {
            System.err.println("distanciaPara[vo] e arestaPara[vo] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == vo) {
                continue;
            }
            if (arestaPara[v] == null && distanciaPara[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distanciaPara[] e arestaPara[] inconsistente");
                return false;
            }
        }

        // checa se todas as arestas a = v1->v2 satisfazem distanciaPara[v2] <= distanciaPara[v1] + a.peso()
        for (int v = 0; v < G.V(); v++) {
            for (Aresta a : G.adj(v)) {
                int v2 = a.getV2().getVertice();
                if (distanciaPara[v] + a.peso() < distanciaPara[v2]) {
                    System.err.println("aresta " + a + " não relaxada");
                    return false;
                }
            }
        }

        // chaca se todas as arestas a = v1->v2 no SP satisfazem distanciaPara[v2] == distanciaPara[v1] + a.peso()
        for (int v2 = 0; v2 < G.V(); v2++) {
            if (arestaPara[v2] == null) {
                continue;
            }
            Aresta a = arestaPara[v2];
            int v1 = a.getV1().getVertice();
            if (v2 != a.getV2().getVertice()) {
                return false;
            }
            if (distanciaPara[v1] + a.peso() != distanciaPara[v2]) {
                System.err.println("edge " + a + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    /**
     * Testa a classe AlgoritmoSPDijkstraDigrafoPonderado
     */
    /*
    public static void main(String[] args) {
        In in = new In(args[0]);
        DigrafoPonderado G = new DigrafoPonderado(in);
        int vo = Integer.parseInt(args[1]);

        System.out.println(G);
        
        // calcula o menor caminho
        AlgoritmoSPDijkstraDigrafoPonderado sp = new AlgoritmoSPDijkstraDigrafoPonderado(G, vo);

        // imprimi o menor caminho
        for (int t = 0; t < G.V(); t++) {
            if (sp.temCaminhoPara(t)) {
                System.out.printf("%d para %d (%.2f)  ", vo, t, sp.distanciaPara(t));
                for (Aresta a : sp.caminhoPara(t)) {
                    System.out.print(a + "   ");
                }
                System.out.println();
            } else {
                System.out.printf("%d para %d         sem caminho\n", vo, t);
            }
        }
    }
*/

}
