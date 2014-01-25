package clarity.model;

import com.rits.cloning.Cloner;



public class Entity implements Cloneable {
    
    private static final Cloner CLONER = new Cloner();
    
    private final int index;
    private final int serial;
    private final DTClass dtClass;
    private PVS pvs;
    private final Object[] state;

    public Entity(int index, int serial, DTClass dtClass, PVS pvs, Object[] state) {
        this.index = index;
        this.serial = serial;
        this.dtClass = dtClass;
        this.pvs = pvs;
        this.state = state;
    }
    
    public int getIndex() {
        return index;
    }

    public int getSerial() {
        return serial;
    }

    public DTClass getDtClass() {
        return dtClass;
    }
    
    public PVS getPvs() {
        return pvs;
    }

    public void setPvs(PVS pvs) {
        this.pvs = pvs;
    }

    public Object[] getState() {
        return state;
    }
    
    public Object getProperty(String property){
    	return state[dtClass.getPropertyIndex(property)];
    }
    
    public Object getProperty(String subclass, String property){
    	return state[dtClass.getPropertyIndex(subclass+"."+property)];
    }
    
    public Object[] getArrayProperty(String property, int size){
    	Object[] array = new Object[size];
    	for(int i = 0; i < size; ++i)
    		array[i] = state[dtClass.getPropertyIndex(property+String.format(".%04d", i))];
    	return array;
    }
    
    public Object[] getArrayProperty(String subclass, String property, int size){
    	Object[] array = new Object[size];
    	String full_property = subclass+"."+property;
    	for(int i = 0; i < size; ++i)
    		array[i] = state[dtClass.getPropertyIndex(full_property+String.format(".%04d", i))];
    	return array;
    }
    
    @Override
    public Entity clone() {
       return CLONER.deepClone(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\n-- Entity [index=");
        builder.append(index);
        builder.append(", serial=");
        builder.append(serial);
        builder.append(", dtClass=");
        builder.append(dtClass.getDtName());
        builder.append("]");
        for (int i = 0; i < state.length; i++) {
            builder.append("\n");
            builder.append(dtClass.getReceiveProps().get(i).getVarName());
            builder.append(" = ");
            builder.append(state[i]);
        }
        
        return builder.toString();
    }
    

}
