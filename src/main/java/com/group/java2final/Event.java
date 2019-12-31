




import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 4800573403
 */
public abstract class Event implements Serializable
{
    ArrayList<Contestant> contestants = new ArrayList<>();
    
    String eventName;
    
    
    //=============================
    //constructors
    
    //this one for the hard coded events
    public Event(String name)
    {
        this.eventName = name;
        
        //call in constructor?

    }
    
    public Event(String name, ArrayList<Contestant> contestants)
    {
        this.eventName = name;
        
        this.contestants = contestants;
        
        this.sortContestants();
        
        //call in constructor?

    }
    //end constructors
    //========================
    
    
    
    public ArrayList<Contestant> getContestants()
    {
        return contestants;
    }
    
    public String getName()
    {
        return eventName;
    }
    
    public void addContestant(String name, double score)
    {
        contestants.add(new Contestant(name, score));
    }
    
    public void sortContestants()
    {
        this.bubbleSortContestants(contestants, contestants.size());
    }
    
    public abstract void bubbleSortContestants(ArrayList<Contestant> contestants, int numContestants);
    
    
    
    
    
    
    
    
}
