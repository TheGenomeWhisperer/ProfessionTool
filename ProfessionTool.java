import java.awt.AWTException;
import java.util.Random;

public class ProfessionTool { 
	
	// instance variables
	private int numStacks;
	private int sleepTimer1; // Rare Ink
	private int sleepTimer2; // Common Ink
	private int sleepTimer3; // Mailbox Sleep Timer
	
	// All of the macros to be added to the robot class ".type()" argument, for example - ControlSend.type(milling_initialize);
	private CharSequence millingInitialize = "\n/run function FnH() for i=0,4 do for j=1,GetContainerNumSlots(i) do "
			+ "local t={GetItemInfo(GetContainerItemLink(i,j) or 0)} if t[7]==\"Herb\" and select(2,GetContainerItemInfo(i,j))>=5 then "
			+ "return i..\" \"..j,t[1] end end end end \n";
	
	private CharSequence millingMainA = "\n/run local f,l,n=AuM or CreateFrame(\"Button\",\"AuM\",nil,\"SecureActionButtonTemplate\") "
			+ "f:SetAttribute(\"type\",\"macro\") l,n=FnH() if l then f:SetAttribute(\"macrotext\",\"/cast Milling\\n/use \"..l) "
			+ "SetMacroItem(\"Milling2\",n) end \n";

	private CharSequence millingMainB = "\n/click AuM \n";
	private CharSequence millingMainC = "\n/use Desecrated Herb \n";

	private CharSequence millingInscription = "\n/cast Inscription\n";
	private CharSequence millingCraftA = "\n/run for i=1,GetNumTradeSkills() do if GetTradeSkillInfo(i)==\"Ink of Dreams\" "
			+ "then DoTradeSkill(i, GetItemCount(\"Shadow Pigment\")/2) break end end \n";
	
	private CharSequence millingCraftB = "\n/run for i=1,GetNumTradeSkills() do if GetTradeSkillInfo(i)==\"Starlight Ink\" "
			+ "then DoTradeSkill(i, GetItemCount(\"Misty Pigment\")/2) break end end \n";
	
	private CharSequence mailbox = "\n/click PostalOpenAllButton \n";
	
	// Milling Class Constructor
	public Milling(int herbStack) throws AWTException {
    	ControlSend mill = new ControlSend();
    	mill.type(millingInitialize);
    	
		numStacks = herbStack;
		sleepTimer1 = numStacks * 1500;
		sleepTimer2 = numStacks * 10200;
		// SleepTimer delay for mailbox calculated
		mailBoxSleepTimer();
	}
	
    public void millHerb() throws AWTException {
    	ControlSend mill = new ControlSend();
    	mill.type(millingMainA);
    	mill.type(millingMainB);
    	mill.type(millingMainC);
    }
    
    public void craftInk(String type) throws AWTException {
    	ControlSend mill = new ControlSend();
    	if (type.equals("starlight")) {
    		mill.type(millingInscription);
    		mill.type(millingCraftA);
    		sleep("starlight");
    		mill.type(""); // This is the Escape Key to stop any action
    		sleep("millPause");
    	}
    	if (type.equals("dreams")) {
    		mill.type(millingInscription);
    		mill.type(millingCraftB);
    		sleep("dreams");
    	}
    }
    
    public void mail() throws AWTException {
    	ControlSend mill = new ControlSend();
    	mill.type(mailbox);
    	sleep ("mailbox");
    	mill.type(""); // hits the ESC key
    }
    
    public void loop(int numStacks) throws AWTException {
    	for (int i = 0; i < numStacks * 4 + 4; i++) {
    		millHerb();
    		sleep("millPause");
    	}
    }
    
    public void decay() {
    	numStacks = numStacks * 7 / 10;
    	sleepTimer1 = numStacks * 1500;
    	sleepTimer2 = numStacks * 10200;
    	mailBoxSleepTimer(); // Double Increment down as it is based on 70% of next amount of herbs
    }
    public void sleep(String name) {
    	Random rand = new Random();
    	int timeSleep = 0;
    	
    	if (name.equals("millPause")) {
    		timeSleep = rand.nextInt((3050 - 2650) + 1) + 2650;
    	}
    	if (name.equals("starlight")) {
    		timeSleep = sleepTimer1;
    	}
    	if (name.equals("dreams")) {
    		timeSleep = sleepTimer2;
    	}
    	if (name.equals("mailbox")) {
    		timeSleep = sleepTimer3;
    	}
    	try {
    		Thread.sleep(timeSleep);
    	} catch (InterruptedException ex) {
    		Thread.currentThread().interrupt();
    	}
    }
    
    public void mailBoxSleepTimer() {
		if (!(numStacks * 7 / 10  > 50)) {
			if (numStacks * 7 / 10 > 10) {
				sleepTimer3 = (numStacks / 10) * 6000;
			} else {
				sleepTimer3 = 6000;
			}
		} else if(!(numStacks * 7 / 10  > 100)) {
			sleepTimer3 = ((numStacks - 50) / 10) * 6000 + 62000; // The extra minute + change added for time to refresh mailbox
		} else if(!(numStacks * 7 / 10  > 150)) {
			sleepTimer3 = ((numStacks - 100) / 10) * 6000 + 124000; // 2 minutes
		} 
    }
    
    public void millUltimate() throws AWTException {
    	while (numStacks >= 5) { // Stop loop when bags are getting full
    		loop(numStacks); // Mill the herbs
    		
    		sleep("millPause"); // Brief Pause
    		
    		craftInk("starlight");
    		craftInk("dreams");
    		mail();
    		
    		decay(); // Adjust instance variables for next loop.
    	}
    }
}
