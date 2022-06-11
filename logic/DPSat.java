package logic;

import java.util.Iterator;

class DPSat
{
  static final int MAXITERATIONS=32;

  public static boolean DPSat1 (ClauseSet phi)
  throws Exception
  {
    for (int itCnt=0;itCnt<MAXITERATIONS;itCnt++)
    {
      System.out.format("DPSat1 iteration %d: %s\n", itCnt, phi.toString());

      // stopping conditions
      if (phi.empty()) { // no more clauses in phi
          System.out.println ("set is empty");
          return true;
      }                
      if (phi.containsEmptyClause()) {
        // empty clause not satisfiable
        System.out.println ("set contains empty clause: "+phi.toString());
        return false;
      }

      // Davis-Putnam procedure:

      // pick a literal occuring with both polarities in phi
      LitStat ls = new LitStat(phi);  // count literals
      ls.sortByName();
      int i, ni;
      Literal l=null;
      Literal nl=null;
      for (i=0; i<ls.size(); i++) {
        l = ls.getLiteral(i);
        nl = new Literal(l.name(),!l.neg());
        ni = ls.lookup(nl);
        if (ni >= 0) { 
          if (ls.getCount(i)>0 && ls.getCount(ni)>0)
            break; // found positive and negative one
        }
      }
      if (i >= ls.size()) {
        // did not find a literal with both polarities
        System.out.println ("cannot resolve any more: "+phi.toString());
        return true;
      }
      //System.out.println ("picked literal "+l.toString());
      ClauseSet res = new ClauseSet();

      // Loop through each Clause in formula and check for literal l
      Iterator<Clause> iter = phi.iterator();
      while (iter.hasNext()) {
        Clause c1 = iter.next();
        if (c1.contains(l)) {
          // Loop through each Clause and check for literal nl;
          // note that we also resolve clauses with themself, if they contain the
          // literal with both polarities !
          Iterator<Clause> iter2 = phi.iterator();
          Clause c2 = null;
          while (iter2.hasNext()) {
            c2 = iter2.next();
            if (c2.contains(nl)) {
              Clause r = c1.filter(l).merge(c2.filter(nl));
              System.out.println ("resolve "+c1.toString()+","+c2.toString()+" using "+l.toString()+
              " -> "+r.toString());
              // add resolved clause to res set
              if (!res.contains(r)) res.add(r);
            }
          }
        }
      }

      // Merge res set with phi
      phi = phi.merge(res);

      // Put all Clauses not containg l or ~l into a new Clause Set
      ClauseSet phi_n = new ClauseSet();
      iter = phi.iterator();
      while (iter.hasNext()) {
        Clause c = iter.next();
        if (!(c.contains(l)||c.contains(nl))) phi_n.add(c);
      }

      // over again !
      phi = phi_n;
    }

    throw new Exception ("DPSat1: MAXITERATIONS reached");
  }
}