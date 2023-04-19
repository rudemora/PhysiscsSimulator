package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import simulator.misc.Vector2D;
import simulator.model.BodiesGroup;
import simulator.model.Body;

@SuppressWarnings("serial")
class Viewer extends SimulationViewer {

	private static final int _WIDTH = 500;
	private static final int _HEIGHT = 500;

	// (_centerX,_centerY) is used as the origin when drawing
	// the bodies
	private int _centerX;
	private int _centerY;

	// values used to shift the actual origin (the middle of
	// the window), when calculating (_centerX,_centerY)
	private int _originX = 0;
	private int _originY = 0;

	// the scale factor, used to reduce the bodies coordinates
	// to the size of the component
	private double _scale = 1.0;

	// indicates if the help message should be shown
	private boolean _showHelp = true;

	// indicates if the position/velocity vectors should be shown
	private boolean _showVectors = true;

	// the list bodies and groups
	private List<Body> _bodies;
	private List<BodiesGroup> _groups;

	// a color generator, and a map that assigns colors to groups
	private ColorsGenerator _colorGen;
	private Map<String, Color> _gColor;

	// the index and Id of the selected group, -1 and null means all groups
	private int _selectedGroupIdx = -1;
	private String _selectedGroup = null;

	Viewer() {
		initGUI();
	}

	private void initGUI() {

		// add a border
		setBorder(BorderFactory.createLineBorder(Color.black, 2));

		// initialize the color generator, and the map, that we use
		// assign colors to groups
		_colorGen = new ColorsGenerator();
		_gColor = new HashMap<>();

		// initialize the lists of bodies and groups
		_bodies = new ArrayList<>();
		_groups = new ArrayList<>();

		// The preferred and minimum size of the components
		setMinimumSize(new Dimension(_WIDTH, _HEIGHT));
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT));
		// add a key listener to handle the user actions
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}			
			
			/*
			 * 
			 * 
			 * 
			 * 
			 * ES: Gestiona las teclas 'j','l','i','m' para sumar 10/-10 a
			 * _originX/_originY, y luego llame a repaint(). Esto hará que el punto de
			 * origen se mueva hacia la izquierda/derecha/arriba/abajo. Vea cómo se calculan
			 * los valores de _centerX y _centerY en el método paintComponent
			 * 
			 * 
			 * ES: Gestiona la tecla 'k' para poner _originX y _originY en 0, y luego llame
			 * a repaint(). Esto hace que el punto de origen sea el centro de la ventana.
			 * 
			 * 
			 * 
			 * ES: gestiona la tecla 'h' para cambiar el valor de _showHelp a !_showHelp, y
			 * luego llame a repaint(). Esto hará que se muestre/oculte el texto de ayuda -
			 * ver método paintComponent
			 * 
			 * 
			 * 
			 * ES: gestiona la tecla 'v' para cambiar el valor de _showVectors a
			 * !_showVectors, y luego llame a repaint(). Tienes que usar esta variable en
			 * drawBodies para decidir si mostrar u ocultar los vectores de
			 * velocidad/fuerza.
			 * 
			 * 
			 * 
			 * ES: gestionar la tecla 'g' de manera que haga visible el siguiente grupo.
			 * Tenga en cuenta que después del último grupo, se muestran todos los cuerpos.
			 * Esto se puede hacer modificando _selectedGroupIdx de -1 (todos los grupos) a
			 * _groups.size()-1 de forma circular. Cuando su valor es -1, _selectedGroup
			 * sería nulo, de lo contrario, sería el id del grupo correspondiente. En el
			 * método showBodies, solo dibujarás los que pertenecen al grupo seleccionado.
			 * 
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					repaint();
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					repaint();
					break;
				case '=':
					autoScale();
					repaint();
					break;
				case 'j':
					_originX += 10;
					repaint();
					break;
				case 'l':
					_originX -= 10;
					repaint();
					break;
				case 'i':
					_originY += 10;
					repaint();
					break;
				case 'm':
					_originY -= 10;
					repaint();
					break;
				case 'k':
					_originX = 0;
					_originY = 0;
					repaint();
					break;
				case 'h':
					_showHelp = !_showHelp;
					repaint();
					break;
				case 'v':
					_showVectors = !_showVectors;
					repaint();
					break;
				case 'g':
					_selectedGroupIdx = _selectedGroupIdx + 1;
					if (_selectedGroupIdx == _groups.size()) {
						_selectedGroupIdx = -1;
					}
					if (_selectedGroupIdx == -1) {
						_selectedGroup = null;
					}
					else {
						_selectedGroup = _groups.get(_selectedGroupIdx).getId();
					}
					repaint();
					break;
				default:
				}
			}
		});

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// a better graphics object
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// calculate the center
		_centerX = getWidth() / 2 - _originX;
		_centerY = getHeight() / 2 - _originY;

		// draw red cross at (_centerX,_centerY)
		gr.setColor(Color.RED);
		gr.drawLine(_centerX - 5, _centerY, _centerX + 5, _centerY);
		gr.drawLine(_centerX, _centerY  - 5, _centerX, _centerY + 5);
		// draw bodies
		drawBodies(gr);

		// show help if needed
		if (_showHelp) {
			showHelp(gr);
		}
	}

	private void showHelp(Graphics2D g) {
		/*
		 * 
		 * 
		 * ES: completa el método para que muestre el siguiente texto en la esquina
		 * superior izquierda:
		 * 
		 * h: toggle help, v: toggle vectors, +: zoom-in, -: zoom-out, =: fit 
		 * l: move right, j: move left, i: move up, m: move down: k: reset 
		 * g: show next group
		 * Scaling ratio: ... 
		 * Selected Group: ...
		 * 
		 */
		g.setColor(Color.RED);
		g.drawString("h: toggle help, v: toggle vectors, +: zoom-in, -: zoom-out, =: fit ", 10, 15);
		g.drawString("g: show next group", 10, 30);
		g.drawString("l: move right, j: move left, i: move up, m: move down: k: reset", 10, 45);
		g.drawString("Scaling ratio: " + _scale, 10, 60); 
		g.setColor(Color.BLUE);
		if (_selectedGroup == null) {
			g.drawString("Selected group: all", 10, 75); //TODO un poco feo el if
		}
		else {
			g.drawString("Selected group: " + _selectedGroup, 10, 75); 
		}
		
	}

	private void drawBodies(Graphics2D g) {
		/*
		 * 
		 * ES: Dibuja todos los cuerpos para los que isVisible(b) devuelve 'true' (ver
		 * isVisible abajo, devuelve 'true' si el cuerpo pertenece al grupo
		 * seleccionado). Para cada cuerpo, debes dibujar los vectores de velocidad y
		 * fuerza si _showVectors es 'true'. Usa el método drawLineWithArrow para
		 * dibujar los vectores. El color del cuerpo 'b' debe ser
		 * _gColor.get(b.getgId()) -- ver el método addGroup. Como punto de origen usar
		 * (_centerX,_centerY), y recordar dividir las coordenadas del cuerpo por el
		 * valor de _scale.
		 * 
		 */
		for (Body b: _bodies) {
			if (isVisible(b)) {
				g.setColor(_gColor.get(b.getgId()));
				int componenteX = (int) (_centerX + b.getPosition().getX()/_scale);
				int componenteY = (int) (_centerY + b.getPosition().getY()/_scale);
				g.fillOval(componenteX, componenteY, 10, 10);
				g.setColor(Color.BLACK);
				g.drawString(b.getId(), componenteX, componenteY - 5);
				if (_showVectors) {
					drawLineWithArrow(g, componenteX + 5, componenteY + 5, componenteX + (int) b.getVelocity().direction().scale(40).getX(), componenteY + (int) b.getVelocity().direction().scale(40).getY(), 5, 5, Color.GREEN, Color.GREEN );
					drawLineWithArrow(g, componenteX + 5, componenteY + 5,componenteX + (int) b.getForce().direction().scale(40).getX(), componenteY + (int) b.getForce().direction().scale(40).getY(), 5, 5, Color.RED, Color.RED );
				}
			}
		}
	}

	private boolean isVisible(Body b) {
		/*
		 * 
		 * ES: devuelve true si _selectedGroup es null o igual a b.getgId()
		 *
		 */
		if (_selectedGroup == null || _selectedGroup.equals(b.getgId())) {
			return true;
		}
		return false;
	}

	// calculates a value for scale such that all visible bodies fit in the window
	private void autoScale() {

		double max = 1.0;

		for (Body b : _bodies) {
			Vector2D p = b.getPosition();
			max = Math.max(max, Math.abs(p.getX()));
			max = Math.max(max, Math.abs(p.getY()));
		}

		double size = Math.max(1.0, Math.min(getWidth(), getHeight()));

		_scale = max > size ? 4.0 * max / size : 1.0;
	}

	@Override
	public void addGroup(BodiesGroup g) {
		/*
		 * 
		 * EN: add g to _groups and its bodies to _bodies
		 *
		 * ES: añadir g a _groups y sus cuerpos a _bodies
		 * 
		 */
		_groups.add(g);
		for(Body b: g) {
			addBody(b);
		}
		_gColor.put(g.getId(), _colorGen.nextColor()); // assign color to group
		autoScale();
		update();
	}

	@Override
	public void addBody(Body b) {
		/*
		 * 
		 *  EN: add b to _bodies
		 *  
		 *  ES: añadir b a _bodies
		 *  
		 */
		_bodies.add(b);
		autoScale();
		update();
	}

	@Override
	public void reset() {
		/*
		 * 
		 * EN: clear the group list, bodies list, and the colors map
		 * 
		 * ES: borrar (usando el método clear) la lista de grupos, la lista de cuerpos y
		 * el mapa de colores
		 * 
		 */
		_groups.clear();
		_bodies.clear();
		_gColor.clear();
		_colorGen.reset(); // reset the color generator
		_selectedGroupIdx = -1;
		_selectedGroup = null;
		update();
	}

	@Override
	void update() {
		repaint();
	}

	// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
	// The arrow is of height h and width w.
	// The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(//
			Graphics g, //
			int x1, int y1, //
			int x2, int y2, //
			int w, int h, //
			Color lineColor, Color arrowColor) {

		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}

}
