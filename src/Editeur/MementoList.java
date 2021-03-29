package Editeur;

import java.util.LinkedList;

public class MementoList {

	   private LinkedList<Memento> mementoList;
	   
	   public MementoList()
	   {
		   mementoList = new LinkedList<Memento>();
	   }

	   public void add(Memento state){
	      mementoList.push(state);
	   }
	   
	   public LinkedList<Memento> getList(){
		  return mementoList;
	   }
	   
	   public Memento removeFront(){
		  return mementoList.remove(0);
	   }
	}

