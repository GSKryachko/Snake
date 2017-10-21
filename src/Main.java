import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		//Creating the window with all its awesome snaky features
		Window window= new Window();
		
		//Setting up the window settings
		window.setTitle("Snake");
		window.setSize(400,400);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
}
