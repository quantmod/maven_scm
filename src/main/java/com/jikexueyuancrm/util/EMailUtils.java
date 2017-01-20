package com.jikexueyuancrm.util;
 
import java.io.File;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
 
/**
 * 邮件发送工具类
 * 
 * @author yuanhai
 *
 */
public class EMailUtils {
	
	
	
	
	
	 /**
	 * @param from 发件邮箱
	 * @param username 发件用户名,必须是邮箱去除@后面部分的用户名，否则验证无法通过
	 * @param password  发件邮箱密码
	 * @param to     收件邮箱数组
	 * @param subject  邮件主题
	 * @param content   邮件内容
	 * @param attachFilePath   附件文件路径数组
	 * @param attachFileName  附件文件名字数组,若为null,文件名不变
	 */
	public static void sendMail(String from,final String username,final String password,String[] to ,String subject,String content,String attachFilePath[],String attachFileName[]) {
	        /**
	         * 1.获得一个Session对象. 2.创建一个代表邮件的对象Message. 3.发送邮件Transport
	         */
	        // 1.获得连接对象
	 
	        // 配置发送邮件的环境属性
	 
	        /*
	         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
	         * mail.user / mail.from
	         */
	        final Properties props = new Properties();
	        // 表示SMTP发送邮件，需要进行身份验证
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.host", "smtp.126.com");
	 
	        // 获取session，创建认证器，指定用户名和密码
	        Session session = Session.getInstance(props, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });
	 
	        // 2.创建邮件对象:
	        Message message = new MimeMessage(session);
	 
	        try {
	            message.setFrom(new InternetAddress(from));// 设置发件人
	            
	            InternetAddress[]  addresses=new InternetAddress[to.length];
	            
	            for( int i=0;i<to.length;i++){
	            	addresses[i]=new InternetAddress(to[i]);
	            }
	            
	            message.addRecipients(RecipientType.TO, addresses);// 设置多个收件人
	            message.addRecipient(RecipientType.CC, new InternetAddress("jethai@163.com"));// 设置抄送，类型为抄送
	            message.addRecipient(RecipientType.BCC, new InternetAddress("934033381@qq.com"));// 设置抄送，类型为密送
	 
	            // 设置标题
	            message.setSubject(subject);
	            
	            // 可以装载多个主体部件！可以把它当成是一个集合
	            MimeMultipart partList = new MimeMultipart();
	            message.setContent(partList);// 把邮件的内容设置为多部件的集合对象
	            // 创建一个部件
	            MimeBodyPart contentPart = new MimeBodyPart();
	            // 给部件指定内容
	            contentPart.setContent(content, "text/html;charset=utf-8");
	            // 部件添加到集合中
	            partList.addBodyPart(contentPart);
	            // 创建多个附件
	            
	        if(attachFilePath!=null){    
	          for(int j=0;j<attachFilePath.length;j++)  {
	            
	            MimeBodyPart attachPart = new MimeBodyPart();
	            // 为部件指定附件
	            attachPart.attachFile(new File(attachFilePath[j]));
	            // 指定附件文件的名字
	            // 使用MimeUtility.encodeText()对中文进行编码
	            
	            if(attachFileName!=null){
	          attachPart.setFileName(MimeUtility.encodeText(attachFileName[j]));
	            }
	            partList.addBodyPart(attachPart);
	          }    
	        }     
	            // 3.发送邮件:
	            Transport.send(message);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	 
	    }
	
	
	
	
	
	
	
    /**
     * 发送邮件的方法
     * 
     * @param to
     *            :收件人
     * @param code
     *            :激活码
     */
    public static void sendMail(String to, String code) {
        /**
         * 1.获得一个Session对象. 2.创建一个代表邮件的对象Message. 3.发送邮件Transport
         */
        // 1.获得连接对象
 
        // 配置发送邮件的环境属性
 
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        final Properties props = new Properties();
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.126.com");
 
        // 获取session，创建认证器，指定用户名和密码
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("jethai", "密码");
            }
        });
 
        // 2.创建邮件对象:
        Message message = new MimeMessage(session);
 
        try {
            message.setFrom(new InternetAddress("jethai@126.com"));// 设置发件人
            message.addRecipient(RecipientType.TO, new InternetAddress(to));// 设置收件人
            message.addRecipient(RecipientType.CC, new InternetAddress("jethai@163.com"));// 设置抄送，类型为抄送
            message.addRecipient(RecipientType.BCC, new InternetAddress("934033381@qq.com"));// 设置抄送，类型为密送
 
            // 设置标题
            message.setSubject("测试邮件");
            // 可以装载多个主体部件！可以把它当成是一个集合
            MimeMultipart partList = new MimeMultipart();
            message.setContent(partList);// 把邮件的内容设置为多部件的集合对象
            // 创建一个部件
            MimeBodyPart part1 = new MimeBodyPart();
            // 给部件指定内容
            part1.setContent("又是一封垃圾邮件", "text/html;charset=utf-8");
            // 部件添加到集合中
            partList.addBodyPart(part1);
            // 又创建一个部件
            MimeBodyPart part2 = new MimeBodyPart();
            // 为部件指定附件
            part2.attachFile(new File("c:/图片.jpg"));
            // 指定附件文件的名字
            // 使用MimeUtility.encodeText()对中文进行编码
            part2.setFileName(MimeUtility.encodeText("大美女.jpg"));
 
            partList.addBodyPart(part2);
 
            // 3.发送邮件:
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
 
    // 接受邮箱地址和激活码
    public static void main(String[] args) {
        sendMail("jethai@126.com", "123456");
    }
}