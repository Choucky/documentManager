package com.documentmanager.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.SpringLayout;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.JTree;

public class MainWindow {

	private JFrame frmDocumentmanager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Utilise le style syst�me natif
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
		}
		catch (ClassNotFoundException e) {
		}
		catch (InstantiationException e) {
		}
		catch (IllegalAccessException e) {
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmDocumentmanager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDocumentmanager = new JFrame();
		frmDocumentmanager.setTitle("Document Manager");
		frmDocumentmanager.setBounds(100, 100, 450, 300);
		frmDocumentmanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDocumentmanager.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JList list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Machin", "Truc", "Bidule", "Chouette", "Chose"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		frmDocumentmanager.getContentPane().add(list, BorderLayout.WEST);
		
		JTree tree = new JTree();
		frmDocumentmanager.getContentPane().add(tree, BorderLayout.EAST);
		
		JMenuBar menuBar = new JMenuBar();
		frmDocumentmanager.setJMenuBar(menuBar);
		
		JMenu mnDomaine = new JMenu("Domaine");
		menuBar.add(mnDomaine);
		
		JMenuItem mntmNouveau = new JMenuItem("Nouveau...");
		mntmNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnDomaine.add(mntmNouveau);
		
		JMenuItem mntmOuvrir = new JMenuItem("Ouvrir...");
		mntmOuvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnDomaine.add(mntmOuvrir);
		
		JMenuItem mntmFermer = new JMenuItem("Fermer");
		mntmFermer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mnDomaine.add(mntmFermer);
		
		JSeparator separator = new JSeparator();
		mnDomaine.add(separator);
		
		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnDomaine.add(mntmQuitter);
		
		JMenu mnFichiers = new JMenu("Fichiers");
		menuBar.add(mnFichiers);
		
		JMenu mnNouveau = new JMenu("Nouveau");
		mnFichiers.add(mnNouveau);
		
		JMenuItem mntmDocumentElectronique = new JMenuItem("Document electronique...");
		mnNouveau.add(mntmDocumentElectronique);
		
		JMenuItem mntmDocumentPapier = new JMenuItem("Document papier...");
		mnNouveau.add(mntmDocumentPapier);
	}

}
