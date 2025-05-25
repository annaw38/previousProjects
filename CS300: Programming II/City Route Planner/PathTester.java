//not required
//import java.util.ArrayList;
//import java.util.NoSuchElementException;
public class PathTester {
  public static boolean testPath() {
    Path p = new Path();
    Intersection i1 = new Intersection(0,0);
    Intersection i2 = new Intersection(0,1);
    Intersection i3 = new Intersection(1,1);
    p.addHead(i1);
    p.addTail(i2);
    p.addTail(i3);
    if(p.getHead().equals(i1) && p.getTail().equals(i3)) {
      return true;
    }
    return false;
     
  }
  public static boolean testLength() {
    Path p = new Path();
    Intersection i1 = new Intersection(0,0);
    Intersection i2 = new Intersection(0,1);
    Intersection i3 = new Intersection(1,1);
    p.addHead(i1);
    p.addTail(i2);
    p.addTail(i3);
    if(p.length() == 3) {
      return true;
    }
    return false;
     
  }
  public static boolean testToString() {
    Path p = new Path();
    Intersection i1 = new Intersection(0,0);
    Intersection i2 = new Intersection(0,1);
    Intersection i3 = new Intersection(1,1);
    p.addHead(i1);
    p.addTail(i2);
    p.addTail(i3);
    System.out.println(p.toString());
    if(p.toString().equals("(0,0)->(0,1)->(1,1)")) {
      return true;
    }
    return false;
     
  }
  
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println("testPath(): " + testPath());
    System.out.println("testLength(): " + testLength());
    System.out.println("testToString(): " + testToString());

  }

}
