package Commands;

import Editeur.Register;

public class Redo implements Command {

	private Register m_reg;
	
	public Redo(Register reg)
	{
		m_reg = reg;
	}
	
	@Override
	public void execute() {

		m_reg.redo();

	}

}
