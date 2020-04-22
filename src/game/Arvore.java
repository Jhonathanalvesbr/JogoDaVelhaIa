package game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arvore {

    private int altura;
    int qntAltura = 7;
    int qntNo = 9;
    private ArrayList<Arvore> filho = new ArrayList();
    private int game[][];
    public int jogador;
    public char c;
    public char t;
    private int valor = -100;
    private int pos;
    private boolean vitoria;
    public int movimento;
    final int QUAD = 3;

    public Arvore(int jogo[][]) {
        game = new int[QUAD][QUAD];
        for (int x = 0; x < jogo.length * jogo.length; x++) {
            game[x / game.length][x % game.length] = jogo[x / game.length][x % game.length];
        }
        
    }

    public Arvore geraArvore(Arvore no) {
        Arvore aux;
        if (no.altura >= qntAltura) {
            verificaJogada(no);
            return no;
        } else {
            for (int i = 0; i < qntNo; i++) {
                Arvore novoFilho = new Arvore(no.game);
                novoFilho.altura = no.altura + 1;
                no.filho.add(novoFilho);
                
                alteraJogador(no, novoFilho);
                movimenta(no, novoFilho);
                verificaJogada(novoFilho);
                minimax(no);
                if (!novoFilho.vitoria) {
                    aux = geraArvore(novoFilho);
                    no.t = aux.t;
                }
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
       // if(no.altura == 1){
        System.out.println("Altura: " + no.altura + " Valor: " + no.valor + " Jogada: " + no.pos + " Vitoria: " + no.vitoria);
        imprime(no.game);//}
    }

    public void vitoria(Arvore no) {
        if (no.altura == 1 && no.valor == 1) {
            movimento = no.pos;
            if (verificaJogo(no.game, no.jogador)) {
                return;
            }
        }
        if (no.altura < qntAltura) {

            for (int i = 0; i < no.filho.size(); i++) {
                vitoria(no.filho.get(i));
            }
        }
    }

    public void empate(Arvore no) {
        if (no.altura == 1 && no.valor == 0) {
            int jogo[][] = new int[QUAD][QUAD];
            for (int x = 0; x < jogo.length * jogo.length; x++) {
                jogo[x / game.length][x % game.length] = no.game[x / game.length][x % game.length];
            }
            jogo[no.pos / jogo.length][no.pos % jogo.length] = 2;

            if (verificaJogo(jogo, 2)) {
                movimento = no.pos;
                return;
            }
        }
        if (no.altura < qntAltura) {

            for (int i = 0; i < no.filho.size(); i++) {
                empate(no.filho.get(i));
        }
            }
    }

    public void mov(Arvore no) {
        if (no.altura == 1 && no.valor == 0) {
            movimento = no.pos;
            return;
        }
        if (no.altura < qntAltura) {
            for (int i = 0; i < no.filho.size(); i++) {
                mov(no.filho.get(i));
            }
        }

    }

    public void derrota(Arvore no) {
        if (no.altura == 1 && no.valor == -1) {
            movimento = no.pos;
            return;
        }
        if (no.altura < qntAltura) {
            for (int i = 0; i < no.filho.size(); i++) {
                derrota(no.filho.get(i));
            }
        }

    }

    public static void main(String[] args) {
        final int QUAD = 3;
        int jogo[][] = new int[QUAD][QUAD];
        for (int x = 0; x < jogo.length * jogo.length; x++) {
            jogo[x / jogo.length][x % jogo.length] = 0;
        }

        Scanner in = new Scanner(System.in);

        Arvore g = new Arvore(jogo);

        for (int x = 0; x < jogo.length * jogo.length; x++) {
            g.game[x / jogo.length][x % jogo.length] = 0;
        } //ia = 1; player =2;
        g.game[0][0] = 0;
        g.game[0][1] = 2;
        g.game[0][2] = 0;
        g.game[1][0] = 0;
        g.game[1][1] = 2;
        g.game[1][2] = 0;
        g.game[2][0] = 1;
        g.game[2][1] = 0;
        g.game[2][2] = 0;

        int count = 0;
        for (int x = 0; x < g.game.length * g.game.length; x++) {
            if (g.game[x / g.game.length][x % g.game.length] == 0) {
                count++;
            }
        }
        if (g.qntNo > count) {
            g.qntNo = count;
        }
        if (g.qntAltura > count) {
            g.qntAltura = 6;
        }
        
        g.imprime(g.game);
        g.c = 'A';
        g.t = g.c;
        g.jogador = 2;
        g.geraArvore(g);
        g.exibe(g);
        g.movimento = -1;
        System.out.println(g.valor);
        g.vitoria(g);
        if (g.movimento == -1) {
            g.empate(g);
        }
        if (g.movimento == -1) {
            g.mov(g);
        }
        if (g.movimento == -1) {
            g.derrota(g);
        }
        System.out.println("Posi: " + g.movimento);//*/
        /////////////////////////////////////////////////
        /*for (int x = 0; x < jogo.length * jogo.length; x++) {
            jogo[x / jogo.length][x % jogo.length] = 0;
        }
        int x = 0;
        Arvore a = new Arvore(jogo);
        while (true) {
            a.imprime(jogo);
            System.out.println("");

            if (x % 2 == 1) {
                if (a.verificaJogo(jogo, 1)) {
                    System.out.println("Jogador: 1 Ganhou!!");
                
                break;}
            }
            else{
                if (a.verificaJogo(jogo, 2)) {
                    System.out.println("Jogador: 2 Ganhou!!");
                
                break;}
            }
                

            if (x == jogo.length * jogo.length) {
                break;
            }

            if (x % 2 == 0) {
                g = new Arvore(jogo);
                g.qntAltura = 1;
                g.qntNo = 2;
                g.c = 'A';
                g.t = g.c;
                g.jogador = 2;
                g.geraArvore(g);
                g.movimento = -1;
                g.vitoria(g);
                if (g.movimento == -1) {
                    g.empate(g);
                }
                if (g.movimento == -1) {
                    g.mov(g);
                }
                if (g.movimento == -1) {
                    g.derrota(g);
                }
                jogo[g.movimento / jogo.length][g.movimento % jogo.length] = 1;
            } else {
                int temp[][] = new int[QUAD][QUAD];;
                for (int i = 0; i < jogo.length; i++) {
                    for (int j = 0; j < jogo.length; j++) {
                        if (jogo[i][j] == 1) {
                            temp[i][j] = 2;
                        } else if (jogo[i][j] == 2) {
                            temp[i][j] = 1;
                        } else {
                            temp[i][j] = 0;
                        }
                    }
                }
                g = new Arvore(temp);
                g.qntAltura = 3;
                g.qntNo = 9;
                g.c = 'A';
                g.t = g.c;
                g.jogador = 2;
                g.geraArvore(g);
                g.movimento = -1;
                g.vitoria(g);
                if (g.movimento == -1) {
                    g.empate(g);
                }
                if (g.movimento == -1) {
                    g.mov(g);
                }
                if (g.movimento == -1) {
                    g.derrota(g);
                }
                jogo[g.movimento / jogo.length][g.movimento % jogo.length] = 2;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Arvore.class.getName()).log(Level.SEVERE, null, ex);
            }
            x++;

        }//*/

    }

    private void minimax(Arvore no) {
        int size = no.filho.size() - 1;
        if (no.jogador == 1)//Min
        {
            int maior = no.filho.get(0).valor;
            for (int i = 1; i < size; i++) {
                if (maior < no.filho.get(i).valor) {
                    maior = no.filho.get(i).valor;
                }
            }
            no.valor = maior;
        }
        else if (no.jogador == 2)//Max
        {
            int menor = no.filho.get(0).valor;
            for (int i = 1; i < size; i++) {
                if (menor > no.filho.get(i).valor) {
                    menor = no.filho.get(i).valor;
                }
            }
            no.valor = menor;
        }

    }

    public int contaPosicoesVazias(int game[][]) {
        int qtd = 0;
        for (int x = 0; x < game.length * game.length; x++) {
            if (game[x / game.length][x % game.length] == 0) {
                qtd++;
            }
        }
        return qtd;
    }

    public void imprime(int jogo[][]) {
        for (int j = 0; j < jogo.length; j++) {
            for (int k = 0; k < jogo.length; k++) {
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
        int size = no.filho.size()-1;
        if (size > 0) {
            Arvore aux = no.filho.get(size - 1);
            boolean existe = false;
            for (int x = aux.pos; x < game.length * game.length; x++) {
                if (aux.game[x / game.length][x % game.length] == 0) {
                    novoFilho.game[x / game.length][x % game.length] = novoFilho.jogador;
                    if (novoFilho.pos == 0) {
                        novoFilho.pos = x;
                        existe = true;
                    }
                    break;
                }
            }
            if (!existe) {
                for (int x = 0; x < game.length * game.length; x++) {
                    if (aux.game[x / game.length][x % game.length] == 0) {
                        novoFilho.game[x / game.length][x % game.length] = novoFilho.jogador;
                        if (novoFilho.pos == 0) {
                            novoFilho.pos = x;
                        }
                        break;
                    }
                }
            }
        } else {
            boolean existe = false;
            for (int x = no.pos; x < game.length * game.length; x++) {
                if (no.game[x / game.length][x % game.length] == 0) {
                    novoFilho.game[x / game.length][x % game.length] = novoFilho.jogador;
                    if (novoFilho.pos == 0) {
                        novoFilho.pos = x;
                        existe = true;
                    }
                    break;
                }
            }
            if (!existe) {
                for (int x = 0; x < game.length * game.length; x++) {
                    if (no.game[x / game.length][x % game.length] == 0) {
                        novoFilho.game[x / game.length][x % game.length] = novoFilho.jogador;
                        if (novoFilho.pos == 0) {
                            novoFilho.pos = x;
                        }
                        break;
                    }
                }
            }
        }
    }

    private boolean verificaJogo(int matriz[][], int jogador) {
        int jogadaTemporaria;
        boolean verifica = true;
        int col;
        for (int linha = 0; linha < matriz.length; linha++) {
            col = 0;
            jogadaTemporaria = matriz[linha][col];
            if (jogadaTemporaria != 0) {
                for (col = 0; col < matriz.length; col++) {
                    if (jogador == jogadaTemporaria && jogadaTemporaria == matriz[linha][col] && jogadaTemporaria != 0) {
                        jogadaTemporaria = matriz[linha][col];
                        verifica = false;
                    } else {
                        verifica = true;
                        break;
                    }
                }
            }
            if (!verifica) {
                break;
            } else {
                verifica = true;
            }
        }
        if (!verifica) {
            return true;
        }

        verifica = true;
        for (int linha = 0; linha < matriz.length; linha++) {
            col = 0;
            jogadaTemporaria = matriz[col][linha];
            if (jogadaTemporaria != 0) {
                for (col = 0; col < matriz.length; col++) {
                    if (jogador == jogadaTemporaria && jogadaTemporaria == matriz[col][linha] && jogadaTemporaria != 0) {
                        jogadaTemporaria = matriz[col][linha];
                        verifica = false;
                    } else {
                        verifica = true;
                        break;
                    }
                }
            }
            if (!verifica) {
                break;
            } else {
                verifica = true;
            }
        }
        if (!verifica) {
            return true;
        }

        verifica = false;
        jogadaTemporaria = matriz[0][0];
        for (int linha = 0; linha < matriz.length - 1; linha++) {
            jogadaTemporaria = matriz[linha][linha];
            if (jogador == jogadaTemporaria && jogadaTemporaria == matriz[linha + 1][linha + 1] && jogadaTemporaria != 0) {
                jogadaTemporaria = matriz[linha + 1][linha + 1];
            } else {
                verifica = true;
                break;
            }
        }
        if (!verifica) {
            return true;
        }

        verifica = false;
        jogadaTemporaria = matriz[0][matriz.length - 1];
        for (int linha = matriz.length - 1; linha >= 0; linha--) {
            jogadaTemporaria = matriz[matriz.length - 1 - linha][linha];
            if (jogador == jogadaTemporaria && jogadaTemporaria == matriz[matriz.length - 1 - linha][linha] && jogadaTemporaria != 0) {
                jogadaTemporaria = matriz[matriz.length - 1 - linha][linha];
            } else {
                verifica = true;
                break;
            }
        }
        if (!verifica) {
            return true;
        }
        return false;
    }

    public void verificaJogada(Arvore no) {
        if (no.jogador == 1) {//Ia
            if (verificaJogo(no.game, no.jogador)) {
                no.valor = 1;
                no.vitoria = true;
            } else {
                for (int x = 0; x < game.length * game.length; x++) {
                    if (no.game[x / game.length][x % game.length] == 0) {
                        return;
                    }
                }
                no.valor = 0;
                no.vitoria = true;
            }
        } else {//Jogador
            if (verificaJogo(no.game, no.jogador)) {
                no.valor = -1;
                no.vitoria = true;
            } else {
                for (int x = 0; x < game.length * game.length; x++) {
                    if (no.game[x / game.length][x % game.length] == 0) {
                        return;
                    }
                }
                no.valor = 0;
                no.vitoria = true;
            }
        }
    }
}
