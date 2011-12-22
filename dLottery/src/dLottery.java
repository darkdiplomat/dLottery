import java.util.logging.Logger;

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
