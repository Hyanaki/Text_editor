package Commands;
import Editeur.GUI;
import Editeur.Buffer;

public class Selectionner implements Command{

	private GUI m_gui; 
	private Buffer m_buffer;
		
	public Selectionner(GUI gui, Buffer buffer)
	{
		m_gui = gui;
		m_buffer = buffer;
	}
	
	@Override
	public void execute() {
		m_buffer.changerSelection(m_gui.getSelectedText());
	}


}
