package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class Game implements KeyListener, MouseListener {

    boolean verifica = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo da Velha");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        JogoVelha game = new JogoVelha();
        game.setBounds(0, 0, 400, 400);
        frame.add(game);
        frame.addMouseListener(game);
        Game g = new Game();
        frame.addKeyListener(g);
        frame.addMouseListener(g);
        int op = -1;
        while (true) {
            if (op == -1 && game.players == 1) {
                game.vezIa();

            } else if (op == -1 && game.players == 0 && op == -1) {
                game.vezIa();

            }

            if (op == -1) {
                op = game.opcao();
                if(op == 1 || op == 2){
                    g.verifica = true;
                }
            }
            else if(g.verifica == false){
                op = game.opcao();
                if(op == 1 || op == 2){
                    g.verifica = true;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(verifica == true){
            verifica = false;
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(verifica == true){
            verifica = false;
        }
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
