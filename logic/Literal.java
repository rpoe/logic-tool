package logic;

/**
 * For now: class Literal is used for Clause and ClauseSet
 * and to implement the first trial version of the DP Algorithm
 */
public class Literal {
  /** the variable name of the literal */
  String name;

  /** flag for negated literal */
  boolean neg;

  /** constructor for empty object
   * 
   * Creates a Literal instance
   */
  public Literal () {
    this.name = null;
    this.neg = false;
  }

  /** The constructor
   * 
   * Creates a Literal instance with specified name and neg flag
   * 
   * @author Peter Sager
   * @param name  the variable name of this literal
   * @param neg   flag for negated literal
   */
  public Literal (String name, boolean neg) {
    this.name = name;
    this.neg = neg;
  }

  /** getter for name
   * @return variable name
   */
  public String name() {
    return this.name;
  }

  /** getter for neg flag
   * @return boolean neg flag
   */
  public boolean neg() {
    return this.neg;
  }

  /** Equals
   * Two Literals are considered equal if they have equal name and equal polarity
   * @param o Object to compare with
   * @return boolean true if Object is a Literal and Literals are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o.getClass() != Literal.class) return false;
    Literal l = (Literal)o;
    return l.name.equals(this.name) && l.neg == this.neg;
  }

  /** Parse Literal from String 
   * @param litstr String containg literal specification
   * @param lit    Destination Literal
   * @return int  number of characters eaten on success
   *              0 if this is not a Literal
   *              -1 on parse error
   */
  public static int parse(String litstr, Literal lit) {
    StringBuffer namebuf = new StringBuffer();
    if (litstr.length() == 0) return -1;
    int ci = 0;
    char c = litstr.charAt(ci);
    if (c == '~') {
      lit.neg = true; ci++;
    }
    else lit.neg = false;
    c = litstr.charAt(ci++);
    if (!(c>='a'&&c<='z')) return 0;
    namebuf.append(c);
    while (litstr.length()>ci) {
      c = litstr.charAt(ci);
      if (!(c>='a'&&c<='z' || c>='0'&&c<='9')) break;
      namebuf.append(c);
      ci++;
    }
    lit.name = namebuf.toString();
    return ci;
  }

  /** Conversion to String
   * 
   * Creates a String describing the Literal. Can be used in Clause etc.
   * to build complex descriptions.
   * Just returns the variable name of the literal, optionally preceded by ~.
   * 
   * @author Peter Sager
   * @return String description
   */
  @Override
  public String toString() {
    if (neg) return "~"+this.name;
    else return this.name;
  }

}
