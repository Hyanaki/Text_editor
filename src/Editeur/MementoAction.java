package Editeur;

public class MementoAction {
	private String state;
	   
	  public MementoAction(String state) 
	  {
		  this.state = state;
	  }

	   public Memento saveStateToMemento(){
	      return new Memento(state);
	   }
	   
}