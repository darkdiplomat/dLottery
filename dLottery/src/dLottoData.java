import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dLottoData {
	Logger log = Logger.getLogger("Minecraft");
	
	String Dire = "plugins/config/dLottery/";
	String Props = "dLottoProps.ini";
	String Reset = "dLottoReset.DONOTEDIT";
	String LottoAct = "dLottoActions.DONOTEDIT";
	String LottoMega = "dLottoMega.DONOTEDIT";
	String LottoBig = "dLottoBig.DONOTEDIT";
	String LottoLittle = "dLottoLittle.DONOTEDIT";
	
	PropertiesFile Pro;
	PropertiesFile Act;
	PropertiesFile Time;
	PropertiesFile MFP;
	PropertiesFile BFP;
	PropertiesFile LFP;
	
	File MF;
	File BF;
	File LF;
	
	long MegaDelay = 30, BigDelay = 20, LittleDelay = 10;
	double MegaCost= 1.00, LittleCost = 1.00, BigCost = 1.00, InstantCost = 0.50, InstaWinnings = 2.50, MegaWin = 300, BigWin = 100, LittleWin = 50;
	
	int IWT = -1, currinstant = 0;
	int[] IWF = new int[2], IWD = new int[4], IW = new int[5], IWMB = new int[10], FT = new int[20];
	
	public dLottoData(){
		loadProps();
	}
	
	public void loadProps(){
		File dire = new File(Dire);
		File props = new File(Dire+Props);
		if(!dire.exists()){
			dire.mkdirs();
		}
		if(!props.exists()){
			makeInitialProps();
		}
		
		Pro = new PropertiesFile(Dire+Props);
		Act = new PropertiesFile(Dire+LottoAct);
		Time = new PropertiesFile(Dire+Reset);
		
		MegaCost = Pro.getDouble("MegaCost");
		if(MegaCost < 0.01){
			MegaCost = 1;
		}
		
		BigCost = Pro.getDouble("BigCost");
		if(BigCost < 0.01){
			BigCost = 1;
		}
		
		LittleCost = Pro.getDouble("LittleCost");
		if(LittleCost < 0.01){
			LittleCost = 1;
		}
		
		InstantCost = Pro.getDouble("InstaCost");
		if(InstantCost < 0.01){
			InstantCost = 0.05;
		}
		
		InstaWinnings = Pro.getDouble("InstaWinnings");
		if(InstaWinnings < InstantCost*2){
			InstaWinnings = InstantCost*2;
		}
		
		MegaDelay = Pro.getLong("MegaDelay") * 60000;
		BigDelay = Pro.getLong("BigDelay") * 60000;
		LittleDelay = Pro.getLong("LittleDelay") * 60000;
		
		MegaWin = Pro.getDouble("MegaWin");
		BigWin = Pro.getDouble("BigWin");
		LittleWin = Pro.getDouble("LittleWin");
		
		if(MegaWin < MegaCost*2){
			MegaWin = MegaCost*2;
		}
		
		if(BigWin < BigCost*2){
			BigWin = BigCost*2;
		}
		
		if(LittleWin < LittleCost*2){
			LittleWin = LittleCost*2;
		}
		
		if(Act.containsKey("IWT")){
			IWT = Act.getInt("IWT");
		}
		if(Act.containsKey("ci")){
			currinstant = Act.getInt("ci");
		}
		for(int i = 0; i < 2; i++){
			if(Act.containsKey("IWF"+(i+1))){
				IWF[i] = Act.getInt("IWF"+(i+1));
			}
			else{
				IWF[i] = -1;
			}
		}
		for(int i = 0; i < 4; i++){
			if(Act.containsKey("IWD"+(i+1))){
				IWD[i] = Act.getInt("IWD"+(i+1));
			}
			else{
				IWD[i] = -1;
			}
		}
		for(int i = 0; i < 5; i++){
			if(Act.containsKey("IW"+(i+1))){
				IW[i] = Act.getInt("IW"+(i+1));
			}
			else{
				IW[i] = -1;
			}
		}
		for(int i = 0; i < 10; i++){
			if(Act.containsKey("IWMB"+(i+1))){
				IWMB[i] = Act.getInt("IWMB"+(i+1));
			}
			else{
				IWMB[i] = -1;
			}
		}
		for(int i = 0; i < 20; i++){
			if(Act.containsKey("FT"+(i+1))){
				FT[i] = Act.getInt("FT"+(i+1));
			}
			else{
				FT[i] = -1;
			}
		}
	}
	
	public void makeInitialProps(){
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(Dire+Props));
			out.write("### dLottery Properties ###"); out.newLine();
			out.write("# Mega Lottery Ticket Cost #"); out.newLine();
			out.write("MegaCost="+MegaCost); out.newLine();
			out.write("# Mega Lottery Winnings - Will be split if Multiple winners (no less than double the cost) - If No winner this will be added to existing amount #"); out.newLine();
			out.write("MegaWin="+MegaWin); out.newLine();
			out.write("# Big Lottery Ticket Cost #"); out.newLine();
			out.write("BigCost="+BigCost); out.newLine();
			out.write("# Big Lottery Winnings - Will be split if Multiple winners (no less than double the cost) - If No winner this will be added to existing amount #"); out.newLine();
			out.write("BigWin="+BigWin); out.newLine();
			out.write("# Little Lottery Ticket Cost #"); out.newLine();
			out.write("LittleCost="+LittleCost); out.newLine();
			out.write("# Little Lottery Winnings - Will be split if Multiple winners (no less than double the cost) - If No winner this will be added to existing amount #"); out.newLine();
			out.write("LittleWin="+LittleWin); out.newLine();
			out.write("# Instant Lottery Ticket Cost #"); out.newLine();
			out.write("InstaCost="+InstantCost); out.newLine();
			out.write("# Instant Lottery Base Winnings Amount #"); out.newLine();
			out.write("InstaWinnings="+InstaWinnings); out.newLine();
			out.write("# Mega Lottery Delay in Minutes #"); out.newLine();
			out.write("MegaDelay="+MegaDelay); out.newLine();
			out.write("# Big Lottery Delay in Minutes #"); out.newLine();
			out.write("BigDelay="+BigDelay); out.newLine();
			out.write("# Little Lottery Delay in Minutes #"); out.newLine();
			out.write("LittleDelay="+LittleDelay); out.newLine();
			out.write("###EOF###");
			out.close();
		}catch(IOException ioe){
			
		}
	}
	
	public void loadOther(dLottoActions dLA){
		
		MF = new File(Dire+LottoMega);
		BF = new File(Dire+LottoBig);
		LF = new File(Dire+LottoLittle);
		
		if(MF.exists()){
			try {
				Scanner scanner = new Scanner(MF);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if(!line.contains("#")){
						String[] s = line.split("=");
						dLA.addMW(s[0], Double.valueOf(s[1]));
					}
				}
				scanner.close();
			} catch (Exception e) {
				log.log(Level.SEVERE, "Exception while reading " + Dire+LottoMega, e);
			}
			MF.delete();
		}
		
		if(BF.exists()){
			try {
				Scanner scanner = new Scanner(BF);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] s = line.split("=");
					dLA.addBW(s[0], Double.valueOf(s[1]));
				}
				scanner.close();
			} catch (Exception e) {
				log.log(Level.SEVERE, "Exception while reading " + Dire+LottoBig, e);
			}
			BF.delete();
		}
		
		if(LF.exists()){
			try {
				Scanner scanner = new Scanner(LF);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] s = line.split("=");
					dLA.addLW(s[0], Double.valueOf(s[1]));
				}
				scanner.close();
			} catch (Exception e) {
				log.log(Level.SEVERE, "Exception while reading " + Dire+LottoLittle, e);
			}
			LF.delete();
		}
		
		double mega = Act.getDouble("Mega", MegaWin);
		double big = Act.getDouble("Big", BigWin);
		double little = Act.getDouble("Little", LittleWin);
		
		dLA.addIncome("Mega", mega);
		dLA.addIncome("Big", big);
		dLA.addIncome("Little", little);
	}
	
	public long getMegaDelay(){
		if(MegaDelay < 1){
			MegaDelay = 30;
		}
		return MegaDelay;
	}
	
	public double getMegaCost(){
		return MegaCost;
	}
	
	public double getMegaWin(){
		return MegaWin;
	}
	
	public long getBigDelay(){
		if(BigDelay < 1){
			BigDelay = 20;
		}
		return BigDelay;
	}
	
	public double getBigCost(){
		return BigCost;
	}
	
	public double getBigWin(){
		return BigWin;
	}
	
	public long getLittleDelay(){
		if(LittleDelay < 1){
			LittleDelay = 10;
		}
		return LittleDelay;
	}
	
	public double getLittleCost(){
		return LittleCost;
	}
	
	public double getLittleWin(){
		return LittleWin;
	}
	
	public double getInstantCost(){
		return InstantCost;
	}
	
	public double getInstaWinnings(){
		return InstaWinnings;
	}
	
	public int getIWT(){
		return IWT;
	}
	
	public int getcurrinstant(){
		return currinstant;
	}
	
	public int[] getIWF(){
		return IWF;
	}
	
	public int[] getIWD(){
		return IWD;
	}
	
	public int[] getIW(){
		return IW;
	}
	
	public int[] getIWMB(){
		return IWMB;
	}
	
	public int[] getFT(){
		return FT;
	}
	
	public long getMegaReset(){
		return Time.getLong("MegaReset");
	}
	
	public long getBigReset(){
		return Time.getLong("BigReset");
	}
	
	public long getLittleReset(){
		return Time.getLong("LittleReset");
	}
	public void saveMegaReset(long reset){
		Time.setLong("MegaReset", reset);
	}
	
	public void saveBigReset(long reset){
		Time.setLong("BigReset", reset);
	}
	
	public void saveLittleReset(long reset){
		Time.setLong("LittleReset", reset);
	}
	
	public void saveMegaWinners(String Name, double amount){
		if(!MF.exists()){
			MFP = new PropertiesFile(Dire+LottoMega);
		}
		MFP.setDouble(Name, amount);
	}
	
	public void saveBigWinners(String Name, double amount){
		if(!BF.exists()){
			BFP = new PropertiesFile(Dire+LottoBig);
		}
		BFP.setDouble(Name, amount);
	}
	
	public void saveLittleWinners(String Name, double amount){
		if(!LF.exists()){
			LFP = new PropertiesFile(Dire+LottoLittle);
		}
		LFP.setDouble(Name, amount);
	}
	
	public void saveCurrentWinnings(String type, double amount){
		Act.setDouble(type, amount);
	}
	
	public void saveIWT(int IWT){
		Act.setInt("IWT", IWT);
	}
	
	public void saveIWF(int spot, int IWF){
		Act.setInt("IWF"+spot, IWF);
	}
	
	public void saveIWD(int spot, int IWD){
		Act.setInt("IWD"+spot, IWD);
	}
	
	public void saveIW(int spot, int IW){
		Act.setInt("IW"+spot, IW);
	}
	
	public void saveIWMB(int spot, int IWMB){
		Act.setInt("IWMB"+spot, IWMB);
	}
	
	public void saveFT(int spot, int FT){
		Act.setInt("FT"+spot, FT);
	}
	
	public void saveCurrInsta(int ci){
		Act.setInt("ci", ci);
	}
}
