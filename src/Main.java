import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import javax.swing.event.MouseInputListener;

public class Main extends JPanel implements MouseInputListener{
    
    private Puzzle puzzle;
    private int[] cursor = {0,0};
    private boolean isMove = false;
    private boolean isAnimation= false;
    private boolean ochikon = true;
    private Color[] colors = {
        new Color(229, 61, 70),     // red
        new Color(78, 44, 232),     // blue
        new Color(53, 198, 63),     // green
        new Color(211, 211, 33),    // yellow
        new Color(127, 19, 173),    // purple
        new Color(45, 239, 226)     // bluegreen
        };
    
    private int x;
    private int y;

    private JLabel label;

    public Main(int x, int y, JLabel label){
        super();
        this.x = x;
        this.y = y;
        this.puzzle = new Puzzle(x, y);
        this.label = label;
        setPreferredSize(new Dimension(600, 500));
        setFocusable(true);
        requestFocus();
        registerKeyMap(KeyEvent.VK_UP);
        registerKeyMap(KeyEvent.VK_DOWN);
        registerKeyMap(KeyEvent.VK_LEFT);
        registerKeyMap(KeyEvent.VK_RIGHT);
        registerKeyMap(KeyEvent.VK_SPACE);
        registerKeyMap(KeyEvent.VK_K);
        registerKeyMap(KeyEvent.VK_D);
        registerKeyMap(KeyEvent.VK_O);
    }

    private void registerKeyMap(int keyCode){
        this.getInputMap().put(KeyStroke.getKeyStroke(keyCode, 0), keyCode);
        this.getActionMap().put(keyCode, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e){
                keyEvent(keyCode);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        Drop[][] drops = puzzle.getDrops();
        for(int i = 0; i < drops.length; i++){
            for(int j = 0; j < drops[i].length; j++){
                try{
                    g2.setColor(colors[drops[i][j].getColor()]);
                }catch(NullPointerException e){
                    continue;
                }
                g2.fillOval(
                    j*getWidth()/drops[i].length,
                    i*getHeight()/drops.length,
                    getWidth()/drops[i].length, 
                    getHeight()/drops.length);
            }
        }
        if(isMove){
            g2.setColor(new Color(0,0,0,100));
            g2.fillOval(
                    cursor[1]*getWidth()/drops[0].length,
                    cursor[0]*getHeight()/drops.length,
                    getWidth()/drops[0].length, 
                    getHeight()/drops.length);
        }
        g2.setColor(new Color(0,0,0));
        g2.drawRect(
                cursor[1]*getWidth()/drops[0].length,
                cursor[0]*getHeight()/drops.length,
                getWidth()/drops[0].length, 
                getHeight()/drops.length);
    }

    public void keyEvent(int keyCode){
        int[] before = cursor.clone();
        if(isAnimation){
            return;
        }
        switch(keyCode){
            case KeyEvent.VK_UP:{
                cursor[0] = cursor[0]==0?0:cursor[0]-1;
                if(isMove){
                    puzzle.swap(before[0], before[1], cursor[0], cursor[1]);
                }
                repaint();
                break;
            }
            case KeyEvent.VK_DOWN:{
                cursor[0] = (cursor[0]==x-1)?x-1:cursor[0]+1;
                if(isMove){
                    puzzle.swap(before[0], before[1], cursor[0], cursor[1]);
                }
                repaint();
                break;
            }
            case KeyEvent.VK_LEFT:{
                cursor[1] = cursor[1]==0?0:cursor[1]-1;
                if(isMove){
                    puzzle.swap(before[0], before[1], cursor[0], cursor[1]);
                }
                repaint();
                break;
            }
            case KeyEvent.VK_RIGHT:{
                cursor[1] = (cursor[1]==y-1)?y-1:cursor[1]+1;
                if(isMove){
                    puzzle.swap(before[0], before[1], cursor[0], cursor[1]);
                }
                repaint();
                break;
            }
            case KeyEvent.VK_SPACE:{
                isMove = !isMove;
                if(!isMove){
                    isAnimation = true;
                    // System.out.println(puzzle.run());
                    new Animation(this, puzzle, ochikon).start();
                }
                repaint();
                break;
            }
            case KeyEvent.VK_K:{
                puzzle.printDrops();
                break;
            }
            case KeyEvent.VK_D:{
                puzzle.newFallDrops();
                repaint();
                break;
            }
            case KeyEvent.VK_O:{
                ochikon = !ochikon;
                break;
            }
        }
    }

    public JLabel getLabel(){
        return label;
    }
    public void setAnimation(boolean b){
        this.isAnimation = b;
    }

    @Override
    public void mouseClicked(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    @Override
    public void mouseDragged(MouseEvent e){}
    @Override
    public void mouseMoved(MouseEvent e){}

    public static void main(String[] args){
        int x = 5;
        int y = 6;
        if(args.length >= 1 && args[0].equals("76")){
            x = 6;
            y = 7;
        }
        JFrame frame = new JFrame();
        JLabel label = new JLabel("0");
        frame.add(label, BorderLayout.SOUTH);
        frame.add(new Main(x, y, label), BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
