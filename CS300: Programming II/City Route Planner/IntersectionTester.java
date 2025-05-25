// not required
public class IntersectionTester {
  
  public static boolean testGetX() {
    Intersection i = new Intersection(0,4);
    if(i.getX() == 0) {
      return true;
    }
    return false;
  }
  public static boolean testGetY() {
    Intersection i = new Intersection(0,4);
    if(i.getY() == 4) {
      return true;
    }
    return false;
    
  }
  
  public static boolean testToString() {
    Intersection i = new Intersection(0,4);
    if(i.toString().equals("(0,4)")) {
      System.out.println(i.toString());
      return true;
    }
    return false;
    
  }
  public static boolean testEquals() {
    Intersection i = new Intersection(0,4);
    Intersection j = new Intersection(0,0);
    if(!i.equals(j)) {
      return true;
    }
    return false;
  }
  public static boolean testGoNorth() {
    Intersection j = new Intersection(0,0);
    Intersection k = new Intersection(0,1);
    if(j.goNorth().equals(k)) {
      return true;
    }
    return false;
  }
  public static boolean testGoSouth() {
    Intersection j = new Intersection(0,0);
    Intersection k = new Intersection(0,-1);
    
    if(j.goSouth().equals(k)) {
      return true;
    }
    return false;
  }
  public static boolean testGoEast() {
    Intersection j = new Intersection(0,0);
    Intersection k = new Intersection(1,0);
    if(j.goEast().equals(k)) {
      return true;
    }
    return false;
  }
  public static boolean testGoWest() {
    Intersection j = new Intersection(0,0);
    Intersection k = new Intersection(-1,0);
    if(j.goWest().equals(k)) {
      return true;
    }
    return false;
  }
  

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println("testGetX(): " + testGetX());
    System.out.println("testGetY(): " + testGetY());
    System.out.println("testToString(): " + testToString());
    System.out.println("testEquals(): " + testEquals());
    System.out.println("testGoNorth(): " + testGoNorth());
    System.out.println("testGoSouth(): " + testGoSouth());
    System.out.println("testGoEast(): " + testGoEast());
    System.out.println("testGoWest(): " + testGoWest());
    
  }

}
