import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;


class Window extends JFrame{
	private static final long serialVersionUID = -2542001418764869760L;
	public static ArrayList<ArrayList<SquarePanel>> Grid;
	public static int width = 20;
	public static int height = 20;
	public Window(){
		
		
		Grid = new ArrayList<>();
		ArrayList<SquarePanel> mapData;
		
		for(int i=0;i<width;i++){
			mapData= new ArrayList<>();
			for(int j=0;j<height;j++){
				SquarePanel panel = new SquarePanel(Color.white);
				mapData.add(panel);
			}
			Grid.add(mapData);
		}
		
		// Setting up the layout of the panel
		getContentPane().setLayout(new GridLayout(height,width,0,0));
		
		// Start & pauses all threads, then adds every square of each thread to the panel
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				getContentPane().add(Grid.get(i).get(j));
			}
		}
		
		// initial position of the snake
		Tuple position = new Tuple(10,10);
		// passing this value to the controller
		ThreadsController threadsController = new ThreadsController(position);
		//Let's start the game now..
		threadsController.start();

		// Links the window to the keyboardlistenner.
		this.addKeyListener((KeyListener) new KeyboardListener());

		//To do : handle multiplayers .. The above works, test it and see what happens
		
		//Tuple position2 = new Tuple(13,13);
		//ControlleurThreads c2 = new ControlleurThreads(position2);
		//c2.start();
		
	}
}
