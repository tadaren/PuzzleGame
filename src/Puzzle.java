import java.util.Random;

public class Puzzle{

    private Drop[][] drops;

    public Puzzle(int x, int y){
        drops = new Drop[x][y];
        Random rand = new Random();
        for(int i = 0; i < drops.length; i++){
            for(int j = 0; j < drops[i].length; j++){
                drops[i][j] = new Drop(rand.nextInt(6));
            }
        }
        run();
    }

    public void swap(int x1, int y1, int x2, int y2){
        Drop buffer = drops[x1][y1];
        drops[x1][y1] = drops[x2][y2];
        drops[x2][y2] = buffer;
    }

    public int run(){
        // printDrops();
        int commbo = deleteDrops();
        if(commbo == 0){
            return 0;
        }
        // printDropsCheck();
        fallDrops();
        newFallDrops();
        // printDrops();
        return commbo += run();
    }
    public Drop[][] getDrops(){
        return this.drops;
    }

    private void printDropsCheck(){
        for(Drop[] ds: drops){
            for(Drop d: ds){
                try{
                    System.out.print(d.isChecked()+"\t");
                }catch(NullPointerException e){
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }
    public void printDrops(){
        for(Drop[] ds: drops){
            for(Drop d: ds){
                if(d != null){
                    System.out.print(d+"\t");
                }else{
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }
    // すでにあるドロップを下に落とす
    public void fallDrops(){
        for(int i = drops.length-2; i >= 0; i--){
            for(int j = 0; j < drops[i].length; j++){
                if(drops[i][j] == null){
                    continue;
                }
                int k = 1;
                for(; k+i < drops.length && drops[i+k][j] == null; k++){}
                if(k != 1){
                    drops[i+k-1][j] = drops[i][j];
                    drops[i][j] = null;
                }
            }
        }
    }
    // 新しいドロップを追加する
    public void newFallDrops(){
        Random rand = new Random();
        for(int i = 0; i < drops.length; i++){
            for(int j = 0; j < drops[i].length; j++){
                if(drops[i][j] == null){
                    drops[i][j] = new Drop(rand.nextInt(6));
                }
            }
        }
    }

    // ドロップを消す
    public int deleteDrops(){
        // 横3つ揃っている場所を探してフラグを立てる
        for(int i = 0; i < drops.length; i++){
            for(int j = 0; j < drops[i].length-2; j++){
                if(drops[i][j] == null || drops[i][j+1] == null || drops[i][j+2] == null){
                    continue;
                }
                if(drops[i][j].getColor() == drops[i][j+1].getColor() && drops[i][j+1].getColor() == drops[i][j+2].getColor()){
                    drops[i][j].checked();
                    drops[i][j+1].checked();
                    drops[i][j+2].checked();
                }
            }
        }
        // 縦3つ揃っている場所を探してフラグを立てる
        for(int i = 0; i < drops.length-2; i++){
            for(int j = 0; j < drops[i].length; j++){
                if(drops[i][j] == null || drops[i+1][j] == null || drops[i+2][j] == null){
                    continue;
                }
                if(drops[i][j].getColor() == drops[i+1][j].getColor() && drops[i+1][j].getColor() == drops[i+2][j].getColor()){
                    drops[i][j].checked();
                    drops[i+1][j].checked();
                    drops[i+2][j].checked();
                }
            }
        }
        int commboCount = 0;
        // フラグを立っている場所を探して消す
        for(int i = 0; i < drops.length; i++){
            for(int j = 0; j < drops[i].length; j++){
                if(drops[i][j] != null && drops[i][j].isChecked()){
                    delete(i, j);
                    commboCount++;
                }
            }
        }
        return commboCount;
    }

    // 消えるドロップを再帰的に消す
    private void delete(int x, int y){
        int color = drops[x][y].getColor();
        drops[x][y] = null;
        try{
            if(drops[x-1][y] != null && drops[x-1][y].isChecked() && color == drops[x-1][y].getColor()){
                delete(x-1, y);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(drops[x+1][y] != null && drops[x+1][y].isChecked() && color == drops[x+1][y].getColor()){
                delete(x+1, y);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(drops[x][y-1] != null && drops[x][y-1].isChecked() && color == drops[x][y-1].getColor()){
                delete(x, y-1);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        try{
            if(drops[x][y+1] != null && drops[x][y+1].isChecked() && color == drops[x][y+1].getColor()){
                delete(x, y+1);
            }
        }catch(ArrayIndexOutOfBoundsException e){}
        
    }

    // public static void main(String[] args){
    //     new Puzzle().run();
    // }
}
