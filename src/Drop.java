public class Drop{

    private final int color;            // 色を指す数値
    private boolean checked = false;    // 消えるかどうかのフラグ

    public Drop(int color){
        this.color = color;
    }
    public int getColor(){
        return color;
    }
    public void checked(){
        this.checked = true;
    }
    public void reset(){
        this.checked = false;
    }
    public boolean isChecked(){
        return checked;
    }

    @Override
    public String toString(){
        return String.valueOf(color);
    }
}