package cz.tul.config;

import cz.tul.entities.*;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Marek on 24.08.2016.
 */
@Configuration
@ComponentScan("cz.tul")
@EnableTransactionManagement
public class ApplicationConfig {
    @Bean(destroyMethod = "close")
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://10.22.22.16:3306/computervision?createDatabaseIfNotExist=true&useSSL=false&max_allowed_packet=33554432");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        return dataSource;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.ddl-auto", "create-drop");
        properties.put("hibernate.cascade", "save-update");

        return properties;
    }

    @Autowired
    @Bean
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addProperties(getHibernateProperties());
        //Sem se musí nasetovat všechny třídy oanotovane @Entity
        sessionBuilder.addAnnotatedClasses(Attribute.class);
        sessionBuilder.addAnnotatedClasses(Chain.class);
        sessionBuilder.addAnnotatedClasses(Method.class);
        sessionBuilder.addAnnotatedClasses(Part.class);
        sessionBuilder.addAnnotatedClasses(PartAttributeValue.class);
        sessionBuilder.addAnnotatedClasses(MethodAttributes.class);
        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean
    public HibernateTransactionManager getTransactionManager(
            SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(
                sessionFactory);

        return transactionManager;
    }
}
