package cn.com.sky.netty3.demo1;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * client和server接收消息共用的handler 由于两个都是继承自SimpleChannelUpstreamHandler,所以就写在一起了。
 * 
 * MessageEvent就是一个事件。这个事件携带了一些信息，例如这里e.getMessage()就是消息的内容，而Handler则描述了处理这种事件的方式。一旦某个事件触发
 * ，相应的Handler则会被调用，并进行处理。这种事件机制在UI编程里广泛应用，而Netty则将其应用到了网络编程领域。s
 * 
 * @author Ransom
 * 
 */
public class Handler extends SimpleChannelUpstreamHandler {
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		System.out.println("recive message,message content:" + e.getMessage());

		// ChannelBuffer buf = (ChannelBuffer) e.getMessage();
		// while (buf.readable()) {
		// System.out.println((char) buf.readByte());
		// }
		e.getChannel().write(e.getMessage());

	}

	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		System.err.println("Client has a error,Error cause:" + e.getCause());
		e.getChannel().close();
	}
}
