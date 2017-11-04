package com.thegame.game.config;

import java.io.File;
import java.util.Base64;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.thegame.design.DesignLevel;
import com.thegame.game.collectable.Collectable;
import com.thegame.game.graphics.SpriteSheet;
import com.thegame.game.level.Level;
import com.thegame.game.mob.Knight;
import com.thegame.game.mob.Knight2;
import com.thegame.game.mob.Mob;
import com.thegame.game.mob.Player;

public class XmlLevelConfig {	
		
	public static boolean writeLevelConfig(DesignLevel level, File xmlFile) {
		if (level == null || xmlFile == null)
			return false;
		
		try {
			if (xmlFile.exists()) 
				xmlFile.delete();
			
			xmlFile.createNewFile();
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("level");
			doc.appendChild(rootElement);

			// level
			rootElement.setAttribute("width", String.valueOf(level.getWidth()));
			rootElement.setAttribute("height", String.valueOf(level.getHeight()));
			rootElement.setAttribute("name", level.getLevelname());

			// background
			Element background = doc.createElement("background");
			rootElement.appendChild(background);
			saveBackground(doc, level, background);

			// map element
			Element map = doc.createElement("map");
			rootElement.appendChild(map);
			saveMap(doc, level, map);

			// player element
			Element players = doc.createElement("players");
			rootElement.appendChild(players);
			savePlayers(doc, level, players);

			// mob element
			Element mob = doc.createElement("mobs");
			rootElement.appendChild(mob);
			saveMobs(doc, level, mob);

			// collectable element
			Element collectable = doc.createElement("collectable");
			rootElement.appendChild(collectable);
			saveCollectable(doc, level, collectable);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlFile);

			transformer.transform(source, result);

			System.out.println("File saved!");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		return true;
	}

	public static void loadLevelConfig(Level level, File xmlFile) {
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
	
			Element root = doc.getDocumentElement();
			root.normalize();

			int width = Integer.valueOf(root.getAttribute("width"));
			int height = Integer.valueOf(root.getAttribute("height"));
			
			level.setLevelname(root.getAttribute("name"));
			level.setSize(width, height);
	
			NodeList map = doc.getElementsByTagName("map");
			loadMap(level, map);

			NodeList background = doc.getElementsByTagName("background");
			loadBackground(level, background);

			NodeList players = doc.getElementsByTagName("players");
			loadPlayers(level, players);

			NodeList mobs = doc.getElementsByTagName("mobs");
			loadMobs(level, mobs);

			NodeList collectables = doc.getElementsByTagName("collectables");
			loadCollectables(level, collectables);
			
	    } catch (Exception e) {
			e.printStackTrace();
	    }
	}


	// ----- MAP
	private static void loadMap(Level level, NodeList map) {
		if (map == null || map.getLength() < 1)
			return;

		if (map.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) map.item(0);
			int width = attr2int(element.getAttribute("width"));
			int height = attr2int(element.getAttribute("height"));
			String base64 = element.getAttribute("data");
			
			level.setSize(width, height);
			level.setTiles(convertFromBase64(base64));
		}
	}
	private static void saveMap(Document doc, DesignLevel level, Element element) {
		String base64 = convertToBase64(level.getTiles());
		element.setAttribute("data", base64);
		element.setAttribute("width", int2attr(level.getWidth()));
		element.setAttribute("height", int2attr(level.getHeight()));
	}

	
	// ----- BACKGROUND
	private static void loadBackground(Level level, NodeList background) {
		if (background == null || background.getLength() < 1)
			return;

		if (background.item(0).getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) background.item(0);
			int width = attr2int(element.getAttribute("width"));
			int height = attr2int(element.getAttribute("height"));
			String base64 = element.getAttribute("data");
			
			level.setBackground(convertFromBase64(base64), width, height);
		}
	}
	private static void saveBackground(Document doc, DesignLevel level, Element element) {
		if (level == null || level.getBackground() == null)
			return;
		
		String base64 = convertToBase64(level.getBackground());
		element.setAttribute("width", int2attr(level.getBgWidth()));
		element.setAttribute("heigt", int2attr(level.getBgHeight()));
		element.setAttribute("data", base64);
	}
	

	// ----- PLAYERS
	private static void loadPlayers(Level level, NodeList players) {
		if (players == null || players.getLength() < 1)
			return;
		
		for (int idx = 0; idx < players.getLength(); idx++) {
			Node player = players.item(idx);
			
			if (player.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) player;
				int x = attr2int(element.getAttribute("x"));
				int y = attr2int(element.getAttribute("y"));
				String name = element.getAttribute("name");
				
				level.add(new Player(name, x, y, null));
			}
		}
	}
	private static void savePlayers(Document doc, Level level, Element element) {
		for (Player player : level.getPlayers()) {
			Element e = doc.createElement("player");
			e.setAttribute("x", int2attr(player.getX()));
			e.setAttribute("y", int2attr(player.getY()));
			e.setAttribute("name", player.getName());
			e.setAttribute("sprite", player.getSprite().getClass().getName());
			e.setAttribute("class", player.getClass().getName());
			element.appendChild(e);
		}
	}
	

	// ----- MOBS
	private static void loadMobs(Level level, NodeList mobs) {
		if (mobs == null || mobs.getLength() < 1)
			return;
		
		for (int idx = 0; idx < mobs.getLength(); idx++) {
			Node mob = mobs.item(idx).getFirstChild();
			
			if (mob.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) mob;
				int x = attr2int(element.getAttribute("x"));
				int y = attr2int(element.getAttribute("y"));
				String classname = element.getAttribute("class");
				
				if (Knight.class.getName().equals(classname)) {
					level.add(new Knight(x, y));
				} else if (Knight2.class.getName().equals(classname)) {
					level.add(new Knight2(x, y));
				}
			}
		}		
	}
	private static void saveMobs(Document doc, Level level, Element element) {
		for (Mob mob : level.getMobs()) {	
			Element e = doc.createElement("mob");	
			e.setAttribute("x", int2attr(mob.getX()));
			e.setAttribute("y", int2attr(mob.getY()));
			e.setAttribute("sprite", mob.getSprite().getClass().getName());
			e.setAttribute("class", mob.getClass().getName());
			element.appendChild(e);
		}
	}
	

	// ----- COLLECTABLES
	private static void loadCollectables(Level level, NodeList collectables) {
		if (collectables == null || collectables.getLength() < 1)
			return;
		
		for (int idx = 0; idx < collectables.getLength(); idx++) {
			Node collectable = collectables.item(idx).getFirstChild();
			
			if (collectable.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) collectable;
				int x = attr2int(element.getAttribute("width"));
				int y = attr2int(element.getAttribute("height"));
				String classname = element.getAttribute("class");
				
				if (Knight.class.getName().equals(classname)) {
					level.add(new Collectable(SpriteSheet.bronze_one, x, y));
				} else if (Knight2.class.getName().equals(classname)) {
					level.add(new Collectable(SpriteSheet.bronze_heart, x, y));
				}
			}
		}	
	}
	private static void saveCollectable(Document doc, DesignLevel level, Element element) {
		for (Collectable collectable : level.getCollectables()) {
			Element e = doc.createElement("collectable");	
			e.setAttribute("x", int2attr(collectable.getX()));
			e.setAttribute("y", int2attr(collectable.getY()));
			e.setAttribute("sprite", collectable.getSprite().getClass().getName());
			e.setAttribute("class", collectable.getClass().getName());
			element.appendChild(e);
		}
	}
	

	private static String convertToBase64(int[] ints) {
	    byte[] bytes = new byte[ints.length];
	    for (int i = 0; i < ints.length; i++) {
	        bytes[i] = (byte)ints[i];
	    }
	    return Base64.getEncoder().encodeToString(bytes);
	}

	private static int[] convertFromBase64(String base64) {
		byte[] bytes = Base64.getDecoder().decode(base64);
	    int[] ints = new int[bytes.length];
	    for (int i = 0; i < bytes.length; i++) {
	        ints[i] = (int)bytes[i];
	    }
	    return ints;
	}
	

	private static int attr2int(String attribute) {
		if (attribute != null && !"".equals(attribute)) {
			return -1;
		}
		
		try {
			return Integer.valueOf(attribute);
		} catch (Exception e) {
			return -1;
		}
	}
	

	private static String int2attr(int num) {
		return String.valueOf(num);
	}
	
}
