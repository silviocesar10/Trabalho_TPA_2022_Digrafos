package br.edu.ifes.ci.si;

public class AlgoritmoTodosCaminhos {
    private boolean[] noCaminho;        // vértices no caminho atual
    private Pilha<Integer> caminho;     // o caminho atual
    private int numeroDeCaminhos;       // número de caminhos simples

    // mostra todos os caminhos simples de vo (vértice origem) para vd (vértice destino) - usando DFS
    public AlgoritmoTodosCaminhos(Digrafo G, int vo, int vd) {
        noCaminho = new boolean[G.V()];
        caminho   = new Pilha<Integer>();
        System.out.printf("b) Todos os caminhos simples entre %d e %d \n", vo, vd);
        dfs(G, vo, vd);
    }

    // usando a ideia de exploração do método DFS
    private void dfs(Digrafo G, int v, int vd) {

        // adiciona v ao caminho atual
        caminho.empilha(v);
        noCaminho[v] = true;

        // encontrado caminho de v para vd (vértice destino)
        if (v == vd) {
            imprimeCaminhoAtual();
            numeroDeCaminhos++;
        }

        // considerar todos os vizinhos que continuariam o caminho
        else {
            for (Aresta a : G.adj(v)) {
                int x = a.getV2().getArtigo();
                if (!noCaminho[x])
                    dfs(G, x, vd);
            }
        }

        // feita a exploração de v, então o remove do caminho
        caminho.desempilha();
        noCaminho[v] = false;
    }

    // esta implementação simplesmente imprime o caminho
    private void imprimeCaminhoAtual() {
        Pilha<Integer> pilhaInvertida = new Pilha<Integer>();
        for (int v : caminho)
            pilhaInvertida.empilha(v);
        if (pilhaInvertida.tamanho() >= 1)
            System.out.print(pilhaInvertida.desempilha());
        while (!pilhaInvertida.isEmpty())
            System.out.print("->" + pilhaInvertida.desempilha());
        System.out.println();
    }

    // retorna o número de caminhos simples entre vo (vértice origem) e vd (vértice destino)
    public int numeroDeCaminhos() {
        return numeroDeCaminhos;
    }

    public void imprimeQtdCaminhos(){
        System.out.println("# caminhos = " + numeroDeCaminhos);
        System.out.println();
    }


}
