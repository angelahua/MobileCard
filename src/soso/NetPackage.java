package soso;

public class NetPackage extends ServicePackage implements NetService{
	private int flow;
	public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }


	@Override
	public void showInfo() {
		System.out.println("网虫套餐信息："+getPrice()+"元的套餐，含"+(double)(flow / 1024.0) +"G国内流量");
		
	}
	
	@Override
	public int netPlay(int flow, MobileCard card){
		card.setRealFlow(card.getRealFlow() + flow);
		return card.getRealFlow();
	}

}
