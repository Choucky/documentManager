package com.documentmanager.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;

import com.documentmanager.models.CategorieMotClef;
import com.documentmanager.models.Critere;
import com.documentmanager.models.Document;
import com.documentmanager.models.DocumentTypeEnum;
import com.documentmanager.models.Domaine;
import com.documentmanager.models.MotClef;
import com.documentmanager.models.Note;
import com.documentmanager.models.PaperDocument;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import javax.swing.DefaultComboBoxModel;
import com.documentmanager.models.Etoile;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditDocumentDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JComboBox critereList;
	private Document document;
	private Domaine domaine;
	private JComboBox noteList;
	private JList critereListDocument;
	private JComboBox motClefCatList;
	private JComboBox motClefList;
	private JList motClefListDocument;

	public EditDocumentDialog(Document doc, Domaine dom) {
		document = doc;
		domaine = dom;
		
		setResizable(false);
		setTitle("Edition de fichier");
		setModal(true);
		setBounds(100, 100, 450, 563);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNomDuFichier = new JLabel("Nom du fichier :");
			lblNomDuFichier.setHorizontalAlignment(SwingConstants.RIGHT);
			lblNomDuFichier.setBounds(12, 12, 122, 15);
			contentPanel.add(lblNomDuFichier);
		}
		
		JLabel filenameLabel = new JLabel();
		filenameLabel.setBounds(146, 12, 272, 15);
		contentPanel.add(filenameLabel);
		
		filenameLabel.setText(doc.toString());
		
		JLabel lblType = new JLabel("Type :");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setBounds(12, 39, 122, 15);
		contentPanel.add(lblType);
		
		JLabel typeLabel = new JLabel("FILETYPE_HERE");
		typeLabel.setBounds(146, 39, 272, 15);
		contentPanel.add(typeLabel);
		
		typeLabel.setText(doc.getDocumentType().toString());
		
		if (doc.getDocumentType() == DocumentTypeEnum.documentPapier) {
			JLabel lblCommentaire = new JLabel("Commentaire :");
			lblCommentaire.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCommentaire.setBounds(22, 66, 112, 15);
			contentPanel.add(lblCommentaire);
			
			PaperDocument paperDoc = (PaperDocument) doc;
			JLabel label = new JLabel(paperDoc.getCommentaire());
			label.setBounds(146, 66, 290, 15);
			contentPanel.add(label);
		}
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Mots clefs", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setBounds(12, 296, 424, 191);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		motClefList = new JComboBox();
		motClefList.setBounds(194, 24, 161, 24);
		panel.add(motClefList);
		
		motClefCatList = new JComboBox();
		motClefCatList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				motClefList.setModel(new MotClefListModel(((CategorieMotClef) motClefCatList.getSelectedItem()).getMotClefs()));
				try {
					motClefList.setSelectedIndex(0);
				} catch (IllegalArgumentException e) {
					System.err.println("Pas de mot clefs pour cette catégorie.");
				}
			}
		});
		motClefCatList.setBounds(12, 24, 170, 24);
		motClefCatList.setModel(new CategorieListModel(dom.getCategoriesMotClef()));
		motClefCatList.setSelectedIndex(0);
		panel.add(motClefCatList);
		
		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MotClef mot = (MotClef) motClefList.getSelectedItem();
				try {
					mot.addDocument(document);
					document.addMotClef(mot);
				} catch (CloneNotSupportedException e) {
					javax.swing.JOptionPane.showMessageDialog(EditDocumentDialog.this,"Impossible d'assigner le mot clef : " + e.getMessage());
				}
				
				
				populateLists();
			}
		});
		button.setBounds(367, 24, 45, 25);
		panel.add(button);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 60, 400, 119);
		panel.add(scrollPane_1);
		
		motClefListDocument = new JList();
		motClefListDocument.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JList source = (JList) arg0.getSource();
				if (source.getSelectedValue() == null) {
					return;
				}
				if (arg0.getClickCount() == 2 ) {
					if (source.getModel().getSize() == 1) {
						javax.swing.JOptionPane.showMessageDialog(EditDocumentDialog.this,"Vous ne pouvez pas supprimer le dernier mot clef !");
						return;
					}
					DocumentMotClefListModel dmclm = (DocumentMotClefListModel) source.getModel();
					MotClef mot = dmclm.getMotClefAt(source.getSelectedIndex());
					mot.removeDocument(document);
					document.removeMotClef(mot);
					populateLists();
				}
			}
		});
		motClefListDocument.setToolTipText("Double-cliquez pour effacer un mot clef.");
		motClefListDocument.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(motClefListDocument);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Crit\u00E8res", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 93, 424, 191);
		contentPanel.add(panel_1);
		
		critereList = new JComboBox();
		critereList.setBounds(12, 24, 169, 24);
		critereList.setModel(new CritereListModel(new ArrayList<Critere>()));
		critereList.setModel(new CritereListModel(domaine.getCriteres()));
		try {
			critereList.setSelectedIndex(0);
		} catch (IllegalArgumentException e) {
			System.err.println("Aucun critère.");
		}
		
		panel_1.add(critereList);
		
		JButton button_1 = new JButton("+");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Critere critere = (Critere) critereList.getSelectedItem();
				Etoile star = (Etoile) noteList.getSelectedItem();
				
				Note note = document.findNoteOf(critere);
				if (note == null) {
					document.addNote(star, document, critere);
				} else {
					note.setEtoile(star);
				}
				populateLists();
			}
		});
		button_1.setBounds(367, 24, 45, 25);
		panel_1.add(button_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("Double cliquez pour effacer un critère.");
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 60, 400, 119);
		panel_1.add(scrollPane);
		
		critereListDocument = new JList();
		critereListDocument.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JList source = (JList) arg0.getSource();
				if (source.getSelectedValue() == null) {
					return;
				}
				if (arg0.getClickCount() == 2) {
					document.deleteNote((Note) source.getSelectedValue());
					populateLists();
				}
			}
		});
		critereListDocument.setToolTipText("Double-cliquez pour effacer un critère.");
		scrollPane.setViewportView(critereListDocument);
		critereListDocument.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		critereListDocument.setModel(new CritereListModel(new ArrayList<Critere>()));
		
		noteList = new JComboBox();
		noteList.setBounds(193, 24, 162, 24);
		panel_1.add(noteList);
		noteList.setModel(new DefaultComboBoxModel(Etoile.values()));
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						EditDocumentDialog.this.setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		populateLists();
	}
	
	private void populateLists() {
		critereListDocument.setModel(new NoteListModel(document.getNotes()));
		motClefListDocument.setModel(new DocumentMotClefListModel(document,domaine));
	}
}
