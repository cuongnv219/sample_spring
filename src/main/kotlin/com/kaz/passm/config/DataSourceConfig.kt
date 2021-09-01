//package com.kaz.passm.config
//
//import org.springframework.boot.jdbc.DataSourceBuilder
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import javax.sql.DataSource
//
//@Configuration
//class DataSourceConfig {
//    @Bean
//    fun getDataSource(): DataSource {
//        val dataSourceBuilder = DataSourceBuilder.create()
//        dataSourceBuilder.driverClassName("org.mariadb.jdbc.Driver")
//        dataSourceBuilder.url("jdbc:mariadb://localhost:3306/passm?autoReconnect=false&useSSL=false&useUnicode=yes&characterEncoding=UTF-8")
//        dataSourceBuilder.username("root")
//        dataSourceBuilder.password("123456a@")
//        return dataSourceBuilder.build()
//    }
//}
