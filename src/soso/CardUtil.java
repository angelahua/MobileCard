package soso;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CardUtil {
	public final static String CONSUM_TYPE_NET = "上网";
	public final static String CONSUM_TYPE_TALK = "通话";
	public final static String CONSUM_TYPE_SMS = "短信";
	public final static int SERVICE_PACKAGE_TALK=1;
    public final static int SERVICE_PACKAGE_NET=2;
    public final static int SERVICE_PACKAGE_SUPER=3;
	static Map<String, MobileCard> cards = new HashMap <String, MobileCard>();
	//号码 - 该号码的所有消费记录
	static Map<String, List<ConsumInfo>> consumInfos = new HashMap <String, List<ConsumInfo>>();
	//套餐对应序号 - 可使用的场景
	static Map<Integer, List<Scene>> scenePkgs = new HashMap<Integer, List<Scene>>();
	static List <Scene> scenes = new ArrayList<Scene>();
	
	
	public static void initScene(){
		Scene scene1 = new Scene(CONSUM_TYPE_TALK, 200,"给女朋友打电话，打了200分钟");
        Scene scene2 = new Scene(CONSUM_TYPE_TALK, 1,"接到推销电话，通话1分钟");
        Scene scene3 = new Scene(CONSUM_TYPE_SMS, 50,"过年群发祝贺短信，发了50条短信");
        Scene scene4 = new Scene(CONSUM_TYPE_SMS, 1,"给老板发短息要求加工资，就发1条短信");
        Scene scene5 = new Scene(CONSUM_TYPE_NET, 500,"看了一部好电影，一下子使用500M");
        Scene scene6 = new Scene(CONSUM_TYPE_NET, 2048,"流量被偷，偷了我2G");
        scenes.add(scene1);
        scenes.add(scene2);
        scenes.add(scene3);
        scenes.add(scene4);
        scenes.add(scene5);
        scenes.add(scene6);
        
        //将各场景存储到相应套餐中
        List<Scene> netPkg = new ArrayList<Scene>();
        netPkg.add(scene5);
        netPkg.add(scene6);
        scenePkgs.put(SERVICE_PACKAGE_NET, netPkg);
        List<Scene> talkPkg = new ArrayList<Scene>();
        talkPkg.add(scene1);
        talkPkg.add(scene2);
        talkPkg.add(scene3);
        talkPkg.add(scene4);
        scenePkgs.put(SERVICE_PACKAGE_TALK, talkPkg);
        List<Scene> superPkg = new ArrayList<Scene>();
        superPkg.add(scene1);
        superPkg.add(scene2);
        superPkg.add(scene3);
        superPkg.add(scene4);
        superPkg.add(scene5);
        superPkg.add(scene6);
        scenePkgs.put(SERVICE_PACKAGE_SUPER, superPkg);
        
	}
	
	public static boolean isExistCard(String number, String passWord){
		if (cards.containsKey(number) && cards.get(number).getPassWord().equals(passWord)){
			return true;
		}
		return false;
	}
	
	public static boolean isExistCard(String number){
		if(cards.containsKey(number)){
			return true;
		}
		return false;
	}
	
	
	//生成随机九位数字
	public static String[] getNewNumbers(int count){
		String[] numbers = new String[count];       
        int counter = 0;
		while(counter < count){
            int random = (int)(Math.random()*100000000);
            if(random > 9999999){
                String number ="139"+random;
                if(!CardUtil.isExistCard(number)){
                    numbers[counter] = number;
                    counter++;
                }
            }
        }
        return numbers;	
	}
	
	public static void addCard (MobileCard card){
		cards.put(card.getCardNumber(), card);
		
	}
	
	public static void delCard(String number){
		cards.remove(number);
		
	}
	
	//三种套餐进行不同余量查询显示
	//可以使用辅助方法简化
	public static void showRemainDetail(String number){
		 System.out.println("**********套餐余量查询**********");
	     System.out.println("您的卡号："+number+",套餐内剩余：");
	     MobileCard card = cards.get(number);
	     ServicePackage serPkg= card.getServicePackage();
	     if (serPkg instanceof NetPackage){
	    	 NetPackage net = (NetPackage)serPkg;
	    	 int difference = net.getFlow() - card.getRealFlow();
	    	 if (difference <= 0){
	    		 difference = 0;
	    	 }
	    	 System.out.println("上网流量： " + (double)(difference / 1024.0) + "GB");  	 
	     }else if (serPkg instanceof TalkPackage){
	    	 TalkPackage talk = (TalkPackage)serPkg;
	    	 int smsDif = talk.getSmsCount() - card.getRealSMSCount();
	    	 int talkDif = talk.getTalkTime() - card.getRealTalkTime();
	    	 if(smsDif <= 0){
	    		 smsDif = 0;
	    	 }
	    	 if (talkDif <= 0){
	    		 talkDif = 0;
	    	 }
	    	 System.out.println("通话时长： " + talkDif + "分钟");
	    	 System.out.println("短信条数： " + smsDif + "条");
	    	 
	     }else if (serPkg instanceof SuperPackage){
	    	 SuperPackage sup = (SuperPackage) serPkg;
	    	 int smsDif = sup.getSmsCount() - card.getRealSMSCount();
	    	 int talkDif = sup.getTalkTime() - card.getRealTalkTime();
	    	 int flowDif = sup.getFlow() - card.getRealFlow();
	    	 if(smsDif <= 0){
	    		 smsDif = 0;
	    	 }
	    	 if (talkDif <= 0){
	    		 talkDif = 0;
	    	 }
	    	 if (flowDif <= 0){
	    		 flowDif = 0;
	    	 }
	    	 System.out.println("通话时长： " + talkDif + "分钟");
	    	 System.out.println("短信条数： " + smsDif + "条");	    	 
	    	 System.out.println("上网流量： " + (double)(flowDif/ 1024.0) + "GB");  	
	     }
	}
	
	public static void showAmountDetail(String number){
		MobileCard myCard = cards.get(number);
		System.out.println("您的卡号： " + number + "当月账单：");
		System.out.println("套餐资费： " + myCard.getServicePackage().getPrice() + "元");
		System.out.println("合计消费：" + myCard.getConsumAmount() + "元");
		System.out.println("账户余额： " + myCard.getMoney() + "元");

    }
    
	//添加消费记录
    public static void addConsumInfo(String number, ConsumInfo info){
    	if (consumInfos.containsKey(number)){
    		consumInfos.get(number).add(info);
    	}else{
    		List<ConsumInfo> newList = new ArrayList<ConsumInfo>();
    		newList.add(info);
    		consumInfos.put(number, newList);
    	}

    }
    
    //模拟使用业务
    public static void useSoso(String number){
    	initScene();
        MobileCard myCard = cards.get(number);
        ServicePackage pkg = myCard.getServicePackage();
        
        if (pkg instanceof NetPackage){
        	NetPackage netPkg = (NetPackage)pkg;
        	List<Scene> supportScenes = scenePkgs.get(SERVICE_PACKAGE_NET);
        	Scene scene = generateRandomScene(supportScenes, number);
        	useNet(myCard, netPkg.getFlow(), scene);
 
        }else if (pkg instanceof TalkPackage){
        	TalkPackage talkPkg = (TalkPackage)pkg;
        	List<Scene> supportScenes = scenePkgs.get(SERVICE_PACKAGE_TALK);
        	Scene scene = generateRandomScene(supportScenes, number);
       		if(scene.getType() == CONSUM_TYPE_SMS){
       			useSMS(myCard, talkPkg.getSmsCount(), scene);
       		}else if(scene.getType() == CONSUM_TYPE_TALK){
       			useTalk(myCard, talkPkg.getTalkTime(), scene);		
       			}
       		
        }else if (pkg instanceof SuperPackage){
        	SuperPackage superPkg = (SuperPackage)pkg;
        	List<Scene> supportScenes = scenePkgs.get(SERVICE_PACKAGE_TALK);
        	Scene scene = generateRandomScene(supportScenes, number);
        	
       		if(scene.getType() == CONSUM_TYPE_SMS){
       			useSMS(myCard, superPkg.getSmsCount(), scene);
       		}else if(scene.getType() == CONSUM_TYPE_TALK){
       			useTalk(myCard, superPkg.getTalkTime(), scene);	
        	}else if (scene.getType() == CONSUM_TYPE_NET){
        		useNet(myCard, superPkg.getFlow(), scene);
        	}
        } 
    }

    public static void showDesc(){

    }

    public static void changingPack(String number, ServicePackage userPkg){
    	MobileCard myCard = cards.get(number);
    	ServicePackage pkg = myCard.getServicePackage();
    	if(pkg == userPkg){
    		System.out.println("对不起，您已是改套餐用户，无需换餐！");
    	}else{
    		if (myCard.getMoney() < userPkg.getPrice()){
    			System.out.println("对不起，您的余额不足以支付新套餐本月资费，请充值后再办理更换套餐业务！");
    		}else{
    			myCard.setMoney(myCard.getMoney() - userPkg.getPrice());
    			myCard.setRealFlow(0);
    			myCard.setRealSMSCount(0);
    			myCard.setRealTalkTime(0);
    			myCard.setServicePackage(userPkg);
    			ConsumInfo info = new ConsumInfo();
    			info.setCardNumber(number);
    			info.setConsumData((int) userPkg.getPrice());
    			info.setType("更改套餐");
    			System.out.print("更换套餐成功！");
    		}
    	}
    	
    }
    
    //打印消费记录到一个文件中
    //并不能运行目前
    public static void printConsumInfo(String number) throws IOException{
    	//test
    	ConsumInfo consumInfo = new ConsumInfo();
        consumInfo.setCardNumber(number);
        consumInfo.setType(CONSUM_TYPE_NET);
        consumInfo.setConsumData(100);
        addConsumInfo(number, consumInfo);

        consumInfo = new ConsumInfo();
        consumInfo.setCardNumber(number);
        consumInfo.setType(CONSUM_TYPE_TALK);
        consumInfo.setConsumData(50);
        addConsumInfo(number, consumInfo);
        
        try {
			OutputStream out = new FileOutputStream("c://consum.txt");
			List<ConsumInfo> list = consumInfos.get(number);
			out.write(("*******"+number+"消费记录*******\n").getBytes());
			out.write(("序号\t类型\t数据（通话（时长）/上网（MB）/短信（条））\n").getBytes());
			int i = 1;
			for (ConsumInfo oneMsg: list){
				out.write((i+".\t"+oneMsg.getType()+"\t"+oneMsg.getConsumData()+"\n").getBytes());
				i++;
			}
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    public static void chargeMoney(String number, double money){
    	MobileCard myCard = cards.get(number);
    	myCard.setMoney(myCard.getMoney() + money);
    	System.out.println("充值成功， 当前花费余额为" + myCard.getMoney() + "元。");
    }
    
    //辅助方法
    public static void useNet(MobileCard myCard, int getFlow, Scene scene){
    	int curFlow = getFlow - myCard.getRealFlow();
		myCard.setRealFlow(myCard.getRealFlow() + scene.getData());
		if (curFlow < scene.getData()){
			int overFlow = scene.getData() - curFlow;
			double overMoney = overFlow *0.1;
			if (myCard.getMoney() < overMoney){
				System.out.println("余额不够，请充值！");
			}else{
				System.out.println("套餐内流量已使用完毕！");
				myCard.setConsumAmount(myCard.getConsumAmount() + overMoney);
				myCard.setMoney(myCard.getMoney() - overMoney);
			} } 	
    }
		
    public static void useTalk(MobileCard myCard, int getTalk, Scene scene){
    	int curTalk = getTalk - myCard.getRealTalkTime();
		myCard.setRealTalkTime(myCard.getRealTalkTime() + scene.getData());
		if(curTalk < scene.getData()){
			int overTalk = scene.getData() - curTalk;
			double overTalkMoney = overTalk *0.2;
			if (myCard.getMoney() <overTalkMoney){
				System.out.println("余额不够，请充值！");
			}else{
				System.out.println("套餐内通话已使用完毕！");
				myCard.setConsumAmount(myCard.getConsumAmount() + overTalkMoney);
				myCard.setMoney(myCard.getMoney() - overTalkMoney);
			}	
       }
    }
    
    public static void useSMS(MobileCard myCard, int getSMS, Scene scene){
    	int curSMS = getSMS - myCard.getRealSMSCount();
		myCard.setRealSMSCount(myCard.getRealSMSCount() + scene.getData());
		if(curSMS < scene.getData()){
			int overSMS = scene.getData() - curSMS;
			double overSMSMoney = overSMS *0.1;
			if (myCard.getMoney() <overSMSMoney){
				System.out.println("余额不够，请充值！");
			}else{
				System.out.println("套餐内短信已使用完毕！");
				myCard.setConsumAmount(myCard.getConsumAmount() + overSMSMoney);
				myCard.setMoney(myCard.getMoney() - overSMSMoney);
			}
		}
    }
    
    //自动生成符合条件的场景并生成消费记录
    public static Scene generateRandomScene(List<Scene>supportScenes, String number){
    	Scene scene = null;
    	do{
    		Random r = new Random();
            int len = scenes.size();
            int n = r.nextInt(len);
            scene = scenes.get(n);
    	}while(!supportScenes.contains(scene));
    	
    	System.out.println(scene.getDiscription());
    	ConsumInfo newInfo = new ConsumInfo();
    	newInfo.setCardNumber(number);
   		newInfo.setConsumData(scene.getData());
   		newInfo.setType(scene.getType());
   		addConsumInfo(number, newInfo);
   		System.out.println("成功添加消费信息");
		return scene;
    }

	
	
	
	

}
