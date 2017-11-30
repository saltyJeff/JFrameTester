package io.github.saltyJeff.jframetester;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.tools.*;

public class GraphicsTest {
	private static JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

	public static void testClassFile(File classFile, File outputFile) throws Exception {
		outputFile.createNewFile();
		//reflection fun
		String className = ZipTester.getNameFromFile(classFile);
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {classFile.getAbsoluteFile().getParentFile().toURI().toURL()});
		Class<JFrame> theClass = (Class<JFrame>) Class.forName(className, true, classLoader);
		try {
			theClass.getMethod("paint", Graphics.class);
		}
		catch(Exception e) {
			throw new Exception("File "+className+".class does not contain a method paint(Graphics g). Are you sure it's a JFrame?");
		}
		//no more error craziness
		Constructor<JFrame> theCtor = theClass.getConstructor();
		JFrame frame = theCtor.newInstance();
		//frame.setVisible(true);
		//save screenshot
		BufferedImage img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();

		frame.pack();
		frame.setVisible(true);
		frame.paint(g);
		frame.setVisible(false);
		frame.dispose();

		ImageIO.write(img, "png", outputFile);
		g.dispose();
	}
	public static void testJavaFile(File javaFile, File outputFile) throws Exception {
		javaFile = javaFile.getAbsoluteFile();
		if(compiler == null) {
			throw new IllegalStateException("compiler not found");
		}
		String className = javaFile.getName().substring(0, javaFile.getName().indexOf(".java"));
		int compileStatus = compiler.run(null, System.out, System.err, "-d", javaFile.getParent(), "-sourcepath", javaFile.getParent(), javaFile.getAbsolutePath());
		if(compileStatus != 0) {
			throw new Exception("Compiler error, status "+compileStatus);
		}
		testClassFile(new File(javaFile.getParent(), className+".class"), outputFile);
	}
}
