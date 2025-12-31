import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        int boardWidth = 600;
        int boardHeight = boardWidth;

        // creating game frame, setting it visible, setting height and making it open in center
        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // the program will terminate when the user clicks on the x button

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack(); // put the jpanel inside the frame with full dimentions
        snakeGame.requestFocus(); // game is the one listening to key presses

    }
}