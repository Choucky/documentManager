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
import java.io.File;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.documentmanager.models.Domaine;

public class NewFileDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField fileTextField;
	final JFileChooser fc = new JFileChooser();
	private FileDialogResultEnum result = FileDialogResultEnum.canceled;

	public NewFileDialog(Domaine domaine) {
		setResizable(false);
		setTitle("Choisir un fichier electronique");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 238);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JButton browseButton = new JButton("...");
			browseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int returnVal = fc.showOpenDialog(NewFileDialog.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fc.getSelectedFile();
			            fileTextField.setText(file.getAbsolutePath());
			        } else {
			        	fileTextField.setText("");
			        }
				}
			});
			browseButton.setBounds(383, 12, 35, 25);
			contentPanel.add(browseButton);
		}
		
		fileTextField = new JTextField();
		fileTextField.setBounds(78, 12, 293, 25);
		contentPanel.add(fileTextField);
		fileTextField.setColumns(10);
		
		JLabel lblFichier = new JLabel("Fichier :");
		lblFichier.setBounds(12, 17, 60, 15);
		contentPanel.add(lblFichier);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Mot clef de d\u00E9part", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 49, 406, 94);
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
		{
			JComboBox catMotClefList = new JComboBox();
			
			for (String s : domaine.getCategoriesMotClef()) {
				catMotClefList.addItem(s);
			}
			
			catMotClefList.setBounds(210, 24, 184, 24);
			panel.add(catMotClefList);
		}
		{
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(210, 56, 184, 24);
			panel.add(comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						result = FileDialogResultEnum.ok;
						NewFileDialog.this.setVisible(false);
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
						NewFileDialog.this.setVisible(false);
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
	
	public String getFileName() {
		return fileTextField.getText();
	}
}
