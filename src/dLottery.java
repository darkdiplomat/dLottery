import java.util.logging.Logger;

/**
* dLottery v1.x
* Copyright (C) 2011 Visual Illusions Entertainment
* @author darkdiplomat <darkdiplomat@hotmail.com>
*
* This file is part of dLottery.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see http://www.gnu.org/licenses/gpl.html.
*/

public class dLottery extends Plugin{
	Logger log = Logger.getLogger("Minecraft");
	dLottoListener dLL;
	static dLottoData dLD;
	static dLottoActions dLA;
	static dLottoTimer dLT;
	String name = "[dLottery]";
	static String version = "1.1";
	
	public void enable(){
		dLD = new dLottoData();
		dLA = new dLottoActions(dLD);
		log.info(name + " version "+ version + " enabled!");
	}
	
	public void disable(){
		dLA.onDisable(false);
		etc.getInstance().removeCommand("/dlottery");
		etc.getInstance().removeCommand("/llotto");
		etc.getInstance().removeCommand("/blotto");
		etc.getInstance().removeCommand("/mlotto");
		etc.getInstance().removeCommand("/ilotto");
		log.info(name + " version "+ version + " disabled!");
	}
	
	public void initialize(){
		dLL = new dLottoListener(dLA);
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, dLL, this, PluginListener.Priority.MEDIUM);
		etc.getInstance().addCommand("/dlottery", "- displays dLottery INFO");
		etc.getInstance().addCommand("/llotto", "- displays dLottery Little Lotto INFO");
		etc.getInstance().addCommand("/blotto", "- displays dLottery Big Lotto INFO");
		etc.getInstance().addCommand("/mlotto", "- displays dLottery Mega Lotto INFO");
		etc.getInstance().addCommand("/ilotto", "- displays dLottery Instant Lotto INFO");
	}
}
