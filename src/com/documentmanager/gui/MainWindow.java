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
import javax.swing.border.TitledBorder;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import java.util.ArrayList;

import com.documentmanager.models.CategorieMotClef;
import com.documentmanager.models.Critere;
import com.documentmanager.models.Document;
import com.documentmanager.models.Domaine;
import com.documentmanager.models.MotClef;

import javax.swing.JComboBox;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow {

	private JFrame frmDocumentmanager;
	
	public final String FILE_EXTENSION = ".obj";

	//Composants
	private JComboBox domainesComboBox;
	private JComboBox motClefCatList;

	//Données applicatives
	ArrayList<String> domaines;
	Domaine domaine;
	private JLabel txtDomaine;
	private boolean ready = false;

	private JList listeFichiers;
	private JComboBox critereList;

	private JComboBox motClefList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//Utilise le style système natif
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
		frmDocumentmanager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmDocumentmanager.getContentPane().setLayout(new BorderLayout(0, 0));
		frmDocumentmanager.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent evt){
				finish();
			}
		});

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Fichiers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmDocumentmanager.getContentPane().add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		listeFichiers = new JList();
		listeFichiers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				JList list = (JList)evt.getSource();
				if (evt.getClickCount() != 2 || list.getSelectedValue() == null) {
					return;
				}
				EditDocumentDialog efd = new EditDocumentDialog((Document) list.getSelectedValue(), domaine);
				efd.setVisible(true);
			}
		});
		listeFichiers.setToolTipText("Double cliquez sur un fichier pour modifier ses propriétés.");
		listeFichiers.setPreferredSize(new Dimension(250, 0));
		listeFichiers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeFichiers.setModel(new FileListModel());

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
			String domaine_nom = f.getName().replace(FILE_EXTENSION, "");
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

		critereList = new JComboBox();
		critereList.setBounds(8, 20, 125, 24);
		critereList.setPreferredSize(new Dimension(125, 24));
		critereList.setMinimumSize(new Dimension(125, 24));
		panel_2.add(critereList);

		JButton critereBtn = new JButton("+");
		critereBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (critereList.getSelectedItem() == null) {
					javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Vous devez créer un critère.");
					return;
				}
			}
		});
		critereBtn.setBounds(138, 20, 44, 25);
		panel_2.add(critereBtn);

		JPanel panel_4 = new JPanel();
		panel_4.setBounds(5, 82, 190, 87);
		panel_4.setBorder(new TitledBorder(null, "Ajouter un mot clef", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_4);
		panel_4.setLayout(null);

		motClefList = new JComboBox();
		motClefList.setBounds(12, 51, 125, 24);
		motClefList.setPreferredSize(new Dimension(125, 24));
		motClefList.setMinimumSize(new Dimension(125, 24));
		panel_4.add(motClefList);

		motClefCatList = new JComboBox();
		motClefCatList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox motClefCatList = (JComboBox) arg0.getSource();
				if (motClefCatList.getSelectedItem() == null) {
					return;
				}
				try {
					motClefList.setModel(new MotClefListModel(((CategorieMotClef) motClefCatList.getSelectedItem()).getMotClefs()));
				} catch (ClassCastException e) {
					System.err.println("Etonnant : Des objets ne se cast pas comme il faudrait !");
				}
				try {
					motClefList.setSelectedIndex(0);
				} catch (IllegalArgumentException e) {
					System.err.println("Liste de mot clefs vide.");
				}
			}
		});
		motClefCatList.setBounds(12, 24, 170, 24);
		motClefCatList.setPreferredSize(new Dimension(125, 24));
		motClefCatList.setMinimumSize(new Dimension(125, 24));
		panel_4.add(motClefCatList);

		JButton button_2 = new JButton("+");
		button_2.setBounds(138, 51, 44, 25);
		panel_4.add(button_2);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(5, 170, 190, 315);
		panel_5.setBorder(new TitledBorder(null, "Filtres actuels", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));

		JScrollPane scrollPane_1 = new JScrollPane((Component) null);
		scrollPane_1.setToolTipText("Double cliquez sur un filtre pour le supprimer.");
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		panel_5.add(scrollPane_1);
		
		JList list = new JList();
		scrollPane_1.setViewportView(list);

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
				domainesComboBox.setSelectedIndex(domainesComboBox.getItemCount() - 1);
				updateFrameContent();
			}
		});
		mntmNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnDomaine.add(mntmNouveau);

		JSeparator separator = new JSeparator();
		mnDomaine.add(separator);

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finish();
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
				if (!canMakeDocument()) {
					return;
				}
				NewElectronicDocumentDialog nfd = new NewElectronicDocumentDialog(domaine);
				nfd.setVisible(true);
				if (nfd.getResult() == FileDialogResultEnum.ok) {
					updateFileList();
				}
			}
		});
		mnNouveau.add(mntmDocumentElectronique);

		JMenuItem mntmDocumentPapier = new JMenuItem("Document papier...");
		mntmDocumentPapier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!canMakeDocument()) {
					return;
				}
				NewPaperDocumentDialog npd = new NewPaperDocumentDialog(domaine);
				npd.setVisible(true);
				if (npd.getResult() == FileDialogResultEnum.ok){
					updateFileList();
				}
			}
		});
		mnNouveau.add(mntmDocumentPapier);

		JMenu mnMotClefs = new JMenu("Mot clefs");
		menuBar.add(mnMotClefs);

		JMenuItem mntmNouvelleCatgorieDe = new JMenuItem("Nouvelle catégorie de mot clef...");
		mntmNouvelleCatgorieDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!domaineExists()) {
					return;
				}
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
		mntmNouveauMotClef.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!domaineExists()) {
					return;
				}
				if (!domaine.hasCategories()) {
					javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Vous devez créer une catégorie de mot clef.");
					return;
				}
				AjoutMotClefDialog amcd = new AjoutMotClefDialog(domaine.getCategoriesMotClef());
				amcd.setVisible(true);
				if (amcd.getResult() == FileDialogResultEnum.ok) {
					motClefCatList.setSelectedItem(amcd.getCategorie());
					motClefList.setSelectedIndex(motClefList.getModel().getSize() - 1);
				}
			}
		});
		mnMotClefs.add(mntmNouveauMotClef);

		JMenu mnCritres = new JMenu("Critères");
		menuBar.add(mnCritres);

		JMenuItem mntmAjouterUnCritre = new JMenuItem("Ajouter un critère...");
		mntmAjouterUnCritre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!domaineExists()) {
					return;
				}
				String critere = JOptionPane.showInputDialog("Veuillez indiquer le nom du critère :");
				if (critere.equals("")) {
					javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Aucun nom de critère saisi, le critère ne sera PAS créé.");
					return;
				}
				domaine.addCritere(new Critere(critere));
				updateCritereList(true);
			}
		});
		mnCritres.add(mntmAjouterUnCritre);

		ready = true;
		try {
			domainesComboBox.setSelectedIndex(0);
		} catch (IllegalArgumentException e) {
			//Rien, juste pour attraper l'exception
		}
	}

	protected void updateMotClefCatList() {
		motClefCatList.setModel(new CategorieListModel(domaine.getCategoriesMotClef()));
		try {
			motClefCatList.setSelectedIndex(0);
		} catch (IllegalArgumentException e) {
			//HACK : Insère une liste vide quand il n'existe pas de données
			motClefList.setModel(new MotClefListModel(new ArrayList<MotClef>()));
		}
	}

	private void updateCritereList(boolean selectEnd) {
		critereList.setModel(new CritereListModel(domaine.getCriteres()));
		try {
			critereList.setSelectedIndex((selectEnd)?critereList.getModel().getSize()-1:0);
		} catch (IllegalArgumentException e) {
			System.err.println("Erreur lors du chargement des critères, sans doute il n'en existe pas.");
		}
	}

	private void updateDomainlist() {
		domainesComboBox.removeAllItems();
		for (String d : domaines) {
			domainesComboBox.addItem(d);
		}
	}

	private void updateFileList() {
		FileListModel flm = (FileListModel) listeFichiers.getModel();
		flm.clear();
		for (CategorieMotClef c : domaine.getCategoriesMotClef()) {
			for (MotClef m : c.getMotClefs()) {
				for (Document d : m.getDocuments()) {
					try {
						flm.add(d);
					} catch(IllegalArgumentException e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}
		listeFichiers.repaint();
	}

	private void loadDomain(String domaine_selection) {
		try {
			ObjectInputStream input_domain = new ObjectInputStream(new FileInputStream(domaine_selection+FILE_EXTENSION));
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

		updateFrameContent();
	}

	private void updateFrameContent() {
		updateMotClefCatList();
		updateCritereList(false);
		updateFileList();
	}

	private void saveDomain() {
		if (domaine == null) {
			return;
		}
		try {
			ObjectOutputStream output_domain = new ObjectOutputStream(new FileOutputStream(domaine.getNom()+FILE_EXTENSION));
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
				return name.endsWith(FILE_EXTENSION);
			}
		});
	}

	private void finish() {
		saveDomain();
		frmDocumentmanager.dispose();
		System.exit(0);
	}

	public JComboBox getComboBox_1() {
		return critereList;
	}
	
	private boolean canMakeDocument() {
		 if (!domaineExists()) {
			 return false;
		 }
		
		if (domaine.getCategoriesMotClef().size() > 0) {
			for (CategorieMotClef c : domaine.getCategoriesMotClef()) {
				if (c.getMotClefs().size() > 0) {
					return true;
				}
			}
			javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Vous devez créer au moins un mot clef.");
		} else {
			javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Vous devez créer au moins une catégorie de mot clef.");
		}
		return false;
	}

	private boolean domaineExists() {
		if (domaine == null) {
			javax.swing.JOptionPane.showMessageDialog(MainWindow.this.frmDocumentmanager,"Vous devez créer un domaine.");
			return false;
		}
		return true;
	}
}
