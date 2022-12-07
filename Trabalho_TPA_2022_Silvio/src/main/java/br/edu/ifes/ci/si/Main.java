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

    }

    public static void topArtigos(Digrafo D){

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

        int funcao = 0;

        Digrafo D = new Digrafo(in);
        do{
            funcao = printMenu(1);
            switch(funcao){
                case 1:
                    int artigoOrigem = printMenu(2);
                    int artigoDestino = printMenu(3);
                    menorQuantidadeArtigosLidos(D, artigoOrigem, artigoDestino);
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
                System.out.println("Digite 3 para o numero de citaçĩes de cada artigo");
                System.out.println("Digite 3 para o numero de citaçĩes de cada autor");
                System.out.println("Digite 5 para encerrar a aplicação");
                System.out.println("\n Digite a opção desejada!!");
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
