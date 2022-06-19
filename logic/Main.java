
package logic;

import java.util.Iterator;
import java.util.Scanner;


/**
 * The Main class of the Logic project.
 * 
 * This class contains the main driver for the test cases
 * of the Logic project.
 *
 * @author Peter Sager
 * @version 2022 June 1
 */
public class Main {
  
  /** version number */
  static final String version_str = "0.0.2";

  /** The main method of the program
   * 
   * Creates some test objects and calls test methods.
   * Outputs test results.
   * 
   * @author Peter Sager
   * @param args cmdline arguments, unused for now
   */
  public static void main(String[] args) {
    System.out.println ("Welcome to LOGIC ! (Version "+version_str+")");

    // Create the global symbol table for formulas
    SymbolTable symtab = new SymbolTable();

    // Endless loop, interpreting and executing commands 
    // commands are: 
    //          ls                       - list table of formulas
    //          sh <form-id>             - show specified formula
    //          def <form-id> <formula>  - create new entry in form table
    //          tt <form-id>             - print truth table of formula
    //          dpsat <form-id>          - invoke DP SAT solver and print results
    //
    Scanner sc = new Scanner(System.in);
    String cmd = null;
    String cmds[] = null;
    do {
      // prompt
      System.out.print ("> ");
      // read command 
      cmd = sc.nextLine();
      cmds = cmd.split(" ");
      try {
        SymbolTable.Symbol sym;
        switch (cmds[0]) {
          case "ls":
            Iterator<SymbolTable.Symbol> iter = symtab.iterator();
            while (iter.hasNext()) {
              sym = iter.next();
              System.out.println (sym.toString());
            }
            System.out.format ("total %d symbols\n", symtab.size());
            break;
          case "sh":
            break;
          case "def":
            if (cmds.length != 3) {
              System.out.println ("Syntax Error: 2 arguments expected");
              break;
            }
            Formula form = new Formula();
            if (Formula.parse(cmds[2],form) > 0) {
              symtab.add(cmds[1],form);
            }
            else System.out.println ("Clause parse error");            
            break;
          case "tt":
            if (cmds.length != 2) {
              System.out.println ("Syntax Error: 1 argument expected");
              break;
            }
            sym = symtab.lookup(cmds[1]);
            if (sym != null) {
              TruthTable tt = new TruthTable();
              tt.compute(sym.form());
              System.out.println(tt.toString());
            }
            else System.out.format ("Symbol %s not found\n", cmds[1]);
            break;
          case "dpsat":
            if (cmds.length != 2) {
              System.out.println ("Syntax Error: 1 argument expected");
              break;
            }
            sym = symtab.lookup(cmds[1]);
            if (sym != null) {
              boolean res = DPSat.DPSat1(sym.form().clauseSet());
              System.out.println("result="+res);
            }
            else System.out.format ("Symbol %s not found\n", cmds[1]);  
            break;
          case "q":
            break; 
          case "":
            break;
          default:
            if (cmds.length > 0)
              System.out.println ("Syntax Error");
        }
      }
      catch (Exception e) {
        System.out.println (e.getMessage());
      }
    } while (!cmd.equals("q"));

    sc.close();

    System.out.println("Thank you for using LOGIC. Goodbye.");

  }

  static void tests () {

  /*    
    try {
    // test Literal
    Literal l1 = new Literal("p1", false);
    System.out.println ("l1="+l1.toString());

    // test Clause
    Clause c1 = new Clause();
    c1.add (new Literal("p1", false));
    c1.add (new Literal("p2", true));
    c1.add (new Literal("p3", false));
    c1.add (new Literal("p4", true));
    System.out.println ("c1="+c1.toString());
    Clause c2 = new Clause();
    c2.add (new Literal("p1", true));
    c2.add (new Literal("p2", false));
    c2.add (new Literal("p3", true));
    c2.add (new Literal("p4", true));
    System.out.println ("c2="+c2.toString());    

    // test Clause.filter
    Clause cf = c1.filter(new Literal("p2",true));
    System.out.println ("cf=c1\\~p2="+cf.toString());  
    
    // test ClauseSet
    ClauseSet cs1 = new ClauseSet();
    cs1.add (c1);
    cs1.add(c2);
    System.out.println ("cs1="+cs1.toString());

    // Test ClauseSet parser
    ClauseSet cs2 = new ClauseSet();
    if (ClauseSet.parse("{{q4,~q2},{~q4,q1},{q3,q2},{~q4,~q1},{~q3,q2}}", cs2) <= 0)
      System.out.println ("Clause parse error");
    System.out.println ("cs2="+cs2.toString());

    ClauseSet cs3 = new ClauseSet();
    if (ClauseSet.parse(
      "{{x1,~x5,x8},{x3},{~x1,x8,x4},{~x5,x2,x6},{x5,x4,~x2,x6},{~x5,x8,~x7},{~x8},{x1,x5,~x2},{~x4},{x5,~x2,x8}}", cs3) <= 0)
      System.out.println ("Clause parse error");
    System.out.println ("cs3="+cs3.toString());

    ClauseSet cs4 = new ClauseSet();
    if (ClauseSet.parse(
      "{{~p2,~p4},{~p5},{p1,p2,p3},{~p1,p5},{p4},{p1,p2,~p3}}", cs4) <= 0)
      System.out.println ("Clause parse error");
    System.out.println ("cs4="+cs4.toString());

    // test Model

    Model m = new Model();
    m.add("p1",1);
    m.add("p2",0);
    m.add("p3",1);
    m.add("p4",1);
    System.out.println ("Model:"+m.toString());
    System.out.println("c1.eval="+c1.eval(m));
    System.out.println("c2.eval="+c2.eval(m));
    System.out.println("cs1.eval="+cs1.eval(m));

    // test Literal Stats
    LitStat ls = new LitStat();
    ls.add(cs4);
    System.out.println (ls.toString());

    // test DPSat
    boolean res = DPSat.DPSat1(cs4);
    System.out.println("result="+res);
    }
    catch(Exception e) {
      System.out.println ("Exception: "+e.toString());
    }
    */
  }
  
}
