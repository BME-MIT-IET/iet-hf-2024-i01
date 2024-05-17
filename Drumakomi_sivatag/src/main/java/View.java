import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class View extends JComponent implements MouseListener, MouseMotionListener {

    private static TableView table;
    public static TableView getTableView() {return table;}
    public static void setTableView(TableView t) {View.table = t;}

    protected int x;
    protected int y;

    Element element;
    transient Game game;

    public View() {}
    public View(int _x, int _y, Element el, Game g) {
        this.setVisible(true);
        x = _x;
        y = _y;
        element = el;
        game = g;
       this.addMouseListener(this);
       this.addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JComponent c = (JComponent) e.getSource();
        //c.setBounds(c.getBounds().x, c.getBounds().y+10, c.getBounds().width, c.getBounds().height);
        if (e.getButton() == MouseEvent.BUTTON1 && View.table.getState() == 0)
            game.Move(element);
        if(e.getButton() == MouseEvent.BUTTON2){
            x=e.getX();
            y=e.getY();
        }
        this.getParent().repaint();
        if (game.getEnded()) {
            ((TableView) this.getParent()).end();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    	//unused in the program
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	//unused in the program
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    	//unused in the program
    }

    @Override
    public void mouseExited(MouseEvent e) {
    	//unused in the program
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        if ((e.getModifiersEx() & e.BUTTON2_DOWN_MASK) != 0) {
            this.setLocation((e.getX() + this.getX()), (e.getY() + this.getY()));
            x = this.getX();
            y = this.getY();
            this.getParent().repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    	//unused in the program
    }
}
