package com.risk.entity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.risk.controller.GamePlayController;

/**
 * This class is used to store Game State.
 * @author Vipul
 * @version 1.0.0
 *
 */
@JsonAutoDetect
public class GameState{
		
	public final void writeObject(GamePlayController gamePlayController) throws IOException{
	      try {
	          FileOutputStream fileOut =
	          new FileOutputStream("player.ser");
	          ObjectOutputStream out = new ObjectOutputStream(fileOut);
	          out.writeObject(gamePlayController);
	          out.close();
	          fileOut.close();
	          System.out.printf("Serialized data is saved in player.ser");
	       } catch (IOException i) {
	          i.printStackTrace();
	       }
	}
	
	
	public final GamePlayController readObject() {
		GamePlayController e = null;
	      try {
	         FileInputStream fileIn = new FileInputStream("player.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         e = (GamePlayController) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch (Exception i) {
	         i.printStackTrace();
	      }
	      return e;
	}


}
