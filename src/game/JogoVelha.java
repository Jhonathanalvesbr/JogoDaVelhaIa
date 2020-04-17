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

    private Font fonte = new Font("Consolas", Font.BOLD, 20);
    private Font fontePequena = new Font("Consolas", Font.BOLD, 10);
    private Font fonteMedia = new Font("Consolas", Font.BOLD, 13);
    private int matriz[][] = new int[3][3], player = 2, v1, v2, v3, vitoria;
    private Arvore ia;
    private int linhaWin[] = new int[4];
    private int vezIa = 0, vezPlayer = 0;
    private boolean comecaPlayer = true, jogadaIa = false;
    private Graphics2D g;
    
    public JogoVelha() {
        v2 = v1 = 0;
        for (int i = 0; i < 4; i++) {
            linhaWin[i] = -1;
        }
    }

    @Override
    public void paintComponent(Graphics g2) {
        g = (Graphics2D) g2.create();
        
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.WHITE);
        g.fillRect(v1, v1, 400, 400);
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
        if (jogadaIa) {
            vezIa();
            jogadaIa = !jogadaIa;
        }

        for (int linha = 0; linha < 3; linha++) {
            for (int col = 0; col < 3; col++) {
                if (matriz[linha][col] == 1) {
                    g.setColor(Color.blue);
                    g.drawString("X", 50 + col * 133, 75 + linha * 133);
                } else if (matriz[linha][col] == 2) {
                    g.setColor(Color.red);
                    g.drawString("O", 50 + col * 133, 75 + linha * 133);
                }
            }
        }

        if (vitoria != 0) {
            player = 2;
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
            g.setColor(Color.black);
            vitoria = -1;
        }

    }
    public void opcao(){
        if (vitoria == 3) {
            int opcao = new JOptionPane().showConfirmDialog(this, "Houve empate!\nDeseja jogar novamente?");
            if (opcao == 0) {
                limpa();
            }
        } else if (vitoria == 1) {
            int opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador " + vitoria + " ganhou!!\nDeseja jogar novamente?");
            if (opcao == 0) {
                limpa();
            }
        } else if (vitoria == 2) {
            int opcao = new JOptionPane().showConfirmDialog(this, "Parabéns\nO Jogador " + vitoria + " ganhou!!\nDeseja jogar novamente?");
            if (opcao == 0) {
                limpa();
            }
        }
    }
    private void limpa() {
        for (int linha = 0; linha < 3; linha++) {
            for (int col = 0; col < 3; col++) {
                matriz[linha][col] = 0;
            }
        }
        for (int i = 0; i < 4; i++) {
            linhaWin[i] = -1;
        }
        player = 2;

        repaint();
    }

    private void ganhou() {
        boolean verifica = false;
        for (int linha = 0; linha < 3; linha++) {
            if (matriz[linha][0] != 0 && matriz[linha][0] == matriz[linha][1] && matriz[linha][0] == matriz[linha][2]) {
                linhaWin[0] = linha;
                vitoria = matriz[linha][0];
                verifica = true;
            }
            if (matriz[0][linha] != 0 && matriz[0][linha] == matriz[1][linha] && matriz[0][linha] == matriz[2][linha]) {
                linhaWin[1] = linha;
                vitoria = matriz[0][linha];
                verifica = true;
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
        if (vitoria == 1) {
            v1++;
        } else if (vitoria == 2) {
            v2++;
        } else if (vitoria == 3) {
            v3++;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int linha = e.getY() / 133;
        int coluna = e.getX() / 133;
        if (comecaPlayer) {
            vez(linha, coluna);
        } else {
            vezIa(linha, coluna);
        }
        ganhou();
        opcao();

    }

    public void vezIa(int linha, int coluna) {
        if (vezIa > vezPlayer) {
            if (player == 1 && matriz[linha][coluna] == 0) {
                matriz[linha][coluna] = player;

            } else if (player == 2 && matriz[linha][coluna] == 0) {
                matriz[linha][coluna] = player;
                vezPlayer++;
            }
        }
        ganhou();
        repaint();
        opcao();
        vezIa();
    }

    public void vezIa() {
        if (vezPlayer == vezIa) {
            vezIa++;
            ia = new Arvore(matriz);
            ia.c = 'A';
            ia.t = ia.c;
            ia.jogador = 2;
            ia.geraArvore(ia);
            ia.movimento = -1;
            ia.vitoria(ia);
            if (ia.movimento == -1) {
                ia.empate(ia);
            }
            if (ia.movimento == -1) {
                ia.derrota(ia);
            }
            if (matriz[ia.movimento / 3][ia.movimento % 3] == 0) {
                matriz[ia.movimento / 3][ia.movimento % 3] = 1;
            }
        }
    }

    public void vez(int linha, int coluna) {
        if (vezIa == vezPlayer) {
            if (player == 1 && matriz[linha][coluna] == 0) {
                matriz[linha][coluna] = player++;
            } else if (player == 2 && matriz[linha][coluna] == 0) {
                matriz[linha][coluna] = player;
                vezPlayer++;
            }
        }
        repaint();
        ganhou();
        opcao();

        if (vezPlayer > vezIa) {
            vezIa++;

            ia = new Arvore(matriz);
            ia.c = 'A';
            ia.t = ia.c;
            ia.jogador = 2;
            ia.geraArvore(ia);
            ia.movimento = -1;
            ia.vitoria(ia);
            if (ia.movimento == -1) {
                ia.empate(ia);
            }
            if (ia.movimento == -1) {
                ia.derrota(ia);
            }
            if (matriz[ia.movimento / 3][ia.movimento % 3] == 0) {
                matriz[ia.movimento / 3][ia.movimento % 3] = 1;
            }
        }
        repaint();
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
