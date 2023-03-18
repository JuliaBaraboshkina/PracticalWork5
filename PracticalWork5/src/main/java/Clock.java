import java.awt.*;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JPanel;
class Clock extends JFrame {
    public Clock() {
        ClockPanel container = new ClockPanel();
        add(container, BorderLayout.CENTER);
        setBackground(Color.white);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new Clock();
    }
}
class ClockPanel extends JPanel implements Runnable {
    Thread t = new Thread(this);
    int xHandSec, yHandSec, xHandMin, yHandMin, xHandHour, yHandHour;
    private final int secondHandLength = 250- 90;
    private final int minuteHandLength = 250 - 110;
    private final int hourHandLength = 250 - 140;

    public ClockPanel() {
        setMinimumSize(new Dimension(250,250));
        setMaximumSize(new Dimension(250,250));
        setPreferredSize(new Dimension(250,250));
        setLayout(null);
        t.start();
    }
    public void run(){
        while(true){
            try{
                int currentSecond = Calendar.getInstance().get(Calendar.SECOND);
                int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
                int currentHour = Calendar.getInstance().get(Calendar.HOUR);

                xHandSec = minToLocation(currentSecond, secondHandLength).x;
                yHandSec = minToLocation(currentSecond, secondHandLength).y;
                xHandMin = minToLocation(currentMinute, minuteHandLength).x;
                yHandMin = minToLocation(currentMinute, minuteHandLength).y;
                xHandHour = minToLocation(currentHour * 5 + getHour(currentMinute), hourHandLength).x;
                yHandHour = minToLocation(currentHour * 5 + getHour(currentMinute), hourHandLength).y;
                repaint();
                Thread.sleep(500);
            } catch(InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }
    private int getHour(int min) {
        return min / 12;
    }

    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.clearRect(0, 0, getWidth(), getHeight());
        g2.setColor(Color.BLACK);
        int angle = 360 / 12;
        int ang = angle*6;
        for (int i = 0; i < 12; i++) {
            g2.drawLine(250+(int)(180 * Math.sin(ang * Math.PI / 180)), 250+(int)(180 * Math.cos(ang * Math.PI / 180)), 250+(int)(190 * Math.sin(ang * Math.PI / 180)),250+(int)(190 * Math.cos(ang * Math.PI / 180)));
            ang=ang+angle;
            Font f = new Font("Comic Sans MS", Font.BOLD, 30);
            g2.setFont(f);
            if (i==11){
                g2.drawString(String.valueOf(12),235+(int)(200 * Math.sin(ang * Math.PI / 180)),250+(int)(200 * Math.cos(ang * Math.PI / 180)));
            }
            else if(i==0 || i==1){
                g2.drawString(String.valueOf(12-(i+1)),230+(int)(210 * Math.sin(ang * Math.PI / 180)),250 + (int)(200 * Math.cos(ang * Math.PI / 180)));
            }
            else if(i==10 || i==9){
                g2.drawString(String.valueOf(12-(i+1)),250+(int)(210 * Math.sin(ang * Math.PI / 180)),250 + (int)(200 * Math.cos(ang * Math.PI / 180)));
            }
            else if(i==8 || i==2){
                g2.drawString(String.valueOf(12-(i+1)),245+(int)(210 * Math.sin(ang * Math.PI / 180)),260 + (int)(200 * Math.cos(ang * Math.PI / 180)));
            }
            else{
                g2.drawString(String.valueOf(12-(i+1)),245+(int)(210 * Math.sin(ang * Math.PI / 180)),270 + (int)(200 * Math.cos(ang * Math.PI / 180)));
            }
        }
        g2.fillOval(248, 248, 5, 5);
        g2.setColor(Color.BLACK);
        g2.drawLine(250, 250, xHandMin, yHandMin);
        g2.drawLine(250, 250, xHandHour, yHandHour);
    }
    private Point minToLocation(int timeStep, int radius) {
        double t = 2 * Math.PI * (timeStep-15) / 60;
        int x = (int)(250 + radius * Math.cos(t));
        int y = (int)(250 + radius * Math.sin(t));
        return new Point(x, y);
    }
}