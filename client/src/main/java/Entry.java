import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.*;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;

import java.nio.*;

import org.json.*;

public class Entry {
    //swing shit
    static JFrame frame;
	static JMenuBar menuBar;
	static JMenuItem menuItem;
	static JMenu menu;

    //config garbage
    static Dimension windowDims;
    static String title;
    static String config;
    static String SendAddr;

    private static void generateConfig() {
        //TODO fix this

        String data;

        JSONObject root = new JSONObject();

        String addr = new String("");

        try {
            addr = JOptionPane.showInputDialog("Please input the server address");
        }

        catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        root.append("server-address", addr);
        root.append("version", "0.1");

        JSONObject windowprop = new JSONObject();
        windowprop.append("title", "PackNGo");
        windowprop.append("width", 800);
        windowprop.append("height", 600);
        root.append("window", windowprop);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./config.json"));
            writer.write(root.toString());
            writer.close();
        }

        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void getConfig() throws IOException {

    	File configFile = new File("./config.json");
    	if(!configFile.exists()) {
    		generateConfig();
	    }

	    config = new String(Files.readAllBytes(Paths.get("./config.json")));

        JSONObject root = new JSONObject(config.toString());

        JSONObject windowProps = root.getJSONObject("window");
        title = new String(windowProps.getString("title"));
        windowDims = new Dimension(windowProps.getInt("width"),
                windowProps.getInt("height"));


    }

    private static void createZipFile(String path) {

    }

    private static void setUpListeners() {

    }

    private static void generateFrame() {
    	setUpListeners();
		frame = new JFrame(title);

		//The menu bar
		menuBar = new JMenuBar();

		menu = new JMenu("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("Save");

		menu.add(menuItem);

	    menuItem = new JMenuItem("Load");
	    menu.add(menuItem);

	    menu = new JMenu("Edit");
	    menuBar.add(menu);

	    menuItem = new JMenuItem("Preferences");
	    menu.add(menuItem);

	    //configuring the frame
		frame.setJMenuBar(menuBar);

		frame.setSize(windowDims);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
    }

    public static void main(String[] args) {

        try {
	        getConfig();
        }

        catch (IOException e) {
        	System.out.println(e.getMessage());
        }

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				generateFrame();
			}
		});

    }
}
