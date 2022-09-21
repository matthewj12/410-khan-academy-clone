package send;
import java.io.File;
import java.io.FileInputStream;
import com.jcraft.jsch.*;
public class SendFile {
	
	public static void main (String[] args){
//		Server IP and Port
		String SFTPHOST = "127.0.0.1"; 
	    int SFTPPORT = 2222;
	    
//	    Server user & pass
	    String SFTPUSER = "osc";
	    String SFTPPASS = "osc";
	    
//	    Server working directory
	    String SFTPWORKINGDIR = "./lol";

	    Session session = null;
	    Channel channel = null;
	    ChannelSftp channelSftp = null;
	    System.out.println("preparing the host information for sftp.");
	    
	    
//	    Create session, sftp channel, then transfer file, then close channel, session
	    try {
	    	File f = new File("lol.txt");
	    	
	        JSch jsch = new JSch();
	        session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
	        session.setPassword(SFTPPASS);
	        java.util.Properties config = new java.util.Properties();
	        config.put("StrictHostKeyChecking", "no");
	        session.setConfig(config);
	        session.connect();
	        System.out.println("Host connected.");
	        channel = session.openChannel("sftp");
	        channel.connect();
	        
	        System.out.println("sftp channel opened and connected.");
	        channelSftp = (ChannelSftp) channel;

//	        change directory of server and put file
	        channelSftp.cd(SFTPWORKINGDIR);
	        channelSftp.put(new FileInputStream(f), f.getName());
	        System.out.println("File transfered successfully to host. ");
	        String url = channelSftp.pwd();
	        System.out.println("Directory " + url);
	        
	    } catch (Exception ex) {
	        System.out.println("Exception found while tranfer the response. " + ex);
	    } finally {
	        channelSftp.exit();
	        System.out.println("sftp Channel exited.");
	        channel.disconnect();
	        System.out.println("Channel disconnected.");
	        session.disconnect();
	        System.out.println("Host Session disconnected.");
	    }
	}
}
