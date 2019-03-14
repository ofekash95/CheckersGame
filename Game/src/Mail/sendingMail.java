//package Mail;
//
//import java.util.Properties;
//import java.util.Scanner;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//public class sendingMail {
//	public static void toSend(){
//		Properties properties = new Properties();
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.host", "smtp.gmail.com");
//		properties.put("mail.smtp.port", "587");
//		
//		Scanner scanner = new Scanner(System.in);
//		
//		System.out.println("Username for Authentication : ");
//		final String username = scanner.nextLine();
//		
//		System.out.println("Password for Authentication : ");
//		final String password = scanner.nextLine();
//		
//		System.out.println("From email : ");
//		String fromEmailAddress = scanner.nextLine();
//		
//		System.out.println("To email : ");
//		String toEmailAddress = scanner.nextLine();
//		
//		System.out.println("Subject : ");
//		String subject = scanner.nextLine();
//		
//		System.out.println("Message : ");
//		String textMessage = scanner.nextLine();
//		
//		Session session = Session.getDefaultInstance(properties, new Authenticator() {
//			
//		})
//	}
//}
