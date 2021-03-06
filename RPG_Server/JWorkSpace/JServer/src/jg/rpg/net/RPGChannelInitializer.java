package jg.rpg.net;

import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLException;

import org.apache.log4j.Logger;

import jg.rpg.config.GameConfig;
import jg.rpg.net.handlers.DataEnsureHandler;
import jg.rpg.net.handlers.LoginValidateHandler;
import jg.rpg.net.handlers.MsgDeserializerHandler;
import jg.rpg.net.handlers.MsgFilterHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class RPGChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private Logger logger = Logger.getLogger(getClass());
	private final SslContext sslCtx;
	static final boolean SSL = System.getProperty("ssl") != null;
	private ByteBuf delimiter;
	
	
	public RPGChannelInitializer() throws SSLException, CertificateException, UnsupportedEncodingException{
		
		byte[] bs = GameConfig.Delimiter.getBytes(GameConfig.DefEncoding);
		delimiter = Unpooled.buffer(bs.length);
		delimiter.writeBytes(bs);
		SelfSignedCertificate ssc = null;
		if (SSL) {
			ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
		} else {
			sslCtx = null;
		}
	}
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		  ChannelPipeline p = ch.pipeline();
          if (sslCtx != null) {
              p.addLast(sslCtx.newHandler(ch.alloc()));
          }
          logger.debug("register handler");
          p.addLast(
                 /* new StringEncoder(CharsetUtil.UTF_8),
                  new LineBasedFrameDecoder(8192),
                  new StringDecoder(CharsetUtil.UTF_8),
                  new ChunkedWriteHandler(),*/
         		// new LineBasedFrameDecoder(1024*1024*2),
        		  new DelimiterBasedFrameDecoder(GameConfig.Max_Frame_Length , delimiter),
        		  new MsgDeserializerHandler(),
        		  new MsgFilterHandler(),
        		  new LoginValidateHandler()
          );
		
	}

}
