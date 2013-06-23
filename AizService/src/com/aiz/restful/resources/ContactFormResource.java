package com.aiz.restful.resources;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.representation.Form;
@Path("/send")
public class ContactFormResource {
 
 @GET
 @Produces("text/plain")
 @Path("/test") 
 public String getTest() {
        return "Hello World!";
    }
 
 @POST
// @Consumes("application/x-www-form-urlencoded")
 @Path("/{appId}")
 public Response sendEmail(@PathParam("appId") String appId, Form form){
	 String email = "<html><header>Contact Form</header><body>" +
	 		"<div>Name:"+form.getFirst("username")+"</br></div>" +
	 		"<div>Company Name:"+form.getFirst("compname")+"</br></div>" +
	 		"<div>Address:"+form.getFirst("addr")+"</br></div>" +
	 		"<div>Email:"+form.getFirst("email")+"</br></div>" +
	 		"<div>Phone:"+form.getFirst("phone")+"</br></div>" +
	 		"<div>Message:"+form.getFirst("msg")+"</br></div></body>" +
	 		"<footer><div>Copyright © 2013 Anand Info Zone India Pvt. Ltd., All rights reserved.</br></div>Our mailing address is:</br>umaoffset@gmail.com</footer></html>";
	 form.getFirst("username");
	 Properties props = new Properties();
     Session session = Session.getDefaultInstance(props, null);

     //String msgBody = "<b>This is a test</b><p>test</p>";
     URI redirectUrl = null;
	try {
		if(form.getFirst("callback") != null || "".equals(form.getFirst("callback")))
			redirectUrl = new URI(form.getFirst("callback"));
	} catch (URISyntaxException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

     try {
         Message msg = new MimeMessage(session);
         msg.setFrom(new InternetAddress("k.harish89@gmail.com", "Contact Form Admin"));
         msg.addRecipient(Message.RecipientType.TO,
                          new InternetAddress("k.harish89@gmail.com"));
         msg.setSubject("Test email from app engine");
         Multipart mp = new MimeMultipart();
         MimeBodyPart htmlPart = new MimeBodyPart();
         htmlPart.setContent(email, "text/html");
         mp.addBodyPart(htmlPart);
         msg.setContent(mp);
         Transport.send(msg);
         
 
     } catch (AddressException e) {
    	 e.printStackTrace();
    	 //return e.getMessage();
     } catch (MessagingException e) {
    	 e.printStackTrace();
    	 //return e.getMessage();
     } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		//return e.getMessage();
	}
	return Response.seeOther(redirectUrl).build();
 }
}