package assignments.Ex2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MapTest2 {

    // 1. בדיקת בנאי פשוט ואתחול
    @Test
    void testInitWithSize() {
        int w = 10;
        int h = 20;
        int val = 5;
        Map map = new Map(w, h, val);

        assertEquals(w, map.getWidth());
        assertEquals(h, map.getHeight());

        // בדיקה שהערך ההתחלתי נכנס לכל התאים
        assertEquals(val, map.getPixel(0, 0));
        assertEquals(val, map.getPixel(w-1, h-1));
    }

    // 2. בדיקת בנאי ממערך קיים (Deep Copy)
    @Test
    void testInitFromArray() {
        int[][] data = {
                {1, 2},
                {3, 4},
                {5, 6}
        };
        Map map = new Map(data);

        // בדיקת מידות
        assertEquals(3, map.getWidth());
        assertEquals(2, map.getHeight());

        // בדיקת ערכים
        assertEquals(1, map.getPixel(0, 0));
        assertEquals(6, map.getPixel(2, 1));

        // בדיקת Deep Copy: שינוי המערך המקורי לא אמור להשפיע על המפה
        data[0][0] = 999;
        assertEquals(1, map.getPixel(0, 0), "Map should be a deep copy!");
    }

    // 3. בדיקת get/set וגבולות
    @Test
    void testGetSetPixel() {
        Map map = new Map(5, 5, 0);

        // הצבה תקינה
        map.setPixel(2, 2, 100);
        assertEquals(100, map.getPixel(2, 2));

        // הצבה באמצעות אובייקט Pixel2D
        Pixel2D p = new Index2D(3, 3);
        map.setPixel(p, 200);
        assertEquals(200, map.getPixel(3, 3));
    }

    // 4. בדיקת מקרי קצה וחריגות (Out of Bounds)
    @Test
    void testOutOfBounds() {
        Map map = new Map(10, 10, 0);

        // ניסיון לגשת מחוץ לגבולות - אמור להחזיר -1 (לפי המימוש שלך)
        assertEquals(-1, map.getPixel(-1, 0));
        assertEquals(-1, map.getPixel(0, -1));
        assertEquals(-1, map.getPixel(10, 5)); // הגבול הוא 0-9
        assertEquals(-1, map.getPixel(5, 10));

        // ניסיון לכתוב מחוץ לגבולות - לא אמור לזרוק שגיאה, פשוט לא לעשות כלום
        assertDoesNotThrow(() -> map.setPixel(-5, -5, 100));
    }

    // 5. בדיקת תקינות הקלט (חריגות אמיתיות)
    @Test
    void testInvalidInit() {
        // גודל שלילי
        assertThrows(RuntimeException.class, () -> new Map(-5, 10, 0));

        // מערך null
        int[][] nullArr = null;
        assertThrows(RuntimeException.class, () -> new Map(nullArr));

        // מערך משונן (Ragged Array)
        int[][] ragged = {
                {1, 2, 3},
                {1, 2}     // שורה קצרה מדי
        };
        assertThrows(RuntimeException.class, () -> new Map(ragged));
    }

    // 6. בדיקת isInside
    @Test
    void testIsInside() {
        Map map = new Map(10, 10, 0);

        assertTrue(map.isInside(new Index2D(0,0)));
        assertTrue(map.isInside(new Index2D(9,9)));

        assertFalse(map.isInside(new Index2D(-1,0)));
        assertFalse(map.isInside(new Index2D(0,-1)));
        assertFalse(map.isInside(new Index2D(10,5)));
    }



    /**
     * טסטים לתשתית הבסיסית של Map.
     * בודק רק: בנאים, get/set, גבולות, getMap ושגיאות.
     * לא בודק עדיין: drawRect, fill, shortestPath.
     */

        // ==========================================
        // קבוצה 1: בדיקות אתחול ובנאים (Constructors)
        // ==========================================

        @Test
        void testInit_Normal() {
            // בדיקה בסיסית של גודל וערך דיפולטיבי
            Map m = new Map(10, 20, 5);
            assertEquals(10, m.getWidth());
            assertEquals(20, m.getHeight());
            assertEquals(5, m.getPixel(0, 0));
            assertEquals(5, m.getPixel(9, 19));
        }

        @Test
        void testInit_Square() {
            // בדיקה של בנאי ריבוע
            Map m = new Map(5);
            assertEquals(5, m.getWidth());
            assertEquals(5, m.getHeight());
            assertEquals(0, m.getPixel(0, 0)); // ברירת מחדל היא 0
        }

        @Test
        void testInit_From2DArray() {
            // בדיקת העתקה ממערך קיים
            int[][] data = {{1, 2}, {3, 4}, {5, 6}};
            Map m = new Map(data);
            assertEquals(3, m.getWidth());
            assertEquals(2, m.getHeight());
            assertEquals(1, m.getPixel(0, 0));
            assertEquals(6, m.getPixel(2, 1));
        }

        @Test
        void testInit_DeepCopycheck() {
            // וידוא שהבנאי עושה העתק עמוק ולא מצביע לאותו מקום
            int[][] data = {{1, 1}, {1, 1}};
            Map m = new Map(data);

            // נשנה את המערך המקורי
            data[0][0] = 999;

            // המפה לא אמורה להשתנות!
            assertEquals(1, m.getPixel(0, 0));
        }

        // ==========================================
        // קבוצה 2: בדיקות חריגות באתחול (Exceptions)
        // ==========================================

        @Test
        void testInit_NegativeDimensions() {
            // אסור ליצור מפה בגודל שלילי או 0
            assertThrows(RuntimeException.class, () -> new Map(-1, 5, 0));
            assertThrows(RuntimeException.class, () -> new Map(5, -1, 0));
            assertThrows(RuntimeException.class, () -> new Map(0, 5, 0));
        }

        @Test
        void testInit_NullOrEmptyArray() {
            // אסור לקבל מערך null
            int[][] nullArr = null;
            assertThrows(RuntimeException.class, () -> new Map(nullArr));

            // אסור לקבל מערך ריק
            int[][] emptyArr = {};
            assertThrows(RuntimeException.class, () -> new Map(emptyArr));
        }

        @Test
        void testInit_RaggedArray() {
            // בדיקה קריטית: מערך שהשורות שלו לא באותו אורך (אסור במוצר שלנו)
            int[][] ragged = {
                    {1, 2, 3},
                    {1, 2}      // שורה קצרה מדי!
            };
            assertThrows(RuntimeException.class, () -> new Map(ragged));

            int[][] ragged2 = {
                    {1, 2},
                    null     // שורה שהיא null
            };
            assertThrows(RuntimeException.class, () -> new Map(ragged2));
        }

        // ==========================================
        // קבוצה 3: בדיקות isInside (גבולות הגזרה)
        // ==========================================

        @Test
        void testIsInside_CornerCases() {
            Map m = new Map(10, 10, 0); // האינדקסים הם 0..9

            // בתוך הגבולות
            assertTrue(m.isInside(new Index2D(0, 0)));
            assertTrue(m.isInside(new Index2D(9, 9)));
            assertTrue(m.isInside(new Index2D(0, 9)));
            assertTrue(m.isInside(new Index2D(9, 0)));

            // בדיוק על הגבול (בחוץ)
            assertFalse(m.isInside(new Index2D(10, 0)));
            assertFalse(m.isInside(new Index2D(0, 10)));

            // שליליים
            assertFalse(m.isInside(new Index2D(-1, 0)));
            assertFalse(m.isInside(new Index2D(0, -1)));
        }

        // ==========================================
        // קבוצה 4: getPixel & setPixel
        // ==========================================

        @Test
        void testGetSet_HappyPath() {
            Map m = new Map(5, 5, 0);
            m.setPixel(2, 2, 7);
            assertEquals(7, m.getPixel(2, 2));

            // דריסה של ערך קיים
            m.setPixel(2, 2, 8);
            assertEquals(8, m.getPixel(2, 2));
        }

        @Test
        void testGetPixel_OutOfBounds() {
            Map m = new Map(5, 5, 0);

            // לפי המימוש שלך, חריגה מחזירה -1
            assertEquals(-1, m.getPixel(-1, 0));
            assertEquals(-1, m.getPixel(0, -1));
            assertEquals(-1, m.getPixel(5, 0)); // גבול עליון
            assertEquals(-1, m.getPixel(100, 100)); // רחוק מאוד
        }

        @Test
        void testSetPixel_OutOfBounds() {
            Map m = new Map(5, 5, 1);

            // ניסיון לכתוב בחוץ לא צריך לזרוק שגיאה, ולא לשנות כלום
            assertDoesNotThrow(() -> m.setPixel(-1, 0, 99));
            assertDoesNotThrow(() -> m.setPixel(0, 5, 99));

            // מוודאים ששום דבר לא השתנה בטעות במקום קרוב
            assertEquals(1, m.getPixel(0, 0));
        }

        @Test
        void testSetPixel_WithObject() {
            // בדיקת ההעמסה (Overloading)
            Map m = new Map(5,5,0);
            Pixel2D p = new Index2D(1,1);
            m.setPixel(p, 50);
            assertEquals(50, m.getPixel(1,1));
        }

        // ==========================================
        // קבוצה 5: getMap (בדיקת חשיפת מידע)
        // ==========================================

        @Test
        void testGetMap_DeepCopy() {
            Map m = new Map(3, 3, 1);
            int[][] extractedMap = m.getMap();

            // שינוי המפה שהוצאנו החוצה
            extractedMap[0][0] = 100;

            // המפה הפנימית חייבת להישאר מוגנת
            assertEquals(1, m.getPixel(0, 0));

            // שינוי המפה הפנימית
            m.setPixel(0, 0, 50);

            // המפה החיצונית לא משתנה
            assertEquals(100, extractedMap[0][0]);
        }
    /**
     * בדיקה 1: מילוי פשוט בשטח פתוח
     */
    @Test
    public void testFillBasic() {
        // תיקון: הוספנו 0 כפרמטר שלישי (צבע התחלתי)
        Map2D map = new Map(10, 10, 0);

        // נמלא בצבע 1 (שחור) מנקודה (5,5)
        int changed = map.fill(new Index2D(5, 5), 1, false);

        assertEquals(100, changed, "Should fill all 100 pixels in an empty map");
        assertEquals(1, map.getPixel(0, 0)); // בודקים שגם הפינה נצבעה
    }

    /**
     * בדיקה 2: מילוי עם חסימה (קירות)
     */
    @Test
    public void testFillWithWalls() {
        int w = 10, h = 10;
        // תיקון: הוספנו 0 כפרמטר שלישי
        Map2D map = new Map(w, h, 0);

        // נצייר קיר בצבע 1 (שחור) סביב האמצע
        for (int x = 3; x <= 6; x++) {
            map.setPixel(x, 3, 1); // קיר עליון
            map.setPixel(x, 6, 1); // קיר תחתון
        }
        for (int y = 3; y <= 6; y++) {
            map.setPixel(3, y, 1); // קיר שמאלי
            map.setPixel(6, y, 1); // קיר ימני
        }

        // נמלא את החלק הפנימי (4,4) בצבע 2 (אדום)
        int changed = map.fill(new Index2D(4, 4), 2, false);

        // בדיקות
        assertEquals(4, changed, "Should only fill the enclosed area (4 pixels)");
        assertEquals(2, map.getPixel(4, 4)); // בפנים - נצבע
        assertEquals(2, map.getPixel(5, 5)); // בפנים - נצבע
        assertEquals(0, map.getPixel(0, 0)); // בחוץ - נשאר צבע מקורי (0)
        assertEquals(1, map.getPixel(3, 3)); // הקיר עצמו - נשאר 1
    }

    /**
     * בדיקה 3: בדיקת ה-Cyclic (העולם המעגלי) vs העולם השטוח
     */
    @Test
    public void testCyclicFill() {
        // חלק א': cyclic = false
        // תיקון: הוספנו 0 כפרמטר שלישי
        Map2D map = new Map(10, 10, 0);

        // נעשה קו חוצה אנכי באמצע (x=5) בצבע 1
        for(int y=0; y<10; y++) {
            map.setPixel(5, y, 1);
        }

        // נמלא את צד שמאל (0,0) בצבע 2.
        int changedFalse = map.fill(new Index2D(0, 0), 2, false);
        assertEquals(50, changedFalse, "Non-cyclic map should stop at walls");
        assertEquals(0, map.getPixel(9, 9)); // צד ימין נשאר נקי (0)


        // חלק ב': cyclic = true
        // תיקון: הוספנו 0 כפרמטר שלישי
        map = new Map(10, 10, 0);
        for(int y=0; y<10; y++) map.setPixel(5, y, 1); // נחזיר את הקיר

        // הפעם נפעיל עם cyclic = true
        int changedTrue = map.fill(new Index2D(0, 0), 2, true);

        assertEquals(90, changedTrue, "Cyclic map should wrap around boundaries");
        assertEquals(2, map.getPixel(9, 9)); // הצליח להגיע לצד ימין!
    }

    /**
     * בדיקה 4: מקרי קצה (Corner Cases)
     */
    @Test
    public void testCornerCases() {
        // תיקון: הוספנו 0 כפרמטר שלישי
        Map2D map = new Map(5, 5, 0);

        // 1. צביעה באותו צבע
        map.setPixel(2, 2, 1); // נקבע צבע 1
        int changedSame = map.fill(new Index2D(2, 2), 1, false); // ננסה לצבוע ב-1
        assertEquals(0, changedSame, "Should return 0 if target color is same as current");

        // 2. צביעה מחוץ לגבולות
        try {
            int changedOutOfBounds = map.fill(new Index2D(100, 100), 2, false);
            assertEquals(0, changedOutOfBounds, "Should handle out of bounds gracefully");
        } catch (Exception e) {
            // התעלמות משגיאה במקרה קצה זה גם בסדר
        }
    }

    @Test
    public void testBasicOps() {
        // יצירת שתי מפות זהות בגודל 10x10 עם ערך התחלתי 1
        Map2D m1 = new Map(10, 10, 1);
        Map2D m2 = new Map(10, 10, 1);
        Map2D m3 = new Map(10, 10, 2); // מפה עם ערכים 2

        // 1. בדיקת equals
        assertTrue(m1.equals(m2), "Maps should be equal");
        assertFalse(m1.equals(m3), "Maps should not be equal");

        // 2. בדיקת sameDimensions
        assertTrue(m1.sameDimensions(m3));
        Map2D mSmall = new Map(5, 5, 1);
        assertFalse(m1.sameDimensions(mSmall));

        // 3. בדיקת addMap2D
        // נחבר את m2 (כולה 1) ל-m1 (כולה 1). התוצאה צריכה להיות כולה 2.
        m1.addMap2D(m2);
        assertTrue(m1.equals(m3), "1 + 1 should be 2 everywhere");

        // ננסה לחבר מפה בגודל לא מתאים (לא אמור לקרות כלום)
        m1.addMap2D(mSmall);
        assertTrue(m1.equals(m3), "Adding wrong dimension map should do nothing");

        // 4. בדיקת mul
        // נכפיל את m1 (שכרגע היא 2) פי 5. מצפים ל-10.
        m1.mul(5.0);
        assertEquals(10, m1.getPixel(0,0));
        assertEquals(10, m1.getPixel(9,9));
    }

    @Test
    public void testMathOperations() {
        // יצירת מפה 10x10 מלאה בערך 2
        Map2D m = new Map(10, 10, 2);

        // בדיקת כפל: מכפילים פי 3. מצפים ל-6.
        m.mul(3.0);
        assertEquals(6, m.getPixel(0, 0));
        assertEquals(6, m.getPixel(9, 9));

        // בדיקת חיבור: מוסיפים מפה מלאה ב-4. מצפים ל-10.
        Map2D toAdd = new Map(10, 10, 4);
        m.addMap2D(toAdd);
        assertEquals(10, m.getPixel(5, 5), "6 + 4 should be 10");

        // בדיקת אי-חיבור כשהמימדים שונים (לא אמור לקרות כלום)
        Map2D wrongSize = new Map(5, 5, 100);
        m.addMap2D(wrongSize);
        assertEquals(10, m.getPixel(5, 5), "Should not add maps with different sizes");
    }

    @Test
    public void testDrawLine() {
        Map2D m = new Map(20, 20, 0);

        // ציור קו אלכסוני מ-(0,0) ל-(5,5) בצבע 1
        Pixel2D p1 = new Index2D(0, 0);
        Pixel2D p2 = new Index2D(5, 5);
        m.drawLine(p1, p2, 1);

        // בדיקה: ההתחלה, הסוף והאמצע צריכים להיות צבועים
        assertEquals(1, m.getPixel(0, 0));
        assertEquals(1, m.getPixel(5, 5));
        assertEquals(1, m.getPixel(2, 2)); // נקודת אמצע

        // בדיקה: פיקסל שלא קשור לקו צריך להישאר 0
        assertEquals(0, m.getPixel(0, 5));

        // בדיקת מקרה קצה: נקודה בודדת (קו באורך 0)
        m.drawLine(new Index2D(10,10), new Index2D(10,10), 2);
        assertEquals(2, m.getPixel(10, 10));
    }

    @Test
    public void testDrawRect() {
        Map2D m = new Map(20, 20, 0);
        Pixel2D p1 = new Index2D(5, 5);
        Pixel2D p2 = new Index2D(10, 10);

        m.drawRect(p1, p2, 1); // מלבן מלא בצבע 1

        // בדיקת פינות
        assertEquals(1, m.getPixel(5, 5));
        assertEquals(1, m.getPixel(10, 10));
        assertEquals(1, m.getPixel(5, 10));

        // בדיקת פנים
        assertEquals(1, m.getPixel(7, 7));

        // בדיקת חוץ
        assertEquals(0, m.getPixel(4, 4));
        assertEquals(0, m.getPixel(11, 11));
    }

    @Test
    public void testDrawCircle() {
        Map2D m = new Map(20, 20, 0);
        Pixel2D center = new Index2D(10, 10);
        m.drawCircle(center, 4.0, 1); // רדיוס 4

        // המרכז חייב להיות צבוע
        assertEquals(1, m.getPixel(10, 10));

        // נקודה במרחק 3 (בתוך המעגל)
        assertEquals(1, m.getPixel(13, 10));

        // נקודה במרחק 5 (מחוץ למעגל)
        assertEquals(0, m.getPixel(15, 10));
    }

    @Test
    public void testRescale() {
        // מפה מקורית 2x2
        // [1, 2]
        // [3, 4]
        Map2D m = new Map(2, 2, 0);
        m.setPixel(0, 0, 1);
        m.setPixel(0, 1, 2);
        m.setPixel(1, 0, 3);
        m.setPixel(1, 1, 4);

        // הגדלה פי 2 (אמור לצאת 4x4)
        m.rescale(2.0, 2.0);

        assertEquals(4, m.getWidth());
        assertEquals(4, m.getHeight());

        // בדיקה שהערכים נשמרו (הפינה השמאלית עליונה עדיין 1)
        assertEquals(1, m.getPixel(0, 0));
        // בדיקה שהפינה הימנית תחתונה היא 4
        assertEquals(4, m.getPixel(3, 3));
    }


}