package Commands;

import Editeur.Buffer;

public class Coller implements Command {

	private Buffer m_buffer;
	
	public Coller(Buffer buffer)
	{
		m_buffer = buffer;
	}
		
	@Override
	public void execute() {
		
		m_buffer.coller();
		
	}

}
