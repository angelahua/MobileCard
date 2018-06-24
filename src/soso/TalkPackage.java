package soso;

public class TalkPackage extends ServicePackage implements CallService, SendService{
	private int talkTime;
	private int smsCount;
	public int getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }



	@Override
	public void showInfo() {
		System.out.println("套餐信息："+getPrice()+"元的套餐，含"+talkTime+"分钟免费通话，"+smsCount+"条短信");
		
	}
	@Override
	public int send(int count, MobileCard card){
		card.setRealSMSCount(card.getRealSMSCount() + smsCount);
		return card.getRealSMSCount();
	}
	@Override
	public int call(int minCount, MobileCard card){
		card.setRealTalkTime(card.getRealTalkTime() + talkTime);
		return card.getRealTalkTime();	
	}


}
