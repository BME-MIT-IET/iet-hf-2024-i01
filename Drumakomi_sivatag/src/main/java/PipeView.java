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

    Image current = selectPipeImage(); // Kiválasztjuk a megfelelő csőképet

    View[] szomszedek = getSortedNeighbours(); // Szomszédok rendezése
    double szelessseg = calculateWidth(szomszedek); // Cső szélességének kiszámítása
    double magassag = calculateHeight(szomszedek); // Cső magasságának kiszámítása

    View[] neighbours = getSortedNeighbours(); 
    drawPipe(g, current, szelessseg, magassag, neighbours);// Cső kirajzolása

    // Játékosok elhelyezése a csőn
    if (!model.player.isEmpty()) {
        int yPos = (int) Math.max(0, magassag);
        Image playerImage = getPlayerImage();
        g.drawImage(playerImage, 0, yPos, null);
    }
}

private Image selectPipeImage() {
    if (model.vizezett) {
        return GameView.Pipe.get(15); // Vízbe került cső
    }
    if (model.getDamaged()) {
        return selectDamagedPipeImage();
    }
    if (model.isstucky() && model.isSlippery()) {
        return GameView.Pipe.get(5); // Ragacsos és csúszós cső
    }
    if (!model.isstucky() && model.isSlippery()) {
        return GameView.Pipe.get(1); // Csúszós cső
    }
    if (model.isstucky() && !model.isSlippery()) {
        return GameView.Pipe.get(9); // Ragacsos cső
    }
    return GameView.Pipe.get(0); // Alapértelmezett csőkép
}

private Image selectDamagedPipeImage() {
    if (model.isSlippery() && model.isstucky()) {
        return GameView.Pipe.get(3); // Sérült, ragacsos és csúszós cső
    }
    if (model.isSlippery() && !model.isstucky()) {
        return GameView.Pipe.get(6); // Sérült és csúszós cső
    }
    if (!model.isSlippery() && model.isstucky()) {
        return GameView.Pipe.get(10); // Sérült és ragacsos cső
    }
    return GameView.Pipe.get(13); // Sérült cső
}

private View[] getSortedNeighbours() {
    View[] neighbours = new View[2];
    neighbours[0] = View.getTableView().getElements().get(model.GetNeighbours().get(0));
    neighbours[1] = View.getTableView().getElements().get(model.GetNeighbours().get(1));

    // Rendezzük a szomszédokat balról jobbra
    if (neighbours[0].x_cord > neighbours[1].x_cord) {
        View temp = neighbours[0];
        neighbours[0] = neighbours[1];
        neighbours[1] = temp;
    }
    return neighbours;
}

private double calculateWidth(View[] neighbours) {
    return Math.abs(neighbours[0].x_cord - neighbours[1].x_cord);
}

private double calculateHeight(View[] neighbours) {
    return Math.max(0, neighbours[0].y_cord - neighbours[1].y_cord);
}

private void drawPipe(Graphics g, Image current, double width, double height, View[] neighbours) {
    int baleltol = (neighbours[0] instanceof MountainsView) ? 73 : 41;
    int jobblevag = (neighbours[1] instanceof CisternsView) ? 85 : 41;
    g.drawImage(current, 0, 0, (int) width - jobblevag - baleltol, current.getHeight(null), null);
}

private Image getPlayerImage() {
    if (model.player.get(0) instanceof Mechanic) {
        return GameView.szerelo;
    } else if (model.player.get(0) instanceof Saboteur) {
        return GameView.szabotor;
    }
    return null;
}

    public void Connect(){
        View szomszed1 = View.getTableView().getElements().get(model.GetNeighbours().get(0));
        View szomszed2 = View.getTableView().getElements().get(model.GetNeighbours().get(1));

        //Rendezzük, hogy 1 legyen baloldalt
        if (szomszed1.x_cord > szomszed2.x_cord) {
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

        double szelessseg = szomszed1.x_cord - szomszed2.x_cord;
        double magassag = szomszed1.y_cord - szomszed2.y_cord;

        y_cord = szomszed1.y_cord-GameView.Pipe.get(0).getHeight(null)/2-((magassag > 0) ? (int)magassag : 0);
        x_cord = szomszed1.x_cord + baleltol;
        this.setBounds(x_cord, y_cord, (int)abs(szelessseg)-jobblevag-baleltol, /*GameView.Pipe.get(0).getWidth(null)*/ (int)abs(magassag)+(int)(GameView.Pipe.get(0).getHeight(null)*1.2));

    }

}
