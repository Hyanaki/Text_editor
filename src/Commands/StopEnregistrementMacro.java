package Commands;

import Editeur.Register;

public class StopEnregistrementMacro implements Command {

	private Register m_reg;
	
	public StopEnregistrementMacro(Register reg)
	{
		m_reg = reg;
	}
	
	@Override
	public void execute() {

		m_reg.stopEnregistrementMacro();
		
	}
}
