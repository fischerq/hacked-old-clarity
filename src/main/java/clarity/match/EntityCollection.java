package clarity.match;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import clarity.model.DTClass;
import clarity.model.Entity;
import clarity.model.Handle;
import clarity.model.PVS;

import com.rits.cloning.Cloner;

public class EntityCollection implements Cloneable {

    private static final Cloner CLONER = new Cloner();

    private final Entity[] entities = new Entity[1 << Handle.INDEX_BITS];
    private final Map<Integer, TreeSet<Integer> > byClass = new TreeMap<Integer, TreeSet<Integer> >();
    
    public void add(int index, int serial, DTClass dtClass, PVS pvs, Object[] state) {
        entities[index] = new Entity(index, serial, dtClass, pvs, state);
        if(byClass.get(dtClass.getClassId()) == null)
        	byClass.put(dtClass.getClassId(), new TreeSet<Integer>());
        byClass.get(dtClass.getClassId()).add(index);
    }

    public Entity getByIndex(int index) {
        return entities[index];
    }
    
    public Entity getByHandle(int handle) {
        Entity e = entities[Handle.indexForHandle(handle)];
        return e == null || e.getSerial() != Handle.serialForHandle(handle) ? null : e;
    }
    
    public void remove(int index) {
    	if(entities[index] != null)
    		if(byClass.get(entities[index].getDtClass().getClassId()) != null)
    			byClass.get(entities[index].getDtClass().getClassId()).remove(index);
        entities[index] = null;
    }
    
    public Set<Integer> getByClass(DTClass dtClass){
    	return byClass.get(dtClass.getClassId());
    }
    
    @Override
    public EntityCollection clone() {
       return CLONER.deepClone(this);
    }

}
