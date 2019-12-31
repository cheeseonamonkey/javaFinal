
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


//to do:

//check timed sort ?


/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author 4800573403
 */
public final class Olympics extends JFrame
{
    
    ArrayList<Event> events;
    
    JPanel pnl1, pnl2, pnlView, pnlAdd;
    JComboBox cmbEvent;
    JButton btnNewEvent, btnSave, btnLoad, btnDelete;
    
    JLabel lblEventName, lblNationLabel, lblScoreLabel, lblGoldLabel, lblGold, lblGoldScore, lblSilverLabel, lblSilver, lblSilverScore, lblBronzeLabel, lblBronze, lblBronzeScore, lblEventType;
    CardLayout cardLayout;
    
    //pnlAdd components:
    JButton btnBack, btnGo;
    JLabel lblContestants, lblScores, lblName;
    JRadioButton rdbTimed, rdbScored;
    ButtonGroup eventGroup = new ButtonGroup();
    
    JTextField[] txtContestants, txtScores;
    JTextField txtName;
    
    
    
    
    GridBagConstraints c;
    
    public Olympics()
    {
        events = new ArrayList<>();
        
        this.setTitle("Olympic Events");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,300);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.LINE_AXIS));
        
        c = new GridBagConstraints();
        
        
        //existing events:
        events.add(new ScoredEvent("Figure Skating"));
        events.get(0).addContestant("United Kingdom", 6);
        events.get(0).addContestant("United States", 9.5);
        events.get(0).addContestant("Russia", 8.3);
        events.get(0).addContestant("China", 7.5);
        events.get(0).addContestant("Brazil", 5.5);
        events.get(0).sortContestants();
        
        events.add(new ScoredEvent("Competitive Eating"));
        events.get(1).addContestant("Netherlands", 63);
        events.get(1).addContestant("North Korea", 95);
        events.get(1).addContestant("South Africa", 88);
        events.get(1).addContestant("Germany", 83);
        events.get(1).addContestant("France", 49);
        events.get(1).sortContestants();
        
        events.add(new TimedEvent("100 Meter Dash"));
        events.get(2).addContestant("United States", 10.8);
        events.get(2).addContestant("Malaysia", 15.1);
        events.get(2).addContestant("Kenya", 11);
        events.get(2).addContestant("Japan", 12);
        events.get(2).addContestant("Czech Republic", 12.5);
        events.get(2).sortContestants();
        
        events.add(new TimedEvent("50 Meter Swim"));
        events.get(3).addContestant("Saudi Arabia", 41.2);
        events.get(3).addContestant("Zimbabwe", 20.4);
        events.get(3).addContestant("Mexico", 37);
        events.get(3).addContestant("Spain", 25.5);
        events.get(3).addContestant("Luxemburg", 30.5);
        events.get(3).sortContestants();
        //end hard coded
        
        
        
        
        
        
        //contents
        pnl1 = new JPanel();
        pnl1.setLayout(new GridLayout(5,1,15,15));
        
        //combo box
        cmbEvent = new JComboBox();
        
        fillComboBox();
        
        cmbEvent.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int chosenEventIndex = ((JComboBox)e.getSource()).getSelectedIndex();
                if(chosenEventIndex >= 0)
                {
                    Event chosenEvent = events.get(chosenEventIndex);
                    outputMedals(chosenEvent);
                }
            }
        });
        
        btnNewEvent = new JButton("New event");
        btnNewEvent.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(pnl2, "add");
            }
        }
        );
        
        btnDelete = new JButton("Delete event");
        btnDelete.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int chosenEventIndex = cmbEvent.getSelectedIndex();
                if(chosenEventIndex >= 0)
                {
                    if(JOptionPane.showConfirmDialog(rootPane, String.format(
                            "Really delete the event: %s?", events.get(chosenEventIndex).getName()))
                            ==
                            JOptionPane.YES_OPTION)
                    {
                        //pressed yes
                        events.remove(chosenEventIndex);
                        cmbEvent.removeItemAt(chosenEventIndex);
                        
                    }else
                    {
                        //pressed no
                        
                    }
                }
            }
            
        }
        );
        
        
        btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    File saveFile = new File("data");
                    if(!   (saveFile.exists()))
                    {
                        saveFile.createNewFile();
                    }
                    
                    FileOutputStream fos = new FileOutputStream(saveFile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    
                    oos.reset();
                    oos.writeObject(events);
                    
                    fos.flush();
                    oos.flush();
                    
                    fos.close();
                    oos.close();
                    
                    
                }catch(Exception ee)
                {
                    JOptionPane.showMessageDialog(rootPane, "Error saving: \n" + ee.toString());
                }
                
                
            }
            
        });
        btnLoad = new JButton("Load");
        btnLoad.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    File saveFile = new File("data");
                    if(saveFile.exists())
                    {
                        FileInputStream fis = new FileInputStream(saveFile);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        
                        events.clear();
                        cmbEvent.removeAllItems();
                        
                        events = (ArrayList<Event>) ois.readObject();
                        
                        fis.close();
                        ois.close();
                        
                        fillComboBox();
                        
                        
                        
                    }
                    
                    
                }catch(Exception ee)
                {
                    JOptionPane.showMessageDialog(rootPane, "Error loading \n" + ee.toString());
                }
            }
            
        });
        
        pnl2 = new JPanel();
        cardLayout = new CardLayout();
        pnl2.setLayout(cardLayout);
        
        //pnlView:
        
        
        
        pnlView = new JPanel();
        pnlView.setLayout(new GridBagLayout());
        c.insets = new Insets(5,5,5,5);
        
        lblEventName = new JLabel();
        lblNationLabel = new JLabel("Nation");
        lblScoreLabel = new JLabel("Score");
        
        lblEventType = new JLabel("evev");
        
        lblGoldLabel = new JLabel(new ImageIcon("gold.png"));
        lblGold = new JLabel();
        lblGoldScore = new JLabel();
        
        lblSilverLabel = new JLabel(new ImageIcon("silver.png"));
        lblSilver = new JLabel();
        lblSilverScore = new JLabel();
        
        lblBronzeLabel = new JLabel(new ImageIcon("bronze.png"));
        lblBronze = new JLabel();
        lblBronzeScore = new JLabel();
        
        //pnlAdd:
        pnlAdd = new JPanel();
        pnlAdd.setLayout(new GridLayout(10,2,30,5));
        
        rdbTimed = new JRadioButton("Timed event");
        rdbScored = new JRadioButton("Scored event");
        
        btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cardLayout.show(pnl2, "view");
            }
            
        }
        );
        
        
        btnGo = new JButton("Accept");
        btnGo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ArrayList<Contestant> contestants = new ArrayList<>();
                
                String eventName;
                try
                {
                    eventName = txtName.getText().toString();
                    
                    for(int i=0; i<txtContestants.length; i++)
                    {
                        contestants.add(new Contestant(
                                txtContestants[i].getText().toString(),
                                Double.parseDouble(txtScores[i].getText())));
                        
                        txtName.setText("");
                        txtContestants[i].setText("");
                        txtScores[i].setText("");
                    }
                    
                    
                    if(rdbTimed.isSelected())
                    {
                        events.add(new TimedEvent(eventName, contestants));
                        cmbEvent.addItem(eventName);
                        
                    }else if(rdbScored.isSelected())
                    {
                        events.add(new ScoredEvent(eventName, contestants));
                        cmbEvent.addItem(eventName);
                        
                    }else
                    {
                        JOptionPane.showMessageDialog(rootPane, "Please check either 'timed' or 'scored' event.");
                        
                    }
                    
                }catch(Exception a)
                {
                    JOptionPane.showMessageDialog(rootPane, "Error entering contestants");
                }
                
                cardLayout.show(pnl2, "view");
            }
            
        }
        );
        
        lblContestants = new JLabel("Contestants:");
        lblScores = new JLabel("Scores:");
        
        lblName = new JLabel("Event name:");
        txtName = new JTextField(15);
        
        
        
        txtContestants = new JTextField[5];
        txtScores = new JTextField[5];
        for(int i=0; i<txtContestants.length; i++)
        {
            txtContestants[i] = new JTextField(3);
            txtScores[i] = new JTextField(5);
        }
        
        
        
        
        
        
        
        
        
        //end contents
        
        
        createContents();
        cmbEvent.setSelectedIndex(0);
        
        this.setVisible(true);
        
        
    }
    
    public void fillComboBox()
    {
        
        for(Event e : events)
        {
            cmbEvent.addItem(new String(e.eventName));
        }
    }
    
    
    
    public void createContents()
    {
        
        //panel 1
        pnl1.add(cmbEvent);
        pnl1.add(btnNewEvent);
        pnl1.add(btnDelete);
        pnl1.add(btnSave);
        pnl1.add(btnLoad);
        
        this.add(pnl1);
        
        
        //panelView:
        this.add(new JSeparator(JSeparator.VERTICAL));
        
        //c = layout constraints
        c.gridx = 0;
        c.gridy = 0;
        
        pnlView.add(lblEventName, c);
        
        
        c.gridy = 1;
        c.gridx = 0;
        pnlView.add(lblEventType, c);
        c.gridx = 1;
        pnlView.add(lblNationLabel, c);
        c.gridx = 2;
        pnlView.add(lblScoreLabel, c);
        
        c.gridy = 2;
        c.gridx = 0;
        pnlView.add(lblGoldLabel, c);
        c.gridx = 1;
        pnlView.add(lblGold, c);
        c.gridx = 2;
        pnlView.add(lblGoldScore, c);
        
        c.gridy = 3;
        c.gridx = 0;
        pnlView.add(lblSilverLabel, c);
        c.gridx = 1;
        pnlView.add(lblSilver, c);
        c.gridx = 2;
        pnlView.add(lblSilverScore, c);
        
        c.gridy = 4;
        c.gridx = 0;
        pnlView.add(lblBronzeLabel, c);
        c.gridx = 1;
        pnlView.add(lblBronze, c);
        c.gridx = 2;
        pnlView.add(lblBronzeScore, c);
        
        
        
        
        pnl2.add("view", pnlView);
        pnl2.add("add", pnlAdd);
        
        //pnlAdd:
        pnlAdd.add(btnBack);
        pnlAdd.add(new JLabel(""));
        
        pnlAdd.add(lblName);
        pnlAdd.add(txtName);
        
        pnlAdd.add(rdbTimed);
        pnlAdd.add(rdbScored);
        eventGroup.add(rdbTimed);
        eventGroup.add(rdbScored);
        
        
        
        pnlAdd.add(lblContestants);
        pnlAdd.add(lblScores);
        
        for(int i=0; i<txtContestants.length; i++)
        {
            pnlAdd.add(txtContestants[i]);
            pnlAdd.add(txtScores[i]);
        }
        
        pnlAdd.add(new JLabel(""));
        pnlAdd.add(btnGo);
        
        
        
        //pnl2 (holds cardLayout)
        this.add(pnl2);
        
        
        
        
    }
    
    public void outputMedals(Event event)
    {
        String eventName = event.getName();
        ArrayList<Contestant> contestants = event.getContestants();
        
        lblEventName.setText(eventName + ":");
        
        if(event instanceof ScoredEvent)
            lblEventType.setText("Scored event");
        else if(event instanceof TimedEvent)
            lblEventType.setText("Timed event");
        
        //contestants are sorted in constructor
        lblGold.setText(contestants.get(0).getName());
        lblSilver.setText(contestants.get(1).getName());
        lblBronze.setText(contestants.get(2).getName());
        
        lblGoldScore.setText(String.valueOf(contestants.get(0).getScore()));
        lblSilverScore.setText(String.valueOf(contestants.get(1).getScore()));
        lblBronzeScore.setText(String.valueOf(contestants.get(2).getScore()));
    }
    
    
    
    public static void main(String[] args) {
        
        new Olympics();
    }
    
}
