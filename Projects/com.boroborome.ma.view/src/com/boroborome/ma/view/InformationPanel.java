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

import com.boroborme.ma.model.MAInformation;
import com.boroborme.ma.model.MAKeyword;
import com.boroborome.footstone.exception.InputException;
import com.boroborome.footstone.ui.AbstractDataPanel;
import com.boroborome.ma.view.res.ResConst;

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
		
	}
	
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
		this.txtKeys.setLstKeyword(value.getLstKeyword());
		this.txtInfoDetail.setText(value.getContent());
	}

	@Override
	public void collectData(MAInformation value)
	{
		value.setLstKeyword(txtKeys.getLstKeyword());
		value.setContent(this.txtInfoDetail.getText());
	}

	@Override
	public void verifyInput() throws InputException
	{
		if (txtKeys.isEmpty())
		{
			throw new InputException(ResConst.ResKey, ResConst.NotEmpty, new Object[]{"Keyword"}, txtKeys, null);
		}
	}

}
