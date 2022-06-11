package logic;
import java.util.Iterator;

/**
 * Instances of the class TruthTable are used to build 
 * a table of logic values for every combination of input
 * var values (models) for a formula
 */
public class TruthTable {

  /** symbol table for input variables */
  SymbolTable vars;

  /** table of result values, has size 2**n where n is the number of input variables */
  boolean[] results;

  /** The constructor
   * 
   * Creates an empty TruthTable instance
   * 
   */
  public TruthTable () {
    vars = null;
    results = null;
  }

  /** Conversion to String
   * 
   * Creates a String with the full table listing
   * 
   * @return String description
   */
  @Override
  public String toString() {
    if (results==null)
      return "TruthTable not computed\n";
    StringBuffer buf = new StringBuffer();
    // head line with column names
    Iterator<SymbolTable.Symbol> iter = vars.iterator();
    while (iter.hasNext()) {
      buf.append(String.format("%6s ", iter.next().name()));
    }
    buf.append("result");
    buf.append ("\n");

    // delimiter
    for (int i=0; i<vars.size()+1; i++) buf.append("-------");
    buf.append ("\n");

    // data rows
    for (int i=0; i<results.length; i++) {
      for (int bi=0; bi<vars.size(); bi++) {
        buf.append("   "+(((i & (1<<bi)) != 0)?"1":"0")+"   ");
      }
      buf.append("   "+((results[i])?"1":"0")+"   ");
      buf.append ("\n");
    }

    // end delimiter
    for (int i=0; i<vars.size()+1; i++) buf.append("-------");
    buf.append ("\n");

    return buf.toString();
  }

  /** Build the truth table for a specified formula
   * @param form    Formula
   */
  public void compute(Formula form)
  throws Exception
  {
    vars = form.symbolTable();
    // allocate results table
    results = new boolean[1<<vars.size()];
    // build model
    Model m = new Model();
    Iterator<SymbolTable.Symbol> iter = vars.iterator();
    while (iter.hasNext()) {
      SymbolTable.Symbol symbol = iter.next();
      m.add(symbol.name(),0);
    }

    // Loop through whole table and compute results
    for (int i=0; i<results.length; i++) {
      for (int bi=0; bi<vars.size(); bi++) {
        m.set(bi, (i & (1<<bi)) != 0);
      }
      results[i] = form.eval(m);
    }
  }
  

}
