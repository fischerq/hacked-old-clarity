package Dataset;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Writer {
	
	PrintWriter games;
	PrintWriter players;
	PrintWriter position_data;
	//PrintWriter position_data_raw;
	PrintWriter death_data;
	
	int nextGameID;
	int nextPlayerID;
	
	int currentGameID;
	int[] playerIDs;
	String[] names;
	
	
	int time_step = 2; //updating every 2 ticks
	
	int current_time = 0;
	int saving_interval = 30;
	int saving_timer;
	
	boolean last_alive[];
	float[][] last_positions;
	
	public Writer(){
		try {
			games = new PrintWriter("games.csv", "UTF-8");
			players = new PrintWriter("players.csv", "UTF-8");
			position_data = new PrintWriter("position_data.csv", "UTF-8");
			death_data = new PrintWriter("death_data.csv", "UTF-8");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		games.println("GameID, SteamGameID");
		nextGameID = 0;
		players.println("PlayerID, GameID, Name, Hero, Team");
		playerIDs = new int[10];
		nextPlayerID = 0;
		position_data.println("PlayerID, GameTime, X, Y");
		death_data.println("PlayerID, GameTime, X, Y");
	}
	
	public void setGame(String gameID){
		currentGameID = nextGameID;
		games.println(nextGameID+", "+gameID);
		nextGameID++;
		current_time = 0;
		
		saving_timer = saving_interval;
		last_alive = new boolean[10];
		for(int i = 0; i < 10; ++i)
			last_alive[i] = false;
	}
	
	public void setPlayers(String[] names){
		this.names = names;
		for(int i = 0; i < 10; ++i){
			playerIDs[i] = nextPlayerID;
			nextPlayerID++;
		}
	}
	
	public void setHeroes(Integer[] ids){
		for(int i = 0; i < 10; ++i){
			players.println(playerIDs[i]+", "+currentGameID+", \""+names[i]+"\", "+ids[i]);
		}
	}
	
	public void tickPositions(float[][] positions){
		boolean save = false;
		if(saving_timer >= saving_interval){
			saving_timer -= saving_interval;
			save = true;
		}
		saving_timer += time_step;
		
		for(int i = 0; i < 10; ++i)
		{
			if(positions[i]==null)
				continue;
			//Saving position data
			if(save)
				position_data.println(playerIDs[i]+", "+current_time+", "+positions[i][0]+", "+positions[i][1]);
		}
		last_positions = positions;
		current_time += time_step;		
	}
	
	public void tickAlive(boolean[] alive){
		for(int i = 0; i < 10; ++i){
			if(last_alive[i] && ! alive[i])
				death_data.println(playerIDs[i]+", "+current_time+", "+last_positions[i][0]+", "+last_positions[i][1]);
		}
		last_alive = alive;
	}
	
	public void finishGame(){
	}
	
	public void finish(){
		games.close();
		players.close();
		position_data.close();
		death_data.close();
	}
}
