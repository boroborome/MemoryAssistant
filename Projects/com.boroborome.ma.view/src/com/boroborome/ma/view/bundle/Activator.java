package com.boroborome.ma.view.bundle;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.osgi.framework.BundleContext;

import com.boroborome.footstone.AbstractFootstoneActivator;
import com.boroborome.ma.view.MainFrame;

public class Activator extends AbstractFootstoneActivator
{

	@Override
	public void bundleStart(BundleContext context)
	{
		MainFrame frame = new MainFrame();
		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                System.exit(0);
            }
        });
		frame.setVisible(true);
	}

	@Override
	public void bundleStop(BundleContext context)
	{
		
	}

}
