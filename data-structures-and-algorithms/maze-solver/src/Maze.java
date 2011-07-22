
/**
 * Cowboy Maze will open a text file containing a maze and attempt to solve it, displaying a little cowboy sprite
 * trying to find water in the desert.
 * 
 */
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class Maze extends JFrame {

	private class VisitedNode {
		public int x;
		public int y;
		public String direction;
		
		VisitedNode( int x, int y, String direction ) {
			this.x = x;
			this.y = y;
			this.direction = direction;
		}
		
		public boolean equals( VisitedNode vn ) {
			return( vn.x == this.x && vn.y == this.y );
		}
		
		public String toString() {
			StringBuffer sb = new StringBuffer("");
			sb.append("(" + x + "," + y + " : " + direction + ")" );
			return sb.toString();
		}
	}
	
	private final ArrayList<VisitedNode> visitedSoFar = new ArrayList<VisitedNode>();
	private ArrayList<VisitedNode> bestPathSoFar = new ArrayList<VisitedNode>();
	private String mazePath;
	
    private static final int MAX_WIDTH = 255;
    private static final int MAX_HEIGHT = 255;
    
    private char [][] maze = new char[MAX_HEIGHT][MAX_WIDTH];

    private Random random = new Random();
    private JPanel mazePanel = new JPanel();
    private int width = 0;
    private int height = 0;
    private boolean step = false;
    
    private boolean timerFired = false;
    private Timer timer;
    private final int TIMER_DELAY = 200;
    private final long MAX_TIME = 140000;
    
    private final int SPRITE_WIDTH = 25;
    private final int SPRITE_HEIGHT = 25;
    
    private BufferedImage mazeImage;
    private ImageIcon ground = new ImageIcon("sprites/ground.png");
    private ImageIcon wall1 = new ImageIcon("sprites/cactus.png");
    private ImageIcon wall2 = new ImageIcon("sprites/rock.png");
    private ImageIcon finish = new ImageIcon("sprites/well.png");
    private ImageIcon south1 = new ImageIcon("sprites/cowboy-forward-1.png");
    private ImageIcon south2 = new ImageIcon("sprites/cowboy-forward-2.png");
    private ImageIcon north1 = new ImageIcon("sprites/cowboy-back-1.png");
    private ImageIcon north2 = new ImageIcon("sprites/cowboy-back-2.png");
    private ImageIcon west1 = new ImageIcon("sprites/cowboy-left-1.png");
    private ImageIcon west2 = new ImageIcon("sprites/cowboy-left-2.png");
    private ImageIcon east1 = new ImageIcon("sprites/cowboy-right-1.png");
    private ImageIcon east2 = new ImageIcon("sprites/cowboy-right-2.png");
    
    private long startTime;
    private long currentTime;
    
    
    /**
     * Constructor for class Maze.  Opens a text file containing the maze, then attempts to 
     * solve it.
     * 
     * @param   fname   String value containing the filename of the maze to open.
     */
    public Maze(String fname) {     
    	mazePath = fname;
        openMaze(fname);
        mazeImage = printMaze();

        timer = new Timer(TIMER_DELAY, new TimerHandler());     // setup a Timer to slow the animation down.
        timer.start();
        
        
        addWindowListener(new WindowHandler());     // listen for window event windowClosing
        
        setTitle("Cowboy Maze");
        setSize(width*SPRITE_WIDTH+10, height*SPRITE_HEIGHT+30);
        setVisible(true);

        add(mazePanel);
        setContentPane(mazePanel);
        
        solveMaze();
    }
    
    /**
     * Called from the operating system.  If no command line arguments are supplied,
     * the method displays an error message and exits.  Otherwise, a new instace of
     * Maze() is created with the supplied filename from the command line.
     * 
     * @param   args[]  Command line arguments, the first of which should be the filename to open.
     */
    public static void main(String [] args) {
        if (args.length > 0) {
            new Maze(args[0]);
        }
        else {
            System.out.println();
            System.out.println("Usage: java Maze <filename>.");
            System.out.println("Maximum Maze size:" + MAX_WIDTH + " x " + MAX_HEIGHT + ".");
            System.out.println();
            System.exit(1);
        }
    }
    
    /**
     * Finds the starting location and passes it to the recursive algorithm solve(x, y, facing).
     * The starting location should be the only '.' on the outer wall of the maze.
     */
    public void solveMaze() {
        boolean startFound = false;
        if (!startFound) {
            for (int i=0; i<width; i++) {       // look for the starting location on the top and bottom walls of the Maze.
                if (maze[0][i] == '.') {
                    preSolve(i, 0, "south");
                    startFound = true;
                }
                else if (maze[height-1][i] == '.') {
                    preSolve(i, height-1, "north");
                    startFound = true;
                }
            }
        }
        if (!startFound) {
            for (int i=0; i<height; i++) {      // look for the starting location on the left and right walls of the Maze.
                if (maze[i][0] == '.') {
                    preSolve(0, i, "east");
                    startFound = true;
                }
                else if (maze[i][width-1] == '.') {
                    preSolve(width-1, i, "west");
                    startFound = true;
                }
            }
        }
        if (!startFound) {
            System.out.println("Start not found!");
        }        
    }
    
    
    public void preSolve(int x, int y, String facing)
    {
        //Graphics2D g2 = (Graphics2D)mazePanel.getGraphics();
        //g2.drawImage(mazeImage, null, 0, 0);
        //g2.drawImage(printGuy(facing), x*SPRITE_WIDTH, y*SPRITE_HEIGHT, null, null);
        Scanner input = new Scanner(System.in);
        System.out.println("Press 1 to start");
        input.nextLine();
        startTime = System.currentTimeMillis();
        solve(x, y, facing);
    }
    
    /**
     * Recursive algorithm to solve a Maze.  Places a X at locations already visited.
     * This algorithm is very inefficient, it follows the right hand wall and will
     * never find the end if the path leads it in a circle.
     * 
     * @param   x       int value of the current X location in the Maze.
     * @param   y       int value of the current Y location in the Maze.
     * @param   facing  String value holding one of four cardinal directions 
     *                  determined by the current direction facing.
     */
    private void solve(int x, int y, String facing) {
        Graphics2D g2 = (Graphics2D)mazePanel.getGraphics(); //don't mess with the next 
 
        while (!timerFired) {   // wait for the timer.
        }
        timerFired = false;
        currentTime = System.currentTimeMillis();
        if((currentTime - startTime) > MAX_TIME)
        {
            closingMethod();
        }
        
        //Do not mess with the above part of this method
        
        
        //Below is where you put your solution to solving the maze.  
        
        if (maze[y][x] != 'F') {  //this is if it doesn't find the finish on a turn.........
            g2.drawImage(mazeImage, null, 0, 0); 
            g2.drawImage(printGuy(facing), x*SPRITE_WIDTH, y*SPRITE_HEIGHT, null, null);
            mazePanel.setSize(width*SPRITE_WIDTH+10, height*SPRITE_HEIGHT+30);
            maze[y][x] = 'X';   // mark this spot as visited. This is how you can keep track of where you've been.
            
            
            /****************************************            
            Overview:
            
            Follow right-hand rule _always_, but make sure we start at the beginning of the array
            
            Algorithm:
            
            If visitedSoFar is empty
            	can we load it from disk?
            		do it
            	leave it empty
            Add next vertex to the visitedSoFar array (along with other information)
            
            ******************************************/
            
            if( visitedSoFar.size() == 0 ) {
            	loadBestPathFromDisk();
            
            	if( visitedSoFar.size() > 0 && visitedSoFar.get(0).x == x && visitedSoFar.get(0).y == y ) {
            		System.out.println("Pre-loaded data:");
            		for( VisitedNode vn : visitedSoFar ) {
            			System.out.println( vn );
            		}
            	
            		// move us up to where we were
            		moveToLastVisited();
            		x = visitedSoFar.get( visitedSoFar.size() - 1 ).x;
            		y = visitedSoFar.get( visitedSoFar.size() - 1 ).y;
            		facing = visitedSoFar.get( visitedSoFar.size() - 1 ).direction;
            		System.out.println("Setting x = " + x + ", y = " + y + ", d = " + facing );
            		solve( x, y, facing );
            	}
            }
            visitedSoFar.add( new VisitedNode( x, y, facing ) );
            
            /**
             * Implement the right-hand rule
             */
            //System.out.println("Loc: " + x + "," + y + " -- " +facing );
            //System.out.println("Making decision... (facing "+facing+")");
            if( facing.matches("north") ) {
            	if( traversable( x+1, y ) ) {
            		solve( x+1, y, "east" ); // no north, so move right, turning 90 degrees right
            	} else if( traversable( x, y-1 ) ) {
            		solve( x, y-1, "north" ); // move up, facing the same direction
            	} else if( traversable( x-1, y ) ) {
            		solve( x-1, y, "west" ); // no east, so move left, turning 90 degrees left
            	} else {
            		solve( x, y, "south" ); // no path, so just turn 180 degrees
            	}
            } else if( facing.matches("east") ) {
            	if( traversable( x, y+1 ) ) {
            		solve( x, y+1, "south" ); // no north, so move down, turning 90 degrees right
            	} else if( traversable( x+1, y ) ) {
            		solve( x+1, y, "east" ); // move right, facing the same direction
            	} else if( traversable( x, y-1 ) ) {
            		solve( x, y-1, "north" ); // no east, so move up, turning 90 degrees left
            	} else {
            		solve( x, y, "west" ); // no path, so just turn 180 degrees
            	}
            } else if( facing.matches("south") ) {
            	if( traversable( x-1, y ) ) {
            		solve( x-1, y, "west" ); // no south, so move left, turning 90 degrees right
            	} else if( traversable( x, y+1 ) ) {
            		solve( x, y+1, "south" ); // move down, facing the same direction
            	} else if( traversable( x+1, y ) ) {
            		solve( x+1, y, "east" ); // no west, so move right, turning 90 degrees left
            	} else {
            		solve( x, y, "north" ); // no path, so just turn 180 degrees
            	}
            } else if( facing.matches("west") ) {
            	if( traversable( x, y-1 ) ) {
            		solve( x, y-1, "north" ); // no west, so move up, turning 90 degrees right
            	} else if( traversable( x-1, y ) ) {
            		solve( x-1, y, "west" ); // move left, facing the same direction
            	} else if( traversable( x, y+1 ) ) {
            		solve( x, y+1, "south" ); // no north, so move down, turning 90 degrees left
            	} else {
            		solve( x, y, "east" ); // no path, so just turn 180 degrees
            	}
            } else {
            	System.out.println("THAR BE ERRARS! facing:" + facing );
            }
            System.out.println("No decision made!?!");
        }
        else {
            System.out.println("Found the finish!");
            visitedSoFar.add( new VisitedNode( x, y, facing ) );
            closingMethod();
        }        
    }
    
    private void moveToLastVisited() {
    	for( VisitedNode vn : visitedSoFar ) {
            Graphics2D g2 = (Graphics2D)mazePanel.getGraphics(); //don't mess with the next 
            
            while (!timerFired) {   // wait for the timer.
            }
            timerFired = false;
            currentTime = System.currentTimeMillis();
            if((currentTime - startTime) > MAX_TIME)
            {
                closingMethod();
            }
            
    		g2.drawImage(mazeImage, null, 0, 0); 
    		g2.drawImage( printGuy( vn.direction ), vn.x*SPRITE_WIDTH, vn.y*SPRITE_HEIGHT, null, null);
    		mazePanel.setSize(width*SPRITE_WIDTH+10, height*SPRITE_HEIGHT+30);
    		//maze[vn.y][vn.x] = 'X';   // mark this spot as visited. This is how you can keep track of where you've been.
    	}
    }
    
    private void loadBestPathFromDisk() {
    	try {
    		Scanner sc = new Scanner( new File( "mem" + this.mazePath ) );
    		String in;
    		while ( sc.hasNextLine() ) {
    			in = sc.nextLine();
    			String[] parts = in.split("\\s");
    			visitedSoFar.add( new VisitedNode( Integer.parseInt( parts[0] ), Integer.parseInt( parts[1] ), parts[2]  ) );
    		}
    		sc.close();
    	} catch( FileNotFoundException e ) {
    		System.out.println( e.getMessage() );
    	}
    }
    
    private boolean traversable( int x, int y ) {
    	//System.out.println( x + "," + y + " is " + maze[y][x] );
    	return ( this.maze[y][x] != '%' && this.maze[y][x] != '#' ); 
    }
    
    /**
     * Opens a text file containing a maze and stores the data in the 2D char array maze[][].
     * 
     * @param   fname   String value containing the file name of the maze to open.
     */
    public void openMaze(String fname) {
        String in = "";
        int i = 0;
        try {
            Scanner sc = new Scanner(new File(fname));
            while (sc.hasNext()) {
                in = sc.nextLine();
                in = trimWhitespace(in);
                if (in.length() <= MAX_WIDTH && i < MAX_HEIGHT) {
                    for (int j=0; j<in.length(); j++) {
                        if (in.charAt(j) == '#') {      // if this spot is a wall, randomize the wall peice to display
                            if (random.nextInt(2) == 0) {
                                maze[i][j] = '#';   
                            }
                            else {
                                maze[i][j] = '%';
                            }
                        }
                        else {
                            maze[i][j] = in.charAt(j);
                        }
                    }
                }
                else {
                    System.out.println("Maximum maze size exceeded: (" + MAX_WIDTH + " x " + MAX_HEIGHT + ")!");
                    System.exit(1);
                }
                i++;
            }
            width = in.length();
            height = i;
            System.out.println("("+width+" x "+height+ ") maze opened.");
            System.out.println();
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + e);
        }
    }
    
    /**
     * Removes white space from the supplied string and returns the trimmed String.
     * 
     * @param   s   String value to strip white space from.
     * @return  String stripped of white space.
     */
    public String trimWhitespace(String s) {
        String newString = "";
        for (int i=0; i<s.length(); i++) {
            if (s.charAt(i) != ' ') {
                newString += s.charAt(i);
            }
        }
        return newString;
    }
    
    /**
     * Returns the sprite facing the direction supplied.
     * 
     * @param   facing  String value containing 1 of 4 cardinal directions to make the sprite face.
     * @return  Image of the sprite facing the proper direction.
     */
    private Image printGuy(String facing) {
        if(facing.equals("south")) {  // draw sprite facing south
            if (step) {
                step = false;
                return south1.getImage();
             }
            else {
                step = true;
                return south2.getImage();
            }
        }
        else if(facing.equals("north")) {  // draw sprite facing north
            if (step) {
                step = false;
                return north1.getImage();
             }
            else {
                step = true;
                return north2.getImage();
            }
        }
        else if(facing.equals("east")) {  // draw sprite facing east
            if (step) {
                step = false;
                return east1.getImage();
            }
            else {
                step = true;
                return east2.getImage();
            }
        }
        else if(facing.equals("west")) {  // draw sprite facing west
            if (step) {
                step = false;
                return west1.getImage();
            }
            else {
                step = true;
                return west2.getImage();
            }
        }
        return null;
    }
    
    /**
     * Prints the Maze using sprites.
     * 
     * @return BufferedImage rendition of the maze.
     */
    public BufferedImage printMaze() {
        BufferedImage mi = new BufferedImage(width*SPRITE_WIDTH, height*SPRITE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = mi.createGraphics();
        
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                if (maze[i][j] == '#') {    // draw wall
                    g2.drawImage(wall1.getImage(), j*SPRITE_WIDTH, i*SPRITE_HEIGHT, null, null);
                }
                else if (maze[i][j] == '%') {   // draw wall
                    g2.drawImage(wall2.getImage(), j*SPRITE_WIDTH, i*SPRITE_HEIGHT, null, null);
                }
                else if (maze[i][j] == '.' || maze[i][j] == 'X') {  // draw ground
                    g2.drawImage(ground.getImage(), j*SPRITE_WIDTH, i*SPRITE_HEIGHT, null, null);
                }
                else if (maze[i][j] == 'F') {   // draw finish
                    g2.drawImage(finish.getImage(), j*SPRITE_WIDTH, i*SPRITE_HEIGHT, null, null);
                }
            }
        }
         return mi;
    }

     public synchronized void closingMethod()
     {
            long endTime = currentTime - startTime;
            long finalTime = endTime / 100;
            System.out.println("Final Time = " + ((double)finalTime/(double)10));  
            
            /**
             * Clean up visitedSoFar by removing dead ends and copying optimal subpath to bestPathSoFar
             */
            for( int j = 0; j < visitedSoFar.size(); j++ ) {
	           	for( int i = visitedSoFar.size() - 1; i > j; i-- ) {
	            	if( visitedSoFar.get( i ).equals( visitedSoFar.get( j ) ) ) {
	            		j = i;
	            	}
	          	}
	           	bestPathSoFar.add( visitedSoFar.get( j ) );
	        }
            /*
             * Save bestPathSoFar to disk
             */
            try {
            	
            	PrintWriter out = new PrintWriter( new FileWriter( "mem" + mazePath ) );
            	for( VisitedNode vn : bestPathSoFar ) {
            		out.print( vn.x + " " + vn.y + " " + vn.direction + "\n" );
            	}
            	out.close();
            } catch( IOException e ) {
            	System.out.println( e.getMessage() );
            }
            System.exit(0);
      }
    /**
     * Handles the Timer, updates the boolean timerFired every time the Timer ticks.
     * Used to slow the animation down.
     */
    private class TimerHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            timerFired = true;
        }
    }
    
    /**
     * Catch the windowClosing event and exit gracefully.
     */
    private class WindowHandler extends WindowAdapter {
        public void windowClosing (WindowEvent e) {
            removeAll();
            closingMethod();
            System.exit(0);
        }        
    }    

}
