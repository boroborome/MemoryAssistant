package com.happy3w.memoryassistant;

import com.happy3w.memoryassistant.utils.ContextHolder;
import com.happy3w.memoryassistant.view.MainFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SpringBootApplication
public class MemoryAssistantApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MemoryAssistantApplication.class, args);
		ContextHolder.setContext(context);

		MainFrame frame = new MainFrame();
//		MAMainFrame frame = new MAMainFrame();
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
}
