package logic;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The class Model implements a model for a set of logic variables
 * It uses the Literal class 
 */
public class Model {

  /** Array of Literals is used for name and vlaue of variables */
  ArrayList<Literal> values;

  /** The constructor
   * 
   * Creates an empty Model instance
   * 
   */
  public Model () {
    this.values = new ArrayList<Literal>();
  }

  /** add a name,value
   */
  void add(String name, boolean value) {
    values.add(new Literal(name,!value));
  }

  /** add a name,value
   */
  void add(String name, int value) {
    //System.out.println ("Model.add int "+name+" "+value);
    values.add(new Literal(name,(boolean)(value==0)));
  }

  /** set value of i-th variable */
  void set(int i, boolean val) {
    values.get(i).neg = !val;
  }

  /** get an iterator
   * @return Iterator<Literal>
   */
  public Iterator<Literal> iterator() {
    return values.iterator();
  }

  /** lookup Literal name in model
   * @param name
   * @return the Literal or null if not found
   */
  public Literal lookup(String name) {
    Iterator<Literal> iter = values.iterator();
    while (iter.hasNext()) {
      Literal lit = iter.next();
      if (lit.name().equals(name)) return lit;
    }
    return null;
  }

  /** Conversion to String
   * 
   * Creates a String describing the Model.
   * @return String description
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();
    Iterator<Literal> iter = values.iterator();
    while (iter.hasNext()) {
      Literal lit = iter.next();
      if (buf.length()>0) buf.append(",");
      buf.append(String.format("%s=%s", lit.name(),(lit.neg()==true?"0":"1")));
    }
    return buf.toString();
  }

}
