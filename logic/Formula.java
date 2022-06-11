package logic;
import java.util.Iterator;

/**
 * Instances of the class Formula represent logig formulas
 * consisting either of a tree of FormNode or a ClauseSet. 
 */
public class Formula {

  /** symbol table for formula variables */
  SymbolTable vars;

  /** root of FormNode tree */
  /* FormNode root */

  /** Clause Set*/
  ClauseSet clauseSet;

  /** The constructor
   * 
   * Creates an empty Formula instance
   * 
   */
  public Formula () {
    clauseSet = null;
    vars = new SymbolTable();
  }

  /** Obtain the symbol table
   * @return ref to the symbol table of the Formula
   */
  public SymbolTable symbolTable() {
    return vars;
  }

  /** Conversion to String
   * 
   * Creates a String describing the Formula.
   * 
   * @return String description
   */
  @Override
  public String toString() {
    if (clauseSet!=null) return clauseSet.toString();
    else return ("???");
  }

  /** Parse Formula from String 
   * @param str     String containing formula specification
   * @param form    Destination Formula
   * @return int  number of characters eaten on success
   *              0 if this is not a Formula
   *              -1 on parse error
   */
  public static int parse(String str, Formula form)
  throws Exception {
    ClauseSet cs = new ClauseSet();
    int rc = ClauseSet.parse(str,cs);
    if (rc > 0) {
      form.clauseSet = cs;
      // fill symbol table
      Iterator<Clause> citer = cs.iterator();
      while (citer.hasNext()) {
        Clause c = citer.next();
        Iterator<Literal> liter=c.iterator();
        while (liter.hasNext()) {
          Literal lit = liter.next();
          if (form.vars.lookup(lit.name())==null)
            form.vars.add(new Literal(lit.name,false));
        }
      }
    }
    return rc;
  }

  /** Evaluate Formula by Model
   * @param model Model containg the variable values
   * @return boolean compute result
   */
  public boolean eval(Model model) 
  throws Exception {
    if (clauseSet != null) return clauseSet.eval(model);
    else throw new Exception ("Formula.eval: no clause set");
  }

}
