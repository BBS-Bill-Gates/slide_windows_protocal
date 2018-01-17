package network;

/*
 * 实现分组传送
 * */


public class Group {
	private SendQueue sq;
	private int sendNumber;
	private Frame[] frameGroup = new Frame[5];
	public Group(SendQueue sq) {
		this.sq = sq;
		this.sendNumber = 0;
	}
	public void initFrame() {
		for(int i=0; i<5; i++)
		{
			this.frameGroup[i]=this.sq.remove();
		}
	}
	public void slideWindow() {
		initFrame();
		multiSend();
	}
	public void multiSend() {
		SupportedThread[] group = new SupportedThread[5];
		int number=0 ;				//success计数器
		int i=0;
		for(int j=0; j<5; j++) {
			group[j]=new SupportedThread(frameGroup[j]);
			group[j].run();
		}
		while(true) {
			if(group[i%5].returnSuccess()) {
				number++;
			} else {
				group[i%5].run();
				this.sendNumber++;
			}
			if(number!=0&&number%5==0) {
				break;
			}
			i++;
		}
		this.sendNumber = this.sendNumber+5;
	}
	public int getSendNumber() {
		return sendNumber;
	}
}
