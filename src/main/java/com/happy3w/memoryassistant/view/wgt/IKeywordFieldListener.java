/**
 * 
 */
package com.happy3w.memoryassistant.view.wgt;

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
