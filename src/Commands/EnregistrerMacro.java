package Commands;

import Editeur.Register;

public class EnregistrerMacro implements Command {

	private Register m_reg;
	
	public EnregistrerMacro(Register reg)
	{
		m_reg = reg;
	}
	
	@Override
	public void execute() {
		m_reg.enregistrerMacro();
	}

}
