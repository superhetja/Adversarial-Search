package support_classes;

public class Action {
    public int x1, y1, x2, y2;
    public Action(int x1, int  y1, int x2, int y2)
    {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
    }
    public Action(int[] coords)
    {
        this.x1 = coords[0]; this.y1 = coords[1];
        this.x2 = coords[2]; this.y2 = coords[3];
    }
    public int[] start(){return new int[]{x1,y1};}
    public int[] end(){return new int[]{x2,y2};}

    public boolean isForwardMove(){
        return x1 == x2;
    }

    public boolean isDiagonalLeft(){
        return (y1< y2 && x1 > x2) || (y1 > y2 && x1 < x2);
    }

    public boolean isDiagonalRight(){
        return (y1 < y2 && x1 < x2) || (y1 > y2 && x1 > x2);
    }

    public boolean isWhite(){
        return y1 < y2;
    }

    public String toString()
    {
        return "(move " + x1 + " " + y1 + " " + x2 + " " + y2 + ")";
    }

}
