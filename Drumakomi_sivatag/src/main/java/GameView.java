import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;

public class GameView extends JFrame implements ActionListener {
    private JPanel menu;
    private JPanel settings;
    private JButton start;
    private JButton startgame=new JButton("OK");
    private JButton back=new JButton("BACK");
    private TableView table;
    private JTextField playerField;
    private JTextField timeField;
    private JTextField stepsField;
    private int players = 2;
    private int steps = 3;
    private int time = 5;
    private int xcord =1280;
    private int ycord =720;
    private const String hegyImage = "\\src\\main\\java\\images\\hegy.png";

    static ArrayList<Image> Pipe = new ArrayList<>();
    static ArrayList<Image> Pump = new ArrayList<>();
    static Image ciszterna;
    static Image hegy;
    static Image gameBackground;
    static Image szerelo;
    static Image szabotor;

    static {
        try {
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\cso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\csuszosCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\csuszosRagadosCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\CsuszosRagadosTorottCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\csuszosRagadosTorottVizesCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\csuszosRagadosVizesCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\csuszosTorottCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\csuszosTorottVizescso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\csuszosVizesCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\ragadosCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\ragadosTorottcso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\ragadosTorottVizesCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\ragadosVizesCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\torottCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\torottVizesCso.png").getImage());
            Pipe.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\csovek\\vizescso.png").getImage());

            Pump.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\pumpak\\pumpa.png").getImage());
            Pump.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\pumpak\\destroyedpumpa.png").getImage());
            Pump.add(new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\pumpak\\vizesPumpa.png").getImage());

            ciszterna = new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\ciszterna.png").getImage();
            hegy = new ImageIcon(new File(".").getCanonicalPath() + hegyImage).getImage();
            gameBackground = (new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\gameBG.png").getImage());

            szerelo = new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\szerelo.png").getImage();
            szabotor = new ImageIcon(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\szabotor.png").getImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public GameView() {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Csohalozat");
        this.setSize(xcord, ycord);
        try {
            this.setIconImage(new ImageIcon(new java.io.File(".").getCanonicalPath() + hegyImage).getImage());
            Image img = new ImageIcon(new java.io.File(".").getCanonicalPath() + "\\src\\main\\java\\images\\bg.png").getImage();
            menu = new JPanel(null) {
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(img, 0, 0, null);
                }
            };
        } catch (IOException e) {
        }

        this.setLayout(null);
        start = new JButton("START");
        start.setBounds(390, 310, 500, 100);
        start.addActionListener(this);

        menu.add(start);
        menu.setVisible(true);
        menu.setBounds(0, 0, xcord, ycord);

        this.add(menu);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
    public void Settings() {
        this.remove(menu);
        this.repaint();
        try {
            String path = new java.io.File(".").getCanonicalPath();
            this.setIconImage(new ImageIcon(path + hegyImage).getImage());
            Image img = new ImageIcon(path + "\\src\\main\\java\\images\\bg.png").getImage();
            settings = new JPanel(new GridLayout(4, 2));
        }
            catch (IOException e) {
            settings = new JPanel(new GridLayout(4, 2));
        }

        settings.setVisible(true);
        settings.setBounds((1280-xcord/2)/2, (720-ycord/2)/2, xcord/2, ycord/2);
        JLabel player = new JLabel("Set player number: ");
        player.setBounds(0, 0, 640, 180);
        playerField = new JTextField("");
        playerField.setBounds(0, 0, 640, 180);

        JLabel t = new JLabel("Set time (how many rounds): ");
        t.setBounds(0, 0, 640, 180);
        timeField = new JTextField("");
        timeField.setBounds(0, 0, 640, 180);

        JLabel step = new JLabel("Set steps in a round: ");
        step.setBounds(0, 0, 640, 180);
        stepsField = new JTextField("");
        stepsField.setBounds(0, 0, 640, 180);

        settings.add(player);
        settings.add(playerField);
        settings.add(t);
        settings.add(timeField);
        settings.add(step);
        settings.add(stepsField);
        settings.add(startgame);
        settings.add(back);
        startgame.addActionListener(this);
        back.addActionListener(this);
        settings.setForeground(Color.WHITE);
        this.add(settings);
        this.revalidate(); // Add this line to update the frame layout
    }
    public void actionPerformed(ActionEvent ae) {
        if(start.equals(ae.getSource())) {
            Settings();
        }
        if(startgame.equals(ae.getSource())) {
            try {
                players = Integer.parseInt(playerField.getText());
                steps = Integer.parseInt(stepsField.getText());
                time = Integer.parseInt(timeField.getText());
            }
            catch (NumberFormatException e){}
            this.remove(settings);
            table = new TableView(time,steps,players);

            table.setVisible(true);
            table.setBounds(0, 0, 1280, 720);
            this.add(table);
            this.repaint();
        }
        if(back.equals(ae.getSource())) {
            this.dispose();
            new GameView();
        }
    }
}