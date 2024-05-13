import java.awt.*;
import java.awt.event.MouseEvent;

public class PumpView extends View {

    Pump model;
    public PumpView(int x, int y, Pump p, Game g) {
        super(x, y, p, g);
        model = p;
        this.setBounds(x-GameView.Pump.get(0).getWidth(null)/2, y-GameView.Pump.get(0).getHeight(null)/2, GameView.Pump.get(0).getWidth(null), GameView.Pump.get(0).getHeight(null));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if(e.getButton() == MouseEvent.BUTTON3) {
            // - (e.getX()*(Math.sin(angle)/Math.cos(angle)) + (double) GameView.Pipe.get(0).getHeight(null) /2)){
            //System.out.println("X: " + e.getX()+ "Y: " + e.getY());
            View.getTableView().setLastRightClickedPump(this.model);
            //View.getTableView().setPumpView();
            View.getTableView().setChangePipeView();
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image current=GameView.Pump.get(0);
        if(model.vizezett){
            current=GameView.Pump.get(2);
        }
        if(model.getBroken()){
            current=GameView.Pump.get(1);
        }
        g.drawImage(current, 0, 0, null);
        if (model.player.size() > 0){
            for (int i = 0; i < model.player.size(); i++){
                if (model.player.get(i) instanceof Mechanic){
                    g.drawImage(GameView.szerelo, 0, 0, null);
                } else if (model.player.get(i) instanceof Saboteur){
                    g.drawImage(GameView.szabotor, 0, 0, null);
                }
            }
        }
    }
}
