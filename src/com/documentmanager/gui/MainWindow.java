package com.documentmanager.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import com.documentmanager.models.Domaine;
import javax.swing.JComboBox;

public class MainWindow {

	private JFrame frmDocumentmanager;
	
	//Composants
	private JComboBox comboBox;
	
	//Données applicatives
	ArrayList<Domaine> domaines;
	private JLabel txtDomaine;

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
		domaines = new ArrayList<Domaine>();
		
		frmDocumentmanager = new JFrame();
		frmDocumentmanager.setTitle("Document Manager");
		frmDocumentmanager.setBounds(100, 100, 800, 600);
		frmDocumentmanager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDocumentmanager.getContentPane().setLayout(new BorderLayout(0, 0));
		frmDocumentmanager.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt){
               //TODO
            }
        });
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Mot clefs", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmDocumentmanager.getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMotClefs = new JPanel();
		
		JScrollPane scrollPane_1 = new JScrollPane(panelMotClefs);
		GridBagLayout gbl_panelMotClefs = new GridBagLayout();
		gbl_panelMotClefs.columnWidths = new int[]{0, 0};
		gbl_panelMotClefs.rowHeights = new int[] {30, 0};
		gbl_panelMotClefs.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelMotClefs.rowWeights = new double[]{0.0, 0.0};
		panelMotClefs.setLayout(gbl_panelMotClefs);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane_1, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Fichiers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmDocumentmanager.getContentPane().add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		
		JList listeFichiers = new JList();
		listeFichiers.setPreferredSize(new Dimension(250, 0));
		listeFichiers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeFichiers.setModel(new AbstractListModel() {
			String[] values = new String[] {"Fichier1", "Fichier2", "Fichier3", "Fichier4", "Fichier5"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(listeFichiers);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		panel_3.add(scrollPane);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		frmDocumentmanager.getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		toolBar.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		txtDomaine = new JLabel();
		txtDomaine.setHorizontalAlignment(SwingConstants.CENTER);
		txtDomaine.setPreferredSize(new Dimension(75, 24));
		txtDomaine.setMinimumSize(new Dimension(75, 24));
		panel_1.add(txtDomaine);
		txtDomaine.setText("Domaine :");
		
		comboBox = new JComboBox();
		comboBox.setPreferredSize(new Dimension(200, 24));
		comboBox.setMinimumSize(new Dimension(200, 24));
		panel_1.add(comboBox);
		
		JMenuBar menuBar = new JMenuBar();
		frmDocumentmanager.setJMenuBar(menuBar);
		
		JMenu mnDomaine = new JMenu("Domaine");
		menuBar.add(mnDomaine);
		
		JMenuItem mntmNouveau = new JMenuItem("Nouveau...");
		mntmNouveau.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomDomaine = JOptionPane.showInputDialog("Veuillez indiquer le nom du domaine :");
				if (nomDomaine.equals("")) {
					javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Aucun nom saisi, le domaine ne sera PAS créé.");
					return;
				}
				for (Domaine d : domaines) {
					if (d.getNom().equals(nomDomaine)) {
						javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Le domaine existe déja. Il ne sera pas créé.");
						return;
					}
				}
				domaines.add(new Domaine(nomDomaine));
				updateDomainlist();
			}
		});
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
		
		JMenuItem mntmSupprimerUnDocument = new JMenuItem("Supprimer un document...");
		mnFichiers.add(mntmSupprimerUnDocument);
		
		JMenu mnMotClefs = new JMenu("Mot clefs");
		menuBar.add(mnMotClefs);
		
		JMenuItem mntmNouveauMotClef = new JMenuItem("Nouveau mot clef...");
		mnMotClefs.add(mntmNouveauMotClef);
		
		JMenuItem mntmSupprimerUnMot = new JMenuItem("Supprimer un mot clef...");
		mnMotClefs.add(mntmSupprimerUnMot);
	}
	
	private void updateDomainlist() {
		comboBox.removeAllItems();
		for (Domaine d : domaines) {
			comboBox.addItem(d.getNom());
		}
	}

}
