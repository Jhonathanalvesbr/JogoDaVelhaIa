package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JogoVelha extends JPanel implements MouseListener {

    private Font fonte = new Font("Consolas", Font.BOLD, 40);
    private Font fontePequena = new Font("Consolas", Font.BOLD, 10);
    private Font fonteMedia = new Font("Consolas", Font.BOLD, 13);
    private int matriz[][] = new int[3][3], v1, v2, v3, vitoria;
    private int linhaWin[] = new int[4];
    private Arvore ia;
    private int qntNo = 3, qntAltura = 2, qntNo2 = 9, qntAltura2 = 9;
    private int delay = 1500;
    int players = 1;
    private boolean jogada = false;
    boolean quemComeca = false;
    private int ganhou = 0;
    private int movimento = 0;

    JogoVelha() {
        v2 = v1 = 0;
        for (int i = 0; i < 4; i++) {
            linhaWin[i] = -1;
        }
        for (int i = 0; i < 9; i++) {
            matriz[i / 3][i % 3] = 0;

        }
    }

    @Override
    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2.create();

        g.setStroke(new BasicStroke(3));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 400, 400);
        g.setFont(fontePequena);
        g.setColor(Color.blue);

        g.drawString("Jogador 1: " + v1, 10, 10);
        g.setColor(Color.black);
        g.setFont(fonteMedia);
        g.drawString("Vitorias", 175, 13);
        g.setFont(fontePequena);
        g.drawString("Empate: " + v3, 174, 25);
        g.setColor(Color.red);
        g.drawString("Jogador 2: " + v2, 310, 10);
        g.setColor(Color.black);

        g.setFont(fonte);
        g.drawLine(0, 133, 400, 133);
        g.drawLine(0, 266, 400, 266);
        g.drawLine(0, 266, 400, 266);
        g.setColor(Color.BLACK);
        g.drawLine(133, 0, 133, 400);
        g.drawLine(266, 0, 266, 400);

        g.setColor(Color.RED);
        if (linhaWin[0] == 0) {
            g.drawLine(0, 66, 400, 66);
        } else if (linhaWin[0] == 1) {
            g.drawLine(0, 199, 400, 199);
        } else if (linhaWin[0] == 2) {
            g.drawLine(0, 332, 400, 332);
        } else if (linhaWin[1] == 0) {
            g.drawLine(66, 0, 66, 400);
        } else if (linhaWin[1] == 1) {
            g.drawLine(199, 0, 199, 400);
        } else if (linhaWin[1] == 2) {
            g.drawLine(332, 0, 332, 400);
        } else if (linhaWin[2] == 0) {
            g.drawLine(0, 0, 400, 400);
        } else if (linhaWin[2] == 1) {
            g.drawLine(380, 0, 0, 380);
        }

        for (int linha = 0; linha < 3; linha++) {
            for (int col = 0; col < 3; col++) {
                if (matriz[linha][col] == 1) {
                    g.setColor(Color.blue);
                    g.drawString("X", 45 + col * 133, 80 + linha * 133);
                } else if (matriz[linha][col] == 2) {
                    g.setColor(Color.red);
                    g.drawString("O", 45 + col * 133, 80 + linha * 133);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int linha = e.getY() / 133;
        int coluna = e.getX() / 133;
        if (players == 2) {
            multiplayer(linha, coluna);
            ganhou();
            repaint();
        }
        if (players == 1) {
            solo(linha, coluna);
        }
    }

    public int opcao() {
        int opcao = -1;
        if (vitoria == 3) {
            opcao = new JOptionPane().showConfirmDialog(this, "Houve empate!\nDeseja jogar novamente?");
            if (opcao == 0) {
                limpa();
            }
        } else if (players == 1) {
            if (ganhou % 2 == 0) {
                if (vitoria == 1) {
                    opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador 2" + " ganhou!!\nDeseja jogar novamente?");
                    if (opcao == 0) {
                        limpa();
                    }
                } else if (vitoria == 2) {
                    opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador 1" + " ganhou!!\nDeseja jogar novamente?");
                    if (opcao == 0) {
                        limpa();
                    }
                }
            } else {
                if (vitoria == 2) {
                    opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador " + vitoria + " ganhou!!\nDeseja jogar novamente?");
                    if (opcao == 0) {
                        limpa();
                    }
                } else if (vitoria == 1) {
                    opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador " + vitoria + " ganhou!!\nDeseja jogar novamente?");
                    if (opcao == 0) {
                        limpa();
                    }
                }
            }
        } else if (players == 0) {
            if (vitoria == 1) {
                opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador 1" + " ganhou!!\nDeseja jogar novamente?");
                if (opcao == 0) {
                    limpa();
                }
            } else if (vitoria == 2) {
                opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador 2" + " ganhou!!\nDeseja jogar novamente?");
                if (opcao == 0) {
                    limpa();
                }
            }
        } else {
            if (vitoria == 1) {
                opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador 1" + " ganhou!!\nDeseja jogar novamente?");
                if (opcao == 0) {
                    limpa();
                }
            } else if (vitoria == 2) {
                opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador 2" + " ganhou!!\nDeseja jogar novamente?");
                if (opcao == 0) {
                    limpa();
                }
            }
        }
        return opcao;
    }

    public void limpa() {
        for (int linha = 0; linha < 3; linha++) {
            for (int col = 0; col < 3; col++) {
                matriz[linha][col] = 0;
            }
        }
        for (int i = 0; i < 4; i++) {
            linhaWin[i] = -1;
        }
        jogada = !jogada;
        if (players == 0) {
            quemComeca = !quemComeca;
        }
        vitoria = 0;
        ganhou++;
        repaint();
    }

    public void ganhou() {
        boolean verifica = false;
        for (int linha = 0; linha < 3; linha++) {
            if (matriz[linha][0] != 0 && matriz[linha][0] == matriz[linha][1] && matriz[linha][0] == matriz[linha][2]) {
                linhaWin[0] = linha;
                vitoria = matriz[linha][0];
                verifica = true;
                break;
            }
            if (matriz[0][linha] != 0 && matriz[0][linha] == matriz[1][linha] && matriz[0][linha] == matriz[2][linha]) {
                linhaWin[1] = linha;
                vitoria = matriz[0][linha];
                verifica = true;
                break;
            }
        }
        if (!verifica) {
            if (matriz[0][0] != 0 && matriz[0][0] == matriz[1][1] && matriz[0][0] == matriz[2][2]) {
                linhaWin[2] = 0;
                vitoria = matriz[0][0];
            } else if (matriz[0][2] != 0 && matriz[0][2] == matriz[1][1] && matriz[0][2] == matriz[2][0]) {
                linhaWin[2] = 1;
                vitoria = matriz[0][2];
            } else {
                int x = 0;
                while (matriz[x / 3][x % 3] != 0) {
                    x++;
                    if (x == 9) {
                        vitoria = 3;
                        break;
                    }
                }
            }
        }
        if (players == 1) {
            if (ganhou % 2 != 0) {
                if (vitoria == 1) {
                    v1++;
                } else if (vitoria == 2) {
                    v2++;
                } else if (vitoria == 3) {
                    v3++;
                }
            } else {
                if (vitoria == 2) {
                    v1++;
                } else if (vitoria == 1) {
                    v2++;
                } else if (vitoria == 3) {
                    v3++;
                }
            }
        } else {
            if (vitoria == 1) {
                v1++;
            } else if (vitoria == 2) {
                v2++;
            } else if (vitoria == 3) {
                v3++;
            }
        }

    }

    public void solo(int linha, int coluna) {
        if (jogada && matriz[linha][coluna] == 0) {
            matriz[linha][coluna] = 1;
            ganhou();
            repaint();
            quemComeca = !quemComeca;
        } else if (!jogada && matriz[linha][coluna] == 0) {
            matriz[linha][coluna] = 2;
            ganhou();
            repaint();
            quemComeca = !quemComeca;
        }
    }

    public void vezIa() {
        if (quemComeca && players == 1) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            quemComeca = !quemComeca;
            if (jogada) {
                int mat[][] = new int[3][3];
                for (int i = 0; i < 9; i++) {
                    if (matriz[i / 3][i % 3] == 1) {
                        mat[i / 3][i % 3] = 2;
                    } else if (matriz[i / 3][i % 3] == 2) {
                        mat[i / 3][i % 3] = 1;
                    } else {
                        mat[i / 3][i % 3] = 0;
                    }
                }
                ia = new Arvore(mat, 2);
            } else {
                ia = new Arvore(matriz, 2);
            }
            ia.qntAltura = qntAltura;
            ia.qntNo = qntNo;
            if (ia.vazio(matriz)) {
                ia.geraArvore(ia);
                System.out.println(ia.a);

                movimento = ia.jogada(ia);
                if (movimento >= 0 && movimento < 9) {
                    if (!jogada) {
                        matriz[movimento / 3][movimento % 3] = 1;
                    } else {
                        matriz[movimento / 3][movimento % 3] = 2;
                    }
                }
            }
            ganhou();
            repaint();
        } else if (players == 0) {
            if (quemComeca) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }

                ia = new Arvore(matriz, 2);
                ia.qntAltura = qntAltura;
                ia.qntNo = qntNo;
                if (ia.vazio(matriz)) {
                    ia.geraArvore(ia);
                    movimento = ia.jogada(ia);
                    matriz[movimento / 3][movimento % 3] = 1;
                }
                ganhou();
                repaint();
                if (vitoria == 0) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    int mat[][] = new int[3][3];
                    for (int i = 0; i < 9; i++) {
                        if (matriz[i / 3][i % 3] == 1) {
                            mat[i / 3][i % 3] = 2;
                        } else if (matriz[i / 3][i % 3] == 2) {
                            mat[i / 3][i % 3] = 1;
                        } else {
                            mat[i / 3][i % 3] = 0;
                        }
                    }
                    ia = new Arvore(mat, 2);
                    ia.qntAltura = qntAltura2;
                    ia.qntNo = qntNo2;
                    if (ia.vazio(matriz)) {
                        ia.geraArvore(ia);
                        movimento = ia.jogada(ia);
                        if (movimento >= 0 && movimento < 9) {
                            matriz[movimento / 3][movimento % 3] = 2;
                        }
                    }
                    ganhou();
                    repaint();
                }
            } else {
                if (!quemComeca) {

                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    int mat[][] = new int[3][3];
                    for (int i = 0; i < 9; i++) {
                        if (matriz[i / 3][i % 3] == 1) {
                            mat[i / 3][i % 3] = 2;
                        } else if (matriz[i / 3][i % 3] == 2) {
                            mat[i / 3][i % 3] = 1;
                        } else {
                            mat[i / 3][i % 3] = 0;
                        }
                    }
                    ia = new Arvore(mat, 2);
                    ia.qntAltura = qntAltura2;
                    ia.qntNo = qntNo2;
                    if (ia.vazio(matriz)) {
                        ia.geraArvore(ia);
                        movimento = ia.jogada(ia);
                        if (movimento >= 0 && movimento < 9) {
                            matriz[movimento / 3][movimento % 3] = 2;
                        }
                    }
                    ganhou();
                    repaint();
                    if (vitoria == 0) {
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ia = new Arvore(matriz, 2);
                        ia.qntAltura = qntAltura;
                        ia.qntNo = qntNo;
                        if (ia.vazio(matriz)) {
                            ia.geraArvore(ia);
                            movimento = ia.jogada(ia);
                            if (movimento >= 0 && movimento < 9) {
                                matriz[movimento / 3][movimento % 3] = 1;
                            }
                        }
                        ganhou();
                        repaint();
                    }
                }
            }
        }
    }

    public void multiplayer(int linha, int coluna) {
        if (jogada && matriz[linha][coluna] == 0) {
            matriz[linha][coluna] = 1;
        } else if (!jogada && matriz[linha][coluna] == 0) {
            matriz[linha][coluna] = 2;
        }
        jogada = !jogada;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
