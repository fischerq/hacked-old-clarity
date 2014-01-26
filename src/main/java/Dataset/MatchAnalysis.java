package Dataset;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.vecmath.Vector2f;

import clarity.match.Match;
import clarity.model.DTClass;
import clarity.model.Entity;
import clarity.parser.Peek;
import clarity.parser.ReplayFile;
import clarity.parser.ReplayIndex;

public class MatchAnalysis {
	
	String file;
	Match match;
	int ID; 
	
	Writer w;
	boolean initialisedHeroes = false;
	
	public MatchAnalysis(int ID, String file, Writer w){
		this.file = file;
		this.w = w;
		this.ID = ID;
	}

	
	public void process(){
		try {
			ReplayIndex idx = ReplayFile.indexForFile(file);
			
			match = new Match(idx.prologueIterator());
			Iterator<Peek> it = idx.matchIterator();
			it.next().apply(match);
			int current_tick = match.getTick();
			while(it.hasNext()){
				Peek p = it.next();
				if(p.getTick() == current_tick)
				{
					p.apply(match);
					continue;
				}
				else					
				{
					if(current_tick == 0)
						initGame();
					analyseTick();
				}
				p.apply(match);
				current_tick = p.getTick();
			}
			finishGame();
			
		} catch (IOException e) {
			System.out.println("bad file"+file);
		}
	}
	
	public void initGame(){
		DTClass dtGRP = match.getDtClasses().forDtName("DT_DOTAGamerulesProxy");
		Entity e = match.getEntities().getByIndex(match.getEntities().getByClass(dtGRP).iterator().next());
		//int gameID = (int)e.getProperty("DT_DOTAGamerules","m_unMatchID");
		w.setGame(ID);
		//System.out.println(e);
		DTClass dtPlayerResource = match.getDtClasses().forDtName("DT_DOTA_PlayerResource");
		for(Integer i: match.getEntities().getByClass(dtPlayerResource))
		{
			e = match.getEntities().getByIndex(i);
			Object[] namesRaw =  e.getArrayProperty("m_iszPlayerNames", 10);
			String[] names= new String[10];
			for(int j = 0; j < namesRaw.length; j++)
			{
				names[j] = (String)namesRaw[j];
			}
			w.setPlayers(names);
			//System.out.println(Arrays.toString(names));
		}
	}
	
	public void finishGame(){
		w.finishGame();
	}
	
	public void analyseTick(){
		//System.out.println(match.getTick());
		DTClass dtGRP = match.getDtClasses().forDtName("DT_DOTAGamerulesProxy");
		Entity e = match.getEntities().getByIndex(match.getEntities().getByClass(dtGRP).iterator().next());
		
		int state = (int) e.getProperty("DT_DOTAGamerules","m_nGameState");
		//System.out.println("state: " + state);	
		
		if((int)e.getProperty("DT_DOTAGamerules","m_bGamePaused")!=0)
			return;
		
		if(state == 4 || state == 5)
		{
			DTClass dtPlayerResource = match.getDtClasses().forDtName("DT_DOTA_PlayerResource");
			e = match.getEntities().getByIndex(match.getEntities().getByClass(dtPlayerResource).iterator().next());
			Object[] heroesRaw = e.getArrayProperty("m_nSelectedHeroID", 10);
			Integer[] heroIDs = new Integer[10];
			boolean allPicked = true;
			for(int i = 0; i < heroesRaw.length; i++)
			{
				heroIDs[i] = (Integer)heroesRaw[i];
				if(heroIDs[i]==-1)
					allPicked = false;
			}
			
			if(!initialisedHeroes && allPicked){
				w.setHeroes(heroIDs);
				initialisedHeroes = true;
			}
			
			Object[] heroHandles = e.getArrayProperty("m_hSelectedHero", 10);
			Entity[] heroes = new Entity[10];
			float[][] positions = new float[10][];
			for(int i = 0; i < heroHandles.length; i++)
			{
				if(heroIDs[i]==-1)
					continue;
				heroes[i] =  match.getEntities().getByHandle((int)heroHandles[i]);
				if((int)heroes[i].getProperty("m_lifeState") == 0)
					positions[i] = getPosition(heroes[i]);
				//System.out.println(i+ " " + Arrays.toString(getPosition(heroes[i]))+" "+heroes[i].getProperty("m_lifeState"));
			}
			w.tickPositions(positions);
			//System.out.println(Arrays.toString(heroIDs));
			//System.out.println(e);
		}
	}
	
	int MAX_COORD_INTEGER = 16384;
	
	public float[] getPosition(Entity e){
		int cell_x = (int) e.getProperty("m_cellX");
		int cell_y = (int) e.getProperty("m_cellY");
	    Vector2f offset = (Vector2f) e.getProperty("m_vecOrigin");
	    int cellbits = (int) e.getProperty("m_cellbits");
	    		
	    int cellwidth = 1 << cellbits;
	    float[] pos = new float[2];
	    pos[0] = ((cell_x * cellwidth) - MAX_COORD_INTEGER) + (int) offset.x;
	    pos[1] = ((cell_y * cellwidth) - MAX_COORD_INTEGER) + (int) offset.y;
	    return pos;
	}
	
}
