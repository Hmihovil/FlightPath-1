package assignmentFive;

import org.junit.Test;

import static org.junit.Assert.*;

public class FlightPathTest {
	
	/**
	 * Testing constructor
	 * @throws Exception
	 */
    @Test
    public void testFlightPath() throws Exception {
        FlightPath fp1 = new FlightPath("0 6 90 8 135 4.24264 225 4.24264 180 2 61.93 8.5");
        FlightPath fp2 = new FlightPath("45 5.6 225 5.6 88.5 6.8");
        FlightPath fp3 = new FlightPath(fp1);
        FlightPath fp4 = new FlightPath(fp2);
        assertTrue(fp1.segments() == 6);
        assertTrue(fp2.segments() == 3);
        assertTrue(fp3.segments() == 6);
        assertTrue(fp4.segments() == 3);
        try{
        	FlightPath fp = new FlightPath("45 0 225 5.6 88.5 6.8");
        	fail("IllegalArgumentException should be thrown");
        }catch(IllegalArgumentException e){
        	//Success
        }
    }

    @Test
    public void testAddSegment() throws Exception {
        FlightPath fp = new FlightPath("0 6 90 8 135 4.24264 225 4.24264 180 2 61.93 8.5");
        fp.addSegment(10, 0, 9);
        fp.addSegment(70, 3, 12.5);
        assertTrue(fp.toString().equals("[(b=0.0, d=9.0), (b=90.0, d=8.0), (b=135.0, d=4.2426), (b=225.0, d=4.2426), (b=180.0, d=2.0), (b=61.93, d=8.5), (b=3.0, d=12.5)]"));
    }

    @Test
    public void testDeleteSegment() throws Exception {
        FlightPath fp = new FlightPath("0 6 90 8 135 4.24264 225 4.24264 180 2 61.93 8.5");
        fp.deleteSegment(10);
        fp.deleteSegment(60);
        fp.deleteSegment(70);
        assertTrue(fp.toString().equals("[(b=90.0, d=8.0), (b=135.0, d=4.2426), (b=225.0, d=4.2426), (b=180.0, d=2.0)]"));
    }

    @Test
    public void testGetBearing() throws Exception {
        FlightPath fp = new FlightPath("0 6 90 8 135 4.24264 225 4.24264 180 2 61.93 8.5");
        assertTrue(fp.getBearing(10) == 0);
        assertTrue(fp.getBearing(60) == 61.93);
        assertTrue(fp.getBearing(5) == -1);
    }

    @Test
    public void testTripLength() throws Exception {
        FlightPath fp1 = new FlightPath("0 6 90 8 135 4.24264 225 4.24264 180 2 61.93 8.5");
        FlightPath fp2 = new FlightPath("45 5.6 225 5.6 88.5 6.8");
        assertTrue(fp1.tripLength() == 32);
        assertTrue(fp2.tripLength() == 16);
    }

    @Test
    public void testDirectDistance() throws Exception {
        FlightPath fp1 = new FlightPath("0 6 90 8 135 4.24264 225 4.24264 180 2 61.93 8.5");
        FlightPath fp2 = new FlightPath("45 5.6 225 5.6 88.5 6.8");
        assertTrue(fp1.directDistance() - 14.53780772 < 0.00001);
        assertTrue(fp2.directDistance() - 10.42168679 < 0.00001);
    }

    @Test
    public void testCompress() throws Exception {
        FlightPath fp = new FlightPath("0 6 90 8 135 4.24264 225 4.24264 180 2 57.77 8.5");
        fp.compress();
        assertTrue(fp.toString().equals("[(b=53.1301, d=10.0), (b=180.0, d=6.0), (b=47.7411, d=9.7151)]"));
    }

    @Test
    public void testEquals() throws Exception {
        FlightPath fp1 = new FlightPath("0 6 90 8 135 4.24264 225 4.24264 180 2 61.93 8.5");
        FlightPath fp2 = new FlightPath("55.4 3");
        FlightPath fp3 = new FlightPath(fp1);
        assertFalse(fp1.equals(null));
        assertFalse(fp1.equals(fp2));
        assertTrue(fp1.equals(fp3));
    }

    @Test
    public void testToString() throws Exception {
        FlightPath fp1 = new FlightPath("0 6 90 8 135 4.24264 225 4.24264 180 2 61.93 8.5");
        FlightPath fp2 = new FlightPath("55.4 3");
        FlightPath fp3 = new FlightPath(fp1);
        FlightPath fp4 = new FlightPath(fp2);
        FlightPath fp5 = new FlightPath("");
        assertTrue(fp1.toString().equals("[(b=0.0, d=6.0), (b=90.0, d=8.0), (b=135.0, d=4.2426), (b=225.0, d=4.2426), (b=180.0, d=2.0), (b=61.93, d=8.5)]"));
        assertTrue(fp3.toString().equals("[(b=0.0, d=6.0), (b=90.0, d=8.0), (b=135.0, d=4.2426), (b=225.0, d=4.2426), (b=180.0, d=2.0), (b=61.93, d=8.5)]"));
        assertTrue(fp2.toString().equals("[(b=55.4, d=3.0)]"));
        assertTrue(fp4.toString().equals("[(b=55.4, d=3.0)]"));
        assertTrue(fp5.toString().equals("[]"));
    }

    @Test
    public void testDescription() throws Exception {
        FlightPath fp1 = new FlightPath("90 2 22.5 8.6");
        FlightPath fp2 = new FlightPath("55.4 3");
        FlightPath fp3 = new FlightPath("");
        assertTrue(fp1.description().equals("segment 10, bearing 90.0 degrees, distance 2.0\nsegment 20, bearing 22.5 degrees, distance 8.6"));
        assertTrue(fp2.description().equals("segment 10, bearing 55.4 degrees, distance 3.0"));
        assertTrue(fp3.description().equals("no path"));
    }


}