package com.amr.denia;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Primary Spring configuration
 * @author amr
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {

	@Value("${dbUser}")
	private String dbUser;

	@Value("${dbPassword}")
	private String dbPassword;
	
	public static final String BASE = "/denia/";
	public static final String PAGES = "resources/pages/";
	public static final String ENTITIES = "com.amr.denia.domain.entity"; 
	public static final String REPOSITORIES = "com.amr.denia.domain.repository"; 

	/**
     * SpringBootServletInitializer implementation.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder app) {
        return app.sources(Application.class);
    }
    
	@Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
	}
	
    /**
     * UTF-8 Encoding Filter
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CharacterEncodingFilter characterEncodingFilter() {
    	CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    /**
     * Hibernate configuration.
     */
    Properties hibernateProperties() {
        return new Properties() {
			private static final long serialVersionUID = 1L;

			/**
			 * Initialization block:
			 * Set hibernate mode to 'create' so that the tables are created when the application runs.
			 * The database must be created previously:
			 *    MySql: "CREATE DATABASE " + APPNAME + " CHARACTER SET utf8 COLLATE utf8_general_ci;"
			 */
            {
                setProperty("hibernate.hbm2ddl.auto", "validate");  // validate | update | create
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            }
        };
    }

    /**
     * Container-managed Entity Manager Factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setPackagesToScan(ENTITIES);
        factory.setJpaProperties(hibernateProperties());

        factory.setDataSource(this.dataSource());

        return factory;
    }
    
	/**
     * Datasource.
     */
    @Bean
    public DriverManagerDataSource dataSource() {

        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/denia?useUnicode=yes&characterEncoding=UTF8");
        driverManagerDataSource.setUsername(dbUser);
        driverManagerDataSource.setPassword(dbPassword);

        return driverManagerDataSource;
    }
 }
