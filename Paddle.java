import java.awt.Color;

public class Paddle extends Sprite {
	static final int PADDLE_WIDTH = 10;
	static final int PADDLE_HEIGHT = 50;
	static final int DISTANCE_FROM_EDGE = 40;
	static final Color PADDLE_COLOUR = Color.RED;
	Player player1 = Player.One;
	Player player2 = Player.Two;
	
	public Paddle (Player player, int panelWidth, int panelHeight) {
		setWidth(PADDLE_WIDTH);
		setHeight(PADDLE_HEIGHT);
		setColour(PADDLE_COLOUR);
		int xPos;
		
		if (player == Player.One) {
			xPos = DISTANCE_FROM_EDGE;		
		}
		else {
			xPos = panelWidth - DISTANCE_FROM_EDGE - getWidth();
		}
		setInitialPosition(xPos, panelHeight /2 - (getHeight() / 2));
		resetToInitialPosition();
	}	
}
