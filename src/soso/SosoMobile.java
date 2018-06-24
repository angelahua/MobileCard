package soso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class SosoMobile {
	static Scanner scnr = new Scanner(System.in);
	public final static int SERVICE_PACKAGE_TALK=1;
    public final static int SERVICE_PACKAGE_NET=2;
    public final static int SERVICE_PACKAGE_SUPER=3;
    static Map<Integer, ServicePackage> serviceMap = new HashMap<Integer, ServicePackage>();

	public static void main (String[] args){
	
		TalkPackage talkPackage = new TalkPackage();
		talkPackage.setPrice(58);
		talkPackage.setSmsCount(30);
		talkPackage.setTalkTime(500);
		serviceMap.put(SERVICE_PACKAGE_TALK, talkPackage);
		
		NetPackage netPackage = new NetPackage();
		netPackage.setFlow(3 *1024);
		netPackage.setPrice(68);
		serviceMap.put(SERVICE_PACKAGE_NET, netPackage);
		
		SuperPackage superPackage = new SuperPackage();
		superPackage.setFlow(1 *1024);
		superPackage.setPrice(78);
		superPackage.setSmsCount(50);
		superPackage.setTalkTime(200);
		serviceMap.put(SERVICE_PACKAGE_SUPER, superPackage);
		
		MobileCard card1 = new MobileCard("15023658974","张晓梅","123456",serviceMap.get(SERVICE_PACKAGE_NET),20,50,0,0,2048);
		CardUtil.cards.put("15023658974", card1);
		MobileCard card2 = new MobileCard("13838386969","王东名","123456",serviceMap.get(SERVICE_PACKAGE_TALK),20,50,100,20,0);
	    CardUtil.cards.put("13838386969", card2);
	    showMenu1();
	}
	
	
	
	public static void showMenu1(){
		int userChoice = 0;
		do{
			System.out.println("***********欢迎使用嗖嗖移动大厅***********");
	        System.out.print("1.用户登录\t");
	        System.out.print("2.用户注册\t");
	        System.out.print("3.使用嗖嗖\t");
	        System.out.print("4.话费充值\t");
	        System.out.print("5.资费说明\t");
	        System.out.print("6.退出系统\n");

	        System.out.println("请选择：");
	        
	        userChoice = scnr.nextInt();
	        
	        switch(userChoice){   
	        case 1:
	        	login();
	        	break;        	
	        case 2:
	        	register();
	        	break;  	
	        case 3:
	        	useSoso();
	        	break;
	        case 4:
	        	addMoney();
	        	break;
	        case 5:
	        	break;
	        case 6:
	        	break;    
	        }
		
		}while (userChoice != 6);
        	
	}
	
	public static void showMenu2(String number){
		System.out.println("***********嗖嗖移动用户菜单***********");
	    System.out.println("1.本月账单查询");
	    System.out.println("2.套餐余量查询");
	    System.out.println("3.打印消费详单");
	    System.out.println("4.套餐变更");
	    System.out.println("5.办理退网");
	    System.out.println("请选择1-5的功能，其他键放回上一级");
	    
        int userChoice = scnr.nextInt();
        
        switch(userChoice){   
        case 1:
        	CardUtil.showAmountDetail(number);
        	break;        	
        case 2:
        	CardUtil.showRemainDetail(number);
        	break;  	
        case 3:
        	try {
				CardUtil.printConsumInfo(number);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	break;
        case 4:
        	changePack(number);
        	break;
        case 5:
        	CardUtil.delCard(number);
        	break;
        default :
        	showMenu1();
        	break;
	    }
	}
	
	public static void login(){
		System.out.print("请输入手机号： ");
		String userNum = scnr.next();
		while(!CardUtil.isExistCard(userNum)){
			System.out.println("号码不存在");
			userNum = scnr.next();
		}
		System.out.print("请输入密码： ");
		String pass = scnr.next();
		if (!CardUtil.isExistCard(userNum, pass)){
			System.out.println("密码不正确");
			login();
		}
		showMenu2(userNum);	
	}
	

	public static void register(){
		System.out.println("*****可选择的卡号*****");
		String[] numbers = CardUtil.getNewNumbers(9); 
		for (int i = 0; i < 9; i++){
			System.out.print((i + 1) + "." + numbers[i]);
			if((i + 1) % 3 == 0){
				System.out.println("");
			}else{
				System.out.print("\t");
			}
		}
		
		System.out.print("请选择卡号（1-9的数字）：");
		int choice = scnr.nextInt();
		System.out.println("");
		while(choice < 1 || choice > 9){
			System.out.println("请选择卡号（1-9的数字）：");
			choice = scnr.nextInt();
		}
		MobileCard myCard = new MobileCard();
		myCard.setCardNumber(numbers[choice - 1]);
		System.out.print("1.话痨套餐  2.网虫套餐  3.超人套餐  请选择套餐（输入序号） ：");
		int choice2 = scnr.nextInt();
		while(choice2 < 1 || choice2 > 3){
			System.out.println("请选择1-3的数字）：");
			choice2 = scnr.nextInt();
		}
		
		myCard.setServicePackage(serviceMap.get(choice2));
		System.out.println("");
		System.out.print("请输入姓名： ");
		String name = scnr.next();
		myCard.setUserName(name);
		System.out.println("");
		System.out.println("请输入密码： ");
		String passWord = scnr.next();
	    myCard.setPassWord(passWord);
	    System.out.println("");
	    System.out.println("请输入预存话费金额： ");
	    double userMoney = scnr.nextDouble();
	    while (userMoney < serviceMap.get(choice2).getPrice()){
	    	System.out.println("您预存的话费金额不足以支付本月套餐费用，请重新充值！");
	    	userMoney = scnr.nextDouble();
	    }
	    myCard.setMoney(userMoney - serviceMap.get(choice2).getPrice());
	    myCard.setConsumAmount(0);
	    CardUtil.addCard(myCard);
	    System.out.println("");
	    System.out.println("注册成功！ 卡号： " + myCard.getCardNumber() + " 用户名： "
	    		+ myCard.getUserName() + " 当前余额： " + myCard.getMoney() + " ");
	    serviceMap.get(choice2).showInfo();

	}
	
	public static void useSoso(){
		String number = "";
		do{
			System.out.println("请输入手机卡号：");
			number = scnr.next();
		}while (!CardUtil.isExistCard(number));
		CardUtil.useSoso(number);

	}
	
	public static void changePack(String number){
		System.out.println("******套餐变更******");
    	System.out.print("1.话痨套餐 2.网虫套餐 3.潮人套餐 请选择 （序号） ：");
    	int userNum = scnr.nextInt();
    	ServicePackage userPkg = serviceMap.get(userNum);
    	CardUtil.changingPack(number, userPkg);
	}
	
	public static void addMoney(){
		System.out.print("请输入充值卡号： ");
		String number = "";
		do{
			number = scnr.next();
		}while(!CardUtil.isExistCard(number));
		System.out.println("");
		System.out.print("请输入充值金额： ");
		double money = 0.0;
		do{
			money = scnr.nextDouble();
		}while(money < 50);
		
		CardUtil.chargeMoney(number, money);
		
	}

}
