import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.io.*;

public class TableView extends JPanel implements KeyListener {

    HashMap<Element, View> elements;
    transient Game game;
    JLabel mscore=new JLabel("Mechanic: 0");
    JLabel sscore=new JLabel("Saboteur: 0");
    JLabel pumpes=new JLabel("Pumpes: 0");

    int state = 0;
    Pipe lastRightClickedPipe;
    Pump lastRightClickedPump;

    Player lastPlayerUsedCP;

    Pipe pumpInput;

    Pump neighbourPump;

    public void setLastRightClickedPipe(Pipe p) {
        lastRightClickedPipe = p;
    }

    public void setLastRightClickedPump(Pump p) {
        lastRightClickedPump = p;
    }

    public TableView(int time, int steps, int players) {
        super();
        View.setTableView(this);
        PipeSystem.resetAll();
        game = new Game(time, steps, players);
        game.Initialization();
        game.CreatePlayers();
        game.setupRound(); // Reset the round
        elements = PipeSystem.getViews();
        setLayout(null);
        mscore.setBounds(125,135,80,80);
        mscore.setVisible(true);
        mscore.setForeground(Color.WHITE);
        this.add(mscore);

        sscore.setBounds(870,135,80,80);
        sscore.setVisible(true);
        sscore.setForeground(Color.WHITE);
        this.add(sscore);

        pumpes.setBounds(870,350,80,80);
        pumpes.setVisible(true);
        pumpes.setForeground(Color.WHITE);
        this.add(pumpes);

        var pipes = PipeSystem.getPipes();
        elements.put(pipes.get(0), new PipeView(287, 300, pipes.get(0), game));
        elements.put(pipes.get(1), new PipeView(609, 300, pipes.get(1), game));
        var pumpes = PipeSystem.getPumpes();
        elements.put(pumpes.get(0), new PumpView(565, 300, pumpes.get(0),game));
        var endpoints = PipeSystem.getEndpoints();
        elements.put(endpoints.get(0), new MountainsView(193, 300, endpoints.get(0), game));
        elements.put(endpoints.get(1), new CisternsView(938, 300, endpoints.get(1), game));

        for (View v : elements.values()) {
            this.add(v);
        }

        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow();
        SwingUtilities.invokeLater(() -> this.requestFocus()); // Request focus explicitly
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(GameView.gameBackground, 0, 0, null);
        mscore.setText("Mechanic: "+PipeSystem.getM_water());
        sscore.setText("Saboteur: "+PipeSystem.getS_water());
        pumpes.setText("Pumpes: "+((Cisterns)PipeSystem.getEndpoints().get(1)).getAvailablePumps());

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //unused in this context
    }
    public void end(){
        if(game.getEnded()){
            var mechanicWater = PipeSystem.getM_water();
            var saboteurWater = PipeSystem.getS_water();
            String fortnite="\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣶⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⠟⠛⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣾⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣾⣿⣿⣿⠿⣿⣿⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⡁⠀⢿⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠭⠛⠻⣿⣿⣿⣿⣤⣼⣿⣿⣿⣿⣿⣷⣶⣶⣄⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣆\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⠀⠀⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠛⠉⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣤⣀⣤⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⣿⣿⣿⠿⠿⠿⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠈⠙⣿⣿⣿⣿⣿⣿⡆⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⣿⣿⢿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⣀⣠⡀⢀⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⡿⠃⠀⠀⠀⠀⠀\n" +
                    "                ⣴⣾⣶⣶⣿⣿⣷⣿⣿⡟⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠻⠿⣿⣿⣿⣿⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠉⠉⠉⠿⠿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣶⡧⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣿⣿⣿⣿⠟⠁⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⡿⠿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀";
            String mikewazovszky="\n" +
                    "                ⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢾⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠏⠃⠀⠀⣀⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠋⠆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣶⣿⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⣿⣿⣿⡟⠀⠀⢀⡀⠀⠀⠀⠀⠀⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣿⣿⣿⣿⠷⠛⣫⣭⡉⠉⠲⣾⡿⠋⠉⣩⣭⣍⠛⢶⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⣿⣿⣿⣿⣿⣿⣿⡇⠀⢸⣿⣿⣿⠀⠀⠟⠧⠀⠸⣿⣿⣿⠀⠈⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⣠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡀⠉⠋⠁⣠⣆⣀⣀⣠⣄⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⢈⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⢀⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⢀⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡄⠈⠉⠛⠻⠿⢿⣿⡿⠟⠛⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡼⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⡇⠀⠀⠀\n" +
                    "                ⠀⠀⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣏⠀⢀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣿⠁⢃⠀⠀⠀\n" +
                    "                ⠀⠀⣿⣿⣿⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠛⠛⠛⠛⠛⠛⠛⠛⠛⠛⠻⠿⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⡇⢸⠀⠀⠀\n" +
                    "                ⠀⢸⣿⣿⡇⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣶⣶⣦⣤⣄⣀⡀⠀⠀⠀⠀⠀⠀⣀⣤⡄⠀⠀⠀⠀⠀⣸⣿⣿⣿⣿⢿⣷⢸⡀⠀⠀\n" +
                    "                ⠀⣼⣿⣿⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⠀⣠⣿⣿⣿⣿⠃⠀⣿⡏⡇⠀⠀\n" +
                    "                ⠀⣿⣿⡇⠀⠀⠀⠘⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⣀⣀⣤⣶⣿⣿⣿⠟⠁⠀⠀⠉⠁⡇⠀⠀\n" +
                    "                ⠀⡿⠛⠃⠀⠀⠀⠀⠀⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀⠀⠀⠀⠀⠀⡇⠀⠀\n" +
                    "                ⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠉⠀⠀⠀⠀⠀⠀⡀⠀⡇⠀⠀\n" +
                    "                ⠀⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⡟⠛⠿⠿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⢠⠀⡇⠀⠀\n" +
                    "                ⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠁⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠉⠉⠉⠉⠉⠉⠁⠀⠀⠀⢹⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⣷⠀⠀\n" +
                    "                ⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⣿⠀⠀\n" +
                    "                ⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠈⠀⢹⡄⠀\n" +
                    "                ⢠⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⡀\n" +
                    "                ⢸⣿⠀⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣣\n" +
                    "                ⣾⣿⠀⠀⢹⡀⠀⠀⠀⠀⠀⠀⠀⠀⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⢀⣿\n" +
                    "                ⢿⢿⡄⠀⠀⠳⡀⠀⠀⠀⠀⠀⠀⠀⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⡄⠁⠀⣾⡟\n" +
                    "                ⠸⡈⢧⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⡿⠁\n" +
                    "                ⠀⠑⢌⣷⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠘⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠴⠞⠉⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠃⠀⠀⣶⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠀⠀⢠⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⣤⡴⠋⠀⢀⣠⢿⡇⠀⣀⠔⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡀⠀⠀⢸⣿⠀⠀⠀⠻⣀⣀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠁⠀⠀⠀⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠢⠀⠸⠛⠓⠢⠄⠀⠉⠉⠁⠀⠀⠀⠀⠀⠀";
            String winner = (mechanicWater > saboteurWater) ? "Mechanic csapat-e lett a Victory Royale!"+fortnite : ( (mechanicWater < saboteurWater) ? "Szabotor csapat-e lett a Victory Royale!"+fortnite : "Dontetlen lett! Mindket csapat rossz volt!"+mikewazovszky);
            JFrame frame=(JFrame)SwingUtilities.getWindowAncestor(this);
            JOptionPane.showMessageDialog(frame,
                    winner,
                    "Game Winners",
                    JOptionPane.PLAIN_MESSAGE);
            frame.dispose();
            new GameView();
            if(mechanicWater > saboteurWater) {
                try {
                    Desktop.getDesktop().open(new File(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\The_Fortnite_Get_Griddy_Dance.mp4"));
                } catch (IOException e) {
                    System.err.println("Nem nyitható meg a fájl");
                }
            } else {
                try {
                    Desktop.getDesktop().open(new File(new File(".").getCanonicalPath() + "\\src\\main\\java\\images\\Mike_Wazowski_Hits_The_Griddy.mp4"));
                } catch (IOException e) {
                    System.err.println("Nem nyitható meg a fájl");
                }
            }
        }
    }

    public int getState() {return state;}

    public void keyPressed(KeyEvent e) {
    char key = e.getKeyChar();
    end();

    if (state == 0) {
        handleKeyPress(key);
    }

    end();
    this.repaint();
}

// Kezeli a billentyű lenyomását a játék állapotának megfelelően
private void handleKeyPress(char key) {
    switch (key) {
        case '0':
            game.Damage();
            break;
        case '1':
            game.SetStucky();
            break;
        case '2':
            game.Skip();
            break;
        case '3':
            game.SetSlippery();
            break;
        case '4':
            handleCutPipe();
            break;
        case '5':
            handleChangePipe();
            break;
        case '6':
            game.Repair();
            break;
        case '7':
            game.PickUpPump();
            break;
        case '8':
            handleSetPump();
            break;
        default:
            System.out.println("Ismeretlen billentyű.");
            break;
    }
}

// Kezeli a cső elvágásának logikáját
private void handleCutPipe() {
    lastPlayerUsedCP = game.currentPlayer;
    cutPipeView();
}

// Kezeli a cső csatlakozásának megváltoztatásának logikáját
private void handleChangePipe() {
    if (state == 0 && lastRightClickedPump != null) {
        neighbourPump = lastRightClickedPump;
        state = 2;
    }
    setChangePipeView();
}

// Kezeli a pumpa beállításának logikáját
private void handleSetPump() {
    if (state == 0 && lastRightClickedPipe != null) {
        pumpInput = lastRightClickedPipe;
        state = 1;
    }
    setPumpView();
}

    public void setPumpView(){
        if(state==1 && pumpInput != lastRightClickedPipe){
            System.out.println("SETP LEFUT");
            game.SetPump(pumpInput,lastRightClickedPipe);
            pumpInput=null;
            lastRightClickedPipe=null;
            state=0;
        }
    }

    public void setChangePipeView(){
        if(state==2 && neighbourPump != lastRightClickedPump){
            System.err.println("CHANGEPIPE LEFUT");
            game.ChangePipe(neighbourPump, lastRightClickedPump);
            pumpInput=null;
            lastRightClickedPipe=null;
            state=0;
        }
    }

    public void cutPipeView(){
        if(game.CutPipe()){
            Pipe uj = PipeSystem.getPipes().get(PipeSystem.getPipes().size()-1);
            Pump ujpump = PipeSystem.getPumpes().get(PipeSystem.getPumpes().size()-1);

            PipeView pv = new PipeView(0, 0, uj, game);
            elements.put(uj, pv);
            this.add(pv);

            int jobbx;
            int jobby;
            if (uj.GetNeighbours().get(0) == ujpump) {
                jobbx = elements.get(uj.GetNeighbours().get(1)).x_cord;
                jobby = elements.get(uj.GetNeighbours().get(1)).y_cord;
            }
            else {
                jobbx = elements.get(uj.GetNeighbours().get(0)).x_cord;
                jobby = elements.get(uj.GetNeighbours().get(0)).y_cord;
            }

            Pipe regiPipe = ((PipeView) elements.get(lastPlayerUsedCP.getElement())).model;
            int balx;
            int baly;
            if (regiPipe.GetNeighbours().get(0) == ujpump) {
                balx = elements.get(regiPipe.GetNeighbours().get(1)).x_cord;
                baly = elements.get(regiPipe.GetNeighbours().get(1)).y_cord;
            }  else {
                balx = elements.get(regiPipe.GetNeighbours().get(0)).x_cord;
                baly = elements.get(regiPipe.GetNeighbours().get(0)).y_cord;
            }


            //Generálunk viewokat

            PumpView pumpv = new PumpView(balx + (jobbx-balx)/2, baly + (jobby-baly)/2 /*elements.get(game.currentPlayer.getElement()).y*/, ujpump, game);
            elements.put(ujpump, pumpv);
            this.add(pumpv);

            pv.Connect();

        }
    }

    public HashMap<Element, View> getElements() {
        return elements;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //unused in this context
    }
}