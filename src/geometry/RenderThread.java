package geometry;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class RenderThread extends Thread{
	Graphics graph; 
	Arena a;
	  
	public RenderThread(Arena aa) {
	      
	       a = aa;
	      run();
	    }
	   
	   public void run(){
		 graph = a.graphics;
		 		
		 a.e.render(a.graphics);
		
	   }
	   
}
