import javax.swing.SwingUtilities;

public class Animation extends Thread{
    
    private Main panel;
    private Puzzle puzzle;
    private boolean ochikon;

    private static final long WAIT_TIME = 500;
    
    public Animation(Main panel, Puzzle puzzle, boolean ochikon){
        this.panel = panel;
        this.puzzle = puzzle;
        this.ochikon = ochikon;
    }

    @Override
    public void run(){
        int commbo = animation();
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    panel.getLabel().setText(String.valueOf(commbo));
                }
            });
        panel.setAnimation(false);
    }
    private int animation(){
        int commbo = puzzle.deleteDrops();
        // System.out.println("deleted");
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    panel.repaint();
                }
            });
        if(commbo == 0){
            return commbo;
        }
        try{
            sleep(WAIT_TIME);
        }catch(InterruptedException e){}
        puzzle.fallDrops();
        if(ochikon){
            puzzle.newFallDrops();
        }
        // System.out.println("added");
        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    panel.repaint();
                }
            });
        try{
            sleep(WAIT_TIME);
        }catch(InterruptedException e){}
        return commbo += animation();
    }
}