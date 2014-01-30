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
import com.documentmanager.models.MotClef;

import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Component;

public class MainWindow {

	private JFrame frmDocumentmanager;

	//Composants
	private JComboBox domainesComboBox;
	private JComboBox motClefCatList;

	//Données applicatives
	ArrayList<String> domaines;
	Domaine domaine;
	private JLabel txtDomaine;
	private boolean ready = false;

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

		domainesComboBox = new JComboBox();
		domainesComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox cb = (JComboBox)arg0.getSource();
		        String domaine_selection = (String)cb.getSelectedItem();
		        if (domaine != null) {
		        	saveDomain();
		        }
		        if (ready && domaine_selection != null) {
		        	loadDomain(domaine_selection);
		        }
			}
		});
		domainesComboBox.setPreferredSize(new Dimension(200, 24));
		domainesComboBox.setMinimumSize(new Dimension(200, 24));
		
		//Trouver les domaines
		File[] files = findDomains();
		for (File f : files) {
			String domaine_nom = f.getName().replace(".bin", "");
			domainesComboBox.addItem(domaine_nom);
			domaines.add(domaine_nom);
		}
		
		panel_1.add(domainesComboBox);

		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 10));
		panel.setSize(new Dimension(200, 0));
		panel.setMinimumSize(new Dimension(200, 10));
		panel.setBorder(new TitledBorder(null, "Recherche", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmDocumentmanager.getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 20, 190, 61);
		panel_2.setBorder(new TitledBorder(null, "Ajouter un crit\u00E8re", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_2);
		panel_2.setLayout(null);

		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(8, 20, 125, 24);
		comboBox_1.setPreferredSize(new Dimension(125, 24));
		comboBox_1.setMinimumSize(new Dimension(125, 24));
		panel_2.add(comboBox_1);

		JButton button = new JButton("+");
		button.setBounds(138, 20, 44, 25);
		panel_2.add(button);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(5, 82, 190, 87);
		panel_4.setBorder(new TitledBorder(null, "Ajouter un mot clef", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_4);
		panel_4.setLayout(null);

		motClefCatList = new JComboBox();
		motClefCatList.setBounds(12, 24, 170, 24);
		motClefCatList.setPreferredSize(new Dimension(125, 24));
		motClefCatList.setMinimumSize(new Dimension(125, 24));
		panel_4.add(motClefCatList);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(12, 51, 125, 24);
		comboBox_3.setPreferredSize(new Dimension(125, 24));
		comboBox_3.setMinimumSize(new Dimension(125, 24));
		panel_4.add(comboBox_3);
		
		JButton button_2 = new JButton("+");
		button_2.setBounds(138, 51, 44, 25);
		panel_4.add(button_2);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(5, 170, 190, 315);
		panel_5.setBorder(new TitledBorder(null, "Filtres actuels", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));
		
		JList listeCriteres = new JList();
		listeCriteres.setPreferredSize(new Dimension(250, 0));
		listeCriteres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeCriteres.setModel(new AbstractListModel() {
			String[] values = new String[] {"Fichier1", "Fichier2", "Fichier3", "Fichier4", "Fichier5"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		panel_5.add(scrollPane_1);
		
		scrollPane_1.add(listeCriteres);

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
				saveDomain();
				updateDomainlist();
			}
		});
		mntmNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnDomaine.add(mntmNouveau);

		JSeparator separator = new JSeparator();
		mnDomaine.add(separator);

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow.this.frmDocumentmanager.dispose();
			}
		});
		mntmQuitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		mnDomaine.add(mntmQuitter);

		JMenu mnFichiers = new JMenu("Fichiers");
		menuBar.add(mnFichiers);

		JMenu mnNouveau = new JMenu("Nouveau");
		mnFichiers.add(mnNouveau);

		JMenuItem mntmDocumentElectronique = new JMenuItem("Document electronique...");
		mntmDocumentElectronique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewFileDialog nfd = new NewFileDialog();
				nfd.setVisible(true);
				if (nfd.getResult() == FileDialogResultEnum.ok) {
					//nfd.getFileName());
					//TODO
				}
			}
		});
		mnNouveau.add(mntmDocumentElectronique);

		JMenuItem mntmDocumentPapier = new JMenuItem("Document papier...");
		mnNouveau.add(mntmDocumentPapier);

		JMenu mnMotClefs = new JMenu("Mot clefs");
		menuBar.add(mnMotClefs);
		
		JMenuItem mntmNouvelleCatgorieDe = new JMenuItem("Nouvelle catégorie de mot clef...");
		mntmNouvelleCatgorieDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String catMotClef = JOptionPane.showInputDialog("Veuillez indiquer le nom de la catégorie de mot clef :");
				if (catMotClef.equals("")) {
					javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Aucun nom saisi, la catégorie ne sera PAS créé.");
					return;
				}
				domaine.addCategorieMotClef(catMotClef);
				updateMotClefCatList();
				saveDomain();
			}
		});
		mnMotClefs.add(mntmNouvelleCatgorieDe);

		JMenuItem mntmNouveauMotClef = new JMenuItem("Nouveau mot clef...");
		mnMotClefs.add(mntmNouveauMotClef);
		
		ready = true;
		domainesComboBox.setSelectedIndex(0);
	}

	protected void updateMotClefCatList() {
		motClefCatList.removeAllItems();
		for (String d : domaine.getCategoriesMotClef()) {
			motClefCatList.addItem(d);
		}
	}

	private void updateDomainlist() {
		domainesComboBox.removeAllItems();
		for (String d : domaines) {
			domainesComboBox.addItem(d);
		}
	}
	
	private void loadDomain(String domaine_selection) {
		try {
			ObjectInputStream input_domain = new ObjectInputStream(new FileInputStream(domaine_selection+".bin"));
			domaine = (Domaine) input_domain.readObject();
			input_domain.close();
		} catch (FileNotFoundException e) {
			javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Impossible de charger le domaine : " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Impossible de lire le domaine : " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Données incompatibles : " + e.getMessage());
			e.printStackTrace();
		}
		
		//TODO : Rafraichir les contrôles
		updateMotClefCatList();
	}

	private void saveDomain() {
		try {
			ObjectOutputStream output_domain = new ObjectOutputStream(new FileOutputStream(domaine.getNom()+".bin"));
			output_domain.writeObject(domaine);
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
