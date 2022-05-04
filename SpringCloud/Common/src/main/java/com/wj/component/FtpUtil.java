package com.wj.component;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.TimeZone;

/**
 * @PackageName: com.example.utils
 * @ClassName: FtpUtils
 * @Description: FtpUtils 实体类
 * @Author: Winjay
 * @Date: 2021-07-17 17:22:13
 */

@Component
@PropertySource(value = "classpath:common-devmac.yml")
public class FtpUtil {

    @Value("${ftp.host}")
    private static String hostname;

    @Value("${ftp.username}")
    private static String username;

    @Value("${ftp.password}")
    private static String password;

    @Value("${ftp.port}")
    private static String port;

    @Value("${ftp.basepath}")
    private static String basepath;

    private static final String ENCODING = "UTF-8";

    public static Object getFtpClient(String username,String password,Integer port){
        //初始化FTP客户端
        FTPClient ftpClient = null;
        //初始化FTP配置
        FTPClientConfig ftpConfig = null;
        //初始化FTP返回的信号
        int replyCode;
        try {
            ftpClient = new FTPClient();
            //判断当前系统是否为linux系统，否则为windows系统
            if(!isLinux()){
                ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_NT);
            }else {
                ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
            }
            //设置时区
            ftpConfig.setServerTimeZoneId(TimeZone.getDefault().getID());
            //连接时加载ftp配置
            ftpClient.configure(ftpConfig);

            //链接FTP服务器
            if (port > 0) {
                ftpClient.connect("192.168.1.8", port);
            } else {
                ftpClient.connect("192.168.1.8");
            }

            // FTP服务器连接回答
            replyCode = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(replyCode)){
                ftpClient.disconnect();
                System.out.println("登录FTP服务失败" );
                return null;
            }
            boolean login = ftpClient.login(username,password);
            if(!login){
                System.out.println("登陆FTP服务器失败："+ ftpClient.getReplyString());
                return null;
            }

            ftpClient.enterLocalPassiveMode(); // 设置FTP传输协议 被动模式，否则上传失败并不报错
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); //设置传输类型为二进制类型，防止数据丢失
            ftpClient.setDataTimeout(60 * 1000); // 获取数据超时 一分钟
            ftpClient.setBufferSize(2 * 1024);  //设置缓冲区大小
            System.out.println("FTP服务器登陆成功！");
            //ftpClient.setControlEncoding(ENCODING); //设置服务器编码  utf-8
        }catch (SocketException e){
            e.printStackTrace();
            System.out.println("FTP的IP地址可能错误，请正确配置。" + e);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("FTP的端口错误,请正确配置。" + e);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(username + "登录FTP服务失败！" + e.getMessage());
        }

        return ftpClient;
    }

    public static Object logoutFtp(){
        FTPClient ftpClient = new FTPClient();
        int replyCode = ftpClient.getReplyCode();
        try {
            // 退出登录
            ftpClient.logout();
            // 断开连接
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  replyCode;
    }

    public Object upload(String fileName, InputStream stream){
        //初始化FTP客户端
        FTPClient ftp = new FTPClient();
        int replyCode;
        try {
            //连接FTP服务器
            ftp.connect(hostname);
            //登陆
            ftp.login(username,password);
            //获取FTP返回的信号
            replyCode = ftp.getReplyCode();
           // replyCode >= 200 && replyCode < 300 则不报错
            if (FTPReply.isPositiveCompletion(replyCode)) {
                throw new RuntimeException("fail load FtpClient");
            }
            //开启被动模式，否则文件上传不成功，也不报错
            ftp.enterLocalPassiveMode();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isLinux() {
        return !System.getProperty("os.name").toLowerCase().startsWith("windows");
    }

    public static void main(String[] args) {
        getFtpClient("winjay","123456",21);
        System.out.println(logoutFtp());
    }
}
