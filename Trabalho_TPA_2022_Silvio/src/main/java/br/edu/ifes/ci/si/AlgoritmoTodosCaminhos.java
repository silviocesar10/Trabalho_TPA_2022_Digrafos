package br.edu.ifes.ci.si;

public class AlgoritmoTodosCaminhos {
    private boolean[] noCaminho;        // Vetor responsável por armazenar se o vertice x esta no caminho entre vo e vd
    private Pilha<Integer> caminho;     // Armazena o caminho atual entre vo e vd
    private int numeroDeCaminhos;       // Armazena a quantidade de caminhos entre vo e vd

    //Construtor da classe: recebe o numero de vertices para instanciar o vetor que valida os caminhos
    public AlgoritmoTodosCaminhos(int v) {
        noCaminho = new boolean[v];
        caminho   = new Pilha<Integer>();
               
    }
    //Função que inicia a busca pelos caminhos existentes
    public void printCaminhos(Digrafo G, int v, int vd){
        System.out.printf("b) Todos os caminhos simples entre %d e %d \n", v, vd);
        dfs(G, v, vd);
        imprimeQtdCaminhos();
    }
    // usando a ideia de exploração do método DFS
    private void dfs(Digrafo G, int v, int vd) {

        //Inclui o vertice v no caminho entre vo e vd
        caminho.empilha(v);
        noCaminho[v] = true;

        // Verifica se o algoritmo chegou ao vertice destino, Caso positivo, imprime o caminho e incrementa o total de caminhos
        if (v == vd) {
            imprimeCaminhoAtual();
            numeroDeCaminhos++;
        }


        else {
            //Percorre os vertices adjacentes ao vertice atual
            for (Aresta a : G.adj(v)) {
                int x = a.getV2().getArtigo();
                //verifica se o vertice x ja está no cominho entre vo e vd, caso negativo, inicia nova exploração, com o metodo DFS partindo de x
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
