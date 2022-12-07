/**
 * ****************************************************************************
 *  Compilação:  javac Aresta.java
 *  Execução:    java Aresta
 *
 *  Aresta Ponderada ou Não.
 *
 *****************************************************************************
 */
package br.edu.ifes.ci.si;

/**
 * Cada aresta é composta por dois números (que representam os vértices) e um peso.
 */
public class Aresta implements Comparable<Aresta> {

    private final Vertice v1;
    private final Vertice v2;
    private final double peso;
 

    /**
     * Inicializa uma aresta (sem peso) entre vértices.
     * @param v1 vértice 1 (origem)
     * @param v2 vértice 2 (destino)
     * @throws IndexOutOfBoundsException se o vértice 1 ou vértice 2 forem negativos
     * @throws IllegalArgumentException se o peso for um valor não numérico
     */
    public Aresta(Vertice v1, Vertice v2) {
        if (v1.getArtigo() < 0) {
            throw new IndexOutOfBoundsException("Vértice deve ser um inteiro não negativo");
        }
        if (v2.getArtigo() < 0) {
            throw new IndexOutOfBoundsException("Vértice deve ser um inteiro não negativo");
        }
        this.v1 = v1;
        this.v2 = v2;
        this.peso = 0;
    }
    
    /**
     * Inicializa uma aresta entre vértices e atribui seu peso.
     * @param v1 vértice 1 (origem)
     * @param v2 vértice 2 (destino)
     * @param peso peso da aresta
     * @throws IndexOutOfBoundsException se o vértice 1 ou vértice 2 forem negativos
     * @throws IllegalArgumentException se o peso for um valor não numérico
     */
    public Aresta(Vertice v1, Vertice v2, double peso) {
        if (v1.getArtigo() < 0) {
            throw new IndexOutOfBoundsException("Vértice deve ser um inteiro não negativo");
        }
        if (v2.getArtigo() < 0) {
            throw new IndexOutOfBoundsException("Vértice deve ser um inteiro não negativo");
        }
        if (Double.isNaN(peso)) {
            throw new IllegalArgumentException("Peso é não numérico");
        }
        this.v1 = v1;
        this.v2 = v2;
        this.peso = peso;
    }
    
    public Vertice getV1() {
        return v1;
    }

    public Vertice getV2() {
        return v2;
    }

    /**
     * Retorna o peso desta aresta.
     * @return o peso desta aresta
     */
    public double peso() {
        return peso;
    }

    /**
     * Retorna um vértice qualquer desta aresta (origem desta aresta).
     * @return vértice 1 desta aresta
     */
    public Vertice umVertice() {
        return getV1();
    }

    /**
     * Retorna o outro vértice da aresta. Ou seja, o vértice diferente do recebido como parâmetro.
     * @param vertice um vértice qualquer desta aresta
     * @return o vértice diferente do recebido como parâmetro.
     * @throws IllegalArgumentException se o vértico do parâmetro não for um dos vértices da aresta
     */
    public int outroVertice(int vertice) {
        if (vertice == getV1().getArtigo()) {
            return getV2().getArtigo();
        } else if (vertice == getV2().getArtigo()) {
            return getV1().getArtigo();
        } else {
            throw new IllegalArgumentException("Vértice inválido");
        }
    }

    /**
     * Compara o peso da aresta atual com o peso da aresta passada como parâmetro.
     * @param aquela a outra aresta
     * @return um inteiro negativo, zero ou um inteiro positivo, dependendo da comparação
     */
    @Override
    public int compareTo(Aresta aquela) {
        return Double.compare(this.peso, aquela.peso);
    }

    /**
     * Retorna a representação String da aresta
     * @return uma representação String da aresta
     */
    public String toString() {
        if(this.peso != 0)
            return String.format("%d-%d %.5f", getV1().getArtigo(), getV2().getArtigo(), peso);
        else
            return String.format("%d", getV2().getArtigo());
    }

    /**
     * Teste de unidade da classe Aresta.
     */
    /*public static void main(String[] args) {
        Aresta a = new Aresta(12, 34, 5.67);
        System.out.println(a);
    }*/

    
}
