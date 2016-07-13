package gameConfigurations;

import java.awt.*;

public class Attribute {
	public Color fieldColor;
	public String aiStoneImagePath;
	public String playerStoneImagePath;
	public String playfield;
	public String level;

	public Attribute() {
		this.fieldColor = new Color(50, 50, 50);
		this.aiStoneImagePath = "img/white.png";
		this.playerStoneImagePath = "img/gruen.png";
		this.playfield = "renpaarden";
		this.level = "free";
	}
}