package com.zengrui.utils;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Created by Zeng Rui on 2018/3/8.
 */
@Component("ftpUtils")
public class FtpUtils {
    //从ftp.properties中读取
    @Value("${host}")
    private String host;    //ftp服务器ip
    @Value("${port}")
    private int port;        //ftp服务器端口
    @Value("${user}")
    private String username;//用户名
    @Value("${password}")
    private String password;//密码
    @Value("${basePath}")
    private String basePath;//存放文件的基本路径

    /**
     * 无参构造方法 默认读取ftp.properties的配置文件
     */
   /* public FtpUtils() {

        Properties properties = new Properties();
        try {
            //使用system.getProperty("user.dir")查看io的默认基础路径
            File file = new File("src/main/resources/ftp.properties");
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            host = properties.getProperty("ftp.host");
            port = Integer.parseInt(properties.getProperty("ftp.port"));
            username = properties.getProperty("ftp.username");
            password = properties.getProperty("ftp.password");
            basePath = properties.getProperty("ftp.basePath");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


    /**
     * @param path     上传文件存放在服务器的路径
     * @param filename 上传文件名
     * @param input    输入流
     * @return
     */

    public boolean uploadFile(String path, String filename, InputStream input) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(host, port);
            ftp.login(username, password);
            //设置文件编码格式
            ftp.setControlEncoding("UTF-8");
            //ftp通信有两种模式
            //PORT(主动模式)客户端开通一个新端口(>1024)并通过这个端口发送命令或传输数据,期间服务端只使用他开通的一个端口，例如21
            //PASV(被动模式)客户端向服务端发送一个PASV命令，服务端开启一个新端口(>1024),并使用这个端口与客户端的21端口传输数据
            //由于客户端不可控，防火墙等原因，所以需要由服务端开启端口，需要设置被动模式
            ftp.enterLocalPassiveMode();
            //设置传输方式为流方式
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            //获取状态码，判断是否连接成功
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                throw new RuntimeException("FTP服务器拒绝连接");
            }
            //转到上传文件的根目录
            if (!ftp.changeWorkingDirectory(basePath)) {
                throw new RuntimeException("根目录不存在，需要创建");
            }

            //判断是否存在目录
            if (!ftp.changeWorkingDirectory(path)) {
                String[] dirs = path.split("/");
                //创建目录
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    //判断是否存在目录
                    if (!ftp.changeWorkingDirectory(dir)) {
                        //不存在则创建
                        if (!ftp.makeDirectory(dir)) {
                            throw new RuntimeException("子目录创建失败");
                        }
                        //进入新创建的目录
                        ftp.changeWorkingDirectory(dir);
                    }
                }
            }

                //设置上传文件的类型为二进制类型
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                //上传文件
                if (!ftp.storeFile(filename, input)) {
                    return false;
                }
                input.close();
                ftp.logout();
                return true;



        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    /**
     * @param filename  文件名，注意！此处文件名为加路径文件名，如：/2015/06/04/aa.jpg
     * @param localPath 存放到本地第地址
     * @return
     */
    public boolean downloadFile(String filename, String localPath) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(host, port);
            ftp.login(username, password);
            //设置文件编码格式
            ftp.setControlEncoding("UTF-8");
            //ftp通信有两种模式
            //PORT(主动模式)客户端开通一个新端口(>1024)并通过这个端口发送命令或传输数据,期间服务端只使用他开通的一个端口，例如21
            //PASV(被动模式)客户端向服务端发送一个PASV命令，服务端开启一个新端口(>1024),并使用这个端口与客户端的21端口传输数据
            //由于客户端不可控，防火墙等原因，所以需要由服务端开启端口，需要设置被动模式
            ftp.enterLocalPassiveMode();
            //设置传输方式为流方式
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            //获取状态码，判断是否连接成功
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                throw new RuntimeException("FTP服务器拒绝连接");
            }

            int index = filename.lastIndexOf("/");
            //获取文件的路径
            String path = filename.substring(0, index);
            //获取文件名
            String name = filename.substring(index + 1);
            //判断是否存在目录
            if (!ftp.changeWorkingDirectory(basePath + path)) {
                throw new RuntimeException("文件路径不存在：" + basePath + path);
            }
            //获取该目录所有文件
            FTPFile[] files = ftp.listFiles();
            for (FTPFile file : files) {
                //判断是否有目标文件
                //System.out.println("文件名"+file.getName()+"---"+name);
                if (file.getName().equals(name)) {
                    //System.out.println("找到文件");
                    //如果找到，将目标文件复制到本地
                    File localFile = new File(localPath + "/" + file.getName());
                    OutputStream out = new FileOutputStream(localFile);
                    ftp.retrieveFile(file.getName(), out);
                    out.close();
                }
            }
            ftp.logout();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    /**
     * 获取当前路径下的文件数量
     *
     * @param path 路径
     * @return 文件数量
     */
    public int getFileNum(String path) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(host, port);
            ftp.login(username, password);
            ftp.setControlEncoding("UTF-8");
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                throw new RuntimeException("FTP服务器拒绝连接");
            }
            //cd path
            try {
                ftp.changeWorkingDirectory(basePath+"/"+path);
            }catch (IOException e){
                throw new RuntimeException("更改目录失败");
            }
            //查看文件数量
            //String[]reply =ftp.doCommandAsStrings("ls -l|grep \"^-\"|wc -l",null);ftp不能执行该命令
            String[] reply = ftp.listNames();
            int num = 0;
            if(reply!=null){
                num = reply.length;
            }
            return num;

        } catch (Exception e) {
            throw new RuntimeException("连接断开");
        }finally {
            if(ftp.isConnected()){
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    /**
     *获取当前目录下的文件夹list
     * @param path
     * @return
     */
    public String[] getDirList(String path) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(host, port);
            ftp.login(username, password);
            ftp.setControlEncoding("UTF-8");
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                throw new RuntimeException("FTP服务器拒绝连接");
            }
            //cd path
            try {
                ftp.changeWorkingDirectory(basePath+"/"+path);
            }catch (IOException e){
                throw new RuntimeException("更改目录失败");
            }

            String[] reply = ftp.listNames();

            return reply;

        } catch (Exception e) {
            throw new RuntimeException("连接断开");
        }finally {
            if(ftp.isConnected()){
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    /**
     * @param filename 要删除的文件路径+文件名 如：/imgs/2015/07/13/aa.jpg
     * @return
     */
    public boolean deleteFiles(String filename) {
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(host, port);
            if (ftp.login(username, password)) {
                //被动模式
                ftp.enterLocalPassiveMode();
                //更改目录
                ftp.changeWorkingDirectory(basePath);
                if (ftp.deleteFile(filename)) {
                    return true;
                } else
                    return false;

            } else {
                //登录失败
                throw new RuntimeException("用户名或密码错误");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ftp.isConnected())
                    ftp.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public String toString() {
        return "FtpUtils [host=" + host + ", port=" + port + ", username=" + username + ", password=" + password
                + ", basePath=" + basePath + "]";
    }


}