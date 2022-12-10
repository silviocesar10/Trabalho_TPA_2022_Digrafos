/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package br.edu.ifes.ci.si;

import java.util.Scanner;

/**
 *
 * @author silvio
 */
public class Main {

    public static void menorQuantidadeArtigosLidos(Digrafo D, int artigoOrigem, int artigoDestino){

        AlgoritmoBFSDigrafoMenorCaminho BFS = new AlgoritmoBFSDigrafoMenorCaminho(D, artigoOrigem);
        BFS.printCaminho(artigoOrigem, artigoDestino);
    }

    public static void todosCaminhos(Digrafo D, int artigoOrigem, int artigoDestino){
        AlgoritmoTodosCaminhos caminhos = new AlgoritmoTodosCaminhos(D.V());
        caminhos.printCaminhos(D, artigoOrigem, artigoDestino);


    }

    public static void topArtigos(Digrafo D){
        AlgoritmoTopArtigos topArtigos = new AlgoritmoTopArtigos(D);
        topArtigos.listaTopArtigos();
    }

    public static void topAutores(Digrafo D){
        AlgoritimoTopAutores ata = new AlgoritimoTopAutores(D);
        ata.printaTopAutores();
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        menu(in);
    }
    
    public static void menu(In in){

        int funcao;

        Digrafo D = new Digrafo(in);
        int artigoOrigem = 0;
        int artigoDestino = 0;
        do{
            funcao = printMenu(0);
            if(funcao == 1 || funcao == 2){
                artigoOrigem = printMenu(1);
                artigoDestino = printMenu(2);
            }
            switch(funcao){
                case 1:
                    menorQuantidadeArtigosLidos(D, artigoOrigem, artigoDestino);
			        break;
                case 2:
                    todosCaminhos(D, artigoOrigem, artigoDestino);
                    break;
                case 3:
                    topArtigos(D);
                    break;
                case 4:
                    topAutores(D);
                    break;
                case 5:
                    System.out.println("Encerrendo o Sistemas!!!");
                    break;
            }
        }while(funcao != 5);
}
    
   
    /**
     *O Método "printMenu(int idTela)" recebe como parametro um inteiro que faz referencia a tela que sera exiida ao usuário
     * e retorna um inteiro, digitado pelo usuário, de acordo com a tela exibida.
     */
    public static int printMenu(int idTela){
        Scanner sc = new Scanner(System.in);
        int opcao = 0;
        switch (idTela){
            case 0:
                System.out.println("Digite 1 para encontrar o menor caminho entre dois artigos");
                System.out.println("Digite 2 para encontrar todos os caminhos entre dois artigos");
                System.out.println("Digite 3 para o numero de citacoes de cada artigo");
                System.out.println("Digite 4 para o numero de citacoes de cada autor");
                System.out.println("Digite 5 para encerrar a aplicacaoo");
                System.out.print("\nDigite a opção desejada: ");
                opcao = sc.nextInt();
                break;

            case 1:
                System.out.println("Digite o ID do artigo origem:");
                opcao = sc.nextInt();
                break;

            case 2:
                System.out.println("Digite o ID do artigo destino:");
                opcao = sc.nextInt();
                break;
        }
        return opcao;

    }
}
