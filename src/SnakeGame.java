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
    ArrayList<Tile> snakeBody;

    // food
    Tile food;
    Random random;

    // game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;


    SnakeGame(int boardWidth, int boardHeight) {
        // distinguish the two boardwifts
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

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
//        for(int i = 0; i < boardWidth/tileSize; i++) {
//            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight); // vertical lines
//            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); // horizontal lines
//
//        }

        //food
        g.setColor(Color.red);
//        g.fillRect(food.x * tileSize, food.y * tileSize,tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize,tileSize, tileSize, true);

        // snake
        g.setColor(Color.green);
//        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // snake body
        for(int i = 0; i < snakeBody.size(); i++) { // loop que passa por cada parte da cobra
            Tile snakePart = snakeBody.get(i); // get pega elemento da posição i
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize); x horizontal, y vertical, largura, altura, multiplica pelo tileSize pra ter a porisção real
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true); //x horizontal, y vertical, largura, altura, multiplica pelo tileSize pra ter a porisção real
        }

        // score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver) {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize); // -16 pra nao ficar colado na tela
        } else {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }

    }

    public void placeFood() { // sortear aleatoriamente as coordenadas do x e y
        food.x = random.nextInt(boardWidth/tileSize); // 600/25 = 24
        food.y = random.nextInt(boardHeight/tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y; //x mesma coluna y mesma linha, true colidiram false nao
    }

    public void move() {
        // eat food
        if(collision(snakeHead, food)) { // verifica se a cabeça da cobra encostou na comida
            snakeBody.add(new Tile(food.x, food.y)); // adiciona um novo bloco ao corpo da cobra
            placeFood(); // nova comida
        }

        // snake body
        for(int i = snakeBody.size()-1; i >= 0; i--) { // loop de tras pra frente, cada parte segue a anterior
            Tile snakePart = snakeBody.get(i);
            if (i == 0) { //0 bloco da cabeça, ele vai pra onde a cabeça tava
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else { // cada bloco segue a posição do bloco anterior
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over
        for(int i = 0; i < snakeBody.size(); i++) { // percorre todas as partes do corpo
            Tile snakePart = snakeBody.get(i); // pega bloco posicao i
            // collide with snake head
            if(collision(snakeHead, snakePart)) { // verifica se a cabeça da cobra ocupa a mesma posicao de algum bloco do corpo
                gameOver = true;
            }

        }

        //  left, right, up, down
        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth ||
                snakeHead.y*tileSize < 0 || snakeHead.y > boardWidth) {
            gameOver = true;
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
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
