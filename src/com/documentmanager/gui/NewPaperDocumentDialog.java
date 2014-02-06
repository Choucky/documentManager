package com.documentmanager.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.documentmanager.models.CategorieMotClef;
import com.documentmanager.models.Domaine;
import com.documentmanager.models.MotClef;
import com.documentmanager.models.PaperDocument;

public class NewPaperDocumentDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField commentaireTextField;
	final JFileChooser fc = new JFileChooser();
	private FileDialogResultEnum result = FileDialogResultEnum.canceled;
	private JTextField titreTextField;

	public NewPaperDocumentDialog(final Domaine domaine) {
		setResizable(false);
		setTitle("Choisir un fichier papier");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 267);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		commentaireTextField = new JTextField();
		commentaireTextField.setBounds(147, 49, 271, 25);
		contentPanel.add(commentaireTextField);
		commentaireTextField.setColumns(10);

		JLabel lblCommentaire = new JLabel("Commentaire :");
		lblCommentaire.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCommentaire.setBounds(29, 54, 117, 15);
		contentPanel.add(lblCommentaire);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Mot clef de d\u00E9part", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 98, 406, 94);
		contentPanel.add(panel);
		panel.setLayout(null);
		{
			JLabel lblMotClef = new JLabel("Catégorie de mot clef :");
			lblMotClef.setBounds(12, 24, 180, 24);
			panel.add(lblMotClef);
			lblMotClef.setHorizontalAlignment(SwingConstants.RIGHT);
		}
		{
			JLabel lblMotClef_1 = new JLabel("Mot clef :");
			lblMotClef_1.setHorizontalAlignment(SwingConstants.RIGHT);
			lblMotClef_1.setBounds(22, 56, 170, 24);
			panel.add(lblMotClef_1);
		}
		final JComboBox motClefList = new JComboBox();
		motClefList.setBounds(210, 56, 184, 24);
		panel.add(motClefList);

		final JComboBox catMotClefList = new JComboBox();
		catMotClefList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				motClefList.setModel(new MotClefListModel(((CategorieMotClef) catMotClefList.getSelectedItem()).getMotClefs()));
				motClefList.setSelectedIndex(0);
			}
		});

		catMotClefList.setModel(new CategorieListModel(domaine.getCategoriesMotClef()));
		catMotClefList.setSelectedIndex(0);

		catMotClefList.setBounds(210, 24, 184, 24);
		panel.add(catMotClefList);
		
		titreTextField = new JTextField();
		titreTextField.setColumns(10);
		titreTextField.setBounds(147, 12, 271, 25);
		contentPanel.add(titreTextField);
		
		JLabel labelTitre = new JLabel("Titre :");
		labelTitre.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTitre.setBounds(29, 17, 117, 15);
		contentPanel.add(labelTitre);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (commentaireTextField.getText().isEmpty()) {
						javax.swing.JOptionPane.showMessageDialog(NewPaperDocumentDialog.this,"Vous devez indiquer le fichier.");
						return;
					}
					result = FileDialogResultEnum.ok;
					
					PaperDocument pd = new PaperDocument(titreTextField.getText(), commentaireTextField.getText());
					MotClef mot = (MotClef) motClefList.getSelectedItem();
					pd.addMotClef(mot);
					try {
						mot.addDocument(pd);
					} catch (CloneNotSupportedException e) {
						System.err.println("Etonnant : un fichier a deux critères identiques à sa création.");
					}
					
					NewPaperDocumentDialog.this.setVisible(false);
				}
			});
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Annuler");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					result = FileDialogResultEnum.canceled;
					NewPaperDocumentDialog.this.setVisible(false);
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
	}

	public FileDialogResultEnum getResult() {
		return result;
	}

	public String getFileName() {
		return commentaireTextField.getText();
	}
	
	public String getFileLink() {
		return commentaireTextField.getText();
	}
	
	public String getCategorie() {
		return null;
	}
}
