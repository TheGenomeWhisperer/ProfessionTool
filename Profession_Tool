;
; Profession Tool
; @Author: Sklug aka TheGenomeWhisperer 

	
; Beginning of WOW Window Detection for Key Sending
	Sleep 1000
	IfWinNotExist, World of Warcraft  ; Disables program if Warcraft Not Detected
	{
		SplashTextOn, 320, , No Warcraft Instance Detected.   Shutting Down
		Sleep 4000
		SplashTextOff
		ExitApp		
	}
	; WinGet returns all instances of World of Warcraft into a List, id1, id2, etc...
	WinGet, id, list, World of Warcraft
	count:= 0
	Loop, %id%
	{
		count++
	}
	If (count > 1) ; If more than one WOW window is open
	{
		SplashTextOn, 200, , Multiple Warcraft Instances Detected!
		Loop %count%   ; This loop minimizes all WOW windows for easier selection
		{
			window := id%A_Index%
			WinMinimize, ahk_id %window%
		}
		Sleep 2000
		SplashTextOff
		Loop %count% ; This activates each window one by one until the correct one is selected
		{
			window2 := id%a_index%
			WinActivate, ahk_id %window2%
			Sleep 250
			MsgBox, 4,, Is This the Desired WOW Instance to Send Commands? (Press Yes or No)
			IfMsgBox Yes
			{
				idMain = %window2%
				break
			}
			else
			{
				WinMinimize, ahk_id %window2%
			}
		}
	}
	else
	{
		idMain = %id1%
		WinActivate, ahk_id %idMain%
	}
; End of Window Identification.
	
; Global Variables

	numStacks :=   	; Total Stacks initial and remaining
	sleepTimer1 := 	; Rare Ink
	sleepTimer2 := 	; Common Ink
	sleepTimer3 := 	; Mailbox Sleep Timer for Milling
	sleepTimer4 := 	; Mailbox Sleep Timer for Prospecting
	
	; Global GUI Variables
	Yes :=			; Noise on completion
	No :=			; No noise on completion
	Ult := 			; GUI radio for Ultimate...
	End := 			; Endless
	Norm := 		; Normal
	Mill :=			; Milling Profession
	Prospect :=		; Jewelcrafting Professions
	Disenchant :=	; Enchanting
	
	; Misc Record Keeping Variables
	finalCount :=	; Estimated Number of Herb Stacks processed
	timeTilFin :=	; Estimated Time until finish.
	
	; Installing Picture and Sound File for GUI
	FileInstall, Sklug.gif,Sklug.gif, 1
	FileInstall, C:\Windows\Media\Ring10.wav, Ring10.wav, 1
	
; End of Global Variable Decelerations
	
; All the Functions Placed at End of Program to be Called upon 

	; Function "Rand(Float,Float);
	; Custom Random macro, to actually make random number.  This is good for rand timer on
	; Keyboard input to mimic human behaviour.  The reason this is necessary is
	; because computers aren't really random.  They fool you into thinking it is random
	; by using a complex math equations usually based off the date down to the millisecond 
	; to create a "seed" behind the scenes to bounce the randomization algorithm off of.
	; However, if the seed never changes, like a random number generator within a loop,
	; the number never changes.  This solves that issue in a unique way.
	Rand( a=0.0, b=1 ) 
	{
		IfEqual,a,,Random,,% r := b = 1 ? Rand(0,0xFFFFFFFF) : b
		Else Random,r,a,b
		Return r
	}
	
	; Function "MillHerb()"
	; Primary Milling Function
	; Send keyboard stroke '1' to the given Window's Process ID (aka handle)
	MillHerb()
	{
		global idMain
		ControlSend,, 1, ahk_id %idMain%
	}
	
	; Function "ProspectOre()"
	; Primary Prospecting Function
	; Send keyboard stroke '1' to the given Window's Process ID (aka handle)
	ProspectOre()
	{
		global idMain
		ControlSend,, 1, ahk_id %idMain%
	}
	
	; Function "Disenchanting()"
	; Same exact code as milling and prospecting, but I use 3 functions purely for
	; the ease of reading and consistency in naming within each profession.
	; Send keyboard stroke '1' to the given Window's Process ID (aka handle)
	Disenchanting()
	{
		global idMain
		ControlSend,, 1, ahk_id %idMain%
	}
	
	; Function "MailBoxSleepTimer(String)"
	; Calculates timer for program to wait while it withdraws herbs or ore from mailbox
	; It is dynamic in that it accounts for bag size, and takes into account the 60 sec
	; delay for each 50 items from the mailbox.  2 Sec extra is given for any lag or delays
	SleepTimer(timerName)
	{
		; Need to call global variable from within the function before
		; I can use those numbers.
		global sleepTimer1
		global sleepTimer2
		global sleepTimer3
		global sleepTimer4
		; Setting the SleepTimer delays for when crafting ink/between mill + prospecting/mailbox
		If (timerName = "millPause")
		{
			sleepRand := Rand(2650,3050) ; Delay between Mill
			sleep, %sleepRand% ;
		}
		else if (timerName = "prospect") ; Delay between Prospect
		{
			sleepRand := Rand(4150,4450)
			sleep, %sleepRand%
		}
		else if (timerNaume = "sparkling") ; Delay between each Serpent's Eye crafts
		{
			sleep, 1500
		}
		else If (timerName = "starlight") ; Rare ink craft timer delay
		{
			sleep, %sleepTimer1%
		}
		else if (timerName = "dreams") ; Common ink craft timer delay
		{
			sleep, %sleepTimer2%
		}
		else if (timerName = "mailbox") ; milling Mailbox Delay
		{
			sleep, %sleepTimer3%
		}
		else if (timerName = "ore") ; Ore Mailbox Delay
		{
			sleep, %sleepTimer4%
		}
		else
		{
			sleep, 2000
		}
	}
	
	; Function "CraftInk(String)"
	; Determines Which Ink to be Crafted and how long to Sleep
	; and "SPACE" key is added after the craft time delays as to interrupt 
	; crafting action if by chance timers are not 100% accurate since this does not rely
	; on memory injection.
	CraftInk(ink)
	{
		global idMain
		
		If (ink = "starlight") ; Macro position 2
		{
			ControlSend,, 2, ahk_id %idMain%
			SleepTimer("starlight")
			ControlSend,, {SPACE}, ahk_id %idMain%
			Sleep, 3000
		}
		else if (ink = "dreams")
		{
			ControlSend,, 3, ahk_id %idMain% ; Macro position 3
			SleepTimer("dreams")
		}
		else
		{
			return
		}
	}
	
	; Function "Mail()"
	; This pulls more herbs from the mailbox at macro position 4
	; With a proper delay before continuing to Mill.  You would think that continuing to mill
	; as herbs are withdrawn from the mailbox would be ok, however WOW often gets
	; a "mailbox database error" as you are withdrawing and milling at the same time.
	; Thus a delay is necessary.
	Mail()
	{
		global idMain
		
		ControlSend,, 4, ahk_id %idMain%
		SleepTimer("mailbox")
		ControlSend,, {SPACE}, ahk_id %idMain%
		Sleep, 2000
	}
	
	; Function "MailOre()"
	; This pulls more stacks of ore from mailbox at position 3 macro
	; with a proper delay before it continues to prospect.  Just as with milling, continuing to prospect
	; while ore is being removed from the mailbox can potentially creat a "mailbox database error"
	; which then halts all ore and can mess up efficiency. Thus, the delay is a necessary time sacrifice.
	MailOre()
	{
		global idMain
		ControlSend,, 3, ahk_id %idMain%
		SleepTimer("ore")
		ControlSend,, {SPACE}, ahk_id %idMain%
		Sleep, 2000
	}
	
	
	; Function "Loop(int)"
	; This basically loops for how many stacks there are multiplied by 4, as 4 p/stack
	; I also add an extra 4 loops to compensate for 1 stacks worth of potential lag or delays
	; Normal programming convention would use a "FOR" loop here, for most other languages,
	; however in AHK a while loops seems necessary.  Ex For Loop Java/C#:  For (int count = 0; count < stacks*4+4; count++){ }
	Loop(stacks)
	{
		count := 0
		while count < (stacks * 4 + 4)
		{
			MillHerb()
			SleepTimer("millPause")
			count++
		}
	}
	
	; Function "LoopOre(int)"
	; Exactly the same as previous "Loop()" function except using Prospecting variables/timers
	LoopOre(stacks)
	{
		count := 0
		while count < (stacks * 4 + 4)
		{
			ProspectOre()
			SleepTimer("prospect")
			count++
		}
	}
	
	; Function SerpentsEye()
	; This converts the estiimated amount of sparkling shards made into Serpent's Eye
	; The actual ratio is approimately 12.5% Sparkling shards per stack, thus, 0.125 x numStacks = number Serpent's Eye.
	; In other words, 100 ore stacks would result in approximately 12.5 serpent's eye.  This algorithm adds an additional
	; few crafts just in case of server lag/interruption/lucky procs. Also note: I round to 13 to keep INTEGERS only, no floats
	SerpentsEye(stacks)
	{
		global idMain
		
		count := 0
		while count < ((stacks * 13 / 100) + 3)
		{
			ControlSend,, 2, ahk_id %idMain%
			sleepTimer("sparkling")
			count++
		}			
	}
	
	; Function "MailBoxSleepTimer();
	; This is a more complicated algorithm.  Warcraft servers have a  delay of 60 seconds for every 
	; 50 letters opened from the mailbox.  Thus if a character has a lot of bag space, depending
	; on the initial starting amount of herbs, there may be a lot of waiting for server
	; to refresh the mailbox and pull more out.  Thus, for each increment of 50 stacks
	; a 64 second delay is added.  The reason why is it takes approximately 30 seconds to withdraw 50
	; items from the mailbox.  The 60 sec refresh timer begins from when the first item is taken.
	; The mailbox is one of the places that gets hit with server lag on frequent occasions, thus
	; 64 seconds gives a 4 second leeway per 50 stacks.  
	; I have made this completely scalable using a "count" variable multiplier
	MailBoxSleepTimer()
	{
		global numStacks
		global sleepTimer3
		count := 0 ; Multiplier for scalable sleep time for large quantities, to deal with 60 sec refresh delay
		
		If (!((numStacks * 7 / 10) > 50 )) ; 50 stacks or less
		{
			if ((numStacks * 7 / 10) > 10) ; 11 to 50 stacks
			{
				sleepTimer3 := ((numStacks / 10) * 7000)
			}
			else ; 10 or less stacks
			{
				sleepTimer3 := 7000
			}
		}
		else
		{
			count := ((numStacks * 7 / 10) / 50) ; +1 for each 50. (count = 1 for 50-99, count = 2 for 100-149, etc.)
			sleepTimer3 := (((numStacks * 7 / 10) - (count * 50)) * 700 + (count * 64000))
		}
	}
	
	; Function "MailBoxSleepTimerOre()
	; This is similar to the milling function, except it bases its value off a 55% retention of bag space after prospecting
	; so this has adjusted values.  If less than 50 stacks the delays is more precise, but it scales infinitely
	; for any higher amount than 50, adding 64 seconds per 50 stacks to account for the 60 sec mailbox server delay p/50 items.
	MailBoxSleepTimerOre()
	{
		global numStacks
		global sleepTimer4
		count := 0 ; Scalable Multiplier for sleep time of large quantities to deal with 60 sec mailbox refresh delay
		
		If (!((numStacks * 55 / 100) > 50 )) ; 50 stacks or less
		{
			if ((numStacks * 55 / 100) > 10) ; 11 to 50 stacks
			{
				sleepTimer4 := ((numStacks / 10) * 7000)
			}
			else ; 10 or less stacks
			{
				sleepTimer4 := 7000
			}
		}
		else ; any amount of stacks higher than 50
		{
			count := ((numStacks * 55 / 100) / 50) ; Scalable multiplier variable for each 50 
			sleepTimer4 := (((numStacks * 55 / 100) - (count * 50)) * 700 + (count * 64000))
		}	
	}
	
	; Function "MillingDecay()"
	; Once all herbs are milled and inks crafted, timers need to be mathematically
	; modified for the next round, as bags have less room for herbs, and thus take less-time
	; to craft inks, etc...  In other words, decaying time variables.
	MillingDecay()
	{
		global numStacks
    	global sleepTimer1
    	global sleepTimer2

		; Having done a lot of testing, averages out to be about 70% bag space remaining
		; Thus, numStacks * 0.7 - though I use * 7 /10 to keep integers rather than a Float
		numStacks := (numStacks * 7 / 10)
		sleepTimer1 := (numStacks * 1500)
		sleepTimer2 := (numStacks * 11200)
		; Adding a quick default minimum to timer because with low stack amount a lot of RNG play
		; Think if someone just added 1 stack... RNG says it is within the realm of possibility to get 1-2 Rare
		; inks from 1 stack.  However, 1 stack would only give 1.5 sec delay, not even enough to craft 1 ink.
		; In large numbers this average work good, however in low numbers not so much.
		if (numStacks <= 15)
		{
			sleepTimer1 := (sleepTimer1 + 10000)
			sleepTimer2 := (sleepTimer2 + 30000)
		}
    	MailBoxSleepTimer() ; Mailbox wait timer decay as well, a more complicated algorithm.
	}
	
	; Function ProspectingDecay()
	; This is similar to the milling decay timers in that it modifies the number of ore stacks down to
	; estimated available space, as well as modifies the MailBoxSleepTimerOre() for ore, which is its own
	; function due to the compliacted nature of its scaling algorithm for any size input.
	ProspectingDecay()
	{
		global numStacks
		
		; After significant testing, and due to this being purely out of memory, human simulated keystrokes,
		; a mathematical prediction based on averages has been calculated on how much available
		; bag room is left for more ore.  Be aware that one should ALWAYS have at least 8 free slots in their bags
		; when they begin prospecting, as gems can fill up those empty spaces rather rapidly.
		; The math shows an avg of 55%  bag space remaining, thus NumStacks * 0.55 + 4 (a few extra in case of lag)
		; Again, I use integers to keep numbers clean, and no Float.
		numStacks := (numStacks * 55 / 100)
		MailBoxSleepTimerOre()
	}
	
	; Function "ElapsedTime(A_TickCount)"
	; This will be used to represent how much time has elapsed from the start to finish of Normal and
	; Ultimate modes.  Its only argument is that actual "A_TickCount" call, which is the number of milliseconds
	; since the computer was last rebooted.  By simply storing an initial value, I can then take it and substract the 
	; old value from the current time, and the difference will give me the time elapsed.
	; I then parse the milliseconds into clean, Hours/minutes/seconds.
	ElapsedTime(timer)
	{
		global finalCount
		global Mill
		global Prospect
		global Disenchant
		global Yes
		global No
		
		finalTime := A_TickCount - timer  ;  Result is in milliseconds
		; Now getting exact values
		hours := finalTime//3600000
		minutes := (finalTime - hours*3600000)//60000
		seconds := (finalTime - hours*3600000 - minutes*60000)//1000
		
		; Determining Which profession to report on
		items1 := ""
		items2 := ""
		If (Mill = 1)
		{
			items1 := "Milling Approximately"
			items2 := "Herb Stacks"			
		}
		else if (Prospect = 1)
		{
			items1 := "Prospected Approximately"
			items2 := "Ore Stacks"	
		}
		else if (Disenchant = 1)
		{
			items1 := "Disenchanted Approximately"
			items2 := "Pieces of Gear"	
		}
		
		; Makes the number rounded an even integer
		finalCount := Round(finalCount)
		
		if (Yes = 1)
		{
			Loop 2
			{	
				SoundPlay, Ring10.wav
				Sleep 6000
			}
		}
		
		; Profession report values set
		If (hours < 1)
		{
			MsgBox, You Finished %items1% %finalCount% %items2% After %minutes% Minutes and %seconds% Seconds!
		}
		else if (hours > 1) ; I am kind of OCD here in that I want a diff msgBox for "1 hour" vs "hours" 
		{
			MsgBox, You Finished %items1% %finalCount% %items2% After %hours% Hours %minutes% Minutes and %seconds% Seconds!
		}
		else
		{
			MsgBox, You Finished %items1% %finalCount% %items2% After %hours% hour %minutes% Minutes and %seconds% Seconds!
		}
	}
	
	; Function "MillUltimate()"
	; This is the ultimate result, the algorithm that takes all the functions together and
	; makes them work.  The steps in the process is that the herbs will be milled,
	; inks will be crafted, and then bags will be refilled from the mailbox.
	; This will loop, endlessly scaled, the stack count, or in other words, available bag
	; space is less than five.  It will scale to any amount and thus could take only a few times
	; refilling bags, or it may take a dozen loops.
	MillUltimate()
	{
		global numStacks
		global finalCount
			
		while (numStacks >= 5)
		{
			finalCount := (finalCount + numStacks) ; Record Keeping
			; Main Algorithm
			Loop(numStacks) ; Mills all the herbs
			SleepTimer("millPause") ; Delay between crafting and making inks (about 3 sec)
			
			CraftInk("starlight") ; Crafts rare ink
			CraftInk("dreams")    ; Crafts remaining ink
			
			Mail() ; Pulls herbs from mailbox
			
			MillingDecay() ; Re-adjusts timers for sleeping and how many stacks for next loop
		}
		
		if (numStacks  < 5) ; Finishes up the last few stacks, crafts their ink, then completes program.
		{
			finalCount := (finalCount + numStacks) ; Record Keeping
			Loop(numStacks)
			SleepTimer("millPause")
			CraftInk("starlight")
			CraftInk("dreams")
		}		
	}
	
	; Function ProspectUltimate()
	; Essentially the same function as millUltimate, except with adjust prospecting
	; variables and timers.  This Prospect all ore in bags, creates all sparkling shards,
	; then refills bags from the mailbox.
	ProspectUltimate()
	{
		global numStacks
		global finalCount
		
		while (numStacks >= 5) ; Basic Housekeeping
		{
			finalCount := (finalCount + numStacks) ; Record Keeping
			;Main Algorithm
			Loop(numStacks)			; Prospects Ore
			SerpentsEye(numStacks)	; Creates all Serpents Eye
			
			MailOre()				; Pulls more ore from mailbox and includes necessary sleep timer delay
			
			ProspectingDecay()		; Adjust timers and stack size changes.
		}
		if (numStacks < 5)
		{
			finalCount := (finalCount + numStacks)
			Loop(numStacks)
			SerpentsEye(numStacks)
		}
	}		
	; End of Main Algorithm Functions, beginning of core program GUI and program implementation
	
	
	; Function ProfessionToolGUI()
	; The purpose of this is to put the GUI into function form to be easily called to and rebuilt as necessary
	; with a simple "Return" call, thus I don't need to "Reload" the whole program inefficiently, just this function.
	ProfessionToolGUI()
	{
		global numStacks
		global SleepTimer1
		global SleepTimer2
		global Yes
		global No
		global Ult
		global End
		global Norm
		global Mill
		global Prospect
		global Disenchant
		global finalCount
		
		Gui, Add, Tab, x-8 y-1 w520 h280 +center, Profession Toolbox|Milling, Prospecting or Disenchanting
		Gui, Tab, Profession Toolbox
		Gui, Font, S8 CDefault, Verdana
		Gui, Add, Text, x52 y109 w390 h20 cRed +Center, Latest Update: July 25th`, 2014

		Gui, Font, S12 Bold, Verdana
		Gui, Add, Text, x52 y79 w390 h20 +Center, Version 2.0
		Gui, Font, S18 W700, Verdana
		Gui, Add, Text, x52 y39 w390 h30 cTeal +Center, The AFK Master's Toolbox
		Gui, Add, Picture, x92 y159 w330 h110 , Sklug.gif	
		Gui, Font, S8 CDefault, Verdana
		Gui, Add, Text, x62 y139 w150 h20 , Created By:

		Gui, Tab, Milling
		Gui, Add, Button, x362 y169 w140 h100 , BEGIN
		Gui, Add, GroupBox, x12 y159 w140 h110 , Sound on Finish:
		Gui, Font, S8 W400 CDefault, Verdana
		Gui, Add, Radio, x22 y189 w120 h20 vYes Checked, Yes
		Gui, Add, Radio, x22 y219 w120 h30 vNo, No
		Gui, Font, S8 W700 CDefault, Verdana
		Gui, Add, GroupBox, x163 y159 w190 h110 , Choose Style:
		Gui, Font, S8 W400 CDefault, Verdana
		Gui, Add, Radio, x172 y179 w170 h20 vUlt Checked, Ultimate (Mill/Prospect)
		Gui, Add, Radio, x172 y239 w170 h20 vEnd, Endless
		Gui, Add, Radio, x172 y209 w170 h20 vNorm, Normal

		Gui, Add, Edit, x422 y129 w60 h20 vnumStacks +Center, 
		Gui, Add, UpDown, x482 y129 w20 h20 Range1-9999, 
		
		Gui, Font, S11 Bold, Verdana
		Gui, Add, Text, x182 y109 w240 h40 cRed +Center, Total Herb/Ore Stacks in Bags or Items to DE:
		Gui, Font, S8 W700 CDefault, Verdana
		Gui, Add, GroupBox, x12 y39 w140 h110 , Choose Profession:
		Gui, Font, S8 W400 CDefault, Verdana
		Gui, Add, Radio, x22 y59 w120 h20 vMill Checked, Milling
		Gui, Add, Radio, x22 y89 w120 h20 vProspect, Prospecting
		Gui, Add, Radio, x22 y119 w120 h20 vDisenchant, Disenchanting
		
		Gui, Show, x127 y87 h277 w510, AFK Master's Toolbox
		Return
					
		; Actions to be taken after BEGIN button is pressed.
		ButtonBEGIN:
			Gui, submit, NoHide
			
			finalCount := 0 ; Resetting stack counter after button press
			timer := A_TickCount ; This will be keeping track of elapsed time.
						
			; Checking Which Professions Was Chose
			; Beginning of Milling Professions
			If (Mill = 1)
			{
				SplashTextOn, 320, , Press 'F10' at Any Time to Interrupt Milling
				Sleep, 4000
				SplashTextOff
					
				If (End = 1)
				{
					Loop
					{
						millHerb()
						SleepTimer("millPause")
					}
					Return
				}
				else if (Ult = 1)
				{
					; Setting up my needed sleepTimer values
					sleepTimer1 := (numStacks * 1500)
					sleepTimer2 := (numStacks * 11200)
					if (numStacks <= 15)
					{
						sleepTimer1 := (sleepTimer1 + 10000)
						sleepTimer2 := (sleepTimer2 + 30000)
					}
					MailBoxSleepTimer()
					MillUltimate()
				}
				else if (Norm = 1) ; Only mills herbs, and crafts ink one time
				{
					finalCount := numStacks
					sleepTimer1 := (numStacks * 1500)
					sleepTimer2 := (numStacks * 11200)
					; Small overall milling count needs adjusted numbers for closer accuracy because RNG is less
					; Precise than when milling large numbers where the avg becomes more apparent.
					if (numStacks <= 15)
					{
						sleepTimer1 := (sleepTimer1 + 10000)
						sleepTimer2 := (sleepTimer2 + 30000)
					}
					if (numStacks <= 10)
					{
						sleepTimer1 := (sleepTimer1 + 8000)
						sleepTimer2 := (sleepTimer2 + 24000)
					}
					if (numStacks <= 5)
					{
						sleepTimer1 := (sleepTimer1 + 4000)
						sleepTimer2 := (sleepTimer2 + 12000)
					}
					Loop(numStacks)
					SleepTimer("millPause")
					CraftInk("starlight")
					CraftInk("dreams")
				}
				ElapsedTime(timer) ; Final Report on Completion
				Return
			} 
			; End of Milling 
			
			; Beginning of Prospecting 
			else if (Prospect = 1)
			{
				SplashTextOn, 320, , Press 'F10' at Any Time to Interrupt Prospecting
				Sleep, 4000
				SplashTextOff
				
				If (End = 1)
				{
					Loop
					{
						ProspectOre()
						SleepTimer("prospect")
					}
					Return
				}
				else if (Ult = 1)
				{
					MailBoxSleepTimerOre()
					ProspectUltimate()
				}
				else if (Norm = 1)
				{
					finalCount := numStacks
					LoopOre(numStacks)
					SerpentsEye(numStacks)
				}
				ElapsedTime(timer) ; Final Report on Completion
			} 
			; End of Prospecting
			
			; Beginning of Disenchanting
			else if (Disenchant = 1)
			{
				SplashTextOn, 320, , Press 'F10' at Any Time to Interrupt Disenchanting
				Sleep, 4000
				SplashTextOff
				
				If (End = 1)
				{

					Return
				}
				else if (Ult = 1)
				{
					MsgBox, The "Ultimate" Option is Note Currently Available for Disenchanting
					Return
				}
				else if (Norm = 1)
				{

				}
				ElapsedTime(timer) ; Final Report on Completion
			} 
			; End Disenchanting
			
			ElapsedTime(timer)
			Return
	
		GuiClose:
		ExitApp
	}
	
	; Enabling the GUI
	ProfessionToolGui()
	
	; Hotkeys
	Pause::Pause   ; Just Pauses the Script, does not interrupt
	$F10:: Reload ; Reloads whole program
	^q::ExitApp ; Completely Exits program at any time	
		
