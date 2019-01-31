# FlightPath

The class FlightPath to model a flight path as a sequence of straight-line segments in 2D space.  Each segment is defined by three fields: a bearing, a distance (both doubles), and an id number (type int).  The bearing is an angle in degrees, where zero degrees corresponds to north, 90 degrees to east, 180 degrees to south, etc. The distance is the length of the segment.  Each segment can be identified by an id number.  

Construct a FlightPath based on a string which contains a series of numbers separated by whitespace. The numbers are always in pairs: a bearing followed by a distance. For instance:
- FlightPath fp = new FlightPath( "0  6  90  8  135  4.24264  225  4.24264  180  2  61.93  8.5" );

Notice that the parameter does not specify id numbers.  Instead, the constructor initializes these.  Segments are added to the flight path in the order specified by the parameter string, and id numbers are added beginning with 10 for the first segment, 20 for the second, etc.
- FlightPath fp = new FlightPath( "45  5.6  225  5.6  88.5  6.8" );

A JUnit test class is also included to test the program with various test cases.
