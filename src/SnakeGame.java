import java.awt.*; // graficos cores dimensoes desenhos
import java.awt.event.*; // teclado mouse
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*; // jpanel jframe

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    // posicoes
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
    int velocityX;
    int velocityY;


    SnakeGame(int boardWidth, int boardHeight) {
        // distinguish the two boardwifts
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

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

    public void move() {
        // snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        }
    }

    // dont need
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
