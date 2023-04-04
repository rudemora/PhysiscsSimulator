package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class ViewerWindow extends JFrame implements SimulatorObserver {
	private Controller _ctrl;
	private SimulationViewer _viewer;
	private JFrame _parent;
	ViewerWindow(JFrame parent, Controller ctrl) {
		super("Simulation Viewer");
		_ctrl = ctrl;
		_parent = parent;
		intiGUI();
		// TODO registrar this como observador
		_ctrl.addObserver(this);
	}
	private void intiGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		// TODO poner contentPane como mainPanel con scrollbars (JScrollPane)
		JScrollPane scp= new JScrollPane(mainPanel);
		setContentPane(scp);
		// TODO crear el viewer y añadirlo a mainPanel (en el centro)
		_viewer= new Viewer();
		// TODO en el método windowClosing, eliminar ‘this’ de los observadores
		addWindowListener(new WindowListener() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				_ctrl.removeObserver(ViewerWindow.this);//TODO pa q ruben se quede tranquilo
				
			}
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			
		});
		pack();
		if (_parent != null)
			setLocation(
					_parent.getLocation().x + _parent.getWidth()/2 - getWidth()/2,
					_parent.getLocation().y + _parent.getHeight()/2 - getHeight()/2);
		setVisible(true);
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		_viewer.update();
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_viewer.reset();//TODO ruben quiere añadir un grupo
		
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(String key: groups.keySet()) {
			_viewer.addGroup(groups.get(key));
		}
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_viewer.addGroup(g);
		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_viewer.addBody(b);
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	// TODO otros métodos van aquí….
}

