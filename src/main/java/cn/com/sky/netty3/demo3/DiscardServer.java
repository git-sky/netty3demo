package cn.com.sky.netty3.demo3;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 
 * 在Netty里，Channel是通讯的载体，而ChannelHandler负责Channel中的逻辑处理。
 * 
 * 那么ChannelPipeline是什么呢？我觉得可以理解为ChannelHandler的容器：一个Channel包含一个ChannelPipeline，
 * 所有ChannelHandler都会注册到ChannelPipeline中，并按顺序组织起来。
 * 
 * 在Netty中，ChannelEvent是数据或者状态的载体，例如传输的数据对应MessageEvent，状态的改变对应ChannelStateEvent。
 * 当对Channel进行操作时，会产生一个ChannelEvent，并发送到ChannelPipeline。ChannelPipeline会选择一个ChannelHandler进行处理。
 * 这个ChannelHandler处理之后， 可能会产生新的ChannelEvent，并流转到下一个ChannelHandler。
 * 
 * 在Netty里，所有事件都来自ChannelEvent接口，这些事件涵盖监听端口、建立连接、读写数据等网络通讯的各个阶段。
 * 而事件的处理者就是ChannelHandler，这样，不但是业务逻辑，连网络通讯流程中底层的处理，都可以通过实现ChannelHandler来完成了。
 * 事实上，Netty内部的连接处理、协议编解码、超时等机制，都是通过handler完成的。
 * 
 * </pre>
 */
public class DiscardServer {
	public static void main(String[] args) throws Exception {
		
		ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
		
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("encode", new StringEncoder());
				pipeline.addLast("decode", new StringDecoder());
				pipeline.addLast("handler", new DiscardServerHandler());
				return pipeline;
			}
		});
		
		bootstrap.setOption("child.tcpNoDelay", true);//关闭Nagle算法
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(8080));
	}
}