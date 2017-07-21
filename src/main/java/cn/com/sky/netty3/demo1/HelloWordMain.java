package cn.com.sky.netty3.demo1;

/**
 * Netty 初步之hello word的client入口
 * 
 * 
 */
public class HelloWordMain {
	public static void main(String[] args) {
		ClientThread r = new ClientThread();
		Thread t = new Thread(r);
		t.setName("client thread");
		t.start();

//		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			r.sendMsg();
//		}

	}
}
