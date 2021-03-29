package Editeur;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

import Commands.Coller;
import Commands.Copier;
import Commands.Couper;
import Commands.EnregistrerMacro;
import Commands.Redo;
import Commands.RejouerMacro;
import Commands.Selectionner;
import Commands.StopEnregistrementMacro;
import Commands.Undo;

public class GUI {

	private Buffer m_buffer;
	private Register m_reg;
	
	private Selectionner m_select;
	private Copier m_copy;
	private Couper m_cut;
	private Coller m_paste;
	private Undo m_undo;
	private Redo m_redo;
	private EnregistrerMacro m_save;
	private StopEnregistrementMacro m_stop;
	private RejouerMacro m_replay;
	
	private JFrame frame;
	private JTextArea textArea;
	private JMenuBar menuBar;
	private JMenu mnEdit;
	private JMenuItem mntmCut;
	private JMenuItem mntmCopy;
	private JMenuItem mntmPaste;
	private JMenuItem menuUndo;
	private JMenuItem menuRedo;
	
	private JMenu mnMacro;
	private JMenu mnMacro_1;
	private JMenuItem startRecordingMacro_1;
	private JMenuItem stopRecordingMacro_1;
	private JMenuItem useMacro_1;
	private JMenu mnMacro_2;
	private JMenuItem startRecordingMacro_2;
	private JMenuItem stopRecordingMacro_2;
	private JMenuItem useMacro_2;
	private JMenu mnMacro_3;
	private JMenuItem startRecordingMacro_3;
	private JMenuItem stopRecordingMacro_3;
	private JMenuItem useMacro_3;
	
	private boolean recording;
	private int indicemacro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		recording = false;
		
		// Création des différents type de commandes existantes
		m_reg = new Register(this);
		m_undo = new Undo(m_reg);
		m_redo = new Redo(m_reg);
		m_buffer = new Buffer(this, m_reg);
		m_select = new Selectionner(this,m_buffer);
		m_copy = new Copier(m_buffer);
		m_cut = new Couper(m_buffer);
		m_paste = new Coller(m_buffer);
		m_save = new EnregistrerMacro(m_reg);
		m_stop = new StopEnregistrementMacro(m_reg);
		m_replay= new RejouerMacro(m_reg);
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//*************************************
    	//*****Barre de Menu(Edit, Macro)******
    	//*************************************
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		mnEdit = new JMenu("Edit");
		mnMacro = new JMenu("Macro");
		menuBar.add(mnEdit);
		menuBar.add(mnMacro);

		//********************
    	//*****Edit Menu******
    	//********************	
		textArea = new JTextArea();
		mntmCut = new JMenuItem("Cut");
		mntmCopy = new JMenuItem("Copy");
		mntmPaste = new JMenuItem("Paste");		
		menuRedo = new JMenuItem("Redo");
		menuRedo.setEnabled(false);
		menuUndo = new JMenuItem("Undo");
		menuUndo.setEnabled(false);
		
		//*********************
    	//*****Macro Menu******
    	//*********************
		
		mnMacro_1 = new JMenu("Macro 1");
		mnMacro.add(mnMacro_1);
		mnMacro_2 = new JMenu("Macro 2");
		mnMacro.add(mnMacro_2);
		mnMacro_3 = new JMenu("Macro 3");
		mnMacro.add(mnMacro_3);
		
		startRecordingMacro_1 = new JMenuItem("Enregistrer la macro");
		stopRecordingMacro_1 = new JMenuItem("Arreter l'enregistrement");
		useMacro_1 = new JMenuItem("Utiliser la macro");
		mnMacro_1.add(startRecordingMacro_1);
		mnMacro_1.add(stopRecordingMacro_1);
		mnMacro_1.add(useMacro_1);
		
		startRecordingMacro_2 = new JMenuItem("Enregistrer la macro");
		stopRecordingMacro_2 = new JMenuItem("Arreter l'enregistrement");
		useMacro_2 = new JMenuItem("Utiliser la macro");
		mnMacro_2.add(startRecordingMacro_2);
		mnMacro_2.add(stopRecordingMacro_2);
		mnMacro_2.add(useMacro_2);
			
		startRecordingMacro_3 = new JMenuItem("Enregistrer la macro");		
		stopRecordingMacro_3 = new JMenuItem("Arreter l'enregistrement");	
		useMacro_3 = new JMenuItem("Utiliser la macro");	
		mnMacro_3.add(startRecordingMacro_3);	
		mnMacro_3.add(stopRecordingMacro_3);		
		mnMacro_3.add(useMacro_3);
		
		// Desactivation des boutons d'arret d'enregistrement à l'initialisation
		stopRecordingMacro_1.setEnabled(false);
		stopRecordingMacro_2.setEnabled(false);			
		stopRecordingMacro_3.setEnabled(false);		

		// Initialise la liste des undos possible avec un retour possible sur un text vide
		m_reg.addUndoMemento();
		
		//**************************
    	//*****Selection Texte******
    	//**************************	
		textArea.setLineWrap(true);
		textArea.setTransferHandler(null);
		textArea.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
			        
		        m_select.execute();
			}
		});
		frame.getContentPane().add(textArea, BorderLayout.CENTER);

		//******************
    	//*****MENU CUT*****
    	//******************
		mntmCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(recording) m_reg.addCommandMacro(m_cut, indicemacro);
				else {
					m_cut.execute();
				}
			}
		});
		mnEdit.add(mntmCut);
		
		//*******************
    	//*****MENU COPY*****
    	//*******************
		mntmCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(recording) m_reg.addCommandMacro(m_copy, indicemacro);
				else {
					m_copy.execute();
				}
			}
		});
		mnEdit.add(mntmCopy);
		
		//********************
    	//*****MENU PASTE*****
    	//********************
		mntmPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(recording) m_reg.addCommandMacro(m_paste, indicemacro);
				else {
					m_paste.execute();
				}
			}
		});
		mnEdit.add(mntmPaste);
		
		//*******************
    	//*****MENU UNDO*****
    	//*******************
		menuUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(recording) m_reg.addCommandMacro(m_undo, indicemacro);
				else {
	            	m_undo.execute();
				}
			}
		});
		mnEdit.add(menuUndo);
		
		//*******************
    	//*****MENU REDO*****
    	//*******************
		menuRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(recording) m_reg.addCommandMacro(m_redo, indicemacro);
				else {
					m_redo.execute();
				}
			}
		});
		mnEdit.add(menuRedo);
		
		//*************************************
    	//*****MENU Start Recording Macro1*****
    	//*************************************
		startRecordingMacro_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recording) {
        			indicemacro = 0;
    				m_save.execute();
        		}
			}
		});
		
		//*************************************
    	//*****MENU Start Recording Macro2*****
    	//*************************************
		startRecordingMacro_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recording) {
        			indicemacro = 1;
    				m_save.execute();
        		}
			}
		});
		
		//*************************************
    	//*****MENU Start Recording Macro3*****
    	//*************************************
		startRecordingMacro_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recording) {
        			indicemacro = 2;
    				m_save.execute();
        		}
			}
		});
		
		//***************************************
    	//*****MENU Stop Recording Macro1********
    	//***************************************
		stopRecordingMacro_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(recording && indicemacro == 0)
    			{
        			m_stop.execute();
    			}
			}	
		});
		
		//***************************************
    	//*****MENU Stop Recording Macro2********
    	//***************************************
		stopRecordingMacro_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(recording && indicemacro == 1)
    			{
        			m_stop.execute();
    			}
			}	
		});
		
		//***************************************
    	//*****MENU Stop Recording Macro3********
    	//***************************************
		stopRecordingMacro_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(recording && indicemacro == 2)
    			{
        			m_stop.execute();
    			}
			}	
		});
		
		//*************************
    	//*****MENU Use Macro1*****
    	//*************************
		useMacro_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recording)
        		{
        			indicemacro = 0;
    				m_replay.execute();
        		}
			}	
		});
		
		//*************************
    	//*****MENU Use Macro2*****
    	//*************************
		useMacro_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recording)
        		{
					indicemacro = 1;
					m_replay.execute();
        		}
			}	
		});
		
		//*************************
    	//*****MENU Use Macro2*****
    	//*************************
		useMacro_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!recording)
        		{
					indicemacro = 2;
					m_replay.execute();
        		}
				
			}	
		});
		
		//**************************
    	//*****TOUCHES CLAVIERS*****
    	//**************************
		textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            	
            	//****************
            	//*****CRTL+C*****
            	//****************
            	if ((e.getKeyCode() == KeyEvent.VK_C) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                   
            		if(recording) m_reg.addCommandMacro(m_copy, indicemacro);
    				else {
    					m_copy.execute();
    				}
                    
                    System.out.println("CTRL + C");
                }
            	//****************
            	//*****CRTL+X*****
            	//****************
            	else if ((e.getKeyCode() == KeyEvent.VK_X) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                	
            		if(recording) m_reg.addCommandMacro(m_cut, indicemacro);
    				else {
    					m_cut.execute();
    				}
                	
                	System.out.println("CTRL + X");
                }
                //****************
            	//*****CRTL+V*****
            	//****************
            	else if ((e.getKeyCode() == KeyEvent.VK_V) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
                	
            		if(recording) m_reg.addCommandMacro(m_paste, indicemacro);
    				else {
    					m_paste.execute();
    				}
    				
                	System.out.println("CTRL + V");
                }
                //****************
            	//*****CRTL+Z*****
            	//****************
            	else if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
        		       	
            		if(recording) m_reg.addCommandMacro(m_undo, indicemacro);
    				else {
    	            	m_undo.execute();
    				}

                	System.out.println("CTRL + Z");	
                }
                //****************
            	//*****CRTL+Y*****
            	//****************
            	else if ((e.getKeyCode() == KeyEvent.VK_Y) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {

            		if(recording) m_reg.addCommandMacro(m_redo, indicemacro);
    				else {
    					m_redo.execute();
    				}
    				
            		System.out.println("CTRL + Y");
                }
            	//************
            	//*****F1*****
            	//************
            	else if (e.getKeyCode() == KeyEvent.VK_F1) {
            		if(recording && indicemacro == 0)
        			{
            			m_stop.execute();
        			}
            		else if(!recording) {
            			indicemacro = 0;
        				m_save.execute();
            		}
            		
            		System.out.println("F1");
                }
            	//************
            	//*****F2*****
            	//************
            	else if (e.getKeyCode() == KeyEvent.VK_F2) {
            		
            		if(recording && indicemacro == 1)
        			{
            			m_stop.execute();
        			}
            		else if(!recording) {
            			indicemacro = 1;
        				m_save.execute();
            		}
            		
            		System.out.println("F2");
                }
            	//************
            	//*****F3*****
            	//************
            	else if (e.getKeyCode() == KeyEvent.VK_F3) {
            		if(recording && indicemacro == 2)
        			{
            			m_stop.execute();
        			}
            		else if(!recording) {
            			indicemacro = 2;
        				m_save.execute();
            		}
            		
            		System.out.println("F3");
                }
            	//****************
            	//*****CRTL+1*****
            	//****************
            	else if ((e.getKeyCode() == KeyEvent.VK_1) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            		if(!recording)
            		{
            			indicemacro = 0;
	    				m_replay.execute();
	 
	    				System.out.println("CTRL+1");
            		}
                }
            	//****************
            	//*****CRTL+2*****
            	//****************
            	else if ((e.getKeyCode() == KeyEvent.VK_2) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            		if(!recording)
            		{
            			indicemacro = 1;
	    				m_replay.execute();
	 
	    				System.out.println("CTRL+2");
            		}
                }
            	//****************
            	//*****CRTL+3*****
            	//****************
            	else if ((e.getKeyCode() == KeyEvent.VK_3) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            		if(!recording)
            		{
            			indicemacro = 2;
	    				m_replay.execute();
	 
	    				System.out.println("CTRL+3");
            		}
                }
            	//*********************
            	//*****BACKSPACE*******
            	//*********************
            	else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE ) {

            		// l'effecement arriere permet d'enregistrer dans la liste des undo l'état d'avant
                	m_reg.addUndoMemento();
            		if(!menuUndo.isEnabled()) menuUndo.setEnabled(true);
            		
            		System.out.println("BACKSPACE");
                }
    			// Si appuie sur le clavier sur touche autre que les modificateurs CTRL ou ALT 
            	else if(!((e.getModifiers() & KeyEvent.CTRL_MASK)!= 0) 
            			&& !((e.getModifiers() & KeyEvent.ALT_MASK)!= 0))
            	{            		
            		// Permet lorsque l'on a plus de undo possible et que l'on tape un caractère d'enregistrer un retour en arrière
            		// vers un text vide puis activation du bonton Undo
            		m_reg.initializeUndoList();
            		if(!menuUndo.isEnabled()) menuUndo.setEnabled(true);
            		// Supprime les commandes de la liste de redo lorsque l'on tape un caractère
            		m_reg.clearRedoList();

            		System.out.println("OTHER");
            	}
            }
			@Override
			public void keyReleased(KeyEvent e) {

                //**************************
            	//*****ESPACE, ENTRER*******
            	//**************************
            	if (e.getKeyCode() == KeyEvent.VK_SPACE 
            			|| e.getKeyCode() == KeyEvent.VK_ENTER) {

            		System.out.println("ESPACE, ENTER");
            		
                	m_reg.addUndoMemento();
            		if(!menuUndo.isEnabled()) menuUndo.setEnabled(true);
                }
			}
			@Override
			public void keyTyped(KeyEvent e) {
        		
			}
		});
		
	}
	
	/**
	 * Remplace le text existant de la JTextArea par un nouveau
	 * @param state : nouveau text
	 */
	public void setText(String state)
	{
		textArea.setText(state);
	}
	
	/**
	 * Permet de faire passer le bouton Undo d'un état à l'autre
	 * @param state : état suivant
	 */
	public void setMenuUndoEnabled(boolean state)
	{
		if(menuUndo.isEnabled() != state) menuUndo.setEnabled(state);
	}
	
	/**
	 * Permet de faire passer le bouton Redo d'un état à l'autre
	 * @param state : état suivant
	 */
	public void setMenuRedoEnabled(boolean state)
	{
		if(menuRedo.isEnabled() != state) menuRedo.setEnabled(state);
	}
	
	/**
	 * Retourne l'état du bouton Undo
	 * @return état de Undo
	 */
	public boolean isMenuUndoEnabled()
	{
		return menuUndo.isEnabled();
	}
	
	/**
	 * Retourne l'état du bouton Redo
	 * @return état de Redo
	 */
	public boolean isMenuRedoEnabled()
	{
		return menuRedo.isEnabled();
	}
	
	/**
	 * Méthode appelé lors de l'appui sur un bouton d'enregistrement d'une macro, elle permet de désactiver
	 * tout les autres boutons liés aux macro sauf l'arret de l'enregistrement de la macro en question
	 */
	public void disableStartRecording()
	{
		startRecordingMacro_1.setEnabled(false);
		stopRecordingMacro_1.setEnabled(false);
		useMacro_1.setEnabled(false);
		startRecordingMacro_2.setEnabled(false);
		stopRecordingMacro_2.setEnabled(false);
		useMacro_2.setEnabled(false);
		startRecordingMacro_3.setEnabled(false);
		stopRecordingMacro_3.setEnabled(false);
		useMacro_3.setEnabled(false);
		
		if(indicemacro == 0)
		{
			 stopRecordingMacro_1.setEnabled(true);
		}
		else if (indicemacro == 1)
		{
			 stopRecordingMacro_2.setEnabled(true);
		}
		else  stopRecordingMacro_3.setEnabled(true);
	}
	
	/**
	 * Méthode appelé lors de l'appui sur un bouton d'arret d'enregistrement d'une macro, elle permet de réactivé
	 * tout les bontons d'enregistrement d'une macro et d'utilisation d'une macro et de désactiver les boutons d'arret
	 * d'enregistrement
	 */
	public void disableStopRecording()
	{
		startRecordingMacro_1.setEnabled(true);
		useMacro_1.setEnabled(true);
		startRecordingMacro_2.setEnabled(true);
		useMacro_2.setEnabled(true);
		startRecordingMacro_3.setEnabled(true);
		useMacro_3.setEnabled(true);
		
		if(stopRecordingMacro_1.isEnabled()) stopRecordingMacro_1.setEnabled(false);
		if(stopRecordingMacro_2.isEnabled()) stopRecordingMacro_2.setEnabled(false);
		if(stopRecordingMacro_3.isEnabled()) stopRecordingMacro_3.setEnabled(false);
	}
	
	/**
	 * Retourne true si le bouton d'enregistrement de la macro de l'indice (bouton appuyé) est activé
	 * @return si l'on peut enregistrer avec le bouton d'enregistrement
	 */
	public boolean isStartRecordingEnabled()
	{
		if(indicemacro == 0)
		{
			return startRecordingMacro_1.isEnabled();
		}
		else if (indicemacro == 1)
		{
			return startRecordingMacro_2.isEnabled();
		}
		else return startRecordingMacro_3.isEnabled();
	}
	
	/**
	 * Retourne true si le bouton d'arret d'enregistrement de la macro de l'indice en cours est activé
	 * @return si l'on peut arreter l'enregistrement de la macro en cours
	 */
	public boolean isStopRecordingEnabled()
	{
		if(indicemacro == 0)
		{
			return stopRecordingMacro_1.isEnabled();
		}
		else if (indicemacro == 1)
		{
			return stopRecordingMacro_2.isEnabled();
		}
		else return stopRecordingMacro_3.isEnabled();
	}
	
	/**
	 * Retourne l'indice de la macro en cours
	 * @return indice de la macro en cours
	 */
	public int getIndiceMacro()
	{
		return indicemacro;
	}
	
	/**
	 * Permet de dire au GUI que l'enregistrement démarre ou s'arrete
	 * @param state : état souhaité
	 */
	public void setRecording(boolean state)
	{
		recording = state;
	}
	
	/**
	 * Remplace la sélection de la JTestArea
	 * @param str : chaine de caractère remplacant la sélection
	 */
	public void replaceSelection(String str)
	{
		textArea.replaceSelection(str);
	}
	
	/**
	 * Recupère la sélection dans la JTextArea
	 * @return sélection
	 */
	public String getSelectedText()
	{
		return textArea.getSelectedText();
	}
	
	/**
	 * Retourne le texte de la JTextArea
	 * @return text
	 */
	public String getText()
	{
		return textArea.getText();
	}
	
	//GETTER POUR TESTS
	public JTextComponent getTextArea() {
		return this.textArea;
	}
	public JMenuItem getMenuCut()
	{
		return mntmCut;
	}
	public JMenuItem getMenuCopy()
	{
		return mntmCopy;
	}
	public JMenuItem getMenuPaste()
	{
		return mntmPaste;
	}
	public JMenuItem getMenuUndo()
	{
		return menuUndo;
	}
	public JMenuItem getMenuRedo()
	{
		return menuRedo;
	}
	public JMenuItem getMenuStartRecordingMacro_1()
	{
		return startRecordingMacro_1;
	}
	public JMenuItem getMenuStopRecordingMacro_1()
	{
		return stopRecordingMacro_1;
	}
	public JMenuItem getMenuUseMacro_1()
	{
		return useMacro_1;
	}
	public JMenuItem getMenuStartRecordingMacro_2()
	{
		return startRecordingMacro_2;
	}
	public JMenuItem getMenuStopRecordingMacro_2()
	{
		return stopRecordingMacro_2;
	}
	public JMenuItem getMenuUseMacro_2()
	{
		return useMacro_2;
	}
	public JMenuItem getMenuStartRecordingMacro_3()
	{
		return startRecordingMacro_3;
	}
	public JMenuItem getMenuStopRecordingMacro_3()
	{
		return stopRecordingMacro_3;
	}
	public JMenuItem getMenuUseMacro_3()
	{
		return useMacro_3;
	}
	public Buffer getBuffer()
	{
		return m_buffer;
	}
	public JFrame getFrame()
	{
		return frame;
	}
}
