package gameConfigurations;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;

/**
 * Created by Luise on 26.06.2016.
 */
public class AttributeGenerator extends AttributesBaseListener{

	static String rgbs = "";
	static String aiPath = "";
	static String playerPath = "";
	static String playfield = "";
	static String level = "";
	static String ai = "";
	static int fieldnumber = 0;

	@Override
	/*Diese Methode verarbeitet die eingegebenen RGB-Werte*/
	public void enterColor(AttributesParser.ColorContext ctx) {
		int i = 0;
		while(i<3){
			String element = ctx.Value().get(i).toString();
			int value = Integer.parseInt(element);
			if(value >= 0 && value <= 255){
				rgbs += value;
				if(i <2) rgbs += ",";
			}
			else System.out.println("Falscher RGB Wert!");
			i++;
		}

	}

	@Override
	/*Methode für PlayerPath*/
	public void enterPlayer(AttributesParser.PlayerContext ctx) {
		playerPath = "\""+ctx.getText().split(":")[1].trim().replace("\\", "/")+"\"";
	}

	@Override
	/*Methode für AIPath*/
	public void enterComputerplayer(AttributesParser.ComputerplayerContext ctx) {
		aiPath = "\"" + ctx.getText().split(":")[1].trim().replace("\\", "/") +"\"";
	}

	@Override
	/*Methode für Playfield*/
	public void enterPlayfield(AttributesParser.PlayfieldContext ctx) {
		playfield = "\"" + ctx.getText().split(":")[1].trim().toLowerCase() +"\"";
	}

	@Override
	/*Methode für Level*/
	public void enterLevel(AttributesParser.LevelContext ctx) {
		level = "\"" + ctx.getText().split(":")[1].trim().toLowerCase() +"\"";
	}

	@Override public void enterAi(AttributesParser.AiContext ctx) { ai = "\"" + ctx.getText().split(":")[1].trim().toLowerCase() +"\""; }

	@Override public void enterFieldnumber(AttributesParser.FieldnumberContext ctx) {
		fieldnumber = Integer.parseInt(ctx.getText().split(":")[1].trim().toLowerCase());
	}


	public static void main (){


		//Parse and convert the attributes.txt
		try {
			CharStream in = new org.antlr.v4.runtime.ANTLRInputStream(new FileReader(System.getProperty("user.dir")+"/src/gameConfigurations/attributes.txt"));
			AttributesLexer lexer = new AttributesLexer(in);
			// Get a list of matched tokens
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			// Pass the tokens to the parser
			AttributesParser parser = new AttributesParser(tokens);
			// Specify our entry point
           //Dont call parser.<rule>() twice -> mismatched input '<EOF>' expecting FIELD_NAME"
			AttributesParser.FileContext fileContext = parser.file();
			// Walk it and attach our listener
			ParseTreeWalker walker = new ParseTreeWalker();
			AttributesBaseListener listener = new AttributeGenerator();
			walker.walk(listener, fileContext);

			System.out.println(aiPath + " und "+ playerPath + " mit Spielfeldfarbe:" + rgbs + " Playfield: " + playfield +"mit "+fieldnumber + " Feldern ,"+ " level: "+ level + " ai: " + ai);

			//Create the String of new File
			String classString = "package gameConfigurations; "+
					"import java.awt.*;"+
					"public class Attribute {"+
					"public Color fieldColor;"+
					"public String aiStoneImagePath;"+
					"public String playerStoneImagePath;"+
					"public String playfield; "+
					"public int fieldnumber;"+
					"public String level;"+
					"public String ai;"+

					"public Attribute() {"+
					"this.fieldColor = new Color("+rgbs+");"+
					"this.aiStoneImagePath = "+aiPath+";"+
					"this.playerStoneImagePath = "+playerPath+";"+
					"this.playfield = "+playfield+";"+
					"this.fieldnumber = "+fieldnumber+";"+
					"this.level = "+level+";"+
					"this.ai = "+ai+";"+
					"}"+
					"}";


			//Create the File
           //Edit the path to edit the working directory!!
			File file = new File(System.getProperty("user.dir")+"/src/gameConfigurations/Attribute.java");
			// if file doesn't exists, then create it
			if (!file.exists()) file.createNewFile();
			//Write all in File
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(classString);
			bw.close(); //generate from .java the .class-file
			System.out.println("Attribute.java erstellt!");

		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
