package Commands;

import Editeur.Register;

public class RejouerMacro implements Command {

	private Register m_reg;
	
	public RejouerMacro(Register reg)
	{
		m_reg = reg;
	}
	
	@Override
	public void execute() {

		m_reg.rejouerMacro();
		
	}
}
