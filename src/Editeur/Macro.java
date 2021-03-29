package Editeur;

import java.util.LinkedList;

import Commands.Command;

public class Macro {
	
	private LinkedList<Command> m_commandList;
	   
	   public Macro()
	   {
		   m_commandList = new LinkedList<Command>();
	   }

	   public void add(Command command){
		   m_commandList.add(command);
	   }
	   
	   public LinkedList<Command> getList(){
		  return m_commandList;
	   }

}
