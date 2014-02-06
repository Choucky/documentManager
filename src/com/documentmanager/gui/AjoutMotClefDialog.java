package com.documentmanager.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.documentmanager.models.CategorieMotClef;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AjoutMotClefDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField motClefField;
	private JComboBox catMotClefList;
	private FileDialogResultEnum result = FileDialogResultEnum.canceled;

	public AjoutMotClefDialog(ArrayList<CategorieMotClef> categories) {
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Ajout d'un mot clef pour une catégorie");
		setBounds(100, 100, 486, 168);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCatgorieDeMot = new JLabel("Catégorie de mot clef :");
			lblCatgorieDeMot.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCatgorieDeMot.setBounds(12, 12, 184, 24);
			contentPanel.add(lblCatgorieDeMot);
		}
		
		catMotClefList = new JComboBox();
		catMotClefList.setBounds(214, 12, 240, 24);
		contentPanel.add(catMotClefList);
		
		for (CategorieMotClef c : categories) {
			catMotClefList.addItem(c);
		}
		
		JLabel lblNouveauMotClef = new JLabel("Nouveau mot clef :");
		lblNouveauMotClef.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNouveauMotClef.setBounds(12, 48, 184, 24);
		contentPanel.add(lblNouveauMotClef);
		
		motClefField = new JTextField();
		motClefField.setBounds(214, 49, 240, 24);
		contentPanel.add(motClefField);
		motClefField.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (motClefField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(AjoutMotClefDialog.this,"Vous devez indiquer le mot clef.");
							return;
						}
						((CategorieMotClef) catMotClefList.getSelectedItem()).addMotClef(motClefField.getText());
						result = FileDialogResultEnum.ok;
						AjoutMotClefDialog.this.setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Annuler");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						result = FileDialogResultEnum.canceled;
						AjoutMotClefDialog.this.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public FileDialogResultEnum getResult() {
		return result;
	}
	
	public CategorieMotClef getCategorie() {
		return (CategorieMotClef) catMotClefList.getSelectedItem();
	}
}
