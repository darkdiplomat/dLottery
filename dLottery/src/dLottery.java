import java.util.logging.Logger;

public class dLottery extends Plugin{
	Logger log = Logger.getLogger("Minecraft");
	dLottoListener dLL;
	static dLottoData dLD;
	static dLottoActions dLA;
	static dLottoTimer dLT;
	String name = "[dLottery]";
	String version = "1.1";
	
	public void enable(){
		dLD = new dLottoData();
		dLA = new dLottoActions(dLD);
		log.info(name + " version "+ version + " enabled!");
	}
	
	public void disable(){
		dLA.onDisable(false);
		etc.getInstance().removeCommand("/llotto");
		etc.getInstance().removeCommand("/blotto");
		etc.getInstance().removeCommand("/mlotto");
		etc.getInstance().removeCommand("/ilotto");
		log.info(name + " version "+ version + " disabled!");
	}
	
	public void initialize(){
		dLL = new dLottoListener(dLA);
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, dLL, this, PluginListener.Priority.MEDIUM);
		etc.getInstance().addCommand("/llotto", "- displays dLottery Little Lotto info");
		etc.getInstance().addCommand("/blotto", "- displays dLottery Big Lotto info");
		etc.getInstance().addCommand("/mlotto", "- displays dLottery Mega Lotto info");
		etc.getInstance().addCommand("/ilotto", "- instant lotto 1 in 50 chance to win BIG INSTANTLY!");
	}
}
