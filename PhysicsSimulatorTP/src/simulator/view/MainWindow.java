package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		ControlPanel cp = new ControlPanel(_ctrl);
		mainPanel.add(cp, BorderLayout.PAGE_START);
		StatusBar sb = new StatusBar(_ctrl);
		mainPanel.add(sb, BorderLayout.PAGE_END);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		InfoTable tg= new InfoTable("Groups", new GroupsTableModel(_ctrl));
		tg.setPreferredSize(new Dimension(500,250));
		InfoTable tb= new InfoTable("Bodies", new BodiesTableModel(_ctrl));
		tb.setPreferredSize(new Dimension(500,250));
		contentPanel.add(tg);
		contentPanel.add(tb);

		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				Utils.quit(MainWindow.this);
			}
			
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {				
			}

			@Override
			public void windowIconified(WindowEvent e) {				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {				
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {				
			}
		});
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
		
	}
}
