/**
 * 
 */
package com.boroborome.ma.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.boroborme.ma.model.MAInformation;
import com.boroborme.ma.model.MAKeyword;
import com.boroborome.footstone.exception.InputException;
import com.boroborome.footstone.ui.AbstractDataPanel;

/**
 * @author boroborome
 *
 */
public class InformationPanel extends AbstractDataPanel<MAInformation>
{
	private KeywordField txtKeys;
	private JTextArea txtInfoDetail;

	public InformationPanel()
	{
		initUI();
	}
	private void initUI()
	{
		this.setLayout(new GridBagLayout());
		this.add(new JLabel("Key:"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
		txtKeys = new KeywordField();
		this.add(txtKeys, new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
				GridBagConstraints.HORIZONTAL, new Insets(0, 12, 0, 0), 0, 0));
		
		txtInfoDetail = new JTextArea();
		this.add(new JScrollPane(txtInfoDetail), new GridBagConstraints(0, 1, 2, 1, 1, 1, GridBagConstraints.NORTHWEST,
				GridBagConstraints.BOTH, new Insets(12, 0, 0, 0), 0, 0));
		
//		this.add(createButtonPnl(), new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
//				GridBagConstraints.HORIZONTAL, new Insets(12, 0, 0, 0), 0, 0));
	}
//	private Component createButtonPnl()
//	{
//		JPanel pnl = new JPanel();
//		pnl.setLayout(new GridBagLayout());
//		
//		int col = 0;
//		
//		pnl.add(new JLabel(""), new GridBagConstraints(col, 0, 1, 1, 1, 0, GridBagConstraints.NORTHWEST,
//				GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
//		
//		JButton btnApply = new JButton("Apply");
//		btnApply.addActionListener(new ActionListener()
//		{
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				doApply();
//			}
//		});
//		pnl.add(btnApply, new GridBagConstraints(++col, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
//				GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
//		
//		JButton btnCancel = new JButton("Cancel");
//		btnCancel.addActionListener(new ActionListener()
//		{
//			@Override
//			public void actionPerformed(ActionEvent e)
//			{
//				doCancel();
//			}
//		});
//		pnl.add(btnCancel, new GridBagConstraints(++col, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST,
//				GridBagConstraints.NONE, new Insets(0, 12, 0, 0), 0, 0));
//		return pnl;
//	}
//	protected void doApply()
//	{
//		// TODO to implement the information panel's apply action
//		
//	}
	@Override
	public void showData(MAInformation value)
	{
		StringBuilder keyBuilder = new StringBuilder();
		for (MAKeyword keyword : value.getLstKeyword())
		{
			if (keyBuilder.length() != 0)
			{
				keyBuilder.append(' ');
			}
			keyBuilder.append(keyword.getKeyword());
		}
		this.txtKeys.setText(keyBuilder.toString());
		this.txtInfoDetail.setText(value.getContent());
	}

	@Override
	public void collectData(MAInformation value)
	{
		value.setContent(this.txtInfoDetail.getText());
	}

	@Override
	public void verifyInput() throws InputException
	{
		// TODO Auto-generated method stub
		
	}

}
