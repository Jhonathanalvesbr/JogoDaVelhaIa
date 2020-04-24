package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Arvore {

    private int altura;
    public int qntAltura = 4;
    public int qntNo = 6;
    private ArrayList<Arvore> filho = new ArrayList();
    private int game[][] = new int[3][3];
    public int jogador;
    public int c;
    public int t;
    private int valor;
    private int pos;
    private boolean vitoria;


    public Arvore(int game[][], int jog) {
        for (int x = 0; x < 9; x++) {
            this.game[x / 3][x % 3] = game[x / 3][x % 3];
        }
        jogador = jog;
    }

    public Arvore geraArvore(Arvore no) {
        Arvore aux;
        if (no.vitoria == true) {
//            no.ma = no.altura;
            return no;
        } else if (no.altura >= qntAltura) {
            return no;
        } else {
            int count = 0;
            for (int x = 0; x < 9; x++) {
                if (game[x / 3][x % 3] == 0) {
                    count++;
                }
            }
            if (no.qntNo > count) {
                no.qntNo = count;
            }
            for (int i = 0; i < no.qntNo; i++) {
                Arvore novoFilho = new Arvore(no.game, no.jogador);
                alteraJogador(no, novoFilho);
                movimenta(no, novoFilho);
                novoFilho.altura = no.altura + 1;
                no.filho.add(novoFilho);
                verificaJogada(novoFilho);
                count = 0;
                for (int x = 0; x < 9; x++) {
                    if (novoFilho.game[x / 3][x % 3] == 0) {
                        count++;
                    }
                }
                if(count == 0){
                     minimax(no);
                    break;
                }
                aux = geraArvore(novoFilho);
                minimax(no);

                no.t = aux.t;
                count = 0;

            }
            return no;
        }

    }

    public void exibe(Arvore no) {
        if (no.altura < qntAltura) {
            for (int i = 0; i < no.filho.size(); i++) {
                exibe(no.filho.get(i));
            }
        }
        System.out.println("Altura: " + no.altura + ": " + no.c + " Valor: " + no.valor + " Jogada: " + no.pos);

        for (int x = 0; x < 9; x++) {
            System.out.print(no.game[x / 3][x % 3]);
        }
        System.out.println("");
        imprime(no.game);
    }


    public int jogada(Arvore aux) {
        Arvore no;
        int temp = aux.filho.get(0).valor;
        int pos = aux.filho.get(0).pos;
        for (int i = 0; i < aux.filho.size(); i++) {
            no = aux.filho.get(i);
            if(temp <= no.valor && pos != -1){
                temp = no.valor;
                pos = no.pos;
                if(verificaJogo(no.game)){
                    break;
                }
            }
            //System.out.println(no.ma + " - Altura: " + no.altura + ": " + no.c + " Valor: " + no.valor + " Jogada: " + no.pos);
        }
        //System.out.println("");
        return pos;
    }

    public static void main(String[] args) {
        int jogo[][] = new int[3][3];
        for (int x = 0; x < 9; x++) {
            jogo[x / 3][x % 3] = 0;
        }

        Scanner in = new Scanner(System.in);

        Arvore g = new Arvore(jogo, 2);

        for (int x = 0; x < 9; x++) {
            g.game[x / 3][x % 3] = 0;
        } //ia = 1; player =2;
        g.game[0][0] = 2;
        g.game[0][1] = 0;
        g.game[0][2] = 0;
        g.game[1][0] = 2;
        g.game[1][1] = 1;
        g.game[1][2] = 0;
        g.game[2][0] = 1;
        g.game[2][1] = 0;
        g.game[2][2] = 2;

        g.imprime(g.game);
        g.c = 0;
        g.t = g.c;
        g.geraArvore(g);
        g.exibe(g);

        System.out.println("");
        System.out.println(g.jogada(g));
        
        
    }

    private void minimax(Arvore no) {
        int aux = no.filho.get(0).pos;
        if (no.filho.get(0).jogador == 2) {
            int size = no.filho.size();
            int menor = no.filho.get(0).valor;
            for (int j = 0; j < size; j++) {
                if (menor > no.filho.get(j).valor) {
                    menor = no.filho.get(j).valor;
                }
            }
            no.valor = menor;
        } else {
            int size = no.filho.size();
            int maior = no.filho.get(0).valor;
            for (int j = 0; j < size; j++) {
                if (maior < no.filho.get(j).valor) {
                    maior = no.filho.get(j).valor;
                }
            }
            no.valor = maior;
        }
    }

    public void imprime(int jogo[][]) {
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                System.out.print(jogo[j][k]);
            }
            System.out.println("");
        }
    }

    public void alteraJogador(Arvore no, Arvore novoFilho) {
        if (no.jogador == 1) {
            novoFilho.jogador = 2;
        } else {
            novoFilho.jogador = 1;
        }
        novoFilho.c = no.t;
        novoFilho.c++;
        no.t++;
        novoFilho.t = no.t;
    }

    public void movimenta(Arvore no, Arvore novoFilho) {
        int size = no.filho.size();
        if (size > 0) {
            Arvore aux = no.filho.get(size - 1);
            boolean existe = false;
            for (int x = aux.pos; x < 9; x++) {
                if (aux.game[x / 3][x % 3] == 0) {
                    novoFilho.game[x / 3][x % 3] = novoFilho.jogador;
                    if (novoFilho.pos == 0) {
                        novoFilho.pos = x;
                        existe = true;
                    }
                    break;
                }
            }
            if (!existe) {
                for (int x = 0; x < 9; x++) {
                    if (aux.game[x / 3][x % 3] == 0) {
                        novoFilho.game[x / 3][x % 3] = novoFilho.jogador;
                        if (novoFilho.pos == 0) {
                            novoFilho.pos = x;
                        }
                        break;
                    }
                }
            }
        } else {
            boolean existe = false;
            for (int x = no.pos; x < 9; x++) {
                if (no.game[x / 3][x % 3] == 0) {
                    novoFilho.game[x / 3][x % 3] = novoFilho.jogador;
                    if (novoFilho.pos == 0) {
                        novoFilho.pos = x;
                        existe = true;
                    }
                    break;
                }
            }
            if (!existe) {
                for (int x = 0; x < 9; x++) {
                    if (no.game[x / 3][x % 3] == 0) {
                        novoFilho.game[x / 3][x % 3] = novoFilho.jogador;
                        if (novoFilho.pos == 0) {
                            novoFilho.pos = x;
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean verificaJogo(int matriz[][]) {
        for (int linha = 0; linha < 3; linha++) {
            if (matriz[linha][0] != 0 && matriz[linha][0] == matriz[linha][1] && matriz[linha][0] == matriz[linha][2]) {
                return true;

            }
            if (matriz[0][linha] != 0 && matriz[0][linha] == matriz[1][linha] && matriz[0][linha] == matriz[2][linha]) {
                return true;

            }
        }
        if (matriz[0][0] != 0 && matriz[0][0] == matriz[1][1] && matriz[0][0] == matriz[2][2]) {
            return true;

        } else if (matriz[0][2] != 0 && matriz[0][2] == matriz[1][1] && matriz[0][2] == matriz[2][0]) {
            return true;

        } else {
            return false;
        }
    }

    public void verificaJogada(Arvore no) {
        //boolean vitoriaIa = vitoriaJogador(no.game); //Jogador=2
        if (no.jogador == 1) {//Ia
            if (verificaJogo(no.game)) {
                no.valor = 1;
                no.vitoria = true;
            } else {
                for (int x = 0; x < 9; x++) {
                    if (no.game[x / 3][x % 3] == 0) {
                        return;
                    }
                }
                no.valor = 0;
                no.vitoria = true;
            }
        } else {//Jogador
            if (verificaJogo(no.game)) {
                no.valor = -1;
                no.vitoria = true;
            } else {
                for (int x = 0; x < 9; x++) {
                    if (no.game[x / 3][x % 3] == 0) {
                        return;
                    }
                }
                no.valor = 0;
                no.vitoria = true;
            }
        }
    }
}
