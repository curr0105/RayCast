package raycast.animator;

import java.util.Objects;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author kathy
 */
public class TestAbstractAnimator {

    private AbstractAnimator a;
    
    @Before
    public void setup() {
        a = new TextAnimator();
    }

    @Test
    public void testABADIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 200, 100, 100, 100, 100, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 100, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 0.0, 0.00001);
    }

    @Test
    public void testABFHIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 200, 100, 150, 100, 150, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 150, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 0.5, 0.00001);
    }

    @Test
    public void testABBCIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 200, 100, 200, 100, 200, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 200, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 1.0, 0.00001);
    }

    @Test
    public void testBAADIntersect() {

        boolean doesIntersect = a.getIntersection(200, 100, 100, 100, 100, 100, 100, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 100, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 1.0, 0.00001);
    }

    @Test
    public void testBAFHIntersect() {

        boolean doesIntersect = a.getIntersection(200, 100, 100, 100, 150, 100, 150, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 150, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 0.5, 0.00001);
    }

    @Test
    public void testBABCIntersect() {

        boolean doesIntersect = a.getIntersection(200, 100, 100, 100, 200, 100, 200, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 200, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 0.0, 0.00001);
    }

    @Test
    public void testABIDIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 200, 100, 100, 150, 100, 200);
        double[] result = a.intersect();
        assertFalse(doesIntersect);
    }

    @Test
    public void testABEHIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 200, 100, 150, 150, 150, 200);
        double[] result = a.intersect();
        assertFalse(doesIntersect);
    }

    @Test
    public void testABGCIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 200, 100, 200, 150, 200, 200);
        double[] result = a.intersect();
        assertFalse(doesIntersect);
    }

    @Test
    public void testABIGIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 200, 100, 100, 150, 200, 150);
        double[] result = a.intersect();
        assertFalse(doesIntersect);
    }

    @Test
    public void testBAIDIntersect() {

        boolean doesIntersect = a.getIntersection(200, 100, 100, 100, 100, 150, 100, 200);
        double[] result = a.intersect();
        assertFalse(doesIntersect);
    }

    @Test
    public void testBAEHIntersect() {

        boolean doesIntersect = a.getIntersection(200, 100, 100, 100, 150, 150, 150, 200);
        double[] result = a.intersect();
        assertFalse(doesIntersect);
    }

    @Test
    public void testBAGCIntersect() {

        boolean doesIntersect = a.getIntersection(200, 100, 100, 100, 200, 150, 200, 150);
        double[] result = a.intersect();
        assertFalse(doesIntersect);
    }

    @Test
    public void testBAIGIntersect() {

        boolean doesIntersect = a.getIntersection(200, 100, 100, 100, 100, 150, 200, 150);
        double[] result = a.intersect();
        assertFalse(doesIntersect);
    }

    @Test
    public void testAFADIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 150, 100, 100, 100, 100, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 100, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 0.0, 0.00001);
    }

    @Test
    public void testAFFHIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 150, 100, 150, 100, 150, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 150, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 1.0, 0.00001);
    }

    @Test
    public void testAFBCIntersect() {

        boolean doesIntersect = a.getIntersection(100, 100, 150, 100, 200, 100, 200, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 200, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 2.0, 0.00001);
    }

    @Test
    public void testFAADIntersect() {

        boolean doesIntersect = a.getIntersection(150, 100, 100, 100, 100, 100, 100, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 100, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 1.0, 0.00001);
    }

    @Test
    public void testFAFHIntersect() {

        boolean doesIntersect = a.getIntersection(150, 100, 100, 100, 150, 100, 150, 200);
        double[] result = a.intersect();
        assertTrue(doesIntersect);
        assertEquals(result[0], 150, 0.00001);
        assertEquals(result[1], 100, 0.00001);
        assertEquals(result[2], 0.0, 0.00001);
    }

    @Test
    public void testFABCIntersect() {

        boolean doesIntersect = a.getIntersection(150, 100, 100, 100, 200, 100, 200, 200);
        double[] result = a.intersect();
        assertFalse(doesIntersect);
    }

    @Test
    public void testForException() {

        try {
          a.intersect();
        } catch (IllegalStateException ex) {
            System.out.println("Illegal State Exception" + ex);
            Assert.fail();
        } catch (NullPointerException e) {
            System.out.println("IntersectResult array must be initilized in the constructor");
            Assert.fail();
        }
    }

}
