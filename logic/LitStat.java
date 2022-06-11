package logic;

import java.util.Iterator;

/**
 * The class LitStat implements a statistics collector
 * for literals in clauses and clause sets
 */
public class LitStat {
  /** Max num of literals */
  static final int MAXLIT = 20;

  /** actual number of literals */
  int nLiterals;

  /** an Array of Literals */
  Literal[] literals;

  /** Array of associated counts */
  int[] counts;

  /** lookup Literal and return index */
  public int lookup(Literal lit) {
    for (int i=0; i<nLiterals; i++) {
      if (literals[i].equals(lit)) return i;
    }
    return -1;
  }

  /** Constructor for empty LitStat
   * 
   * Initializes the arrays
   */
  public LitStat () {
    this.nLiterals = 0;
    this.literals = new Literal[MAXLIT];
    this.counts = new int[MAXLIT];
  }

  /** Constructor with ClauseSet as argument
   * 
   * Initializes the arrays
   */
  public LitStat (ClauseSet cs)
  throws Exception {
    this();
    add(cs);
  }

  /** getter for size
   * @return number of literals in stats array
   */
  public int size() {
    return nLiterals;
  }

  /** get count for specified Literal
   * @param literal Literal to lookup count for
   */
  public int getCount(Literal lit) {
    int i = lookup(lit);
    if (i<0) return 0;
    else return counts[i];
  }

  /** get count for specified index
   * @param idx index of Literal in table
   */
  public int getCount(int idx) {
    if (idx<0) return 0;
    else return counts[idx];
  }

  /** get Literal by index
   * @param idx index of Literal in table
   */
  public Literal getLiteral(int idx) {
    if (idx<0) return null;
    else return literals[idx];
  }
  
  /** add/count literal 
   * @param literal object to add to the Clause 
   */
  public void add(Literal lit) 
  throws Exception {
    // check for existing entry
    int i=lookup(lit);
    if (i<0) {
      // create new entry
      if (nLiterals >= MAXLIT)
        throw new Exception("LitStat.add: table overflow");
      i = nLiterals;
      literals[i] = lit;
      counts[i] = 0;
      nLiterals++;
    }

    // Increment count
    counts[i] ++;
    return;
  }

  /** add/count all literals of a Clause 
   * @param Clause
   */
  public void add(Clause clause) 
  throws Exception {
    Iterator<Literal> iter = clause.iterator();
    while (iter.hasNext()) {
      add(iter.next());
    }
  }

  /** add/count all literals of a ClauseSet 
   * @param ClauseSet
   */
  public void add(ClauseSet cs) 
  throws Exception {
    Iterator<Clause> iter = cs.iterator();
    while (iter.hasNext()) {
      add(iter.next());
    }
  }

  /** Conversion to String
   * 
   * Creates a String describing the statistics.
   * 
   * @return String description
   */
  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer(100);
    buf.append("Literal Stats:\n=================================\n");
    for (int i=0; i<nLiterals; i++) {
      Literal l = literals[i];
      buf.append(String.format("%c%-10s: %2d\n", l.neg()?'~':' ', l.name(), counts[i]));
    }
    buf.append("=================================\n");
    return buf.toString();
  }

  /** sort alphabetically by literal name */
  public void sortByName () {
    int nswaps;
    do {
      nswaps = 0;
      for (int i=0; i<nLiterals-1; i++) {
        Literal l1 = literals[i];
        Literal l2 = literals[i+1];
        if (l1.name().compareTo(l2.name())>0) {
          literals[i] = l2;
          literals[i+1] = l1;
          int count1 = counts[i];
          int count2 = counts[i+1];
          counts[i] = count2;
          counts[i+1] = count1;
          nswaps++;
        }
      }
    } while (nswaps > 0);
  }


}
