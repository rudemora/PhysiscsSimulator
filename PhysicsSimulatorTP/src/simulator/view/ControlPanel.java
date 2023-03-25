package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {

	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton;
	private JButton _loadButton;
	private JButton _forceButton;
	private JButton _openButton;
	private JButton _runButton;
	private JButton _stopButton;
	// TODO añade más atributos aquí …
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		// TODO registrar this como observador
		ctrl.addObserver(this);
	
	}
	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);
		// TODO crear los diferentes botones/atributos y añadirlos a _toolaBar.
		// Todos ellos han de tener su correspondiente tooltip. Puedes utilizar
		// _toolaBar.addSeparator() para añadir la línea de separación vertical
		// entre las componentes que lo necesiten
		
		// Quit Button
		_toolaBar.add(Box.createGlue()); // this aligns the button to the right
		_toolaBar.addSeparator();
		_quitButton = new JButton(); //crear boton
		_quitButton.setToolTipText("Quit"); //descripcion del boton
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png")); //imagen del boton
		_quitButton.addActionListener((e) -> Utils.quit(this)); 
		_toolaBar.add(_quitButton);
		// TODO crear el selector de ficheros
		// Selector ficheros
		_fc = new JFileChooser();
		_toolaBar.addSeparator();
		_loadButton = new JButton();
		_loadButton.setToolTipText("Load an input file into the simulator");
		_loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		/*class GestorFile implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				int res = _fc.showOpenDialog(Utils.getWindow(this));
			}
			
		}*/
		//GestorFile gestorFileChooser = new GestorFile();
		//_loadButton.addActionListener(gestorFileChooser);
		_toolaBar.add(_loadButton,FlowLayout.LEFT);
		
		//ForceLaws
		_toolaBar.addSeparator();
		_forceButton = new JButton(); //crear boton
		_forceButton.setToolTipText("Select force laws for groups"); //descripcion del boton
		_forceButton.setIcon(new ImageIcon("resources/icons/physics.png")); //imagen del boton
		_forceButton.addActionListener((e) -> Utils.quit(this)); 
		_toolaBar.add(_forceButton, FlowLayout.LEFT + 1);
		
		//ViewerWindow
		_toolaBar.addSeparator();
		_openButton = new JButton(); //crear boton
		_openButton.setToolTipText("Open viewer window"); //descripcion del boton
		_openButton.setIcon(new ImageIcon("resources/icons/open.png")); //imagen del boton
		_openButton.addActionListener((e) -> Utils.quit(this)); 
		_toolaBar.add(_openButton, FlowLayout.LEFT + 2);
		
		//Run Button
		_toolaBar.addSeparator();
		_runButton = new JButton(); //crear boton
		_runButton.setToolTipText("Run the simulator"); //descripcion del boton
		_runButton.setIcon(new ImageIcon("resources/icons/run.png")); //imagen del boton
		_runButton.addActionListener((e) -> Utils.quit(this)); 
		_toolaBar.add(_runButton, FlowLayout.LEFT + 3);
		
		//Stop Button
		_toolaBar.addSeparator();
		_stopButton = new JButton(); //crear boton
		_stopButton.setToolTipText("Stop the simulator"); //descripcion del boton
		_stopButton.setIcon(new ImageIcon("resources/icons/stop.png")); //imagen del boton
		_stopButton.addActionListener((e) -> Utils.quit(this)); 
		_toolaBar.add(_stopButton, FlowLayout.LEFT + 4);
	}
	
	
	

	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}

}
