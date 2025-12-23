package assignments.Ex2; // <--- הוספנו את השורה הזו, היא קריטית!

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Index2DTest {

    // משתנה עזר לדיוק של השוואת מספרים עשרוניים
    final double EPS = 0.0001;

    @Test
    void testDistance_NormalCase() {
        Index2D p1 = new Index2D(0, 0);
        Index2D p2 = new Index2D(3, 4);

        assertEquals(5.0, p1.distance2D(p2), EPS, "Distance between (0,0) and (3,4) should be 5");
    }

    @Test
    void testDistance_ZeroDistance() {
        Index2D p1 = new Index2D(10, 20);

        assertEquals(0.0, p1.distance2D(p1), EPS, "Distance to self must be 0");
    }

    @Test
    void testDistance_NegativeCoordinates() {
        Index2D p1 = new Index2D(-1, -1);
        Index2D p2 = new Index2D(2, 3);

        assertEquals(5.0, p1.distance2D(p2), EPS, "Distance calculation should work with negative numbers");
    }

    @Test
    void testDistance_Symmetry() {
        Index2D p1 = new Index2D(100, 5);
        Index2D p2 = new Index2D(10, 50);

        assertEquals(p1.distance2D(p2), p2.distance2D(p1), EPS, "Distance must be symmetric");
    }

    @Test
    void testEquals_Basic() {
        // בדיקה בסיסית: שתי נקודות עם אותם ערכים חייבות להיות שוות
        Index2D p1 = new Index2D(5, 5);
        Index2D p2 = new Index2D(5, 5);

        // משתמשים ב-assertTrue כדי לוודא שפונקציית ה-equals מחזירה אמת
        assertTrue(p1.equals(p2), "Points with same (x,y) should be equal");
    }

    @Test
    void testEquals_DifferentValues() {
        // בדיקה שנקודות שונות באמת מחזירות False
        Index2D p1 = new Index2D(5, 5);
        Index2D p2 = new Index2D(5, 6); // y שונה
        Index2D p3 = new Index2D(4, 5); // x שונה

        assertFalse(p1.equals(p2), "Points with different y should not be equal");
        assertFalse(p1.equals(p3), "Points with different x should not be equal");
    }

    @Test
    void testEquals_CornerCases() {
        // בדיקות סוגי נתונים שונים ו-Null
        Index2D p1 = new Index2D(1, 1);

        // 1. האם זה קורס כשמשווים ל-null? (אסור שיקרוס, צריך להחזיר false)
        assertFalse(p1.equals(null), "Should return false when comparing to null");

        // 2. האם זה קורס כשמשווים למשהו שהוא לא נקודה בכלל? (למשל מחרוזת)
        assertFalse(p1.equals("I am not a point"), "Should return false when comparing to a String");
    }

    @Test
    void testToString() {
        // וידוא שההדפסה יוצאת בדיוק בפורמט המבוקש
        Index2D p = new Index2D(3, 7);
        String expected = "(3,7)";

        assertEquals(expected, p.toString(), "toString format should be (x,y)");
    }
}