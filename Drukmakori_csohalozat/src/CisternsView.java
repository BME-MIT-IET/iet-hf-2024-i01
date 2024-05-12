import java.awt.*;

public class CisternsView extends View{

    Cisterns model;
    public CisternsView(int x, int y, Node c, Game g) {
        super(x, y, c, g);
        model = (Cisterns) c;
        this.setBounds(x-GameView.hegy.getWidth(null)/2, y-GameView.ciszterna.getHeight(null)/2, GameView.ciszterna.getWidth(null), GameView.ciszterna.getHeight(null));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(GameView.ciszterna, 0, 0, null);
        if (model.player.size() > 0){
            for (int i = 0; i < model.player.size(); i++){
                if (model.player.get(i) instanceof Mechanic){
                    g.drawImage(GameView.szerelo, 50, 50, null);
                } else if (model.player.get(i) instanceof Saboteur){
                    g.drawImage(GameView.szabotor, 50, 50, null);
                }
            }
        }
       /* int s = model.Get
        for (int i = 0; i < s; i++) {

        }*/
    }
}
