import com.zengrui.manager.dao.ItemMapper;
import com.zengrui.manager.model.Item;
import com.zengrui.manager.service.interfaces.ImageService;
import com.zengrui.manager.service.interfaces.ItemCateService;
import com.zengrui.utils.FtpUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;


/**
 * Created by Zeng Rui on 2018/2/24.
 */
public class Test {


    @Before
    public void init() {


    }



    public void test1() {


        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-dao.xml");
        ItemMapper itemMapper = (ItemMapper) ctx.getBean("itemMapper");
        Item item = itemMapper.selectByPrimaryKey(new Long(536563));
        System.out.println(item.getNum());


    }


    public void test2() {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-service.xml", "/spring/spring-dao.xml");
        ctx.getBean("itemService");


    }


    public void test3() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-service.xml", "/spring/spring-dao.xml");
        ItemCateService service = (ItemCateService) ctx.getBean("itemCateServiceImp");
        //System.out.println(service.getItemCategory(0).get(2).isParent());


    }

    /**
     * ftp文件上传
     *
     * @throws Exception
     */

    public void test4() throws Exception {
        FileInputStream inputStream = new FileInputStream(new File("C:\\Users\\Zeng Rui\\Desktop\\test.jpg"));
        //创建对象
        FTPClient ftpClient = new FTPClient();
        //创建FTP连接
        ftpClient.connect("118.24.17.130", 21);
        //登录FTP服务器
        ftpClient.login("ftpuser", "zengruiR0112");
        //上传文件
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.changeWorkingDirectory("/home/ftpuser/ww/pic/test");
        int reply =  ftpClient.list("/home/ftpuser/ww/pic/test");
        System.out.println(reply);
        //ftpClient.storeFile("test1.jpg",inputStream);
        //关闭连接
        ftpClient.logout();
        ftpClient.disconnect();


    }


    public void test5() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-dao.xml", "/spring/spring-service.xml");
        //System.out.println("ls -l|grep \"^-\"|wc -l");

        FtpUtils ftpUtils = (FtpUtils) ctx.getBean("ftpUtils");
        //System.out.println(ftpUtils.getBasePath());
        System.out.println(ftpUtils.getFileNum(null));


    }

    public void  test6(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/spring/spring-dao.xml", "/spring/spring-service.xml");
        ImageService service = (ImageService) ctx.getBean("imageServiceImp");
        service.uploadImg(null);


    }


}
