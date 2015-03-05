package com.hts.web.common.util.email;

import java.util.*;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import sun.misc.BASE64Encoder;

/**
 * 邮件发送实现类
 * @author zxx
 *
 */
public class SendImplement {
	//定义收件人、cc(抄送)、bcc(密抄)、发送人、主题等
		String to = "", cc = "", bcc = "", from = "", host = "", filename = "",
				messagetext = "", subject = "";
		boolean htmlformat = false;
		boolean debug = false;
		String user, password;
		//保存发送的正文
		String Txtmsg = null;
		//邮件服务器是否要认证
		private boolean authentication = true;

		private Multipart mp = new MimeMultipart();

		public SendImplement() {
		}

		public SendImplement(String to, String cc, String bcc, String from,
				String smtpServer, String subject, String msg, String user,
				String password, boolean flgHtml) {
			//定义收件人、cc、bcc、发送人、主题等
			this.to = to;
			this.cc = cc;
			this.bcc = bcc;
			this.from = from;
			this.host = smtpServer;
			this.subject = subject;
			this.Txtmsg = msg;
			this.user = user;
			this.password = password;
			this.htmlformat = flgHtml;
		}

		public SendImplement(String to, String cc, String bcc, String from,
				String smtpServer, String subject, String msg, String fileName,
				String user, String password, boolean flgHtml) {
			//定义收件人、cc、bcc、发送人、主题等
			this.to = to;
			this.cc = cc;
			this.bcc = bcc;
			this.from = from;
			this.host = smtpServer;
			this.subject = subject;
			this.Txtmsg = msg;
			this.user = user;
			this.password = password;
			this.htmlformat = flgHtml;
			this.filename = fileName;
		}

		//收集用户与密码
		public void setUserPassword(String user, String password) {
			this.user = user;
			this.password = password;
		}

		//收集邮件正文
		public void setMessage(String msg) {
			this.Txtmsg = msg;
		}

		//设置调试标志
		public void setDebug(boolean debug) {
			this.debug = debug;
		}

		public void setAuthentication(boolean authentication) {
			this.authentication = authentication;
		}

		public boolean getAuthentication() {
			return authentication;
		}

		//邮件发送函数
		public boolean sendmail() throws Exception {
			boolean returnValue = true;
			String mailer = "Send Mail From ZhiTu";
			Session session = null;
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", host);

			PopupAuthenticator authenticator = new PopupAuthenticator(user,
					password);

			if (authentication) {
				props.put("mail.smtp.auth", "true");
				session = Session.getInstance(props, authenticator);
			} else {
				props.put("mail.smtp.auth", "false");
				session = Session.getDefaultInstance(props, null);
			}
			session.setDebug(debug);
			try {
				//创建一个消息,并初始化该消息的各项元素
				InternetAddress[] toAddrs = null, ccAddrs = null, bccAddrs = null;
				MimeMessage msg = new MimeMessage(session);
				//设置发信人
				msg.setFrom(new InternetAddress(from));
				//设置收信人
				toAddrs = InternetAddress.parse(to, false);
				msg.setRecipients(Message.RecipientType.TO, toAddrs);
				//设置抄送
				if (cc.length() > 5) {
					ccAddrs = InternetAddress.parse(cc, false);
					msg.setRecipients(Message.RecipientType.CC, ccAddrs);
				}
				//设置暗送
				if (bcc.length() > 5) {
					bccAddrs = InternetAddress.parse(bcc, false);
					msg.setRecipients(Message.RecipientType.BCC, bccAddrs);
				}
				//设置邮主题
				msg.setSubject(subject);
				msg.setHeader("X-Mailer", mailer);
				BodyPart bp = new MimeBodyPart();
				//设置邮件内容
				if (htmlformat) {
					//msg.setContent(Txtmsg, "text/html;charset=GBK");
					bp.setContent(Txtmsg, "text/html;charset=GBK");
				} else {
					//msg.setContent(Txtmsg, "text/plain;charset=GBK");
					bp.setContent(Txtmsg, "text/html;charset=GBK");
				}
				mp.addBodyPart(bp);
				this.addFileAffix(filename);
				msg.setContent(mp);
				// 设置邮件头的发送日期
				msg.setSentDate(new Date());
				// 发送邮件
				Transport.send(msg);
			} catch (Exception e) {
				returnValue = false;
				e.printStackTrace();
				throw new Exception(e);
			}
			return returnValue;
		}

		/**
		 *  添加附件
		 * @param filename  多个附件之间用分号分隔
		 * @return
		 */
		public boolean addFileAffix(String filename) {
			if (filename.equals("") || filename == null) {
				return false;
			}
			String file[];
			file = filename.split(";");
			try {
				for (int i = 0; i < file.length; i++) {
					BodyPart bp = new MimeBodyPart();
					FileDataSource fileds = new FileDataSource(file[i]);
					bp.setDataHandler(new DataHandler(fileds));
					
					//通过下面的Base64编码的转换可以保证中文附件标题名在发送时不会变成乱码
					BASE64Encoder enc = new BASE64Encoder();
					String fileName = "=?GBK?B?" + enc.encode(fileds.getName().getBytes()) + "?=";
					bp.setFileName(fileName);
					mp.addBodyPart(bp);
				}
				return true;
			} catch (Exception e) {
				return false;
			}
		}
}
