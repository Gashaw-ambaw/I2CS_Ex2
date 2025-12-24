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
    }