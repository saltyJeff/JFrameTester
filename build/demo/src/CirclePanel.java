import javax.swing.*;
import java.awt.*;

public class CirclePanel extends JPanel {
	public CirclePanel () {
		this.setPreferredSize(new Dimension(500, 500));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.RED);
		g.fillOval(0, 0, 500, 500);
	}
}