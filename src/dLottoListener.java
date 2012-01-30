
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

public class dLottoListener extends PluginListener{
	dLottoActions dLA;
	
	public dLottoListener(dLottoActions dLA){
		this.dLA = dLA;
	}
	
	public boolean onCommand(Player player, String[] cmd){
		if((cmd[0].equalsIgnoreCase("/LLotto"))||(cmd[0].equalsIgnoreCase("/LL"))){
			if(player.canUseCommand("/llotto")){
				if(cmd.length > 1){
					if(cmd[1].equals("buy")){
						if(cmd.length > 4){
							String nums = etc.combineSplit(2, cmd, ",");
							return dLA.getLittleTicket(player, nums);
						}
						else{
							player.notify("Usage: /llotto buy # # # (# = 1 - 39)");
							return true;
						}
					}
					else if(cmd[1].equalsIgnoreCase("broadcast")){
						if(player.isAdmin()){
							return dLA.broadcastLittle();
						}
						else{
							return dLA.checkLittle(player);
						}
					}
					else if(cmd[1].equalsIgnoreCase("draw")){
						if(player.isAdmin()){
							return dLA.drawLittleNow();
						}
					}
					else if(cmd[1].equalsIgnoreCase("check")){
						return dLA.checkLittleTicket(player);
					}
					else if(cmd[1].equalsIgnoreCase("time")){
						return dLA.checkLittle(player);
					}
				}
				else{
					return dLA.littleINFO(player);
				}
			}
			else{
				player.sendMessage("§2[§3dLotto§2]§c You don't have permission to play Little Lotto...");
				return true;
			}
		}
		else if((cmd[0].equalsIgnoreCase("/BLotto"))||(cmd[0].equalsIgnoreCase("/BL"))){
			if(player.canUseCommand("/blotto")){
				if(cmd.length > 1){
					if(cmd[1].equals("buy")){
						if(cmd.length > 6){
							String nums = etc.combineSplit(2, cmd, ",");
							return dLA.getBigTicket(player, nums);
						}
						else{
							player.notify("Usage: /blotto buy # # # # # (# = 1 thru 52)");
							return true;
						}
					}
					else if(cmd[1].equalsIgnoreCase("broadcast")){
						if(player.isAdmin()){
							return dLA.broadcastBig();
						}
						else{
							return dLA.checkBig(player);
						}
					}
					else if(cmd[1].equalsIgnoreCase("draw")){
						if(player.isAdmin()){
							return dLA.drawBigNow();
						}
					}
					else if(cmd[1].equalsIgnoreCase("check")){
						return dLA.checkBigTicket(player);
					}
					else if(cmd[1].equalsIgnoreCase("time")){
						return dLA.checkBig(player);
					}
				}
				else{
					return dLA.bigINFO(player);
				}
			}
			else{
				player.sendMessage("§2[§3dLotto§2]§c You don't have permission to play Big Lotto...");
				return true;
			}
		}
		else if((cmd[0].equalsIgnoreCase("/MLotto"))||(cmd[0].equalsIgnoreCase("/ML"))){
			if(player.canUseCommand("/mlotto")){
				if(cmd.length > 1){
					if(cmd[1].equals("buy")){
						if(cmd.length > 7){
							String nums = etc.combineSplit(2, cmd, ",");
							return dLA.getMegaTicket(player, nums);
						}
						else{
							player.notify("Usage: /mlotto buy # # # # # @ (# = 1 thru 56)(@ = 1 thru 46)");
							return true;
						}
					}
					else if(cmd[1].equalsIgnoreCase("broadcast")){
						if(player.isAdmin()){
							return dLA.broadcastMega();
						}
						else{
							return dLA.checkMega(player);
						}
					}
					else if(cmd[1].equalsIgnoreCase("draw")){
						if(player.isAdmin()){
							return dLA.drawMegaNow();
						}
					}
					else if(cmd[1].equalsIgnoreCase("check")){
						return dLA.checkMegaTicket(player);
					}
					else if(cmd[1].equalsIgnoreCase("time")){
						return dLA.checkMega(player);
					}
				}
				else{
					return dLA.megaINFO(player);
				}
			}
			else{
				player.sendMessage("§2[§3dLotto§2]§c You don't have permission to play Mega Lotto...");
				return true;
			}
		}
		else if((cmd[0].equalsIgnoreCase("/ilotto"))||(cmd[0].equalsIgnoreCase("/IL"))){
			if(player.canUseCommand("/ilotto")){
				if(cmd.length == 1){
					return dLA.instantINFO(player);
				}
				else if(cmd[1].equalsIgnoreCase("buy")){
					return dLA.getInstantTicket(player);
				}
			}
			else{
				player.sendMessage("§2[§3dLotto§2]§c You don't have permission to play Instant Lotto...");
				return true;
			}	
		}
		else if(cmd[0].equalsIgnoreCase("/dlottery")){
			return dLA.dLottoINFO(player);
		}
		else if((cmd[0].equals("/#save-all"))||(cmd[0].equals("/#stop"))){
				saveallstop(player);
		}
		return false;
	}
	
	public boolean onConsoleCommand(String[] cmd){
		if((cmd[0].equals("save-all"))||(cmd[0].equals("stop"))){
			dLA.onDisable(true);
		}
		return false;
	}
	
	public void onLogin(Player player){
		dLA.payPlayer(player);
	}
	
	private void saveallstop(Player player){
		try{
			OServerConfigurationManager oscm = etc.getMCServer().h;
			if(oscm.h(player.getName())){
				dLA.onDisable(true);
			}
		}catch (Error e){
			//Notch/Jeb changed the code again...
		}catch (Exception ex){
			//Notch/Jeb changed the code again...
		}
	}
}
