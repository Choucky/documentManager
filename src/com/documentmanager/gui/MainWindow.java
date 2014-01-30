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
import java.util.ArrayList;

import com.documentmanager.models.Domaine;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainWindow {

	private JFrame frmDocumentmanager;

	//Composants
	private JComboBox comboBox;

	//Données applicatives
	ArrayList<String> domaines;
	Domaine domaine;
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
		domaines = new ArrayList<String>();

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
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		comboBox.setPreferredSize(new Dimension(200, 24));
		comboBox.setMinimumSize(new Dimension(200, 24));
		
		//Trouver les domaines
		File[] files = findDomains();
		for (File f : files) {
			comboBox.addItem(f.getName().replace(".bin", ""));
		}
		
		panel_1.add(comboBox);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 10));
		panel.setSize(new Dimension(200, 0));
		panel.setMinimumSize(new Dimension(200, 10));
		panel.setBorder(new TitledBorder(null, "Recherche", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmDocumentmanager.getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setVgap(0);
		panel_2.setBorder(new TitledBorder(null, "Ajouter un crit\u00E8re", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_2);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setPreferredSize(new Dimension(125, 24));
		comboBox_1.setMinimumSize(new Dimension(125, 24));
		panel_2.add(comboBox_1);

		JButton button = new JButton("+");
		panel_2.add(button);

		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setVgap(0);
		panel_4.setBorder(new TitledBorder(null, "Ajouter un mot clef", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_4);

		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setPreferredSize(new Dimension(125, 24));
		comboBox_2.setMinimumSize(new Dimension(125, 24));
		panel_4.add(comboBox_2);

		JButton button_1 = new JButton("+");
		panel_4.add(button_1);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Filtres actuels", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_5);

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
				for (String d : domaines) {
					if (d.equals(nomDomaine)) {
						javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Le domaine existe déja. Il ne sera pas créé.");
						return;
					}
				}
				domaine = new Domaine(nomDomaine);
				domaines.add(domaine.getNom());
				saveDomain(domaine);
				updateDomainlist();
			}
		});
		mntmNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnDomaine.add(mntmNouveau);

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

		JMenu mnMotClefs = new JMenu("Mot clefs");
		menuBar.add(mnMotClefs);

		JMenuItem mntmNouveauMotClef = new JMenuItem("Nouveau mot clef...");
		mnMotClefs.add(mntmNouveauMotClef);
	}

	private void updateDomainlist() {
		comboBox.removeAllItems();
		for (String d : domaines) {
			comboBox.addItem(d);
		}
	}

	private void saveDomain(Domaine d) {
		try {
			ObjectOutputStream output_domain = new ObjectOutputStream(new FileOutputStream(d.getNom()+".bin"));
			output_domain.writeObject(d);
			output_domain.flush();
			output_domain.close();
		} catch (FileNotFoundException e) {
			javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Impossible de sauvegarder le domaine : " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Impossible d'écrire le domaine : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private File[] findDomains() {
		return new File(".").listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".bin");
		    }
		});
	}

}
