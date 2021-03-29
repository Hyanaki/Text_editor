package Editeur;

import java.util.Iterator;

import Commands.Command;

public class Register {

	private GUI m_gui;
	private MementoList m_memlistundo;
	private MementoList m_memlistredo;
	private MacroList m_macrolist;
	private int indicemacro;
	
	public Register(GUI gui)
	{
		m_gui = gui;
		m_memlistundo = new MementoList();
		m_memlistredo = new MementoList();
		m_macrolist = new MacroList();
		indicemacro = 0;
	}
	
	public void enregistrerMacro()
	{
		// Recuperation de l'indice de la macro qui a appelee la commande
		indicemacro = m_gui.getIndiceMacro();
		// On supprime les commandes de la macro si elle a ete deja enregistree
		m_macrolist.get(indicemacro).getList().clear();
		
		// On commence l'enregistrement seulement s'il est possible d'en lancer un
		if(m_gui.isStartRecordingEnabled())
    	{
			m_gui.setRecording(true);
			// Desactive les autres boutons du menu macro sauf l'arret de cette macro
			m_gui.disableStartRecording();
    	}
	}
	
	public void stopEnregistrementMacro()
	{
		// Recuperation de l'indice de la macro qui a appelee la commande
		indicemacro = m_gui.getIndiceMacro();
		
		// On termine l'enregistrement seulement s'il est possible d'en terminer un
		if(m_gui.isStopRecordingEnabled())
    	{
			m_gui.setRecording(false);
			// On reactive les boutons d'enregistrement et de lecture des macros et desactive les boutons d'arret d'enregistrement
			m_gui.disableStopRecording();
    	}
	}
	
	public void rejouerMacro()
	{
		// Recuperation de l'indice de la macro qui a appelee la commande
		indicemacro = m_gui.getIndiceMacro();
		// Parcours des commandes de la macro pour les rejouer
		for(Iterator<Command> c = m_macrolist.get(indicemacro).getList().iterator(); c.hasNext();) {
			
		    Command com = c.next();
		    com.execute();	    
		} 
	}
	
	public void addCommandMacro(Command command, int indice)
	{
		m_macrolist.get(indice).add(command);
	}
	
	public void undo()
	{
		// S'il est possible d'effectuer un retour en arriere
		if(m_gui.isMenuUndoEnabled())
    	{
			// Si la liste des undo n'est pas vide
			if(!m_memlistundo.getList().isEmpty())
			{
				// On retire de la liste des undo la dernière action effectuee
				Memento mem = m_memlistundo.removeFront();
	
				// On recupere l'etat du text au moment de la derniere commande
				String state = mem.getState();
				// Si la liste des undo devient vide on desactive le bouton associe
				if(m_memlistundo.getList().isEmpty())
					m_gui.setMenuUndoEnabled(false);
				
				// On ajoute l'etat actuel (avant de retourner en arriere) dans la liste des redo
				addRedoMemento();
				
				// On restaure le texte recuperee dans la liste de undo
				m_gui.setText(state);
			}
			// On active le bouton Redo
			m_gui.setMenuRedoEnabled(true);
    	}
	}
	
	public void redo()
	{
		// S'il est possible d'effectuer d'avancer
		if(m_gui.isMenuRedoEnabled())
    	{
			// Si la liste des redo n'est pas vide
			if(!m_memlistredo.getList().isEmpty())
			{
				// On retire de la liste des redo la dernière action ajoutee
				Memento mem = m_memlistredo.removeFront();

				// On recupere l'etat du text au moment du memento
				String state = mem.getState();
				// Si la liste des redo devient vide on desactive le bouton associe
				if(m_memlistredo.getList().isEmpty())
					m_gui.setMenuRedoEnabled(false);

				// On ajoute l'etat actuel (avant d'avancer) dans la liste des undo
				addUndoMemento();

				// On restaure le texte recuperee dans la liste de redo
				m_gui.setText(state);
			}
			// On active le bouton Undo
			m_gui.setMenuUndoEnabled(true);
    	}
	}
	
	/**
	 * Initialize the undo list with one entry of an empty string if the list is empty
	 */
	public void initializeUndoList()
	{
		if(m_memlistundo.getList().isEmpty())
		{
		    MementoAction m_memaction = new MementoAction("");
		    m_memlistundo.add(m_memaction.saveStateToMemento());
		}
	}

	/**
	 *  Clear the list of redo if it isn't empty
	 */
	public void clearRedoList()
	{
		// Si action apres avoir fait un Undo alors on vide la liste des Redo
		if(!m_memlistredo.getList().isEmpty())
		{
			m_memlistredo.getList().clear();
			m_gui.setMenuRedoEnabled(false);
		}
	}
	
	/**
	 * Add a new entry of a memento in the list of undo
	 */
	public void addUndoMemento()
	{
	    MementoAction m_memaction = new MementoAction(m_gui.getText());
	    m_memlistundo.add(m_memaction.saveStateToMemento());
	}
	
	/**
	 * Add a new entry of a memento in the list of redo
	 */
	public void addRedoMemento()
	{
	    MementoAction m_memaction = new MementoAction(m_gui.getText());
	    m_memlistredo.add(m_memaction.saveStateToMemento());
	}

}
