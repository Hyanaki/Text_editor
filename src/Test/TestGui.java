package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.junit.jupiter.api.Test;

import Editeur.*;
import Commands.*;

class TestGui {

	
	@Test
	void copyTest() {
		GUI m_gui = new GUI();
		
		System.out.println("\n-------------copyTestVoid-------------------\n");
		/*--------------------Test copier avec sélection----------------------*/
		m_gui.replaceSelection("Alohomora");
		m_gui.getTextArea().setSelectionStart(0);
		m_gui.getTextArea().setSelectionEnd(5);

		m_gui.getMenuCopy().doClick();
		
		assertEquals("Alohomora", m_gui.getText());
		assertEquals("Aloho", m_gui.getBuffer().getPressePapiers());
		
		
		/*--------------------Test copier sans sélection----------------------*/
		m_gui.getTextArea().setSelectionStart(0);
		m_gui.getTextArea().setSelectionEnd(0);

		m_gui.getMenuCopy().doClick();
		
		assertEquals("Alohomora", m_gui.getText());
		assertEquals("Aloho", m_gui.getBuffer().getPressePapiers());
	}

	
	@Test
	void cutTest() {
		GUI m_gui = new GUI();
		System.out.println("\n-------------cutTest-------------------\n");
		/*--------------------Test couper avec sélection----------------------*/
		
		m_gui.getMenuCut().doClick();
		
		m_gui.replaceSelection("Alohomora");
		m_gui.getTextArea().setSelectionStart(0);
		m_gui.getTextArea().setSelectionEnd(5);
		
		m_gui.getMenuCut().doClick();
		
		assertEquals("mora", m_gui.getText());
		assertEquals("Aloho",m_gui.getBuffer().getPressePapiers());
		
		/*--------------------Test couper si aucune sélection----------------------*/ 
		m_gui.getTextArea().setSelectionStart(0);
		m_gui.getTextArea().setSelectionEnd(0);
		
		m_gui.getMenuCut().doClick();
		
		assertEquals("mora", m_gui.getText());
		assertEquals("Aloho",m_gui.getBuffer().getPressePapiers());
	
	}
	
	@Test
	void cutPasteTest() {
		GUI m_gui = new GUI();
		System.out.println("\n-------------cutPasteTest-------------------\n");
		/*--------------------Test cutpaste avec sélection----------------------*/
		m_gui.replaceSelection("Alohomora");
		m_gui.getTextArea().setSelectionStart(0);
		m_gui.getTextArea().setSelectionEnd(5);
		
		 m_gui.getMenuCut().doClick();
		 
		assertEquals("mora", m_gui.getText());
		assertEquals("Aloho",m_gui.getBuffer().getPressePapiers());
		
		m_gui.getTextArea().setCaretPosition(3);

		m_gui.getMenuPaste().doClick();
		
		assertEquals("morAlohoa", m_gui.getText());
		assertEquals("Aloho",m_gui.getBuffer().getPressePapiers());
	}	
	
	
	@Test
	void copyPasteTest() {
		GUI m_gui = new GUI();
		System.out.println("\n-------------copyPasteTest-------------------\n");
		/*--------------------Test copypaste avec sélection----------------------*/
		m_gui.replaceSelection("Alohomora");
		m_gui.getTextArea().setSelectionStart(0);
		m_gui.getTextArea().setSelectionEnd(5);

		m_gui.getMenuCopy().doClick();
		
		assertEquals("Alohomora", m_gui.getText());
		assertEquals("Aloho",m_gui.getBuffer().getPressePapiers());
		
		m_gui.getTextArea().setSelectionStart(2);
		m_gui.getTextArea().setSelectionEnd(4);

		m_gui.getMenuPaste().doClick();

		assertEquals("AlAlohoomora", m_gui.getText());
		assertEquals("Aloho",m_gui.getBuffer().getPressePapiers());
	}
	
	@Test
	void undoRedoTest1() {
		
		try {
			GUI m_gui = new GUI();
			m_gui.getFrame().setVisible(true);
			
			System.out.println("\n-------------undoRedoTest1-------------------\n");
			/*--------------------Test1 undo redo ----------------------*/	
			
            Robot r = new Robot();
            
            while(!m_gui.getTextArea().hasFocus())
            {
            	m_gui.getTextArea().requestFocusInWindow();
            }
            
            // On ajoute un text dans notre TextArea : 
            // alohomora alo
            // al
            int[] keys = {
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_H,
                KeyEvent.VK_O, KeyEvent.VK_M,
                KeyEvent.VK_O, KeyEvent.VK_R,
                KeyEvent.VK_A, KeyEvent.VK_SPACE,
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_ENTER,
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_BACK_SPACE
            };
            for(int key : keys)
            {
            	r.keyPress(key);
            	r.keyRelease(key);
            }

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			m_gui.getTextArea().setSelectionStart(0);
			m_gui.getTextArea().setSelectionEnd(5);
			
			// Effectue la commande copier
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			m_gui.getTextArea().setSelectionStart(2);
			m_gui.getTextArea().setSelectionEnd(4);
			
			// Effectue la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			assertEquals("alalohoomora alo\n" + "al", m_gui.getText());
			
			// Effectue la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			assertEquals("alohomora alo\n" + "al", m_gui.getText());
			
			// Effectue la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora alo\n" + "alo", m_gui.getText());
            
            // Effectue la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora alo\n", m_gui.getText());
            
            // Effectue la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora ", m_gui.getText());
            
            // Effectue la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("", m_gui.getText());
            
            // Effectue la commande redo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora ", m_gui.getText());
            
            // Effectue la commande redo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora alo\n", m_gui.getText());
            
            // Effectue la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            // Effectue la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            // Effectue la commande undo une troisieme fois = inutile (pas de commande precedente)
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

            assertEquals("", m_gui.getText());
            
            r.keyPress(KeyEvent.VK_O);
            r.keyRelease(KeyEvent.VK_O);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
            assertEquals("o", m_gui.getText());
            
            // Effectue la commande redo = inutile
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
            assertEquals("o", m_gui.getText());
            
		} catch (AWTException ex) {
            ex.printStackTrace(System.err);
        }	
	}
	
	@Test
	void undoRedoTest1UsingMenu() {
		
		try {
			GUI m_gui = new GUI();
			m_gui.getFrame().setVisible(true);
			
			System.out.println("\n-------------undoRedoTest1UsingMenu-------------------\n");
			/*--------------------Test1 undo redo using menu----------------------*/	
			
            Robot r = new Robot();
            
            while(!m_gui.getTextArea().hasFocus())
            {
            	m_gui.getTextArea().requestFocusInWindow();
            }
            
            // On ajoute un text dans notre TextArea : 
            // alohomora alo
            // al
            int[] keys = {
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_H,
                KeyEvent.VK_O, KeyEvent.VK_M,
                KeyEvent.VK_O, KeyEvent.VK_R,
                KeyEvent.VK_A, KeyEvent.VK_SPACE,
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_ENTER,
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_BACK_SPACE
            };
            for(int key : keys)
            {
            	r.keyPress(key);
            	r.keyRelease(key);
            }

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			m_gui.getTextArea().setSelectionStart(0);
			m_gui.getTextArea().setSelectionEnd(2);
			
			// Effectue la commande copier
            m_gui.getMenuCopy().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			m_gui.getTextArea().setSelectionStart(2);
			m_gui.getTextArea().setSelectionEnd(4);
			
			// Effectue la commande coller
			m_gui.getMenuPaste().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			assertEquals("alalomora alo\n" + "al", m_gui.getText());
			
			// Effectue la commande undo
			m_gui.getMenuUndo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			assertEquals("alohomora alo\n" + "al", m_gui.getText());
			
			// Effectue la commande undo
			m_gui.getMenuUndo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora alo\n" + "alo", m_gui.getText());
            
            // Effectue la commande undo
            m_gui.getMenuUndo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora alo\n", m_gui.getText());
            
            // Effectue la commande undo
            m_gui.getMenuUndo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora ", m_gui.getText());
            
            // Effectue la commande undo
            m_gui.getMenuUndo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("", m_gui.getText());
            
            // Effectue la commande redo
            m_gui.getMenuRedo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora ", m_gui.getText());
            
            // Effectue la commande redo
            m_gui.getMenuRedo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
            assertEquals("alohomora alo\n", m_gui.getText());
            
            // Effectue la commande undo
            m_gui.getMenuUndo().doClick();
            // Effectue la commande undo
            m_gui.getMenuUndo().doClick();
            // Effectue la commande undo une troisieme fois = inutile (pas de commande precedente)
            m_gui.getMenuUndo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

            assertEquals("", m_gui.getText());
            
            r.keyPress(KeyEvent.VK_O);
            r.keyRelease(KeyEvent.VK_O);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
            assertEquals("o", m_gui.getText());
            
            // Effectue la commande redo = inutile
            m_gui.getMenuRedo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
            assertEquals("o", m_gui.getText());
            
		} catch (AWTException ex) {
            ex.printStackTrace(System.err);
        }	
	}
	
	@Test
	void undoRedoTest2() {
		try {
			GUI m_gui = new GUI();
			m_gui.getFrame().setVisible(true);
			
			System.out.println("\n-------------undoRedoTest2-------------------\n");
			/*--------------------Test2 undo redo----------------------*/
					
            Robot r = new Robot();
            
            while(!m_gui.getTextArea().hasFocus())
            {
            	m_gui.getTextArea().requestFocusInWindow();
            }
            
            // On ajoute un text dans notre TextArea : alohomora
            int[] keys = {
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_H,
                KeyEvent.VK_O, KeyEvent.VK_M,
                KeyEvent.VK_O, KeyEvent.VK_R,
                KeyEvent.VK_A
            };
            for(int key : keys)
            {
            	r.keyPress(key);
            	r.keyRelease(key);
            }
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			m_gui.getTextArea().setSelectionStart(0);
			m_gui.getTextArea().setSelectionEnd(5);

            // Effectue un undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Faire undo sans commande effectuee = aucun changement
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

			assertEquals("", m_gui.getText());
			
			// Effectue un redo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Faire redo sans commande suivante = aucun changement
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("alohomora", m_gui.getText());
			
			m_gui.getTextArea().setSelectionStart(0);
			m_gui.getTextArea().setSelectionEnd(5);
			
			// Effectue la commande copier
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			m_gui.getTextArea().setSelectionStart(2);
			m_gui.getTextArea().setSelectionEnd(4);
			
			// Effectue la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("alalohoomora", m_gui.getText());
			
			// Execution undo = retourne en arrière 
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("alohomora", m_gui.getText());
			
            m_gui.getTextArea().setCaretPosition(3);
            
			// Execution d'une commande paste à l'emplacement 3
			r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("aloalohohomora", m_gui.getText());
			
			// Execution de redo après avoir effectuer une commande = inutile
			r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("aloalohohomora", m_gui.getText());
			
			// Execution de undo = retourne en arrière
			r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("alohomora", m_gui.getText());
			
			// Execution de redo après avoir effectuer un undo = avance d'une commande effectuee
			r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("aloalohohomora", m_gui.getText());
			
			m_gui.getTextArea().setSelectionStart(0);
			m_gui.getTextArea().setSelectionEnd(6);
			
			// Ajout d'une commande cut de 0 à 6
			r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_X);
            r.keyRelease(KeyEvent.VK_X);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("hohomora", m_gui.getText());
			
			// Execution de undo = retourne en arrière
			r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("aloalohohomora", m_gui.getText());
			
			// Execution de redo après avoir effectuer un undo = avance d'une commande effectuee
			r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("hohomora", m_gui.getText());
			
			r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("", m_gui.getText());
			
		} catch (AWTException ex) {
            ex.printStackTrace(System.err);
        }	
			
	}
	
	@Test
	void undoRedoTest2UsingMenu() {
		try {
			GUI m_gui = new GUI();
			m_gui.getFrame().setVisible(true);
			
			System.out.println("\n-------------undoRedoTest2UsingMenu-------------------\n");
			/*--------------------Test2 undo redo using menu----------------------*/
					
            Robot r = new Robot();
            
            while(!m_gui.getTextArea().hasFocus())
            {
            	m_gui.getTextArea().requestFocusInWindow();
            }
            
            // On ajoute un text dans notre TextArea : alohomora
            int[] keys = {
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_H,
                KeyEvent.VK_O, KeyEvent.VK_M,
                KeyEvent.VK_O, KeyEvent.VK_R,
                KeyEvent.VK_A
            };
            for(int key : keys)
            {
            	r.keyPress(key);
            	r.keyRelease(key);
            }
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			m_gui.getTextArea().setSelectionStart(0);
			m_gui.getTextArea().setSelectionEnd(5);

            // Effectue un undo
            m_gui.getMenuUndo().doClick();
            
            // Faire undo sans commande effectuee = aucun changement
            m_gui.getMenuUndo().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

			assertEquals("", m_gui.getText());
			
			// Effectue un redo
            m_gui.getMenuRedo().doClick();
            
            // Faire redo sans commande suivante = aucun changement
            m_gui.getMenuRedo().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("alohomora", m_gui.getText());
			
			m_gui.getTextArea().setSelectionStart(0);
			m_gui.getTextArea().setSelectionEnd(5);
			
			// Effectue la commande copier
            m_gui.getMenuCopy().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
			
			m_gui.getTextArea().setSelectionStart(2);
			m_gui.getTextArea().setSelectionEnd(4);
			
			// Effectue la commande coller
            m_gui.getMenuPaste().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("alalohoomora", m_gui.getText());
			
			// Execution undo = retourne en arrière 
            m_gui.getMenuUndo().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("alohomora", m_gui.getText());
			
            m_gui.getTextArea().setCaretPosition(3);
            
			// Execution d'une commande paste à l'emplacement 3
            m_gui.getMenuPaste().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("aloalohohomora", m_gui.getText());
			
			// Execution de redo après avoir effectuer une commande = inutile
            m_gui.getMenuRedo().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("aloalohohomora", m_gui.getText());
			
			// Execution de undo = retourne en arrière
            m_gui.getMenuUndo().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("alohomora", m_gui.getText());
			
			// Execution de redo après avoir effectuer un undo = avance d'une commande effectuee
            m_gui.getMenuRedo().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("aloalohohomora", m_gui.getText());
			
			m_gui.getTextArea().setSelectionStart(0);
			m_gui.getTextArea().setSelectionEnd(6);
			
			// Ajout d'une commande cut de 0 à 6
            m_gui.getMenuCut().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("hohomora", m_gui.getText());
			
			// Execution de undo = retourne en arrière
            m_gui.getMenuUndo().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("aloalohohomora", m_gui.getText());
			
			// Execution de redo après avoir effectuer un undo = avance d'une commande effectuee
            m_gui.getMenuRedo().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("hohomora", m_gui.getText());

            m_gui.getMenuUndo().doClick();

            m_gui.getMenuUndo().doClick();

            m_gui.getMenuUndo().doClick();

            m_gui.getMenuUndo().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
			assertEquals("", m_gui.getText());
			
		} catch (AWTException ex) {
            ex.printStackTrace(System.err);
        }	
			
	}
	
	@Test
	void macroTest(){

		try {
			GUI m_gui = new GUI();
			m_gui.getFrame().setVisible(true);
			
			System.out.println("\n-------------macroTest-------------------\n");
			/*--------------------Test macro----------------------*/
					
            Robot r = new Robot();
            
            while(!m_gui.getTextArea().hasFocus())
            {
            	m_gui.getTextArea().requestFocusInWindow();
            }
            
            
            // On ajoute un text dans notre TextArea : alohomora
            int[] keys = {
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_H,
                KeyEvent.VK_O, KeyEvent.VK_M,
                KeyEvent.VK_O, KeyEvent.VK_R,
                KeyEvent.VK_A
            };
            for(int key : keys)
            {
            	r.keyPress(key);
            	r.keyRelease(key);
            }
            
            //*******************************
        	//*****Enregistrement Macro1*****
        	//*******************************
            
            // Démarrage de l'enregistrement de la macro 1
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            
            // Ajout de la commande copier
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Test de démarrage d'enregistrement de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            
            // Test de démarrage d'enregistrement de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            
            // Ajout de la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Ajout de la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Arret de l'enregistrement de la macro 1
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            
            //*******************************
        	//*****Enregistrement Macro2*****
        	//*******************************
            
            // Démarrage de l'enregistrement de la macro 2
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            
            // Ajout de la commande copier
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Test de démarrage d'enregistrement de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            
            // Test de démarrage d'enregistrement de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            
            // Ajout de la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Ajout de la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Ajout de la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Arret de l'enregistrement de la macro 2
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            
            //*******************************
        	//*****Enregistrement Macro3*****
        	//*******************************
            
            // Démarrage de l'enregistrement de la macro 3
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            
            // Ajout de la commande copier
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Test de démarrage d'enregistrement de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            
            // Test de démarrage d'enregistrement de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            
            // Ajout de la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Arret de l'enregistrement de la macro 3
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
               
            //********************
        	//****Test Macro1*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(5);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alohoalohomora", m_gui.getText());
            
            //********************
        	//****Test Macro2*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(2);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alalalohoalohomora", m_gui.getText());
            
            //********************
        	//****Test Macro3*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(6);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alalalohoalohomora", m_gui.getText());
            
            //**************************
        	//****Reecriture Macro3*****
        	//**************************
            
            // Démarrage de l'enregistrement de la macro 3
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            
            // Ajout de la commande couper
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_X);
            r.keyRelease(KeyEvent.VK_X);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Test de démarrage d'enregistrement de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            
            // Test de démarrage de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Test de démarrage d'enregistrement de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            
            // Test de démarrage de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Ajout de la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Ajout de la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
                        
            // Arret de l'enregistrement de la macro 3
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            
            //**************************
        	//****Reecriture Macro1*****
        	//**************************
            
            // Démarrage de l'enregistrement de la macro 1
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            
            // Ajout de la commande couper
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_X);
            r.keyRelease(KeyEvent.VK_X);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Test de démarrage d'enregistrement de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            
            // Test de démarrage de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Test de démarrage d'enregistrement de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            
            // Test de démarrage de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Ajout de la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);

            // Arret de l'enregistrement de la macro 1
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            
            //**************************
        	//****Reecriture Macro2*****
        	//**************************
            
            // Démarrage de l'enregistrement de la macro 2
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            
            // Ajout de la commande copier
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_C);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Test de démarrage d'enregistrement de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            
            // Test de démarrage de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Test de démarrage d'enregistrement de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            
            // Test de démarrage de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Ajout de la commande coller
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_V);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Ajout de la commande undo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_Z);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            // Ajout de la commande redo
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_Y);
            r.keyRelease(KeyEvent.VK_CONTROL);

            // Arret de l'enregistrement de la macro 
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
            //********************
        	//****Test Macro1*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(5);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alalalohoalohomora", m_gui.getText());
            
            //********************
        	//****Test Macro2*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(4);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alalalohoalohomora", m_gui.getText());
            
            //********************
        	//****Test Macro3*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(9);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alohomora", m_gui.getText());
            

        } catch (AWTException ex) {
            ex.printStackTrace(System.err);
        }	
	}
	
	@Test
	void macroTestUsingMenu(){

		try {
			GUI m_gui = new GUI();
			m_gui.getFrame().setVisible(true);
			
			System.out.println("\n-------------macroTest-------------------\n");
			/*--------------------Test macro----------------------*/
					
            Robot r = new Robot();
            
            while(!m_gui.getTextArea().hasFocus())
            {
            	m_gui.getTextArea().requestFocusInWindow();
            }
            
            
            // On ajoute un text dans notre TextArea : alohomora
            int[] keys = {
                KeyEvent.VK_A, KeyEvent.VK_L,
                KeyEvent.VK_O, KeyEvent.VK_H,
                KeyEvent.VK_O, KeyEvent.VK_M,
                KeyEvent.VK_O, KeyEvent.VK_R,
                KeyEvent.VK_A
            };
            for(int key : keys)
            {
            	r.keyPress(key);
            	r.keyRelease(key);
            }
            
            //*******************************
        	//*****Enregistrement Macro1*****
        	//*******************************
            
            // Démarrage de l'enregistrement de la macro 1
            m_gui.getMenuStartRecordingMacro_1().doClick();
            
            // Ajout de la commande copier
            m_gui.getMenuCopy().doClick();
            
            // Test de démarrage d'enregistrement de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            // OU
            m_gui.getMenuStartRecordingMacro_2().doClick();
            // Test arret macro 2
            m_gui.getMenuStopRecordingMacro_2().doClick();
            
            // Test de démarrage d'enregistrement de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            // OU
            m_gui.getMenuStartRecordingMacro_3().doClick();
            // Test arret macro 3
            m_gui.getMenuStopRecordingMacro_3().doClick();
            
            // Ajout de la commande coller
            m_gui.getMenuPaste().doClick();
            
            // Ajout de la commande coller
            m_gui.getMenuPaste().doClick();
            
            // Arret de l'enregistrement de la macro 1
            m_gui.getMenuStopRecordingMacro_1().doClick();
            
            //*******************************
        	//*****Enregistrement Macro2*****
        	//*******************************
            
            // Démarrage de l'enregistrement de la macro 2
            m_gui.getMenuStartRecordingMacro_2().doClick();
            
            // Ajout de la commande copier
            m_gui.getMenuCopy().doClick();
            
            // Test de démarrage d'enregistrement de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            // OU
            m_gui.getMenuStartRecordingMacro_1().doClick();
            // Test arret macro 1
            m_gui.getMenuStopRecordingMacro_1().doClick();
            
            // Test de démarrage d'enregistrement de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            // OU
            m_gui.getMenuStartRecordingMacro_3().doClick();
            // Test arret macro 3
            m_gui.getMenuStopRecordingMacro_3().doClick();
            
            // Ajout de la commande coller
            m_gui.getMenuPaste().doClick();
            
            // Ajout de la commande coller
            m_gui.getMenuPaste().doClick();
            
            // Ajout de la commande coller
            m_gui.getMenuPaste().doClick();
            
            // Arret de l'enregistrement de la macro 2
            m_gui.getMenuStopRecordingMacro_2().doClick();
            
            //*******************************
        	//*****Enregistrement Macro3*****
        	//*******************************
            
            // Démarrage de l'enregistrement de la macro 3
            m_gui.getMenuStartRecordingMacro_3().doClick();
            
            // Ajout de la commande copier
            m_gui.getMenuCopy().doClick();
            
            // Test de démarrage d'enregistrement de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            // OU
            m_gui.getMenuStartRecordingMacro_1().doClick();
            // Test arret macro 1
            m_gui.getMenuStopRecordingMacro_1().doClick();
            
            // Test de démarrage d'enregistrement de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            // OU
            m_gui.getMenuStartRecordingMacro_2().doClick();
            // Test arret macro 3
            m_gui.getMenuStopRecordingMacro_2().doClick();
            
            // Ajout de la commande coller
            m_gui.getMenuPaste().doClick();
            
            // Arret de l'enregistrement de la macro 3
            m_gui.getMenuStopRecordingMacro_3().doClick();
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
               
            //********************
        	//****Test Macro1*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(5);

            

            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
        	System.out.println(m_gui.getText());

            assertEquals("alohoalohomora", m_gui.getText());
            
            //********************
        	//****Test Macro2*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(2);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alalalohoalohomora", m_gui.getText());
            
            //********************
        	//****Test Macro3*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(6);
            
            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alalalohoalohomora", m_gui.getText());
            
            //**************************
        	//****Reecriture Macro3*****
        	//**************************
            
            // Démarrage de l'enregistrement de la macro 3
            m_gui.getMenuStartRecordingMacro_3().doClick();
            
            // Ajout de la commande couper
            m_gui.getMenuCut().doClick();
            
            // Test de démarrage d'enregistrement de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            // OU
            m_gui.getMenuStartRecordingMacro_1().doClick();
            // Test arret macro 1
            m_gui.getMenuStopRecordingMacro_1().doClick();
            
            // Test de démarrage de la macro 1 = inutile
            m_gui.getMenuUseMacro_1().doClick();
            
            // Test de démarrage d'enregistrement de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            // OU
            m_gui.getMenuStartRecordingMacro_2().doClick();
            // Test arret macro 2
            m_gui.getMenuStopRecordingMacro_2().doClick();
            
            // Test de démarrage de la macro 2 = inutile
            m_gui.getMenuUseMacro_2().doClick();
            
            // Ajout de la commande coller
            m_gui.getMenuPaste().doClick();
            
            // Ajout de la commande undo
            m_gui.getMenuUndo().doClick();
                        
            // Arret de l'enregistrement de la macro 3
            m_gui.getMenuStopRecordingMacro_3().doClick();
            
            //**************************
        	//****Reecriture Macro1*****
        	//**************************
            
            // Démarrage de l'enregistrement de la macro 1
            m_gui.getMenuStartRecordingMacro_1().doClick();
            
            // Ajout de la commande couper
            m_gui.getMenuCut().doClick();
            
            // Test de démarrage d'enregistrement de la macro 2 = inutile
            r.keyPress(KeyEvent.VK_F2);
            r.keyRelease(KeyEvent.VK_F2);
            // OU
            m_gui.getMenuStartRecordingMacro_2().doClick();
            // Test arret macro 2
            m_gui.getMenuStopRecordingMacro_2().doClick();
            
            // Test de démarrage de la macro 2 = inutile
            m_gui.getMenuUseMacro_2().doClick();
            
            // Test de démarrage d'enregistrement de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            // OU
            m_gui.getMenuStartRecordingMacro_3().doClick();
            // Test arret macro 3
            m_gui.getMenuStopRecordingMacro_3().doClick();
            
            // Test de démarrage de la macro 3 = inutile
            m_gui.getMenuUseMacro_3().doClick();
            
            // Ajout de la commande undo
            m_gui.getMenuUndo().doClick();

            // Arret de l'enregistrement de la macro 1
            m_gui.getMenuStopRecordingMacro_1().doClick();
            
            //**************************
        	//****Reecriture Macro2*****
        	//**************************
            
            // Démarrage de l'enregistrement de la macro 2
            m_gui.getMenuStartRecordingMacro_2().doClick();
            
            // Ajout de la commande copier
            m_gui.getMenuCopy().doClick();
            
            // Test de démarrage d'enregistrement de la macro 1 = inutile
            r.keyPress(KeyEvent.VK_F1);
            r.keyRelease(KeyEvent.VK_F1);
            // OU
            m_gui.getMenuStartRecordingMacro_1().doClick();
            // Test arret macro 1
            m_gui.getMenuStopRecordingMacro_1().doClick();
            
            // Test de démarrage de la macro 1 = inutile
            m_gui.getMenuUseMacro_1().doClick();
            
            // Test de démarrage d'enregistrement de la macro 3 = inutile
            r.keyPress(KeyEvent.VK_F3);
            r.keyRelease(KeyEvent.VK_F3);
            // OU
            m_gui.getMenuStartRecordingMacro_3().doClick();
            // Test arret macro 3
            m_gui.getMenuStopRecordingMacro_3().doClick();
            
            // Test de démarrage de la macro 3 = inutile
            m_gui.getMenuUseMacro_3().doClick();
            
            // Ajout de la commande coller
            m_gui.getMenuPaste().doClick();
            
            // Ajout de la commande undo
            m_gui.getMenuUndo().doClick();
            
            // Ajout de la commande redo
            m_gui.getMenuRedo().doClick();
            
            // Arret de l'enregistrement de la macro 
            m_gui.getMenuStopRecordingMacro_2().doClick();

            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}
            
            //********************
        	//****Test Macro1*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(5);

            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_1);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alalalohoalohomora", m_gui.getText());
            
            //********************
        	//****Test Macro2*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(4);

            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_2);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alalalohoalohomora", m_gui.getText());
            
            //********************
        	//****Test Macro3*****
        	//********************
            m_gui.getTextArea().setSelectionStart(0);
            m_gui.getTextArea().setSelectionEnd(9);

            r.keyPress(KeyEvent.VK_CONTROL);
            r.keyPress(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_3);
            r.keyRelease(KeyEvent.VK_CONTROL);
            
            
            try{Thread.sleep(50);} catch (InterruptedException ex) {ex.printStackTrace(System.err);}

        	System.out.println(m_gui.getText());

            assertEquals("alohomora", m_gui.getText());
            

        } catch (AWTException ex) {
            ex.printStackTrace(System.err);
        }	
	}
	}
