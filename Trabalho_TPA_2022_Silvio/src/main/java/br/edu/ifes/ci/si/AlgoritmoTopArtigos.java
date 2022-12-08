package br.edu.ifes.ci.si;

import java.util.List;

public class AlgoritmoTopArtigos {

    private List<Aresta> referencias;

    private int qtdArtigos;

    public AlgoritmoTopArtigos(Digrafo D) {
        this.referencias = D.arestas();
        this.qtdArtigos = D.V();
    }

    public void listaTopArtigos(){
        int[] artigos = new int[qtdArtigos];

        for (int i = 0; i < qtdArtigos; i++){
            artigos[i] = 0;
        }
        for (Aresta referencia: referencias ){
            artigos[referencia.getV2().getArtigo()]++;
        }

        for(int k =0; k < qtdArtigos;){
            if(artigos[k] == 0){
                k++;
            }else{
                System.out.printf("%d: %d\n", k, artigos[k]);
                k++;
            }
        }
    }
}
