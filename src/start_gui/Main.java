package start_gui;

public class Main {
	static StartWindow neu = new StartWindow();
	
	public static void main(String[] args) {
		try {
			neu.show();
		} catch (Exception e) { e.printStackTrace(); }
	}

}
