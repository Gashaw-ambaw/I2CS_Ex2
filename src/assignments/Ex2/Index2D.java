package assignments.Ex2;

import java.util.LinkedList;
import java.util.Queue;

public class Index2D implements Pixel2D {
    private int _x;
    private int _y;

    public Index2D(int w, int h) {
       this._x = w;
       this._y = h;
    }
    public Index2D(Pixel2D other) {
        this._x =  other.getX();
        this._y =  other.getY();


    }
    @Override
    public int getX() {;
        return _x ;
    }

    @Override
    public int getY() {

        return _y;
    }

    @Override
    public double distance2D(Pixel2D p2) {

        double dx = this._x - p2.getX();
        double dy = this._y - p2.getY();

        return  Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        String  point=  "(" + _x + "," + _y + ")";
        return  point;
    }

    @Override
    public boolean equals(Object p) {
        boolean ans = true;
        if (p instanceof Pixel2D ){
            Pixel2D p2 = (Pixel2D) p;

            boolean xEqual = (this._x== p2.getX());
            boolean yEqual = (this._y== p2.getY());
            if(xEqual && yEqual){
                return ans;
            }
        }
        return !(ans);
    }

}


