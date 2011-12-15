import java.util.logging.Logger;

public class dLottery extends Plugin{
	Logger log = Logger.getLogger("Minecraft");
	dLottoListener dLL;
	static dLottoData dLD;
	static dLottoActions dLA;
	static dLottoTimer dLT;
	
	public void enable(){
		dLD = new dLottoData();
		dLA = new dLottoActions(dLD);
		log.info("[dLottery] version 1.0 enabled!");
	}
	
	public void disable(){
		dLA.onDisable(false);
		etc.getInstance().removeCommand("/llotto");
		etc.getInstance().removeCommand("/llotto buy");
		etc.getInstance().removeCommand("/llotto check");
		etc.getInstance().removeCommand("/blotto");
		etc.getInstance().removeCommand("/blotto buy");
		etc.getInstance().removeCommand("/blotto check");
		etc.getInstance().removeCommand("/mlotto");
		etc.getInstance().removeCommand("/mlotto buy");
		etc.getInstance().removeCommand("/mlotto check");
		etc.getInstance().removeCommand("/ilotto");
		log.info("[dLottery] version 1.0 disabled!");
	}
	
	public void initialize(){
		dLL = new dLottoListener(dLA);
		etc.getLoader().addListener(PluginLoader.Hook.COMMAND, dLL, this, PluginListener.Priority.MEDIUM);
		etc.getInstance().addCommand("/llotto", "- check time till Little Lotto nums are drawn");
		etc.getInstance().addCommand("/llotto buy", " # # # - buy a Little Lotto ticket (# is 1 thru 39)");
		etc.getInstance().addCommand("/llotto check", " check your Little Lotto numbers if you have a ticket");
		etc.getInstance().addCommand("/blotto", "- check time till Big Lotto nums are drawn");
		etc.getInstance().addCommand("/blotto buy", " # # # # # - buy a Big Lotto ticket (# is 1 thru 52)");
		etc.getInstance().addCommand("/blotto check", " check your Big Lotto numbers if you have a ticket");
		etc.getInstance().addCommand("/mlotto", "- check time till Mega Lotto nums are drawn");
		etc.getInstance().addCommand("/mlotto buy", " # # # # # @ - buy a Mega Lotto ticket (# is 1 thru §c56)(@ is 1 thru 46)");
		etc.getInstance().addCommand("/mlotto check", " check your Mega Lotto numbers if you have a ticket");
		etc.getInstance().addCommand("/ilotto", "- instant lotto 1 in 50 chance to win BIG INSTANTLY!");
	}
}
