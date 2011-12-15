import java.util.Timer;
import java.util.TimerTask;

public class dLottoTimer{
	long megadelay = 30, bigdelay = 20, littledelay = 10;
	long megarestart = 0, bigrestart = 0, littlerestart = 0;
	Timer megatimer = new Timer();
	Timer bigtimer = new Timer();
	Timer littletimer = new Timer();
	dLottoActions dLA;
	dLottoData dLD;
	
	public dLottoTimer(dLottoActions dLA, dLottoData dLD){
		this.dLA = dLA;
		this.dLD = dLD;
		megarestart = dLD.getMegaReset();
		bigrestart = dLD.getBigReset();
		littlerestart = dLD.getLittleReset();
		megadelay = dLD.getMegaDelay();
		bigdelay = dLD.getBigDelay();
		littledelay = dLD.getLittleDelay();
		
		if(megarestart < 1){
			megarestart = megadelay;
		}
		else{
			megarestart -= System.currentTimeMillis();
			if(megarestart < 1){
				megarestart = 2000;
			}
		}
		if(bigrestart < 1){
			bigrestart = bigdelay;
		}
		else{
			bigrestart -= System.currentTimeMillis();
			if(bigrestart < 1){
				bigrestart = 1500;
			}
		}
		if(littlerestart < 1){
			littlerestart = littledelay;
		}
		else{
			littlerestart -= System.currentTimeMillis();
			if(littlerestart < 1){
				littlerestart = 1000;
			}
		}
		starttimers(megarestart, bigrestart, littlerestart);
	}
	
	public void starttimers(long mdelay, long bdelay, long ldelay){
		megatimer.schedule(new MegaTimer(), mdelay);
		megarestart = mdelay+System.currentTimeMillis();
		dLD.saveMegaReset(megarestart);

		bigtimer.schedule(new BigTimer(), bdelay);
		bigrestart = bdelay+System.currentTimeMillis();
		dLD.saveBigReset(bigrestart);
		
		littletimer.schedule(new LittleTimer(), ldelay);
		littlerestart = ldelay+System.currentTimeMillis();
		dLD.saveLittleReset(littlerestart);
	}
	
	public void MDOC(){
		megatimer.cancel();
		megatimer.purge();
		megatimer = new Timer();
		megatimer.schedule(new MegaTimer(), megadelay);
		megarestart = megadelay+System.currentTimeMillis();
		dLD.saveMegaReset(megarestart);
	}
	
	public void BDOC(){
		bigtimer.cancel();
		bigtimer.purge();
		bigtimer = new Timer();
		bigtimer.schedule(new BigTimer(), bigdelay);
		bigrestart = bigdelay+System.currentTimeMillis();
		dLD.saveMegaReset(bigrestart);
	}
	
	public void LDOC(){
		littletimer.cancel();
		littletimer.purge();
		littletimer = new Timer();
		littletimer.schedule(new LittleTimer(), littledelay);
		littlerestart = littledelay+System.currentTimeMillis();
		dLD.saveMegaReset(littlerestart);
	}
	
	public void stop(){
		megatimer.cancel();
		megatimer.purge();
		bigtimer.cancel();
		bigtimer.purge();
		littletimer.cancel();
		littletimer.purge();
	}
	
	public long getMTime(){
		return megarestart;
	}
	
	public long getBTime(){
		return bigrestart;
	}
	
	public long getLTime(){
		return littlerestart;
	}
	
	public class MegaTimer extends TimerTask{
		public MegaTimer(){}
		
		public void run(){
			dLA.drawMegaNums();
			megatimer.schedule(new MegaTimer(), megadelay);
			megarestart = megadelay+System.currentTimeMillis();
			dLD.saveMegaReset(megarestart);
		}
	}
	
	public class BigTimer extends TimerTask{
		public BigTimer(){}
		
		public void run(){
			dLA.drawBigNums();
			bigtimer.schedule(new BigTimer(), bigdelay);
			bigrestart = bigdelay+System.currentTimeMillis();
			dLD.saveBigReset(bigrestart);
		}
	}
	
	public class LittleTimer extends TimerTask{
		public LittleTimer(){}
		
		public void run(){
			dLA.drawLittleNums();
			littletimer.schedule(new LittleTimer(), littledelay);
			littlerestart = littledelay+System.currentTimeMillis();
			dLD.saveLittleReset(littlerestart);
		}
	}
}
