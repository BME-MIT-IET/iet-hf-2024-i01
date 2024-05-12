import java.awt.*;

public class MountainsView extends View{

    Mountains model;
    public MountainsView(int x, int y, Node m, Game g) {
        super(x, y, m, g);
        model = (Mountains) m;
        this.setBounds(x-GameView.hegy.getWidth(null)/2, y-GameView.hegy.getHeight(null)/2, GameView.hegy.getWidth(null), GameView.hegy.getHeight(null));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(GameView.hegy, 0, 0, null);
        if (model.player.size() > 0){
            for (int i = 0; i < model.player.size(); i++){
                if (model.player.get(i) instanceof Mechanic){
                    g.drawImage(GameView.szerelo, 50, 50, null);
                } else if (model.player.get(i) instanceof Saboteur){
                    g.drawImage(GameView.szabotor, 50, 50, null);
                }
            }
        }
    }
}
