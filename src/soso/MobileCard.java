package soso;

public class MobileCard {
	private String cardNumber;
	private String userName;
	private String passWord;
	private ServicePackage serPackage;
	private double consumAmount;
	private double money;
	private int realTalkTime;
	private int realSMSCount;
	private int realFlow;
	
	public MobileCard (String num, String name, String pw, ServicePackage pkg, 
			double amt, double mon, int talkt, int smsc, int realf){
		cardNumber = num;
		userName = name;
		passWord = pw;
		serPackage = pkg;
		consumAmount = amt;
		money = mon;
		realTalkTime = talkt;
		realSMSCount = smsc;
		realFlow = realf;
	}
	
	public MobileCard() {}

	public String getCardNumber(){return cardNumber;}
	public void setCardNumber(String cardNumber){this.cardNumber = cardNumber;}
	
	public String getUserName(){return userName;}
	public void setUserName(String userName){this.userName = userName;}
	
	public String getPassWord(){return passWord;}
	public void setPassWord(String passWord){this.passWord = passWord;}
	
	public ServicePackage getServicePackage(){return serPackage;}
	public void setServicePackage(ServicePackage serPackage){this.serPackage = serPackage;}
	
	public double getConsumAmount(){return consumAmount;}
	public void setConsumAmount(double consumAmount){this.consumAmount = consumAmount;}
	
	public double getMoney(){return money;}
	public void setMoney(double money){this.money = money;}
	
	public int getRealTalkTime(){return realTalkTime;}
	public void setRealTalkTime(int realTalkTime){this.realTalkTime = realTalkTime;}
	
	public int getRealSMSCount(){return realSMSCount;}
	public void setRealSMSCount(int realSMSCount){this.realSMSCount = realSMSCount;}
	
	public int getRealFlow(){return realFlow;}
	public void setRealFlow(int realFlow){this.realFlow = realFlow;}
	
}
