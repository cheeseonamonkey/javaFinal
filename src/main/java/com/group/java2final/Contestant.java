
import java.io.Serializable;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 4800573403
 */
public class Contestant implements Serializable
{
    String name;
    double score;
    
    
    //constructor
    public Contestant(String name, double score)
    {
        this.name = name;
        this.score = score;
    }
    
    
    public String getName()
    {
        return name;
    }
    public double getScore()
    {
        return score;
    }
    
}
