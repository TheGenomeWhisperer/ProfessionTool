# ProfessionTool
<font color='red'>**Note:** With profession changes this is mostly redundant now.</font>
Assist with Milling / Enchanting / Prospecting in WOW

I wrote this program because I was somewhat unsatisfied with the other options out there.  Also, I liked the idea of no memory hooks and pure keyboard simulation.  I felt it was important to be able to send keystrokes to the "background" windwos though, so it could be doing its work whilst I was doing something else.  Other "similar" projects only worked on the foreground window. This was as simple as hooking the windows "handle" and then pushing the keys to that window with the AHK "ControlSend" command.

## Why AutoHotkey?
I chose to use AHK(which is essentially a more modern FORK of AutoIt) as it is a really simple and fun "scripting" language with a lot of built-in windows features, but because it is windows based, as a side project, I re-wrote all of the functions into JAVA format with the intent on establishing a UI that could be used on any platform.  I have converted all functions into JAVA so far, but have not built a JAVA UI, so for now, just an AHK UI.  Work in progress


## MAIN FEATURE
*Ultimate Milling* - This will mill all herbs in your bag, convert all to ink, then re-fill up your bags of more herbs from the mailbox, and keep repeating this procedure until you are either out of herbs, or your bags are full.  This scales indefinitely to any bag size amount and runs completely in the background.  The way this was accomplished was basic math calculations on probability, avgs, and a nice algorithm to keep it all in line.

### Necessary Macros (May need to be updated for each expansion)
#### Milling Position #1
```bash
#showtooltip Milling
/cast Milling
/use Rain Poppy
/use Silkweed
/use Desecrated Herb
/use Green Tea Leaf
/use Fool's Cap
/use Snow Lily
```

#### Milling Position #2
```bash
#show Misty Pigment
/cast Inscription
/run for i=1,GetNumTradeSkills() do if GetTradeSkillInfo(i)=="Starlight Ink" then DoTradeSkill(i, GetItemCount("Misty Pigment")/2) break end end *
```

#### Milling Position #3
```bash
#show Shadow Pigment
/cast Inscription
/run for i=1,GetNumTradeSkills() do if GetTradeSkillInfo(i)=="Ink of Dreams" then DoTradeSkill(i, GetItemCount("Shadow Pigment")/2) break end end *
```
#### Milling Position #4
```bash
/click PostalOpenAllButton
```
