import java.awt.*; // graficos cores dimensoes desenhos
import java.awt.event.*; // teclado mouse
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*; // jpanel jframe

public class SnakeGame extends JPanel implements ActionListener{ // posicoes
    private class Tile {
        int x;
        int y;
    // constructor
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;

    // snake
    Tile snakeHead;

    // food
    Tile food;
    Random random;

    // game logic
    Timer gameLoop;

    SnakeGame(int boardWidth, int boardHeight) {
        // distinguish the two boardwifts
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);

        snakeHead = new Tile(5, 5);

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }


    public void paintComponent(Graphics g) { // used for drawing
        super.paintComponent(g); // limpa a tela antes de redesenhar
        draw(g); // desenha o jogo
    }

    public void draw(Graphics g) {
        // grid
        for(int i = 0; i < boardWidth/tileSize; i++) {
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight); // vertical lines
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); // horizontal lines

        }
        //food
        g.setColor(Color.red);
        g.fillRect(food.x * tileSize, food.y * tileSize,tileSize, tileSize);

        // snake
        g.setColor(Color.green);
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
    }

    public void placeFood() { // sortear aleatoriamente as coordenadas do x e y
        food.x = random.nextInt(boardWidth/tileSize); // 600/25 = 24
        food.y = random.nextInt(boardHeight/tileSize);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
