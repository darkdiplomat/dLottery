import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class dLottoActions {
	dLottoData dld;
	dLottoTimer dlt;
	double megacost, bigcost, littlecost, instantcost, InstaWinnings, megawin, bigwin, littlewin;
	int IWT = -1, currinstant = 0;
	int[] IWF, IWD, IW, IWMB, FT;
	
	HashMap<String, Double> MegaWinners = new HashMap<String, Double>();
	HashMap<String, Double> BigWinners = new HashMap<String, Double>();
	HashMap<String, Double> LittleWinners = new HashMap<String, Double>();
	HashMap<String, int[]> MegaNums = new HashMap<String, int[]>();
	HashMap<String, int[]> BigNums = new HashMap<String, int[]>();
	HashMap<String, int[]> LittleNums = new HashMap<String, int[]>();
	HashMap<String, Double> income = new HashMap<String, Double>();
	ArrayList<String> tempMegaWins = new ArrayList<String>();
	ArrayList<String> tempBigWins = new ArrayList<String>();
	ArrayList<String> tempLittleWins = new ArrayList<String>();
	
	Random rand = new Random();
	
	public dLottoActions(dLottoData dld){
		this.dld = dld;
		megacost = dld.getMegaCost();
		megawin = dld.getMegaWin();
		bigcost = dld.getBigCost();
		bigwin = dld.getBigWin();
		littlecost = dld.getLittleCost();
		littlewin = dld.getLittleWin();
		IWT = dld.getIWT();
		currinstant = dld.getcurrinstant();
		IWF = dld.getIWF();
		IWD = dld.getIWD();
		IW = dld.getIW();
		IWMB = dld.getIWMB();
		FT = dld.getFT();
		InstaWinnings = dld.getInstaWinnings();
		instantcost = dld.getInstantCost();
		dld.loadOther(this);
		dlt = new dLottoTimer(this, dld);
	}
	
	public boolean littleINFO(Player player){
		player.sendMessage("§6-----§3dLottery Little Lotto§6-----");
		player.sendMessage("§3Cost = §6"+priceForm(littlecost));
		player.sendMessage("§3Numbers are §6 1 §3thru §639 §3- Pick 3 (can't be the same)");
		player.sendMessage("§3/LL buy # # # -§6 Buys a Little Lotto Ticket");
		player.sendMessage("§3/LL time -§6 displays time to Number draw");
		player.sendMessage("§3/LL check -§6 checks if you have a ticket and what the numbers are");
		if(player.isAdmin()){
			player.sendMessage("§3/LL draw -§6 immediately draws Little Lotto");
			player.sendMessage("§3/LL broadcast -§6 broadcasts to all when Numbers will be drawn");
		}
		return true;
	}
	
	public boolean bigINFO(Player player){
		player.sendMessage("§6-----§3dLottery Big Lotto§6-----");
		player.sendMessage("§3Cost = §6"+priceForm(bigcost));
		player.sendMessage("§3Numbers are §61 §3thru §652 §3- Pick 5 (can't be the same)");
		player.sendMessage("§3/BL buy # # # # # -§6 Buys a Big Lotto Ticket");
		player.sendMessage("§3/BL time -§6 displays time to Number draw");
		player.sendMessage("§3/BL check -§6 checks if you have a ticket and what the numbers are");
		if(player.isAdmin()){
			player.sendMessage("§3/BL draw -§6 immediately draws Big Lotto");
			player.sendMessage("§3/BL broadcast -§6 broadcasts to all when Numbers will be drawn");
		}
		return true;
	}
	
	public boolean megaINFO(Player player){
		player.sendMessage("§6-----§3dLottery Mega Lotto§6-----");
		player.sendMessage("§3Cost = §6"+priceForm(megacost));
		player.sendMessage("§3Numbers are §61 §3thru §656 §3- Pick 5 (can't be the same)");
		player.sendMessage("§3MegaNums are §61 §3thru §646 §3- Pick 1 (as # - 6)");
		player.sendMessage("§3/ML buy # # # # # # -§6 Buys a Mega Lotto Ticket");
		player.sendMessage("§3/ML time -§6 displays time to Number draw");
		player.sendMessage("§3/ML check -§6 checks if you have a ticket and what the numbers are");
		if(player.isAdmin()){
			player.sendMessage("§3/ML draw -§6 immediately draws Mega Lotto");
			player.sendMessage("§3/ML broadcast -§6 broadcasts to all when Numbers will be drawn");
		}
		return true;
	}
	
	public boolean instantINFO(Player player){
		player.sendMessage("§6-----§3dLottery Instant Lotto§6-----");
		player.sendMessage("§3Cost = §6"+priceForm(instantcost));
		player.sendMessage("§31 in 1000 chance for -§6"+priceForm((InstaWinnings*10)));
		player.sendMessage("§31 in 500 chance for -§6"+priceForm((InstaWinnings*5)));
		player.sendMessage("§31 in 250 chance for -§6"+priceForm((InstaWinnings*5)));
		player.sendMessage("§31 in 200 chance for -§6"+priceForm(InstaWinnings));
        player.sendMessage("§31 in 100 chance for -§6"+priceForm(instantcost));
        player.sendMessage("§31 in 50 chance for -§6Free Ticket (used immediately)");
        return true;
	}
	
	public boolean getInstantTicket(Player player){
		String name = player.getName();
		if(!hasEIC(name)){
			player.sendMessage("§2[§3dLotto§2]§c Sorry, you don't have enough money to play...");
			return true;
		}
		
		verifyInstantNums();
		
		if(currinstant == IWT){
			payout(InstaWinnings*10, player.getName());
			player.sendMessage("§2[§3dLotto§2]§b Congratz! You won §e"+priceForm(InstaWinnings*10));
		}
		else{
			boolean win = false;
			for(int i = 0; i < 2; i++){
				if(currinstant == IWF[i]){
					payout(InstaWinnings*5, player.getName());
					player.sendMessage("§2[§3dLotto§2]§b Congratz! You won §e"+priceForm(InstaWinnings*5));
					win = true;
					break;
				}
			}
			if(!win){
				for(int i = 0; i < 4; i++){
					if(currinstant == IWD[i]){
						payout(InstaWinnings*2, player.getName());
						player.sendMessage("§2[§3dLotto§2]§b Congratz! You won §e"+priceForm(InstaWinnings*2));
						win = true;
						break;
					}
				}
			}
			if(!win){
				for(int i = 0; i < 5; i++){
					if(currinstant == IW[i]){
						payout(InstaWinnings, player.getName());
						player.sendMessage("§2[§3dLotto§2]§b Congratz! You won §e"+priceForm(InstaWinnings));
						win = true;
						break;
					}
				}
			}
			if(!win){
				for(int i = 0; i < 10; i++){
					if(currinstant == IWMB[i]){
						payout(instantcost, player.getName());
						player.sendMessage("§2[§3dLotto§2]§b You won your money back");
						win = true;
						break;
					}
				}
			}
			if(!win){
				for(int i = 0; i < 20; i++){
					if(currinstant == FT[i]){
						player.sendMessage("§2[§3dLotto§2]§b You won a free ticket");
						currinstant++;
						return getInstantTicket(player);
					}
				}
			}
			if(!win){
				player.sendMessage("§2[§3dLotto§2]§c You lost.");
			}
		}
		if(currinstant == 999){
			currinstant = 0;
			IWT = -1;
			for(int i = 0; i < 2; i++){
				IWF[i] = -1;
			}
			for(int i = 0; i < 4; i++){
				IWD[i] = -1;
			}
			for(int i = 0; i < 5; i++){
				IW[i] = -1;
			}
			for(int i = 0; i < 10; i++){
				IWMB[i] = -1;
			}
			for(int i = 0; i < 4; i++){
				FT[i] = -1;
			}
		}
		else{
			currinstant++;
		}
		return true;
	}
	
	public boolean getMegaTicket(Player player, String numbers){
		String name = player.getName();
		if(MegaNums.containsKey(name)){
			player.sendMessage("§2[§3dLotto§2]§c You already have a Mega Lotto Ticket.");
		}
		if(!hasEMC(name)){
			player.sendMessage("§2[§3dLotto§2]§c Sorry, you don't have enough money to play...");
			return true;
		}
		
		String[] num = numbers.split(",");
		int[] nums = new int[6];
		for (int i = 0; i < 5; i++){
			try{
				nums[i] = Integer.parseInt(num[i]);
			}catch (NumberFormatException nfe){
				player.sendMessage("§2[§3dLotto§2]§c You didn't enter an incorrect number at Num:"+(i+1)+" (1-56)");
				return true;
			}
			if(nums[i] < 0 || nums[i] > 56){
				player.sendMessage("§2[§3dLotto§2]§c You didn't enter an incorrect number at Num:"+(i+1)+" (1-56)");
				return true;
			}
		}
		try{
			nums[5] = Integer.parseInt(num[5]);
		}catch (NumberFormatException nfe){
			player.sendMessage("§2[§3dLotto§2]§c You didn't enter an incorrect Mega Number (1-46)");
			return true;
		}
		
		if(nums[5] < 0 || nums[5] > 46){
			player.sendMessage("§2[§3dLotto§2]§c You didn't enter an incorrect Mega Number (1-46)");
			return true;
		}
		if(nums[1] == nums[0] || nums[1] == nums[2] || nums[1] == nums[3] || nums[1] == nums[4]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:2");
			return true;
		}
		if(nums[2] == nums[0] || nums[2] == nums[1] || nums[2] == nums[3] || nums[2] == nums[4]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:3");
			return true;
		}
		if(nums[3] == nums[0] || nums[3] == nums[1] || nums[3] == nums[2] || nums[3] == nums[4]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:4");
			return true;
		}
		if(nums[4] == nums[0] || nums[4] == nums[1] || nums[4] == nums[2] || nums[4] == nums[3]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:5");
			return true;
		}
		
		charge(megacost, name);
		MegaNums.put(name, nums);
		player.sendMessage("§2[§3dLotto§2]§b Mega Lotto Ticket purchased!");
		return true;
	}
	
	public boolean checkMega(Player player){
		player.sendMessage("§2[§3dLotto§2]§bPulling winning Mega Lotto numbers in:");
		player.sendMessage("§e"+timeUntil(dlt.megarestart));
		player.sendMessage("§bCurrent Total Prize is: §e"+priceForm(income.get("Mega")));
		return true;
	}
	
	public boolean broadcastMega(){
		etc.getServer().messageAll("§2[§3dLotto§2]§bPulling winning Mega Lotto numbers in:");
		etc.getServer().messageAll("§e"+timeUntil(dlt.megarestart));
		etc.getServer().messageAll("§bCurrent Total Prize is: §e"+priceForm(income.get("Mega")));
		return true;
	}
	
	public boolean getBigTicket(Player player, String numbers){
		String name = player.getName();
		if(BigNums.containsKey(name)){
			player.sendMessage("§2[§3dLotto§2]§c You already have a Big Lotto Ticket.");
		}
		if(!hasEBC(name)){
			player.sendMessage("§2[§3dLotto§2]§c Sorry, you don't have enough money to play...");
			return true;
		}
		String[] num = numbers.split(",");
		int[] nums = new int[5];
		for (int i = 0; i < 5; i++){
			try{
				nums[i] = Integer.parseInt(num[i]);
			}catch (NumberFormatException nfe){
				player.sendMessage("§2[§3dLotto§2]§c You didn't enter an incorrect number at Num:"+(i+1)+" (1-52)");
				return true;
			}
			if(nums[i] < 0 || nums[i] > 56){
				player.sendMessage("§2[§3dLotto§2]§c You didn't enter an incorrect number at Num:"+(i+1)+" (1-52)");
				return true;
			}
		}
		
		if(nums[1] == nums[0]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:2");
			return true;
		}
		if(nums[2] == nums[0] || nums[2] == nums[1]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:3");
			return true;
		}
		if(nums[3] == nums[0] || nums[3] == nums[1] || nums[3] == nums[2]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:4");
			return true;
		}
		if(nums[4] == nums[0] || nums[4] == nums[1] || nums[4] == nums[2] || nums[4] == nums[3]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:5");
			return true;
		}
		
		charge(bigcost, name);
		BigNums.put(name, nums);
		player.sendMessage("§2[§fdLotto§2]§b Big Lotto Ticket purchased!");
		return true;
	}
	
	public boolean checkBig(Player player){
		player.sendMessage("§2[§3dLotto§2]§bPulling winning Big Lotto numbers in:");
		player.sendMessage("§e"+timeUntil(dlt.bigrestart));
		player.sendMessage("§bCurrent Total Prize is: §e"+priceForm(income.get("Big")));
		return true;
	}
	
	public boolean broadcastBig(){
		etc.getServer().messageAll("§2[§3dLotto§2]§bPulling winning Big Lotto numbers in:");
		etc.getServer().messageAll("§e"+timeUntil(dlt.bigrestart));
		etc.getServer().messageAll("§bCurrent Total Prize is: §e"+priceForm(income.get("Big")));
		return true;
	}
	
	public boolean getLittleTicket(Player player, String numbers){
		String name = player.getName();
		if(LittleNums.containsKey(name)){
			player.sendMessage("§2[§3dLotto§2]§c You already have a Little Lotto Ticket.");
		}
		if(!hasELC(name)){
			player.sendMessage("§2[§3dLotto§2]§c Sorry, you don't have enough money to play...");
			return true;
		}
		String[] num = numbers.split(",");
		int[] nums = new int[3];
		for (int i = 0; i < 3; i++){
			try{
				nums[i] = Integer.parseInt(num[i]);
			}catch (NumberFormatException nfe){
				player.sendMessage("§2[§3dLotto§2]§c You didn't enter an incorrect number at Num:"+(i+1)+" (1-39)");
				return true;
			}
			if(nums[i] < 0 || nums[i] > 39){
				player.sendMessage("§2[§3dLotto§2]§c You didn't enter an incorrect number at Num:"+(i+1)+" (1-39)");
				return true;
			}
		}
		
		if(nums[1] == nums[0]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:2");
			return true;
		}
		if(nums[2] == nums[0] || nums[2] == nums[1]){
			player.sendMessage("§2[§3dLotto§2]§c You entered a duplicate number at Num:3");
			return true;
		}
		
		LittleNums.put(name, nums);
		charge(bigcost, name);
		player.sendMessage("§2[§3dLotto§2]§b Little Lotto Ticket purchased!");
		return true;
	}
	
	public boolean checkLittle(Player player){
		player.sendMessage("§2[§3dLotto§2]§bPulling winning Little Lotto numbers in:");
		player.sendMessage("§e"+timeUntil(dlt.littlerestart));
		player.sendMessage("§bCurrent Total Prize is: §e"+priceForm(income.get("Little")));
		return true;
	}
	
	public boolean broadcastLittle(){
		etc.getServer().messageAll("§2[§3dLotto§2]§bPulling winning Little Lotto numbers in:");
		etc.getServer().messageAll("§e"+timeUntil(dlt.littlerestart));
		etc.getServer().messageAll("§bCurrent Total Prize is: §e"+priceForm(income.get("Little")));
		return true;
	}
	
	public boolean hasEMC(String name){
		double bal = (Double)etc.getLoader().callCustomHook("dCBalance", new Object[]{"Player-Balance", name});
		if(bal > megacost){
			return true;
		}
		return false;
	}
	
	public boolean hasEBC(String name){
		double bal = (Double)etc.getLoader().callCustomHook("dCBalance", new Object[]{"Player-Balance", name});
		if(bal > bigcost){
			return true;
		}
		return false;
	}
	
	public boolean hasELC(String name){
		double bal = (Double)etc.getLoader().callCustomHook("dCBalance", new Object[]{"Player-Balance", name});
		if(bal > littlecost){
			return true;
		}
		return false;
	}
	
	public boolean hasEIC(String name){
		double bal = (Double)etc.getLoader().callCustomHook("dCBalance", new Object[]{"Player-Balance", name});
		if(bal > instantcost){
			return true;
		}
		return false;
	}
	
	public void charge(double amount, String name){
		etc.getLoader().callCustomHook("dCBalance", new Object[]{"Player-Charge", name, amount});
	}
	
	public void payout(double amount, String name){
		etc.getLoader().callCustomHook("dCBalance", new Object[]{"Player-Pay", name, amount});
	}
	
	public String timeUntil(long time) {
		double timeLeft = Double.parseDouble(Long.toString(((time - System.currentTimeMillis()) / 1000)));
		String stringTimeLeft = " ";
		if(timeLeft >= 60 * 60 * 24) {
			int days = (int) Math.floor(timeLeft / (60 * 60 * 24));
			timeLeft -= 60 * 60 * 24 * days;
			if(days == 1) {
				stringTimeLeft += Integer.toString(days) + " day, ";
			} else {
				stringTimeLeft += Integer.toString(days) + " days, ";
			}
		}
		if(timeLeft >= 60 * 60) {
			int hours = (int) Math.floor(timeLeft / (60 * 60));
			timeLeft -= 60 * 60 * hours;
			if(hours == 1) {
				stringTimeLeft += Integer.toString(hours) + " hour, ";
			} else {
				stringTimeLeft += Integer.toString(hours) + " hours, ";
			}
		}
		if(timeLeft >= 60) {
			int minutes = (int) Math.floor(timeLeft / (60));
			timeLeft -= 60 * minutes;
			if(minutes == 1) {
				stringTimeLeft += Integer.toString(minutes) + " minute ";
			} else {
				stringTimeLeft += Integer.toString(minutes) + " minutes ";
			}
		} else {
			if(!stringTimeLeft.equals(" ")) {
				stringTimeLeft = stringTimeLeft.substring(0, stringTimeLeft.length()-1);
			}
		}
		int secs = (int) timeLeft;
		if(!stringTimeLeft.equals(" ")) {
			stringTimeLeft += "and ";
		}
		if(secs == 1) {
			stringTimeLeft += secs + " second.";
		} else {
			stringTimeLeft += secs + " seconds.";
		}
		
		return stringTimeLeft;
	}
	
	public void clearMegaWin(){
		MegaWinners.clear();
	}
	
	public void addMW(String win, double amount){
		MegaWinners.put(win, amount);
	}
	
	public void addBW(String win, double amount){
		BigWinners.put(win, amount);
	}
	
	public void addLW(String win, double amount){
		LittleWinners.put(win, amount);
	}
	
	public void addIncome(String type, double amount){
		income.put(type, amount);
	}
	
	public boolean drawMegaNow(){
		dlt.MDOC();
		drawMegaNums();
		return true;
	}
	
	public boolean drawBigNow(){
		dlt.BDOC();
		drawBigNums();
		return true;
	}
	
	public boolean drawLittleNow(){
		dlt.LDOC();
		drawLittleNums();
		return true;
	}
	
	public boolean checkMegaTicket(Player player){
		String name = player.getName();
		if(MegaNums.containsKey(name)){
			int[] nums = MegaNums.get(name);
			player.sendMessage("§2[§3dLotto§2]§b Your Mega Ticket Numbers are:");
			player.sendMessage("§e"+nums[0]+" "+nums[1]+" "+nums[2]+" "+nums[3]+" "+nums[4]+" §6MegaNum:§e"+nums[5]);
		}
		else{
			player.sendMessage("§2[§3dLotto§2]§c You don't have a Mega Lotto Ticket...");
		}
		return true;
	}
	
	public boolean checkBigTicket(Player player){
		String name = player.getName();
		if(BigNums.containsKey(name)){
			int[] nums = BigNums.get(name);
			player.sendMessage("§2[§3dLotto§2]§b Your Big Ticket Numbers are:");
			player.sendMessage("§e"+nums[0]+" "+nums[1]+" "+nums[2]+" "+nums[3]+" "+nums[4]);
		}
		else{
			player.sendMessage("§2[§3dLotto§2]§c You don't have a Big Lotto Ticket...");
		}
		return true;
	}
	
	public boolean checkLittleTicket(Player player){
		String name = player.getName();
		if(LittleNums.containsKey(name)){
			int[] nums = LittleNums.get(name);
			player.sendMessage("§2[§3dLotto§2]§b Your Little Ticket Numbers are:");
			player.sendMessage("§e"+nums[0]+" "+nums[1]+" "+nums[2]);
		}
		else{
			player.sendMessage("§2[§3dLotto§2]§c You don't have a Little Lotto Ticket...");
		}
		return true;
	}
	
	public void drawMegaNums(){
		int[] nums = new int[6];
		nums[0] = rand.nextInt(56)+1;
		nums[1] = rand.nextInt(56)+1;
		nums[2] = rand.nextInt(56)+1;
		nums[3] = rand.nextInt(56)+1;
		nums[4] = rand.nextInt(56)+1;
		nums[5] = rand.nextInt(46)+1;
		while(nums[1] == nums[0] || nums[1] == nums[2] || nums[1] == nums[3] || nums[1] == nums[4]){
			nums[1] = rand.nextInt(56);
		}
		while(nums[2] == nums[0] || nums[2] == nums[1] || nums[2] == nums[3] || nums[2] == nums[4]){
			nums[2] = rand.nextInt(56);
		}
		while(nums[3] == nums[0] || nums[3] == nums[1] || nums[3] == nums[2] || nums[3] == nums[4]){
			nums[3] = rand.nextInt(56);
		}
		while(nums[4] == nums[0] || nums[4] == nums[1] || nums[4] == nums[2] || nums[4] == nums[3]){
			nums[4] = rand.nextInt(56);
		}
		
		etc.getServer().messageAll("§2[§3dLotto§2]§b - Mega Lotto has been drawn! #'s are:");
		etc.getServer().messageAll("§e"+nums[0]+" "+nums[1]+" "+nums[2]+" "+nums[3]+" "+nums[4]+" §6MegaNum:§e"+nums[5]);
		dld.log.info("[dLotto] Mega Lotto has been drawn! #'s are:"+nums[0]+" "+nums[1]+" "+nums[2]+" "+nums[3]+" "+nums[4]+" MegaNum:"+nums[5]);
		
		double megapay = megawin;
		if(!income.isEmpty()){
			megapay = income.get("Mega");
		}
		if(megapay > 0){
			for(String key: MegaNums.keySet()){
				int[] mn = MegaNums.get(key);
				boolean n1 = false, n2 = false, n3 = false, n4 = false, n5 = false;
				for(int i = 0; i < 5; i++){
					if(nums[0] == mn[i]){
						n1 = true;
						continue;
					}
					else if(nums[1] == mn[i]){
						n2 = true;
						continue;
					}
					else if(nums[2] == mn[i]){
						n3 = true;
						continue;
					}
					else if (nums[3] == mn [i]){
						n4 = true;
						continue;
					}
					else if (nums[4] == mn[i]){
						n5 = true;
						continue;
					}
				}
				if(n1 && n2 && n3 && n4 && n5){
					if(mn[5] == nums[5]){
						tempMegaWins.add(key);
					}
				}
			}
			MegaNums.clear();
			etc.getServer().messageAll("§2[§3dLotto§2]§b There were §e"+tempMegaWins.size()+"§b winners!");
			if(!tempMegaWins.isEmpty()){
				megapay /= tempMegaWins.size();
				if(megapay < megacost*2){
					megapay = megacost*2;
				}
				for(int i = 0; i < tempMegaWins.size(); i++){
					Player p = etc.getServer().getPlayer(tempMegaWins.get(i));
					if((p != null) && (p.isConnected())){
						p.sendMessage("§2[§fdLotto§2]§b Congrats You won the Mega Lotto: §e"+ priceForm(megapay));
					}
					else{
						if(MegaWinners.containsKey(tempMegaWins.get(i))){
							double addwinnings = MegaWinners.get(i) + megapay;
							MegaWinners.put(tempMegaWins.get(i), addwinnings);
						}
						MegaWinners.put(tempMegaWins.get(i), megapay);
					}
				}
				income.put("Mega", megawin);
				tempMegaWins.clear();
			}
			else{
				double oldwin = income.get("Mega");
				income.put("Mega", megawin+oldwin);
			}
		}
	}
	
	public void drawBigNums(){
		int[] nums = new int[5];
		nums[0] = rand.nextInt(52)+1;
		nums[1] = rand.nextInt(52)+1;
		nums[2] = rand.nextInt(52)+1;
		nums[3] = rand.nextInt(52)+1;
		nums[4] = rand.nextInt(52)+1;
		while(nums[1] == nums[0] || nums[1] == nums[2] || nums[1] == nums[3] || nums[1] == nums[4]){
			nums[1] = rand.nextInt(52);
		}
		while(nums[2] == nums[0] || nums[2] == nums[1] || nums[2] == nums[3] || nums[2] == nums[4]){
			nums[2] = rand.nextInt(52);
		}
		while(nums[3] == nums[0] || nums[3] == nums[1] || nums[3] == nums[2] || nums[3] == nums[4]){
			nums[3] = rand.nextInt(52);
		}
		while(nums[4] == nums[0] || nums[4] == nums[1] || nums[4] == nums[2] || nums[4] == nums[3]){
			nums[4] = rand.nextInt(52);
		}
		etc.getServer().messageAll("§2[§3dLotto§2]§b - Big Lotto has been drawn! #'s are:");
		etc.getServer().messageAll("§e"+nums[0]+" "+nums[1]+" "+nums[2]+" "+nums[3]+" "+nums[4]);
		dld.log.info("[dLotto] Big Lotto has been drawn! #'s are:"+nums[0]+" "+nums[1]+" "+nums[2]+" "+nums[3]+" "+nums[4]);
		
		double bigpay = bigwin;
		if(!income.isEmpty()){
			bigpay = income.get("Big");
		}
		if(bigpay > 0){
			for(String key: BigNums.keySet()){
				int[] mn = BigNums.get(key);
				boolean n1 = false, n2 = false, n3 = false, n4 = false, n5 = false;
				for(int i = 0; i < 5; i++){
					if(nums[0] == mn[i]){
						n1 = true;
						continue;
					}
					else if(nums[1] == mn[i]){
						n2 = true;
						continue;
					}
					else if(nums[2] == mn[i]){
						n3 = true;
						continue;
					}
					else if (nums[3] == mn [i]){
						n4 = true;
						continue;
					}
					else if (nums[4] == mn[i]){
						n5 = true;
						continue;
					}
				}
				if(n1 && n2 && n3 && n4 && n5){
					tempBigWins.add(key);
				}
			}
			BigNums.clear();
			etc.getServer().messageAll("§2[§3dLotto§2]§b There were §e"+tempBigWins.size()+"§b winners!");
			if(!tempBigWins.isEmpty()){
				bigpay /= tempBigWins.size();
				if(bigpay < bigcost*2){
					bigpay = bigcost*2;
				}
				for(int i = 0; i < tempBigWins.size(); i++){
					Player p = etc.getServer().getPlayer(tempBigWins.get(i));
					if((p != null) && (p.isConnected())){
						p.sendMessage("§2[§3dLotto§2]§b Congrats You won the Big Lotto: §e"+ priceForm(bigpay));
					}
					else{
						if(BigWinners.containsKey(tempBigWins.get(i))){
							double addwinnings = BigWinners.get(i) + bigpay;
							BigWinners.put(tempBigWins.get(i), addwinnings);
						}
						BigWinners.put(tempBigWins.get(i), bigpay);
					}
				}
				income.put("Big", bigwin);
				tempBigWins.clear();
			}
			else{
				double oldwin = income.get("Big");
				income.put("Big", bigwin+oldwin);
			}
		}
	}
	
	public void drawLittleNums(){
		int[] nums = new int[3];
		nums[0] = rand.nextInt(39)+1;
		nums[1] = rand.nextInt(39)+1;
		nums[2] = rand.nextInt(39)+1;
		while(nums[1] == nums[0] || nums[1] == nums[2]){
			nums[1] = rand.nextInt(39);
		}
		while(nums[2] == nums[0] || nums[2] == nums[1]){
			nums[2] = rand.nextInt(39);
		}
		etc.getServer().messageAll("§2[§3dLotto§2]§b - LittleLotto has been drawn! #'s are:");
		etc.getServer().messageAll("§e"+nums[0]+" "+nums[1]+" "+nums[2]);
		dld.log.info("[dLotto] LittleLotto has been drawn! #'s are:"+nums[0]+" "+nums[1]+" "+nums[2]);
		
		double littlepay = littlewin;
		if(!income.isEmpty()){
			littlepay = income.get("Little");
		}
		if(littlepay > 0){
			for(String key: LittleNums.keySet()){
				int[] mn = MegaNums.get(key);
				boolean n1 = false, n2 = false, n3 = false;
				for(int i = 0; i < 5; i++){
					if(nums[0] == mn[i]){
						n1 = true;
						continue;
					}
					else if(nums[1] == mn[i]){
						n2 = true;
						continue;
					}
					else if(nums[2] == mn[i]){
						n3 = true;
						continue;
					}
					else{
						break;
					}
				}
				if(n1 && n2 && n3){
					tempLittleWins.add(key);
				}
			}
			LittleNums.clear();
			etc.getServer().messageAll("§2[§3dLotto§2]§b There were §e"+tempLittleWins.size()+"§b winners!");
			if(!tempLittleWins.isEmpty()){
				littlepay /= tempLittleWins.size();
				if(littlepay < littlecost*2){
					littlepay = littlecost*2;
				}
				for(int i = 0; i < tempLittleWins.size(); i++){
					Player p = etc.getServer().getPlayer(tempLittleWins.get(i));
					if((p != null) && (p.isConnected())){
						p.sendMessage("§2[§fdLotto§2]§b Congrats You won the Little Lotto: §e"+ priceForm(littlepay));
					}
					else{
						if(LittleWinners.containsKey(tempLittleWins.get(i))){
							double addwinnings = LittleWinners.get(i) + littlepay;
							LittleWinners.put(tempLittleWins.get(i), addwinnings);
						}
						LittleWinners.put(tempLittleWins.get(i), littlepay);
					}
				}
				income.put("Little", littlewin);
				tempLittleWins.clear();
			}
			else{
				double oldwin = income.get("Little");
				income.put("Little", littlewin+oldwin);
			}
		}
	}
	
	public void verifyInstantNums(){
		if(IWT == -1){
			IWT = rand.nextInt(1000);
		}
		for(int i = 0; i < 2; i++){
			if(IWF[i] == -1){
				IWF[i] = rand.nextInt(1000);
				for(int j = 0; j < i; j++){
					while(IWF[i] == IWF[j] || IWF[i] == IWT){
						IWF[i] = rand.nextInt(1000);
					}
				}
			}
		}
		for(int i = 0; i < 4; i++){
			if(IWD[i] == -1){
				IWD[i] = rand.nextInt(1000);
				for(int j = 0; j < i; j++){
					while(IWD[i] == IWD[j] || IWD[i] == IWT || IWD[i] == IWF[0] || IWD[i] == IWF[1]){
						IWD[i] = rand.nextInt(1000);
					}
				}
			}
		}
		for(int i = 0; i < 5; i++){
			if(IW[i] == -1){
				IW[i] = rand.nextInt(1000);
				for(int j = 0; j < i; j++){
					while(IW[i] == IW[j] || IW[i] == IWT || IW[i] == IWF[0] || IW[i] == IWF[1] || IW[i] == IWD[0] || IW[i] == IWD[1] || IW[i] == IWD[2] || IW[i] == IWD[3]){
						IW[i] = rand.nextInt(1000);
					}
				}
			}
		}
		for(int i = 0; i < 10; i++){
			if(IWMB[i] == -1){
				IWMB[i] = rand.nextInt(1000);
				for(int j = 0; j < i; j++){
					while(IWMB[i] == IWMB[j] || IWMB[i] == IWT || IWMB[i] == IWF[0] || IWMB[i] == IWF[1] || IWMB[i] == IWD[0] || IWMB[i] == IWD[1] || IWMB[i] == IWD[2] 
							|| IWMB[i] == IWD[3] || IWMB[i] == IW[0] || IWMB[i] == IW[1] || IWMB[i] == IW[2] || IWMB[i] == IW[3] || IWMB[i] == IW[4]){
						IWMB[i] = rand.nextInt(1000);
					}
				}
			}
		}
		for(int i = 0; i < 20; i++){
			if(FT[i] == -1){
				FT[i] = rand.nextInt(1000);
				for(int j = 0; j < i; j++){
					while(FT[i] == FT[j] || FT[i] == IWT || FT[i] == IWF[0] || FT[i] == IWF[1] || FT[i] == IWD[0] || FT[i] == IWD[1] || FT[i] == IWD[2] 
							|| FT[i] == IWD[3] || FT[i] == IW[0] || FT[i] == IW[1] || FT[i] == IW[2] || FT[i] == IW[3] || FT[i] == IW[4]
							|| FT[i] == IWMB[0] || FT[i] == IWMB[1] || FT[i] == IWMB[2] || FT[i] == IWMB[3] || FT[i] == IWMB[4]
							|| FT[i] == IWMB[5] || FT[i] == IWMB[6] || FT[i] == IWMB[7] || FT[i] == IWMB[8] || FT[i] == IWMB[9]){
						FT[i] = rand.nextInt(1000);
					}
				}
			}
		}
	}
	
	
	public void payPlayer(Player player){
		double winnings = 0;
		if(MegaWinners.containsKey(player.getName())){
			winnings = MegaWinners.get(player.getName());
			MegaWinners.remove(player.getName());
		}
		if(BigWinners.containsKey(player.getName())){
			winnings += BigWinners.get(player.getName());
			BigWinners.remove(player.getName());
		}
		if(LittleWinners.containsKey(player.getName())){
			winnings += LittleWinners.get(player.getName());
			LittleWinners.remove(player.getName());
		}
		if(winnings > 0){
			payout(winnings, player.getName());
			player.sendMessage("§2[§3dLotto§2]§b Congrats You have winnings waiting in the Amount of: §e"+ priceForm(winnings));
		}
	}
	
	public void onDisable(boolean oncommand){
		if(!oncommand){ dlt.stop(); }
		for(String key : MegaWinners.keySet()){
			dld.saveMegaWinners(key, MegaWinners.get(key));
		}
		for(String key : BigWinners.keySet()){
			dld.saveBigWinners(key, BigWinners.get(key));
		}
		for(String key : LittleWinners.keySet()){
			dld.saveLittleWinners(key, LittleWinners.get(key));
		}
		dld.saveCurrentWinnings("Mega", income.get("Mega"));
		dld.saveCurrentWinnings("Big", income.get("Big"));
		dld.saveCurrentWinnings("Little", income.get("Little"));
		dld.saveIWT(IWT);
		dld.saveCurrInsta(currinstant);
		for(int i = 0; i < 2; i++){
			dld.saveIWF(i+1, IWF[i]);
		}
		for(int i = 0; i < 4; i++){
			dld.saveIWD(i+1, IWD[i]);
		}
		for(int i = 0; i < 5; i++){
			dld.saveIW(i+1, IW[i]);
		}
		for(int i = 0; i < 10; i++){
			dld.saveIWMB(i+1, IWMB[i]);
		}
		for(int i = 0; i < 20; i++){
			dld.saveFT(i+1, FT[i]);
		}
	}
	
	public String priceForm(double price){
		String newprice = String.valueOf(price);
		String[] form = newprice.split("\\.");
		if(form[1].length() == 1){
			newprice += "0";
		}
		else{
			newprice = form[0] + "." + form[1].substring(0, 2);
		}
		return newprice;
	}
}
