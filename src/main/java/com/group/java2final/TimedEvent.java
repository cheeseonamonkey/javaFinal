




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
public class TimedEvent extends Event
{
    
    
    
    public TimedEvent(String name)
    {
        super(name);
    }
    
    public TimedEvent(String name, ArrayList<Contestant> list)
    {
        super(name, list);
    }
    
    //called in super constructor
    @Override
    public void bubbleSortContestants(ArrayList<Contestant> contestants, int numContestants)
    {
        //sorts low to high
        //System.out.print("Sorting   ");
        if(numContestants == 1)
        {
            return;
        }
        
        for(int i=0; i<numContestants - 1; i++)
        {
            
            if(contestants.get(i).getScore()   >   contestants.get(i+1).getScore())
            {
                Contestant temp = new Contestant(contestants.get(i).getName(),
                        contestants.get(i).getScore());
                
                contestants.set(i, contestants.get(i+1));

                contestants.set(i+1, temp);

                
            }
        }
        
        bubbleSortContestants(contestants, numContestants-1);
    }
    
    
    
    
    
}
