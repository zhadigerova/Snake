import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame{
    public MainWindow(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320, 345);
        setLocation(400, 400);
        /*setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon("background.png")));
        setLayout(new FlowLayout());*/
        add(new GameField());
        setVisible(true);


    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
