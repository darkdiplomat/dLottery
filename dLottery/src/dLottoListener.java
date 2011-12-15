
public class dLottoListener extends PluginListener{
	dLottoActions dLA;
	
	public dLottoListener(dLottoActions dLA){
		this.dLA = dLA;
	}
	
	public boolean onCommand(Player player, String[] cmd){
		if((cmd[0].equalsIgnoreCase("/LLotto"))||(cmd[0].equalsIgnoreCase("/ll"))){
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
				}
				else{
					return dLA.checkLittle(player);
				}
			}
			else{
				player.sendMessage("§2[§3dLotto§2]§c You don't have permission to play Little Lotto...");
				return true;
			}
		}
		else if((cmd[0].equalsIgnoreCase("/BLotto"))||(cmd[0].equalsIgnoreCase("/bl"))){
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
				}
				else{
					return dLA.checkBig(player);
				}
			}
			else{
				player.sendMessage("§2[§3dLotto§2]§c You don't have permission to play Big Lotto...");
				return true;
			}
		}
		else if((cmd[0].equalsIgnoreCase("/MLotto"))||(cmd[0].equalsIgnoreCase("/ml"))){
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
				}
				else{
					return dLA.checkMega(player);
				}
			}
			else{
				player.sendMessage("§2[§3dLotto§2]§c You don't have permission to play Mega Lotto...");
				return true;
			}
		}
		else if((cmd[0].equalsIgnoreCase("/ilotto"))||(cmd[0].equalsIgnoreCase("/il"))){
			if(player.canUseCommand("/ilotto")){
				return dLA.getInstantTicket(player);
			}
			else{
				player.sendMessage("§2[§3dLotto§2]§c You don't have permission to play Instant Lotto...");
				return true;
			}	
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
