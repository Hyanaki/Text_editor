package Commands;

import Editeur.Buffer;

public class Copier implements Command {

	private Buffer m_buffer;
	
	public Copier(Buffer buffer)
	{
		m_buffer = buffer;
	}
		
	@Override
	public void execute() {
		
		m_buffer.copier();
		
	}

}