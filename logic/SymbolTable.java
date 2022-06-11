package logic;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * The class SymbolTable implements a table of symbols.
 * A Symbol has a name and an associated variable literal or a formula.
 */
public class SymbolTable {

  public enum SymType {
    FORM,
    VAR
  }

  public class Symbol {
    SymType type;
    String name;
    Literal lit;
    Formula form;
    public Symbol (Literal lit) {
      this.name = lit.name();
      this.type = SymType.VAR;
      this.lit = lit;
    }
    public Symbol (String name, Formula form) {
      this.name = name;
      this.type = SymType.FORM;
      this.form = form;
    }
    public SymType type() { return this.type; }
    public String name() { return this.name; }
    public Literal lit() { return this.lit; }
    public Formula form() { return this.form; }
    public String toString() {
      switch (this.type) {
        case FORM:
          return this.name+"="+this.form;
        case VAR:
          return this.name;
      }
      return "???";
    }
  }

  ArrayList<Symbol> table;

  public SymbolTable() {
    table = new ArrayList<Symbol>();
  }

  public Iterator<Symbol> iterator() {
    return table.iterator();
  }

  public int size() { return table.size(); }

  public Symbol lookup(String name) {
    Iterator<Symbol> iter = table.iterator();
    while (iter.hasNext()) {
      Symbol sym = iter.next();
      if (sym.name().equals(name))
        return sym;
    }
    return null;
  }

  void add(String name, Formula form)
  throws Exception {
    if (lookup(name)!= null) {
      throw new Exception ("Formula "+name+" exists");
    }
    Symbol sym = new Symbol (name, form);
    table.add(sym);
  }

  void add(Literal lit)
  throws Exception {
    if (lookup(lit.name())!= null) {
      throw new Exception ("Variable "+lit.name()+" exists");
    }
    Symbol sym = new Symbol (lit);
    table.add(sym);
  }

}
