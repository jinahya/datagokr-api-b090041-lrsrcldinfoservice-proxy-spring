package com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.context;

import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.domain.LunCalInfo;
import com.github.jinahya.datagokr.api.b090041_.lrsrcldinfoservice.proxy.data.jpa.repository.LunCalInfoRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = {
                LunCalInfoRepository.class
        }
//        ,
//        basePackages = {
//                "com.github.jinahya.datagokr.api.b090041.lrsrcldinfoservice.proxy.data.jpa.repository"
//        }
        )
@EntityScan(
        basePackageClasses = {
                LunCalInfo.class
        }
//        ,
//        basePackages = {
//        "com.github.jinahya.datagokr.api.b090041.lrsrcldinfoservice.proxy.data.jpa.domain"
//        }
        )
@Configuration
public class DataJpaConfiguration {

//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource) {
//        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan("com.acme.domain");
//        factory.setDataSource(dataSource);
//        return factory;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
//        final JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory);
//        return transactionManager;
//    }
}
