package assignments.Ex2;
import java.awt.Color;
import java.io.FileNotFoundException;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 *
 */
public class Ex2_GUI {

    public static void drawMap(Map2D map) {
        if (map == null){return;}

        int w = map.getWidth();
        int h= map.getHeight();
                                           //Graphics setting
        StdDraw.setCanvasSize(Math.max(w * 20, 500), Math.max(h * 20, 500));
        StdDraw.setXscale(0, w );           //Axis systems: x,y
        StdDraw.setYscale(0, h );
        //Cleaning the screen
        StdDraw.clear(StdDraw.WHITE);

        for (int i = 0; i < w; i++){
            for (int j = 0; j < h; j++){

                int v =map.getPixel(i, j);      //We take the val in the point
                Color colV = getColor(v);       //make the value the color of the pen
                StdDraw.setPenColor(colV);

                //Drawing the map
                StdDraw.filledSquare( i+0.5,j+0.5,0.5 );
                StdDraw.setPenColor(Color.LIGHT_GRAY);
                StdDraw.square(i+0.5,j+  0.5,0.5 );

                StdDraw.setPenColor(Color.BLUE);             //Drawing the numbers on the map
                StdDraw.text(i+0.5, j+0.5,String.valueOf(v) );
            }
        }
        StdDraw.show();
    }

    /**
     * @param mapFileName
     * @return
     */
    public static Map2D loadMap(String mapFileName) {

        if (mapFileName == null  || mapFileName.isEmpty()){
            System.err.println("Error : mapFileName is null or empty");
            return null;
        }
        //Check whether the file we are looking for exists or not.
        java.io.File file = new java.io.File(mapFileName);
         if (!file.exists() ) {
            System.err.println("Error:  the file  not  found " + mapFileName);
            return  null;
        }

         try {
             java.util.Scanner read = new java.util.Scanner(file);
             if(!read.hasNext()){
                 System.err.println("Error : the file is empty" + read);      //This check if the file is empty.
                 read.close();
                 return null;
             }
             //Now after we check the file we can


         } catch (Exception e) {          //If their error we print it.
             e.printStackTrace();
             return null;
         }

         return  null;
    }

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {
        if(map==null){
            System.err.println("Error : the map is null");     //Check: The map and files are
            return;
        }                                                      //correct if no error message.
        if (mapFileName==null  || mapFileName.isEmpty()){
            System.err.println("Error : mapFileName is null or empty");
            return;
        }

        try {
            java.io.PrintWriter outText = new java.io.PrintWriter(mapFileName);

            int w=map.getWidth();
            int h=map.getHeight();

            outText.println(w + ","+ h);

            for (int i = 0; i < h; i++){
                for (int j = 0; j < w; j++) {

                    int value= map.getPixel(j , i) ;
                    outText.print(value);

                    if(j < w-1){
                        outText.print(",");
                    }
                }
                outText.println();
            }
          outText.close();        //close the text
            System.out.println(" The file saved ");

        } catch (Exception e) {          //If their error we print it.
            e.printStackTrace();
        }


    }
    public static void main(String[] a) {
        String mapFile = "map.txt";
        Map2D map = loadMap(mapFile);
        drawMap(map);
    }


    /// ///////////// Private functions ///////////////

    //The func return the color of the val,  -1=black else white
    private static Color getColor( int v){
        if(v== -1){return Color.BLACK;}
        if(v== 0){return Color.GREEN;}
        else {return Color.WHITE;}
    }


}
