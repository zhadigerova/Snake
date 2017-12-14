import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 320;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField(){
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }

    public void createApple(){
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("goal.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dotOfSnake.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (inGame){
            graphics.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                graphics.drawImage(dot, x[i], y[i], this);
            }
        }
        else {
            String str = "Game Over";
            Font f = new Font("Arial", Font.BOLD, 14);
            graphics.setColor(Color.WHITE);
            graphics.setFont(f);
            graphics.drawString(str, 125, SIZE/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (left){
            x[0] -= DOT_SIZE;
        }
        if (right){
            x[0] += DOT_SIZE;
        }
        if (up){
            y[0] -= DOT_SIZE;
        }
        if (down){
            y[0] += DOT_SIZE;
        }

        // если змейка больше игрового поля
        if (x[0] > SIZE){
            x[0] = 0;
        }
        if (x[0] < 0){
            x[0] = SIZE;
        }
        if (y[0] > SIZE){
            y[0] = 0;
        }
        if (y[0] < 0){
            y[0] = SIZE;
        }
    }
    public void checkApple(){
        //если координаты головы змейки равны координатам яблока
        if (x[0] == appleX && y[0] == appleY){
            dots++;
            //тогда создаем новое яблоко
            createApple();
        }
    }


    public void checkCollisions(){
        // змейка столкнулась сама с собой
        for (int i = dots; i > 0 ; i--) {
            // >4 - только если длина змейки больше 4 возможно столкновение
            if (i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            super.keyPressed(keyEvent);
            int key = keyEvent.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down){
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up){
                down = true;
                right = false;
                left = false;
            }

        }
    }
}
