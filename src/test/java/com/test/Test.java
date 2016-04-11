package test.java.com.test;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Test {

    public static void main(String[] args) {

        byte[] bytes = args[0].getBytes();
        Long f = new Date().getTime();

        File newFile = new File("E:/comments/" + f + ".jpg");

        try {

            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
            System.out.println("newFile :" + newFile);
            bufferedOutputStream.write(bytes);
            bufferedOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("return file :" + newFile.getName());
        String s = "comments/" + newFile.getName();

        System.out.println(s.contains("/"));

    }

}


/*database.driverClassName=com.mysql.jdbc.Driver
database.url=jdbc:mysql://feedbacktool.czynv8hdsnrr.us-east-1.rds.amazonaws.com:3306/FeedBackTool
database.username=root
database.password=green123$
hibernet.createtable=update
hibernate.dialect=org.hibernate.dialect.MySQLDialect
hibernate.show_sql=true  
hibernate.hbm2ddl.auto=update*/