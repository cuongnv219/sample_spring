package com.kaz.passm.config

import org.glassfish.jersey.server.ResourceConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.ws.rs.ApplicationPath
import javax.ws.rs.Path

@Configuration
@Component
@ApplicationPath("/api")
class JersyConfig : ResourceConfig() {

//
//    init {
//        this.packages("com.kaz.passm.resource", "com.kaz.passm.exception", "com.kaz.passm.service")
//    }

    companion object {
        private val logger = LoggerFactory.getLogger(JersyConfig::class.java)
    }

    @Autowired
    lateinit var appCtx: ApplicationContext

    @PostConstruct
    fun setup() {
        logger.info("Rest classes found:");
        val beans: Map<String, Any> = appCtx.getBeansWithAnnotation(Path::class.java)
        for (o in beans.values) {
            logger.info("Rest classes -> " + o.javaClass.name)
            register(o)
        }
    }
}
