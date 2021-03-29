package Commands;

import Editeur.Buffer;

public class Couper implements Command {

	private Buffer m_buffer;
		
	public Couper(Buffer buffer)
	{
		m_buffer = buffer;
	}
	
	@Override
	public void execute() {
		
		m_buffer.couper();
		
	}
	

}
