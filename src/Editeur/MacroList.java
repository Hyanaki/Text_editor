package Editeur;

import java.util.ArrayList;

public class MacroList {
	
	private ArrayList<Macro> m_macroList;
	private int size;
	   
	   public MacroList()
	   {
		   m_macroList = new ArrayList<Macro>();
		   m_macroList.add(new Macro());
		   m_macroList.add(new Macro());
		   m_macroList.add(new Macro());
		   size = 0;
	   }
	   
	   public Macro get(int index){
	      return m_macroList.get(index);
	   }

}
