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

    public static void main(String[] args) {
        In in = new In(args[0]);
        int vo = Integer.parseInt(args[1]);
        //System.out.println("Hello World!");
        menu(in, vo);
    }
    
    public static void menu(In in, int vo)
    {
        Scanner sc = new Scanner(System.in);
        int op = 0;
        Digrafo G = new Digrafo(in);
        do{
            printMenu();
            op = sc.nextInt();
            switch(op){
                case 1: 
                    AlgoritmoBFSDigrafoMenorCaminho algoritmoBFS = new AlgoritmoBFSDigrafoMenorCaminho(G, vo);
                    for (int v = 0; v < G.V(); v++) 
                    {
                        if (algoritmoBFS.temCaminhoPara(v)) {
                            System.out.printf("%d para %d (%d):  ", vo, v, algoritmoBFS.distanciaPara(v));
                            for (int x : algoritmoBFS.caminhoPara(v)) 
                            {
                                if (x == vo) {
                                    System.out.print(x);
                                } else {
                                    System.out.print("->" + x);
                                }
                            }
                            System.out.println();
                        } else {
                            System.out.printf("%d para %d (-):  não conectado\n", vo, v);
                        }
                    }
                    break;
                case 4:
                    AlgoritimoTopAutores ata = new AlgoritimoTopAutores(G);
                    ata.printaTopAutores();
                    break;
                case 5:
                    System.out.println("Encerrendo o Sistemas!!!");
                    op =5;
                    break;
            }
        }while(op != 5);
}
    
   
    /**
     *
     */
    public static void printMenu(){
        System.out.println("Digite 1 para encontrar o menor caminho entre dois artigos");
        System.out.println("Digite 2 para encontrar todos os caminhos entre dois artigos");
        System.out.println("Digite 3 para o numero de citaçĩes de cada artigo");
        System.out.println("Digite 3 para o numero de citaçĩes de cada autor");
        System.out.println("Digite 5 para encerrar a aplicação");
        System.out.println("\n Digite a opção desejada!!");
    }
}
