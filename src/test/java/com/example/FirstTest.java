package com.example;

import com.example.SSHConnector.SSHTunnel;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import com.jcraft.jsch.ChannelSftp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Created by Tarasov on 07.12.2016.
 */

public class FirstTest {

    @Test
    public void getNameFromFile() throws JSONException, IOException {
        String remoteFile = "/opt/cosmos/etc/ipf/scs.ipf";
        SSHTunnel sshTunnel = new SSHTunnel();
        ChannelSftp sftpChannel = null;
        try {
            sftpChannel = (ChannelSftp) sshTunnel.getSession().openChannel("sftp");
        } catch (JSchException e) {
            e.printStackTrace();
        }
        try {
            sftpChannel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }

        InputStream out = null;
        try {
            out = sftpChannel.get(remoteFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(out));
            String line;
            while ((line = br.readLine()) != null)
                if (line.contains("Yara")){
                    System.out.println(line);
                    Assert.assertNotNull(line);
                }
            br.close();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}
