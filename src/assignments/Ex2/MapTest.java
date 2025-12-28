package assignments.Ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Intro2CS, 2026A, this is a very
 */
class MapTest {
    /**
     *
     */
    private int[][] _map_3_3 = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
    private Map2D _m0, _m1, _m3_3;

    @BeforeEach
    public void setuo() {
        _m3_3 = new Map(_map_3_3);
        _m0 = new Map(3, 3, -1);
         _m1 = new Map(3, 3, -1);
    }

    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int[500][500];
        _m1.init(bigarr);

        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3, 2);
         _m1.fill(p1, 1, true);
    }

    @Test
     void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
         assertEquals(1, _m0.getPixel(1, 0));
    }

    @Test
    void testEquals() {
        assertEquals(_m0, _m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }

    @Test
    void testShortestPath() {
        Pixel2D start = new Index2D(0, 0);
        Pixel2D end = new Index2D(2, 2);
        Pixel2D[] pathReg = _m3_3.shortestPath(start, end, 1, false);
        assertNull(pathReg, "The map is not Cyclic so the point (0,0) blocked ");

        Pixel2D[] pathCyclic = _m3_3.shortestPath(start, end, 1, true);
        assertNotNull(pathCyclic, "The map is  Cyclic so there  path from (0,0) to the other point ");

        boolean wrap = false;
        Pixel2D p1 = pathCyclic[0];
        Pixel2D p2 =  pathCyclic[1];
        if (Math.abs(p1.getX() - p2.getX()) > 1 || Math.abs(p1.getY() - p2.getY()) > 1) {
            wrap =true ;
        }
        assertTrue(wrap, "The path need use the cyclic wrapping correctly");

    }

    @Test
    void testAllDistance() {
        Map2D dist = _m3_3.allDistance(new Index2D(0, 0), 1, false);

         assertEquals(0, dist.getPixel(0, 0));
        assertEquals(-2, dist.getPixel(1, 0), "Neighbor  (1,0) is a wall");
        assertEquals(-2, dist.getPixel(0, 1), "Neighbor (0,1) is a wall");
        assertEquals(-1, dist.getPixel(1, 1), "Point (1,1) is blocked from arrival");
    }


}