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

import com.documentmanager.models.Document;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import javax.swing.DefaultComboBoxModel;
import com.documentmanager.models.Etoile;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class EditFileDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public EditFileDialog(Document d) {
		setResizable(false);
		setTitle("Edition de fichier");
		setModal(true);
		setBounds(100, 100, 450, 579);
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
		
		filenameLabel.setText(d.toString());
		
		JLabel lblType = new JLabel("Type :");
		lblType.setHorizontalAlignment(SwingConstants.RIGHT);
		lblType.setBounds(12, 39, 122, 15);
		contentPanel.add(lblType);
		
		JLabel typeLabel = new JLabel("FILETYPE_HERE");
		typeLabel.setBounds(146, 39, 272, 15);
		contentPanel.add(typeLabel);
		
		typeLabel.setText(d.getDocumentType());
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Mots clefs", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setBounds(12, 310, 424, 191);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(12, 24, 170, 24);
		panel.add(comboBox_1);
		
		JButton button = new JButton("+");
		button.setBounds(367, 24, 45, 25);
		panel.add(button);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setBounds(194, 24, 161, 24);
		panel.add(comboBox_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 60, 400, 119);
		panel.add(scrollPane_1);
		
		JList list_1 = new JList();
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(list_1);
		
		JLabel lblNote = new JLabel("Note :");
		lblNote.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNote.setBounds(12, 80, 122, 15);
		contentPanel.add(lblNote);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(Etoile.values()));
		comboBox.setBounds(146, 75, 290, 24);
		contentPanel.add(comboBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 66, 424, 2);
		contentPanel.add(separator);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "Crit\u00E8res", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(12, 107, 424, 191);
		contentPanel.add(panel_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setBounds(12, 24, 343, 24);
		panel_1.add(comboBox_2);
		
		JButton button_1 = new JButton("+");
		button_1.setBounds(367, 24, 45, 25);
		panel_1.add(button_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setToolTipText("Double cliquez pour effacer un critère.");
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 60, 400, 119);
		panel_1.add(scrollPane);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						EditFileDialog.this.setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
