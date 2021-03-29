package Commands;

import Editeur.Register;

public class Undo implements Command {
	
	private Register m_reg;
	
	public Undo(Register reg)
	{
		m_reg = reg;
	}

	@Override
	public void execute() {
		
		m_reg.undo();
		
	}

}
