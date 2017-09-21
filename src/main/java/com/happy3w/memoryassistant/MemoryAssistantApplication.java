package com.happy3w.memoryassistant;

import com.happy3w.memoryassistant.utils.ContextHolder;
import com.happy3w.memoryassistant.view.MainFrame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SpringBootApplication
@ComponentScan(basePackages = {"com.happy3w.footstone.bundle", "com.happy3w.footstone.sql", "com.happy3w.memoryassistant"})
public class MemoryAssistantApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(MemoryAssistantApplication.class).headless(false).run(args);
//		ConfigurableApplicationContext context = SpringApplication.run(MemoryAssistantApplication.class, args);
		ContextHolder.setContext(context);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
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
		});
	}
}
