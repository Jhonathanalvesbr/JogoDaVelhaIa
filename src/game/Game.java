/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Administrador
 */
public class Game {

    /**
     * @param args the command line arguments
     */
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
        frame.addMouseMotionListener(game);
        
        while(true){
            if(game.players == 1){

                game.vezIa();
            }
        }
    }

}
