package test;

import com.sun.webkit.dom.NodeFilterImpl;
import com.szqd.framework.util.BeanUtil;
import com.szqd.framework.util.DateUtils;
import com.szqd.project.common.model.push.PushMessageDB;
import com.szqd.project.common.service.PushMessageService;
import com.szqd.project.mobile.lock.model.HowLongUseEnum;
import com.szqd.project.mobile.lock.model.URLCategoryEntity;
import com.szqd.project.mobile.lock.service.AppAnalysisService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.BulletList;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Log4jConfigurer;



import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;


/**
 * Created by like on 5/7/15.
 */
public class Java8 {

    private static final Logger LOGGER = Logger.getLogger(Java8.class);

    public static void main(String[] args) throws Exception {
        String path = Thread.currentThread().getContextClassLoader().getResource("com/szqd/framework/config/log4j.properties").getPath();
        Log4jConfigurer.initLogging(path);
        ApplicationContext context = new ClassPathXmlApplicationContext("com/szqd/framework/config/applicationContext.xml");
        PushMessageService service = (PushMessageService)context.getBean("pushMessageService");

        Integer i = service.queryCategoryByAppName("QQ");
        System.out.println(i);

//        PushMessageDB push = new PushMessageDB();
//        push.setId(1);
//        push.setAppName("appName");
//        List<PushMessageDB> pushList = new ArrayList<>();
//        pushList.add(push);
//        service.testSave(push, "key2");
//        service.testGet("key2");
//        System.out.println();

//        LinkedHashMap<String,Object> map = new LinkedHashMap<>();
//        map.put("appName","appName");
//        map.put("id",null);
//        PushMessageDB push = null;//PushMessageDB.class.newInstance();
//
//        push = new BeanUtil<PushMessageDB>().convertOneMapListToOneBean(map,PushMessageDB.class);
////        BeanUtils.populate(push,map);
//        System.out.println(push);



//        AppAnalysisService appAnalysisService = (AppAnalysisService)context.getBean("analysisService");
//        Integer categoryID = appAnalysisService.queryCategoryByAppName("微信1");
//        System.out.println(categoryID);

//        URLCategoryEntity u = new URLCategoryEntity();
//        u.setCategoryID(1);
//        u.setCategoryName("cateOne");
//
//        URLCategoryEntity u4 = new URLCategoryEntity();
//        u4.setCategoryID(1);
//        u4.setCategoryName("cateFour");
//
//        URLCategoryEntity u2 = new URLCategoryEntity();
//        u2.setCategoryID(2);
//        u2.setCategoryName("cateTwo");
//
//        URLCategoryEntity u3 = new URLCategoryEntity();
//        u3.setCategoryID(2);
//        u3.setCategoryName("cateThree");
//
//        List<URLCategoryEntity> list = new ArrayList<>();
//        list.add(u);
//        list.add(u4);
//        list.add(u2);
//        list.add(u3);
//
//        System.out.println("@@@@::: " + list.stream().filter(uVar -> uVar.getCategoryID().equals(2)).findAny().get().getCategoryName());

//        Date d = new Date(1440345600000l);
//        String s = DateUtils.DateToString(d,"yyyy-MM-dd");
//        System.out.println(s);


    }

    public static void addAllCategory(List<URLCategoryEntity> list)
    {
        for(URLCategoryEntity uc : list)
        {
            for (int i = 1; i <= uc.getPageCount(); i++)
            {
                while (true)
                {
                    try
                    {
                        addCategoryToDb(uc,i);
                        break;
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        }
    }

    public static void addCategoryToDb(URLCategoryEntity uc,int page)throws Exception
    {

        String url = String.format(uc.getUrl(),page);

        String html = getUrlResult("GET",url,new HashMap<>());

        NodeFilter ulNodefileter = new NodeFilter() {
            @Override
            public boolean accept(Node node) {
                if (node instanceof LinkTag)
                {
                    LinkTag link = (LinkTag)node;
                    Node parentNode = link.getParent().getParent().getParent();
                    if (parentNode instanceof BulletList)
                    {
                        BulletList ul = (BulletList)parentNode;
                        String id = ul.getAttribute("id");

                        if ("iconList".equals(id))
                        {
                            return true;
                        }
                    }

                }
                return false;

            }
        };

        Parser parser = new Parser();
        parser.setResource(html);
        parser.setEncoding("UTF-8");
        NodeList nodeList = parser.parse(ulNodefileter);
        for (int i = 0; i < nodeList.size(); i++)
        {
            Node liVar = nodeList.elementAt(i);
            String innerText = liVar.toPlainTextString().trim();
            System.out.println(innerText);
        }
    }


    public static String getUrlResult(String method, String urlString, Map<String, String> paramMap)
    {
        try
        {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDefaultUseCaches(false);
            connection.setReadTimeout(20000);
            connection.setConnectTimeout(20000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();


            StringBuilder param = new StringBuilder();
            for (Map.Entry<String, String> en : paramMap.entrySet())
            {
                param.append("&").append(en.getKey() + "=");
                param.append(URLEncoder.encode(en.getValue()));
            }
            if (!param.toString().equals("")) {
                System.out.println(param);
                byte[] data = param.toString().getBytes();
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
                outputStream.close();
            }


            InputStream inputStream = connection.getInputStream();
            boolean isNotEnd = true;
            byte[] src = new byte[0];
            do
            {
                byte[] returnData = new byte[1024 * 1];
                int readByteCount = inputStream.read(returnData);
                isNotEnd = readByteCount != -1;
                if (isNotEnd)
                {
                    byte[] temp = new byte[readByteCount];
                    System.arraycopy(returnData,0,temp,0,readByteCount);
                    src = ArrayUtils.addAll(src,temp);
                }

            }
            while (isNotEnd);
            connection.disconnect();
            return new String(src,"UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("失败");
        }

    }


}
