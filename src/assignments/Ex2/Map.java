package assignments.Ex2;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
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
		if(p == null){
			return -1;
		}
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
		if(p == null){
			return ;
		}
		setPixel(p.getX(),p.getY(),v);
	}



	// Her we're checking that the p is legal with func isInBounds
    @Override
    public boolean isInside(Pixel2D p) {
		if(p == null){
			return false;
		}
        if(isInBounds(p.getX(),p.getY())){
			return true;
		}
        return false ;
    }
    //A function that checks for non-negativity and range of coordinates
	private boolean isInBounds(int x, int y) {
        return (0 <= x) && (x < getWidth()) && (0 <= y) && (y < getHeight());
    }



	//Check if the dimensions equals
     @Override
    public boolean sameDimensions(Map2D p) {

		 if(p == null){ return false;}

        if (p.getWidth() ==this.getWidth()){
			if (p.getHeight() == this.getHeight()){
               return true;
			}
		}
        return false;
    }



	/*If the dimensions are the same,
	we add values at the same locations.*/
    @Override
    public void addMap2D(Map2D p) {
		if(p == null){return ;}

		if(!sameDimensions(p)){
			return;
		}

		for(int i=0 ;i < getWidth() ; i++ ){
			for(int j=0 ;j < getHeight() ; j++ ){
               int value= p.getPixel(i ,  j);
			   int sum= value + this.getPixel(i,j);
			   setPixel(i , j , sum);
			}
		}

    }



    @Override
    public void mul(double scalar) {
		for(int i=0 ;i < getWidth() ; i++ ){
			for(int j=0 ;j < getHeight() ; j++ ){

				double multi = getPixel(i ,  j)*scalar;
				int value= (int)(multi);
				setPixel(i , j , value);
			}
		}
    }



    @Override
    public void rescale(double sx, double sy) {

		if(sx<=0 || sy<=0 ){return;}

		int nW =(int)(getWidth() * sx);
		int nH = (int)(getHeight() * sy);

		int[][] nMap = new int[nW][nH];

		for(int i=0 ;i < nW ; i++ ){
			for(int j=0 ;j < nH ; j++ ){

				//Calculating the previous indexes
				int preX = (int) (i/sx);
				int preY = (int) (j/sy);

				//Copy the value from the original _map to nMap
				nMap[i][j] = _map[preX][preY];

			}
		}
        //Saving the new map in place of the original map
		this._map= nMap;
    }



    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        if(center == null){ return ;}

        //We surround the circle with a square, and check its range.
		int minY= (int)(center.getY() - rad);
		int maxY= (int)(center.getY() + rad);
		int minX= (int)(center.getX() - rad);
		int maxX= (int)(center.getX() + rad);

		for(int i=minX ; i<=maxX ; i++){
			for(int j=minY ; j<=maxY ; j++){

				                                          //We check the distance using Pythagoras,
				double dx = i -center.getX();             //if the distance is less than
				double dy = j -center.getY();             //the radius we color.
				double dist=Math.sqrt(dx*dx +  dy*dy);

				if(dist < rad){
					setPixel(i , j ,color);
				}
			}
		}
    }



    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
		if((p1 == null) || ((p2 == null))){ return ;}
		int x1= p1.getX();
		int x2= p2.getX();
		int y1= p1.getY();
		int y2= p2.getY();

		int moveX= Math.abs(x2-x1);
		int moveY= Math.abs(y2-y1);

		// Check which Axis is the biggest
		int maxAxis= Math.max(moveX , moveY);

        // If its the same point we color on of them and return
		if (maxAxis == 0){
			setPixel(x1, y1, color);
			return;
		}
		/*Calculation of the number of steps taken
		 at any time on each axis*/
		double stepOnX = (double)(x2 - x1)/maxAxis;
		double stepOnY = (double)(y2 - y1)/maxAxis;

		double currX = x1;
		double currY = y1;
        // Round the numbers and color them
		for(int i=0 ; i <= maxAxis ; i++){
			setPixel((int)Math.round(currX),(int)Math.round(currY), color);
            //Math.round() is long type setPixel is int type so we need to switch

			//Add the distance we moved previously to the current location.
			currX = currX + stepOnX;
			currY = currY + stepOnY;
		}

    }


    //The function receives two opposite points p1 and p2
	// in a rectangle, checks which is larger/smaller, and
	// then colors the rectangle.
    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {

	  if((p1 == null) || ((p2 == null))){ return ;}

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

	//This func return true if the object is Map2D and he have the same dimensions & pixels
    @Override
    public boolean equals(Object ob) {
	    // Check if the ob is null or not the type Map2D
		if(ob == null || !(ob instanceof Map2D)){
			return false;
		}

        //Casting
		Map2D p2 = (Map2D) ob;

		//Checking that the sizes are the same
		if (!this.sameDimensions(p2)){
			return false;
		}

		// Going over all pixels and checking that they are the same
		for(int i=0 ;i < this.getWidth() ; i++ ){
			for(int j=0 ;j < this.getHeight() ; j++ ){

				if(this.getPixel(i ,j) != p2.getPixel(i ,j)){
					return  false;
				}
			}
		}
        return true;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */

    /* Implementation the Flood Fill algorithm
	 *  pixel coloring , using the BFS algorithm
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		if(xy == null ){ return -1;}

		//Save the original color and check that the point legal
		int oldV= getPixel(xy.getX() ,xy.getY());

		if(oldV == new_v || oldV== -1){
			return 0;
		}

		//Restart the BFS
		Queue<Pixel2D> queue = new LinkedList<Pixel2D>();
		queue.add(xy);

		//Color the first point
		setPixel(xy.getX(), xy.getY(), new_v);
		int count = 1;

		//Direction arrays for 4 neighbors (up ,down ,left .right)
		int[] dx= {1 ,-1, 0, 0};
		int[] dy= {0 , 0, 1 ,-1};
		int w = getWidth();
		int h = getHeight();

		//The main loop for the BFS
		while (!queue.isEmpty()){
			Pixel2D currPixel = queue.poll();

			//Calculate the neighbors
			for (int i=0 ; i < 4 ; i++ ){
				 int neighborX = currPixel.getX() +dx[i];
				 int neighborY = currPixel.getY() +dy[i];

				 //Handle cyclic boundaries
				 if(cyclic){
					 neighborX = (neighborX + w) % w;
					 neighborY = (neighborY + h) % h;
				 }

				 /*Checking whether the neighbor is within
				 the boundaries and whether it is in the original color
				 * */
				 if((isInBounds(neighborX,neighborY)) && getPixel(neighborX,neighborY) == oldV){

                     setPixel(neighborX , neighborY , new_v);
					 queue.add( new Index2D(neighborX ,neighborY));
					 count++;
				 }
			}
		}
		return  count;
	}



	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {

		if((p1 == null) || ((p2 == null))){ return null;}

		Map2D disMap= allDistance(p1 , obsColor , cyclic);  // We get the map of the distance from p1 to all.
        if(disMap==null){return null;}

		int dis2P2= disMap.getPixel(p2);                   //Get the dist to p2 from disMap
		if (dis2P2 == -1){return null;}                    //If there is no path from p1 to p2 return null

		Pixel2D[] path = new  Pixel2D[dis2P2 + 1];

		Pixel2D curr = p2;                                  //Filling the array from the last point to the beginning
		path[dis2P2]= p2;

		int[] dx= {1 ,-1, 0, 0};          //Movement directions: up, down, left, right
		int[] dy= {0 , 0, 1 ,-1};
		int w = getWidth();
		int h = getHeight();

		for (int i=dis2P2-1 ; i>=0 ; i--){
			for (int j=0 ; j < 4 ; j++ ) {
				int nX = curr.getX() + dx[j];
				int nY = curr.getY() + dy[j];

				//If it is cyclic we fix
				if(cyclic){
					nX = (nX + w) % w;
					nY = (nY + h) % h;
				}                       // If it is not cyclic and not within the bounds, skip to the next neighbor
				else if (!isInBounds(nX ,nY)) {
					continue;
				}
				//If the neighbor has the correct distance, add it to the array.
				if (disMap.getPixel(nX , nY) == i){

					curr = new Index2D(nX , nY);
					path[i] = curr;
					break;
				}
			}

		}
		return path;
	}


	@Override
	public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic){

		/*Check: The point is not empty, the point is within the legal range,
		 *the starting point is not a wall */
		if((start == null ) || (!isInside(start)) || ( this.getPixel(start) ==obsColor )){
			return null;}

		int[][] distMap = new int[getWidth()][getHeight()];

		for(int i=0 ;i < this.getWidth() ; i++ ){
			for(int j=0 ;j < this.getHeight() ; j++ ){
				distMap[i][j]= -1;
			}
		}
		int xS=start.getX();
		int yS=start.getY();
		distMap[xS][yS] = 0 ;         //Initializing the starting point

		Queue<Pixel2D> q = new LinkedList<Pixel2D>();
		q.add(start);

		int[] dx= {1 ,-1, 0, 0};
		int[] dy= {0 , 0, 1 ,-1};                     //Movement directions: up, down, left, right
		int w = getWidth();
		int h = getHeight();

		//The main loop for the BFS
		while (!q.isEmpty()){
			Pixel2D currPixel = q.poll();
			int currX= currPixel.getX();
			int currY= currPixel.getY();

			//Calculate the neighbors
			for (int i=0 ; i < 4 ; i++ ) {
				int nX = currPixel.getX() + dx[i];
				int nY = currPixel.getY() + dy[i];

				//If it is cyclic we fix
				if(cyclic){
					nX = (nX + w) % w;
					nY = (nY + h) % h;
				}                                    // If it is not cyclic and
				else if (!isInBounds(nX ,nY)) {      //not within the bounds, skip to the next neighbor
					continue;
				}

				//Check if its obstacle, or we already visit there if yes  we continue to the next neighbor
				if ((distMap[nX][nY] != -1) || (getPixel(nX, nY) == obsColor)) {
					continue;
				}

				//Neighbor's distance = current distance +1
				distMap[nX][nY] = distMap[currX][currY] +1;
				// Add the neighbor to queue
				q.add(new Index2D(nX , nY));
			}
		}
		return new Map(distMap);
	}


	////////////////////// Private Methods ///////////////////////

}

