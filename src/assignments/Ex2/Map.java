package assignments.Ex2;
import java.io.Serializable;
/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{
	private int[][] _map;
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {init(w, h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {this(size,size, 0);}
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}
	@Override
	public void init(int w, int h, int v) {
		if (w<= 0  ||  h <= 0 ){
			throw new RuntimeException("The dimensions must be positive");
			}
		this._map= new int[w][h];

		for(int i=0 ; i< w ; i++ ){
			for(int j=0 ; j< h ; j++ ){
				this._map[i][j] = v;
			}
		}

	}
	@Override
	public void init(int[][] arr) {

		//check if the arr legal
		if (arr == null ||  arr.length == 0 || arr[0]==null ) {
			throw new RuntimeException("Error : arr is null or empty");
		}
		int w = arr.length;
		int h = arr[0].length;

		this._map= new int[w][h];  //deep copy

		for(int i = 0 ; i<w ; i++ ){

            //Checking  that the arr is not  ragged
			if((arr[i]== null  || arr[i].length != h)){
				throw new RuntimeException("Error: the array ragged at index" + i);
			}

			//The check is clear we can copy
			for(int j=0 ;j< h ; j++){
               this._map[i][j]= arr[i][j];
			}
		}



	}
	// Deep copy of the 2D matrix
	@Override
	public int[][] getMap() {
		int w= getWidth();
		int h =  getHeight();
		int[][] deepCopyMap= new int[w][h];

		for (int i=0; i < w;i++){
			for (int j=0; j < h;j++){
				deepCopyMap[i][j]= _map[i][j];
			}
		}
        return deepCopyMap;
    }
	@Override
	public int getWidth() {

        return _map.length;
    }
	@Override
	public int getHeight() {;

        return _map[0].length;
    }
	//The function checks if the point is correct and legal, if so, returns the position in the array.
	@Override
	public int getPixel(int x, int y) {
        int ans = -1;
		if (isInBounds(x,y)){
			return  _map[x][y];
		}
        return ans;
    }
	@Override
	public int getPixel(Pixel2D p) {

        return getPixel(p.getX(),p.getY());
	}
	//If the coordinate (x,y) is valid, put the value v in it.
	@Override
	public void setPixel(int x, int y, int v) {
		if(isInBounds(x,y)){
			_map[x][y]=v;
		}
    }
	@Override
	public void setPixel(Pixel2D p, int v) {
		setPixel(p.getX(),p.getY(),v);
	}

	// Her we're checking that the p is legal with func isInBounds
    @Override
    public boolean isInside(Pixel2D p) {
        if(isInBounds(p.getX(),p.getY())){
			return true;
		}
        return false ;
    }
    //A function that checks for non-negativity and range of coordinates
	private boolean isInBounds(int x, int y) {
        return (0 <= x) && (x < getWidth()) && (0 <= y) && (y < getHeight());
    }

    @Override
    public boolean sameDimensions(Map2D p) {
        boolean ans = false;

        return ans;
    }

    @Override
    public void addMap2D(Map2D p) {

    }

    @Override
    public void mul(double scalar) {

    }

    @Override
    public void rescale(double sx, double sy) {

    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {

    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {

    }

    //The function receives two opposite points p1 and p2
	// in a rectangle, checks which is larger/smaller, and
	// then colors the rectangle.
    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
      int x1= p1.getX();
	  int x2= p2.getX();
	  int y1= p1.getY();
	  int y2= p2.getY();

	  int minY= Math.min(y1,y2);
	  int maxY= Math.max(y1,y2);
	  int minX= Math.min(x1,x2);
	  int maxX= Math.max(x1,x2);

	  for(int i=minX ; i<=maxX ; i++){
		 for(int j=minY ; j<=maxY ; j++){
            setPixel(i , j ,color);
		}
	  }
    }

    @Override
    public boolean equals(Object ob) {
        boolean ans = false;

        return ans;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int ans = -1;

		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.

		return ans;
	}
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }
	////////////////////// Private Methods ///////////////////////

}
