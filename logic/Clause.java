package logic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The class Clause is used to implelemnt logic clauses
 * which are simply sets of Literals
 */
public class Clause {
  /** an Array of Literals */
  ArrayList<Literal> litList;

  /** The constructor
   * 
   * Creates an empty Clause instance
   */
  public Clause () {
    this.litList = new ArrayList<Literal>();
  }

  /** Copy constructor
   * 
   * Creates an Clause containing all literals of source Clause
   * @param orig  original Clause
   */
  public Clause (Clause orig) {
    this();
    Iterator<Literal> iter = orig.iterator();
    while (iter.hasNext()) {
      Literal lit = iter.next();
      litList.add(lit);
    }
  }

  /** getter for size
   * @return number of literals in Clause
   */
  public int size() {
    return litList.size();
  }

  /** check for empty clause
   * @return true if clause is empty
   */
  public boolean empty() {
    return size() == 0;
  }

  /** getter for i-th element
   * @return i-th Litearl
   */
  public Literal getLiteral(int i) {
    return litList.get(i);
  }

  /** obtain Iterator for Clauses
   * @return Iterator<Clause>
   */
  public Iterator<Literal> iterator() {
    return litList.iterator();
  }
  
  /** Equals
   * Two Clauses are considered equal if they have equal number of elements
   * and each element of c1 is also contained in c2 (sequence does not matter)
   * @param o Object to compare with
   * @return boolean true if Object is a Clause and Clauses are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass() != Clause.class) return false;
    Clause c = (Clause)o;
    if (c.size() != this.size()) return false;
    Iterator<Literal> iter = this.iterator();
    while (iter.hasNext()) {
      Literal lit = iter.next();
      if (!c.contains(lit)) return false;
    }
    return true;
  }

  /** add literal 
   * @param literal object to add to the Clause 
   */
  public void add(Literal lit) 
  throws Exception {
    if (this.contains(lit)) throw new Exception ("Clause.add: Literal exists: "+lit.toString());
    litList.add(lit);
  }

  /** add by String description
   * @param litstr string describing the literal
   */
  public void add(String litstr) 
  throws Exception {
    Literal lit = new Literal();
    int rc = Literal.parse(litstr, lit);
    System.out.format ("parsed literal %s rc=%d\n", litstr, rc);
    if (rc <= 0) throw new Exception ("could not parse literal");
    this.add(lit);
  }

  /** Conversion to String
   * 
   * Creates a String describing the Clause.
   * 
   * @author Peter Sager
   * @return String description
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer(100);
    buf.append("{");
    
    Iterator<Literal> iter = litList.iterator();
    while (iter.hasNext()) {
      Literal lit = iter.next();
      buf.append(lit.toString());
      if (iter.hasNext()) buf.append(",");
    }
    buf.append ("}");
    return buf.toString();
  }

  /** Filter Literal 
   * 
   * Creates a copy of the clause, but remove the specified
   * Literal from the new clause.
   *   nC := C \ {L}
   * @param filter Literal to be filtered out
   * @return new Clause
   */
  public Clause filter(Literal filter)
  throws Exception {
    Clause nC = new Clause();
    Iterator<Literal> iter = litList.iterator();
    while (iter.hasNext()) {
      Literal l = iter.next();
      if (!l.equals(filter)) {
        nC.add(l);
      }
    }
    return nC;
  }

  /** Merge clauses
   * 
   * Creates a copy of the clause, which contains all elements
   * of both clauses, but no dup elements
   * 
   * @param c2 Clause to merge with
   * @return new Clause
   */
  public Clause merge(Clause c2) 
  throws Exception {
    Clause nC = new Clause(this);
    Iterator<Literal> iter = c2.iterator();
    while (iter.hasNext()) {
      Literal l = iter.next();
      if (!this.contains(l)) {
        nC.add(l);
      }
    }
    return nC;
  }

  /** check for Literal 
   *  Checks wether the Clause contains the specified literal
   * @param lit
   * @return boolean true if Clause contains the literal
   */
  public boolean contains(Literal lit) {
    Iterator<Literal> iter = litList.iterator();
    while (iter.hasNext()) {
      Literal l = iter.next();
      if (l.equals(lit)) return true;
    }
    return false;
  }

  /** Parse Clause from String 
   * @param clstr String containing clause specification
   * @param cl    Destination Clause
   * @return int  number of characters eaten on success
   *              0 if this is not a Clause
   *              -1 on parse error
   */
  public static int parse(String clstr, Clause cl) 
  throws Exception {
    if (clstr.length() < 2) return -1;
    int ci = 0;
    char c = clstr.charAt(ci++);
    if (c != '{') return 0; // not a clause
    while (clstr.length()>ci) {
      c = clstr.charAt(ci);
      if (c == '}') { ci++; break; }
      Literal lit = new Literal();
      int rc = Literal.parse(clstr.substring(ci), lit);
      if (rc <= 0) return -1;
      cl.add (lit);
      ci += rc;
      c = clstr.charAt(ci);
      if (c == ',') { ci++; }
    }
    return ci;
  }

  /** Evaluate Clause by Model
   * @param model Model containg the variable values
   * @return boolean Clause compute result
   */
  public boolean eval(Model model) 
  throws Exception {
    boolean result=false;
    Iterator<Literal> iter = litList.iterator();
    while (iter.hasNext()) {
      Literal l = iter.next();
      Literal mv = model.lookup(l.name());
      if (mv == null)
        throw new Exception (String.format("Clause.eval: model has no variable %s", l.name()));
      boolean val = (l.neg() && mv.neg()) || (!l.neg() && !mv.neg());
      result = result || val;
    }
    return result;
  }


}
