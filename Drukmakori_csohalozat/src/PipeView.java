import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.EventListener;

import static java.lang.Math.abs;

public class PipeView extends View implements EventListener {

    Pipe model;

    private double angle = 0;
    private double hossz = 0;

    public PipeView(int x, int y, Pipe p,Game g) {
        super(x, y, p,g);
        //this.y = y-GameView.Pipe.get(0).getHeight(null)/2;
        model = p;
        this.setBounds(x, y-GameView.Pipe.get(0).getHeight(null)/2, GameView.Pipe.get(0).getWidth(null), GameView.Pipe.get(0).getHeight(null));


        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (true) { //Csövön belül van
            super.mouseClicked(e);
            if(e.getButton() == MouseEvent.BUTTON3) {
                // - (e.getX()*(Math.sin(angle)/Math.cos(angle)) + (double) GameView.Pipe.get(0).getHeight(null) /2)){
                //System.out.println("X: " + e.getX()+ "Y: " + e.getY());
                View.getTableView().setLastRightClickedPipe(this.model);
                View.getTableView().setPumpView();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image current=GameView.Pipe.get(0);
        if (model.vizezett){
            current=GameView.Pipe.get(15);
        }
        if(model.isstucky() && !model.vizezett){
            current=GameView.Pipe.get(9);
        }
        if(model.isSlippery() && !model.vizezett){
            current=GameView.Pipe.get(1);
        }
        if(model.isSlippery() && model.vizezett){
            current=GameView.Pipe.get(8);
        }
        if(model.isstucky() && model.vizezett){
            current=GameView.Pipe.get(12);
        }
        if(model.isstucky() && model.isSlippery() && !model.vizezett){
            current=GameView.Pipe.get(2);
        }
        if(model.isstucky() && model.isSlippery() && model.vizezett){
            current=GameView.Pipe.get(5);
        }
        if(model.getDamaged()){
            current=GameView.Pipe.get(13);
        }
        if(model.getDamaged() && model.isSlippery()){
            current=GameView.Pipe.get(6);
        }
        if(model.getDamaged() && model.isstucky()){
            current=GameView.Pipe.get(10);
        }
        if(model.isstucky() && model.isSlippery() && !model.vizezett && model.getDamaged()){
            current=GameView.Pipe.get(3);
        }


        //TODO: NULL SZOMSZED?!, egyik szomszed nincs bekotve
        View szomszed1 = View.getTableView().getElements().get(model.GetNeighbours().get(0));
        View szomszed2 = View.getTableView().getElements().get(model.GetNeighbours().get(1));

        //Rendezzük, hogy 1 legyen baloldalt
        if (szomszed1.x > szomszed2.x) {
            var temp = szomszed1;
            szomszed1 = szomszed2;
            szomszed2 = temp;
        }

        double szelessseg = szomszed1.x - szomszed2.x;
        double magassag = szomszed1.y - szomszed2.y;

        double atmero = Math.sqrt(szelessseg*szelessseg + magassag*magassag);

        double forgatasiSzog = Math.atan(magassag/szelessseg);

        Graphics2D g2d = (Graphics2D) g;
        int w2 = getWidth() / 2;
        int h2 = getHeight() / 2;

        //g2d.translate(0, GameView.Pipe.get(0).getHeight(null)/2);

        //TODO: 2D-re...
        int baleltol = 41;
        if (szomszed1 instanceof MountainsView)
            baleltol = 73;

        int jobblevag = 41;
        if (szomszed2 instanceof CisternsView)
            jobblevag = 85;




        //angle = forgatasiSzog;
        //g2d.rotate(forgatasiSzog, szomszed1.x - getX(), getHeight() / 2);
       // this.setBounds(x,y, (int)abs((szelessseg)), GameView.Pipe.get(0).getHeight(null));
        g.drawImage(current, 0, 0, (int) abs(szelessseg)-jobblevag-baleltol, current.getHeight(null), null);

        //((magassag > 0) ? (int)magassag : 0)
        //model.GetNeighbours().get(1);

        //Image newImage = yourImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);

        if (model.player.size() > 0){
            if (model.player.get(0) instanceof Mechanic){
                g.drawImage(GameView.szerelo, 0, ((magassag > 0) ? (int)magassag : 0), null);
            } else if (model.player.get(0) instanceof Saboteur){
                g.drawImage(GameView.szabotor, 0, ((magassag > 0) ? (int)magassag : 0), null);
            }
        }
    }

    public void Connect(){
        //TODO: NULL SZOMSZED?!, egyik szomszed nincs bekotve
        View szomszed1 = View.getTableView().getElements().get(model.GetNeighbours().get(0));
        View szomszed2 = View.getTableView().getElements().get(model.GetNeighbours().get(1));

        //Rendezzük, hogy 1 legyen baloldalt
        if (szomszed1.x > szomszed2.x) {
            var temp = szomszed1;
            szomszed1 = szomszed2;
            szomszed2 = temp;
        }
        int baleltol = 41;
        if (szomszed1 instanceof MountainsView)
            baleltol = 73;

        int jobblevag = 41;
        if (szomszed2 instanceof CisternsView)
            jobblevag = 85;

        double szelessseg = szomszed1.x - szomszed2.x;
        double magassag = szomszed1.y - szomszed2.y;

        y = szomszed1.y-GameView.Pipe.get(0).getHeight(null)/2-((magassag > 0) ? (int)magassag : 0);
        x = szomszed1.x + baleltol;
        this.setBounds(x, y, (int)abs(szelessseg)-jobblevag-baleltol, /*GameView.Pipe.get(0).getWidth(null)*/ (int)abs(magassag)+(int)(GameView.Pipe.get(0).getHeight(null)*1.2));

    }

}
