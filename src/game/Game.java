package game;

import javax.swing.JFrame;

public class Game {

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

        while (true) {
            if (game.players == 1) {
                game.vezIa();
            } else if (game.players == 0) {
                game.vezIa();
            }
            if (game.opcao() == 1) {
                while (game.opcao() == 1) {                    
                    
                }
            }
            
        }
    }

}
