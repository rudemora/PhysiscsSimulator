package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class StatusBar extends JPanel implements SimulatorObserver {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private JLabel _tiempo;
	private JLabel _grupos;
	private double time;
	private int groups;
	// Añadir los atributos necesarios, si hace falta …
	StatusBar(Controller ctrl) {
	_ctrl=ctrl;
	time=0;
	groups=0;
	initGUI();
	_ctrl.addObserver(this);
	
	
	// registrar this como observador
	}
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		// Crear una etiqueta de tiempo y añadirla al panel
		_tiempo= new JLabel("Time:   "+time);
		add(_tiempo);
		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);
		
		_grupos=new JLabel("Groups:   "+groups);
		add(_grupos);
			// Utilizar el siguiente código para añadir un separador vertical
	
	}
	// el resto de métodos van aquí…
		@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		this.time+=time;
		_tiempo.setText("Time:   " + time);
	}
		
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {			
		this.time=0;
		_tiempo.setText("Time:   " + this.time);
		this.groups = 0;
		_grupos.setText("Groups:   "+ this.groups);
	}
	
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {		
	}
	
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		this.groups++;
		_grupos.setText("Groups:   "+this.groups);
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		this.time=0;
		_tiempo.setText("Time:   " + this.time);
	}
	
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}
}
