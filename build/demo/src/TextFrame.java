import javax.swing.*;
import java.awt.*;

public class TextFrame extends JFrame {
	public TextFrame () {
		this.setSize(300, 300);
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.CYAN);
		g.fill3DRect(0, 0, 150, 150, true);
	}
}