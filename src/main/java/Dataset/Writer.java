package Dataset;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Writer {
	
	PrintWriter games;
	PrintWriter players;
	PrintWriter position_data;
	PrintWriter position_data_raw;
	
	int nextGameID;
	int nextPlayerID;
	
	int currentGameID;
	int[] playerIDs;
	String[] names;
	
	
	int accumulator_size = 15;
	int time_step = 2; //updating every 2 ticks
	
	int current_time = 0;
	Deque<float[][]> accPast;
	Queue<float[][]> accFuture;
		
	public Writer(){
		try {
			games = new PrintWriter("games.csv", "UTF-8");
			players = new PrintWriter("players.csv", "UTF-8");
			position_data = new PrintWriter("position_data.csv", "UTF-8");
			position_data_raw = new PrintWriter("position_data_raw.csv", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		games.println("GameID, SteamGameID");
		nextGameID = 0;
		players.println("PlayerID, GameID, Name, Hero");
		playerIDs = new int[10];
		nextPlayerID = 0;
		position_data.println("PlayerID, GameTime, X, Y, DX, DY, AccumulatedPastX, AccumulatedPastY, AccumulatedFutureX, AccumulatedFutureY");
		position_data_raw.println("PlayerID, GameTime, X, Y");
	}
	
	public void setGame(int gameID){
		currentGameID = nextGameID;
		games.println(nextGameID+", "+gameID);
		nextGameID++;
		accPast = new LinkedList<float[][]>();
		accFuture = new LinkedList<float[][]>();
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
		accFuture.add(positions);
		if(accFuture.size() > accumulator_size)
		{
			float[][] current = accFuture.poll();
			
			//compute
			float[][] accPastVal = new float[10][2];
			float[][] accFutureVal = new float[10][2];
			
			for(float[][] s: accPast){
				for(int i = 0; i < 10; ++i){
					if(s[i]==null)
						continue;
					accPastVal[i][0] += s[i][0]/accPast.size();
					accPastVal[i][1] += s[i][1]/accPast.size();
				}
			}
			for(float[][] s: accFuture){
				for(int i = 0; i < 10; ++i){
					if(s[i]==null)
						continue;
					accFutureVal[i][0] += s[i][0]/accFuture.size();
					accFutureVal[i][1] += s[i][1]/accFuture.size();
				}
			}
			
			float[][] last = accPast.peekLast();
			float[][] next = accFuture.peek();
			float[][] dPos = new float[10][2];
			for(int i = 0; i < 10; ++i){
				if(last==null || last[i] == null || next[i] ==null)
					continue;
				dPos[i][0] = next[i][0]-last[i][0];
				dPos[i][1] = next[i][1]-last[i][1];
			}
			for(int i = 0; i < 10; ++i)
			{
				if(current[i]==null)
					continue;
				position_data.println(playerIDs[i]+", "+current_time+", "+current[i][0]+", "+current[i][1]+
						", "+dPos[i][0]+", "+dPos[i][1]+", "+accPastVal[i][0]+", "+accPastVal[i][1]+", "+accFutureVal[i][0]+", "+accFutureVal[i][1]);
				position_data_raw.println(playerIDs[i]+", "+current_time+", "+current[i][0]+", "+current[i][1]);
			}
			
			current_time += time_step;
			accPast.add(current);
			if(accPast.size()>accumulator_size)
				accPast.poll();
		}
		
	}
	
	public void finishGame(){
		for(int i = 0; i<accumulator_size; ++i){
			tickPositions(new float[10][]);
		}
		current_time = 0;
	}
	
	public void finish(){
		games.close();
		players.close();
		position_data.close();
		position_data_raw.close();
	}
}
