package Editeur;

public class Buffer {
	
	private GUI m_gui;
	private String m_selection;
	private PressePapier m_pp;
	private Register m_reg;
	
	public Buffer(GUI gui, Register reg)
	{
		m_gui = gui;
		m_pp = new PressePapier();
		m_reg = reg;
	}
	
	public void couper()
	{
		m_reg.clearRedoList();	
		m_reg.addUndoMemento();
		
		m_gui.setMenuUndoEnabled(true);
		if(m_selection != "" && m_selection != null) m_pp.setTxt(m_selection);
		m_gui.replaceSelection("");
	}
	
	public void copier()
	{
		if(m_selection != "" && m_selection != null) m_pp.setTxt(m_selection);
	}
	
	public void coller()
	{
		m_reg.clearRedoList();	
		m_reg.addUndoMemento();
		
		m_gui.setMenuUndoEnabled(true);
		m_gui.replaceSelection(m_pp.getTxt());
	}
	
	public void changerSelection(String newtxt)
	{
		m_selection = newtxt;
	}

	public String getPressePapiers() {
		return m_pp.getTxt();
	}

}
