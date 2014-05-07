/**
 * 
 */
package com.boroborome.ma.view.wgt;

import java.util.EventListener;

/**
 * @author boroborome
 *
 */
public interface IKeywordFieldListener extends EventListener
{
	public static final String EVENT_KEYWORD_CHANGE = "onKeywordChange";
	
	public void onKeywordChange(KeywordFieldEvent e);
}
