import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Stroke;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PongPanel extends JPanel implements ActionListener, KeyListener {

	private final static Color WINDOW_BG_COLOR = Color.BLACK;
	private final static int TIMER_DELAY = 5;
	private final static int BALL_MOVEMENT_SPEED = 3;
	Ball ball;
	Paddle paddle1, paddle2;
	GameState gameState = GameState.Initialising;
	private final static int POINTS_TO_WIN = 3;
	int player1Score = 0, player2Score = 0;
	Player gameWinner;
	private final static int SCORE_TEXT_X = 100;
    private final static int SCORE_TEXT_Y = 100;
    private final static int SCORE_FONT_SIZE = 50;
    private final static String SCORE_FONT_FAMILY = "Serif";
    
    private final static int WIN_TEXT_X = 100;
    private final static int WIN_TEXT_Y = 150;
    private final static int WIN_FONT_SIZE = 50;
    private final static String WIN_FONT_FAMILY = "Serif";
    
    
	
	@Override
	public void keyTyped(KeyEvent event) {
		// TODO Auto-generated method stub		
	}

	@Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_UP) {
            paddle2.setYVelocity(-5);
       } else if(event.getKeyCode() == KeyEvent.VK_DOWN) {
            paddle2.setYVelocity(5);
        }
        
        if(event.getKeyCode() == KeyEvent.VK_W) {
            paddle1.setYVelocity(-5);
       } else if(event.getKeyCode() == KeyEvent.VK_S) {
            paddle1.setYVelocity(5);
        }
    }

   @Override
   public void keyReleased(KeyEvent event) {
       if(event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
           paddle2.setYVelocity(0);
       }
       if(event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
           paddle1.setYVelocity(0);
       }
   }
   
	private void update() {
        switch(gameState) {
            case Initialising: {
                createObjects();
               gameState = GameState.Playing;
               ball.setXVelocity(BALL_MOVEMENT_SPEED);
               ball.setYVelocity(BALL_MOVEMENT_SPEED);
                break;
            }
            case Playing: {
            	moveObject(paddle1);
            	moveObject(paddle2);
            	moveObject(ball);
            	checkWallBounce();
            	checkPaddleBounce();
            	checkWin();
                break;
           }
           case GameOver: {
               break;
           }
       }
   }

	public void actionPerformed(ActionEvent event) {
		update();	
		repaint();
	}
	
	public PongPanel() {
		setBackground(WINDOW_BG_COLOR);
		Timer timer = new Timer(TIMER_DELAY, this);
			timer.start();	
		addKeyListener(this);
		setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintDottedLine(g);
		if(gameState != GameState.Initialising) {
            paintSprite(g, ball);
            paintSprite(g, paddle1);
            paintSprite(g, paddle2);
            paintScores(g);
        }
		if (gameState == GameState.GameOver) {
			paintWin(g);
		}
	}
	
	private void paintDottedLine(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
			Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {9}, 0);
			g2d.setStroke(dashed);
			g2d.setPaint(Color.WHITE);
			g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
			g2d.dispose();
	}
	
	private void createObjects() {
		ball = new Ball(getWidth(), getHeight());
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	private void paintSprite(Graphics g, Sprite sprite) {
	      g.setColor(sprite.getColour());
	      g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	private void moveObject(Sprite object) {
	      object.setXPosition(object.getXPosition() + object.getXVelocity(),getWidth());
	      object.setYPosition(object.getYPosition() + object.getYVelocity(),getHeight());
	 }	  

	  private void checkWallBounce() {
	      if(ball.getXPosition() <= 0) {
	          // Hit left side of screen
	          ball.setXVelocity(-ball.getXVelocity());
	          resetBall();
	          addScore(Player.Two);
	      } else if(ball.getXPosition() >= getWidth() - ball.getWidth()) {
	          // Hit right side of screen
	          ball.setXVelocity(-ball.getXVelocity());
	          resetBall();
	          addScore(Player.One);
	      }
	      if(ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() - ball.getHeight()) {
	          // Hit top or bottom of screen
	          ball.setYVelocity(-ball.getYVelocity());
	      }	
	  }
	private void resetBall() {
		ball.resetToInitialPosition();
	}
	
	private void checkPaddleBounce() {
	      if(ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
	          ball.setXVelocity(BALL_MOVEMENT_SPEED);
	      }

	      if(ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
	          ball.setXVelocity(-BALL_MOVEMENT_SPEED);
	      }
	}
	
	private void addScore(Player player) {
		switch (player) {
		case One: {
			player1Score = player1Score + 1;
			break;
		}
		case Two: {
			player2Score = player2Score + 1;
			break;
		}
		}
	}
	
	private void checkWin() {
		if (player1Score >= POINTS_TO_WIN) {
			gameWinner = Player.One;
			gameState = GameState.GameOver;
		}
		else if (player2Score >= POINTS_TO_WIN) {
			gameWinner = Player.Two;
			gameState = GameState.GameOver;
		}
	}
	
	private void paintScores(Graphics g) {
        Font scoreFont = new Font(SCORE_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
       String leftScore = Integer.toString(player1Score);
       String rightScore = Integer.toString(player2Score);
       g.setFont(scoreFont);
       g.drawString(leftScore, SCORE_TEXT_X, SCORE_TEXT_Y);
       g.drawString(rightScore, getWidth()-SCORE_TEXT_X, SCORE_TEXT_Y);
   }
	
	private void paintWin(Graphics g) {
        Font winFont = new Font(WIN_FONT_FAMILY, Font.BOLD, WIN_FONT_SIZE);
        g.setFont(winFont);
       
     switch (gameWinner) {
	     case One: {
	    	 g.drawString("Winner!",WIN_TEXT_X, WIN_TEXT_Y);
	    	 break;
	     }
	     case Two: {
	    	 g.drawString("Winner!",  getWidth()-WIN_TEXT_X, WIN_TEXT_Y);
	     }
     }       
   }
	
}
