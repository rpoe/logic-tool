package logic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The class ClauseSet is used to implement a set of logic clauses
 */
public class ClauseSet {
  /** an Array of Clauses */
  ArrayList<Clause> clauseList;

  /** The constructor
   * 
   * Creates an empty ClauseSet instance
   * 
   * @author Peter Sager
   */
  public ClauseSet () {
    this.clauseList = new ArrayList<Clause>();
  }

  /** Copy constructor
   * 
   * Creates a ClauseSet containing all elements of source ClauseSet
   * @param orig  original ClauseSet
   */
  public ClauseSet (ClauseSet orig) {
    this();
    Iterator<Clause> iter = orig.iterator();
    while (iter.hasNext()) {
      Clause c = iter.next();
      clauseList.add(c);
    }
  }

  /** getter for size
   * @return number of clauses in set
   */
  public int size() {
    return clauseList.size();
  }

  /** getter for i-th element
   * @return i-th Clause
   */
  public Clause get(int i) {
    return clauseList.get(i);
  }

  /** check for empty clause set
   * @return true if set is empty
   */
  public boolean empty() {
    return size() == 0;
  }

  /** add clause 
   * @param clause object to add to the ClauseSet
   */
  public void add(Clause clause) {
    clauseList.add(clause);
  }

  /** obtain Iterator for Clauses
   * @return Iterator<Clause>
   */
  public Iterator<Clause> iterator() {
    return clauseList.iterator();
  }

  /** Conversion to String
   * 
   * Creates a String describing the ClauseSet.
   * 
   * @author Peter Sager
   * @return String description
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer(100);
    buf.append("{");
    
    Iterator<Clause> iter = clauseList.iterator();
    while (iter.hasNext()) {
      Clause clause = iter.next();
      buf.append(clause.toString());
      if (iter.hasNext()) buf.append(",");
    }
    buf.append ("}");
    return buf.toString();
  }

  /** Check for an empty Clause
   * 
   * Check wether the set contains an empty clause
   * 
   * @author Peter Sager
   * @return boolean true if contains an emopty clause
   */
  public boolean containsEmptyClause () {
    Iterator<Clause> iter = clauseList.iterator();
    while (iter.hasNext()) {
      Clause clause = iter.next();
      if (clause.empty()) return true;
    }
    return false;
  }

  /** check for Clause
   *  Checks wether the set contains the specified clause
   * @param c
   * @return boolean true if ClauseSet contains the Clause
   */
  public boolean contains(Clause c) {
    Iterator<Clause> iter = clauseList.iterator();
    while (iter.hasNext()) {
      if (iter.next().equals(c)) return true;
    }
    return false;
  }

  /** Parse ClauseSet from String 
   * @param clstr String containing clause set specification
   * @param cs    Destination ClauseSet
   * @return int  number of characters eaten on success
   *              0 if this is not a ClauseSet
   *              -1 on parse error
   */
  public static int parse(String csstr, ClauseSet cs) 
  throws Exception {
    if (csstr.length() < 2) return -1;
    int ci = 0;
    char c = csstr.charAt(ci++);
    if (c != '{') return 0; // not a clause set
    while (csstr.length()>ci) {
      c = csstr.charAt(ci);
      if (c == '}') { ci++; break; }
      Clause cl = new Clause();
      int rc = Clause.parse(csstr.substring(ci), cl);
      if (rc <= 0) return -1;
      cs.add (cl);
      ci += rc;
      c = csstr.charAt(ci);
      if (c == ',') { ci++; }
    }
    return ci;
  }

  /** Merge Clause Sets
   * 
   * Creates a copy of the clause set, which contains all elements (clauses)
   * of both clause sets, but no dup elements
   * 
   * @param cs2 ClauseSet to merge with
   * @return new ClauseSet
   */
  public ClauseSet merge(ClauseSet cs2) 
  throws Exception {
    ClauseSet nCs = new ClauseSet(this);
    Iterator<Clause> iter = cs2.iterator();
    while (iter.hasNext()) {
      Clause c = iter.next();
      if (!this.contains(c)) {
        nCs.add(c);
      }
    }
    return nCs;
  }

  /** Evaluate ClauseSet by Model
   * @param model Model containg the variable values
   * @return boolean ClauseSet compute result
   */
  public boolean eval(Model model) 
  throws Exception {
    boolean result=true;
    Iterator<Clause> iter = iterator();
    while (iter.hasNext()) {
      Clause c = iter.next();
      result = result && c.eval(model);
    }
    return result;
  }

}
