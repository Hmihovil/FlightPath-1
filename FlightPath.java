package assignmentFive;

import java.text.NumberFormat;
/**
 * Flight path class
 * @author MarkYu
 * @version 2017
 */
public class FlightPath {
	
	//head of the linked list
    private Segment head;
    
    /**
     * Constructor
     * @param s user's input string in pair of bearing and distance
     */
    public FlightPath(String s) {
        String[] numbers = s.split(" ");
        if (numbers.length > 1) {
            for (int i = 0; i < numbers.length; i += 2) {
                addSegment((i / 2 + 1) * 10, Double.parseDouble(numbers[i]), Double.parseDouble(numbers[i + 1]));
                if (Double.parseDouble(numbers[i + 1]) == 0) {
                	throw new IllegalArgumentException();
                }
            }
        }
        
    }
    
    /**
     * Constructor
     * @param fp flight path
     */
    public FlightPath(FlightPath fp) {
        Segment cfp = fp.head;
        while (cfp != null) {
            addSegment(cfp.id, cfp.bearing, cfp.distance);
            cfp = cfp.next;
        }
    }

    /**
     * The size of the list
     * @return the count of segment
     */
    public int segments() {
        Segment curr = head;
        int count = 0;
        while (curr != null) {
            count++;
            curr = curr.next;
        }
        return count;
    }

    /**
     * Adding a segment
     * @param id id of the segment
     * @param bearing bearing of the segment
     * @param distance distance of the segment
     */
    public void addSegment(int id, double bearing, double distance) {
        Segment ns = new Segment(bearing, distance, id);
        if (head == null) {
            head = ns;
        } else {
            Segment curr = head;
            do {
                if (curr.id == id) {
                    curr.bearing = bearing;
                    curr.distance = distance;
                    break;
                } else if (curr.id > id) {
                    int i = curr.id;
                    double b = curr.bearing;
                    double d = curr.distance;
                    Segment n = curr.next;
                    curr.id = id;
                    curr.bearing = bearing;
                    curr.distance = distance;
                    curr.next = ns;
                    ns.id = i;
                    ns.bearing = b;
                    ns.distance = d;
                    ns.next = n;
                    break;
                } else if (curr.next == null) {
                    curr.next = ns;
                }
                curr = curr.next;
            } while (curr != null);
        }
    }
    
    /**
     * Delete segment from flight path by id, return segment distance or 0.0 if segment absent
     * @param id of the segment
     * @return the distance 
     */
    public double deleteSegment(int id) {
        double d = 0.0;
        if (head.id == id) {
            d = head.distance;
            head = head.next;
            return d;
        }
        Segment curr = head;
        while (curr.next != null) {
            if (curr.next.id == id) {
                d = curr.next.distance;
                curr.next = curr.next.next;
                return d;
            }
            curr = curr.next;
        }
        return d;
    }

    /**
     * Get bearing of segment by id, return bearing if segment exist or -1 otherwise
     * @param id of the segment
     * @return -1 if the segment does not exist, otherwise, return the bearing
     */
    public double getBearing(int id) {
        Segment curr = head;
        while (curr != null) {
            if (curr.id == id)
                return curr.bearing;
            curr = curr.next;
        }
        return -1;
    }

    /**
     * Calculate and return trip length
     * @return the trip length
     */
    public double tripLength() {
        Segment curr = head;
        int length = 0;
        while (curr != null) {
            length += curr.distance;
            curr = curr.next;
        }
        return length;
    }

    /**
     * Calculate and return length from start and end points
     * @return the direct distance between 2 positions
     */
    public double directDistance() {
        Segment first = head;
        Segment curr = head.next;
        while (curr.next != null) {
            double cos = Math.cos(Math.PI * (curr.bearing - curr.next.bearing)) / 180.;
            first.distance = Math.sqrt(Math.pow(first.distance, 2) + Math.pow(curr.distance, 2) - 2 * first.distance * curr.distance * cos);
            curr = curr.next;
        }
        return first.distance;
    }

    /**
     * Compress flight path
     */
    public void compress() {
        Segment first = head;
        while (first != null && first.next != null) {
            Segment second = first.next;
            double d1 = first.distance;
            double d2 = second.distance;
            double b1 = first.bearing;
            double b2 = second.bearing;
            double cos = Math.cos(Math.toRadians(b1 - b2));
            first.distance = Math.sqrt(Math.pow(d1, 2) + Math.pow(d2, 2) - 2 * d1 * d2 * cos);
            first.bearing = first.bearing + Math.toDegrees(Math.acos((Math.pow(d1, 2) + Math.pow(first.distance, 2) - Math.pow(d2, 2)) / (2 * d1 * first.distance)));
            if (first.bearing > 180)
                first.bearing %= 180;
            deleteSegment(second.id);
            first = second.next;
        }
    }
    /**
     * Overriding the equal method
     * @return boolean value, true if 2 objects are equals, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        FlightPath other;
        if (obj == null)
            return false;
        if (obj instanceof FlightPath)
            other = (FlightPath)obj;
        else
            return false;
        Segment co = other.head;
        Segment ct = this.head;
        while(co != null && ct != null) {
            if (co.bearing != ct.bearing)
                return false;
            if (co.distance != ct.distance)
                return false;
            co = co.next;
            ct = ct.next;
        }
        if(co != null || ct != null)
            return false;
        return true;
    }
    /**
     * Overriding toString method
     * @return the string representation
     */
    @Override
    public String toString() {
        Segment curr = this.head;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(4);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (curr != null) {
            sb.append("(");
            String sbearing = nf.format(curr.bearing);
            sbearing = sbearing.replace(",", ".");
            if (!sbearing.contains("."))
                sbearing = sbearing + ".0";
            String sdistance = nf.format(curr.distance);
            sdistance = sdistance.replace(",", ".");
            if (!sdistance.contains("."))
                sdistance = sdistance + ".0";
            sb.append("b=").append(sbearing);
            sb.append(", ");
            sb.append("d=").append(sdistance);
            sb.append(")");
            if (curr.next != null)
                sb.append(", ");
            curr = curr.next;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * return the description for the path
     * @return the description for the path, return "no path" if there is non
     */
    public String description() {
        if (head != null)
            return head.description(head, "");
        else
            return "no path";
    }

    /**
     * Reverse flight path and return it as new
     * @param the flight path
     * @return a path
     */
    private static FlightPath reversePath(FlightPath a) {
        if (a.head != null)
            return a.head.reversePath(new FlightPath(""), a.head);
        return new FlightPath(a);
    }
    
/**
 * Linked list data structure
 * Private inner class Segment
 * @author MarkYu
 * @version 2017
 */
    private class Segment {
    	// bearing of the segment
        public double bearing;
        //distance of the segment
        public double distance;
        //id for the segment
        public int id;
        //the next field
        public Segment next;
        
        /**
         * Constructor
         * @param bearing the bearing of the segment
         * @param distance distance of the segment
         * @param id id of the segment
         */
        public Segment(double bearing, double distance, int id) {
            this.bearing = bearing;
            this.distance = distance;
            this.id = id;
        }
        
        /**
         * description for segment
         * @param s Segment
         * @param d String 
         * @return the description
         */
        public String description(Segment s, String d) {
            if (s == null)
                return d;

            if (!d.isEmpty())
                d += "\n";
            d += "segment " + s.id + ", bearing " + s.bearing + " degrees, distance " + s.distance;
            return description(s.next, d);
        }
        /**
         * Reverse path of an existing path
         * @param path FlightPath
         * @param s Segment
         * @return the path
         */
        private FlightPath reversePath(FlightPath path, Segment s) {
            if (s.next != null)
                reversePath(path, s.next);
            path.addSegment((path.segments() + 1) * 10, s.bearing, s.distance);
            return path;
        }
    }

}