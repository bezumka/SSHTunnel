package com.example.SSHConnector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Created by Tarasov on 07.12.2016.
 */

public class SSHTunnel {

    private static String SFTPHOST = "192.168.2.211";
    private static int SFTPPORT = 22;
    private static String SFTPUSER = "ubuntu";
    private static String privateKey = "C:\\JOB\\Dealing\\key\\test.pem";

    private JSch jSch = new JSch();
    private Session session = null;
    private Channel channel = null;

    public SSHTunnel() {
        try {
            jSch.addIdentity(privateKey);
            System.out.println("Private Key Added.");
            session = jSch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            System.out.println("session created.");

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            System.out.println("shell channel connected....");
        } catch (JSchException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }
}
