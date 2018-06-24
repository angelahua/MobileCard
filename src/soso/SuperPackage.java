package soso;

public class SuperPackage extends ServicePackage implements CallService, SendService, NetService{
	private int talkTime;
	private int smsCount;
	private int flow;
	
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
    public int getFlow() {
        return flow;
    }
    public void setFlow(int flow) {
        this.flow = flow;
    }

    @Override
	public void showInfo() {
		System.out.println("超人套餐： "+ "通话时长为" + talkTime + "分钟/月, "
				+ "短信条数为"+smsCount+"条/月, "+ "上网流量为" + (double)flow / 1024.0 +"GB/月");
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
	
    @Override
	public int netPlay(int flow, MobileCard card){
		card.setRealFlow(card.getRealFlow() + flow);
		return card.getRealFlow();
	}

}
