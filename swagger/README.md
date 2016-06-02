springMVC和swagger，swagger-ui测试

springMVC 4.1.0
swagger-springmvc 1.0.2

百度前几个搜索结果对于config文件的注解只写了
@Configration,@EnableSwagger

除了这两个外，还需要@EnableWebMvc注解,并不需要在配置文件中配置
<bean class="com.peabee.me.configration.MySwaggerConfig"></bean>

如果在配置文件中配置了这个bean,启动时会出错：

 org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'mySwaggerConfig': Injection of autowired dependencies failed; 
	nested exception is org.springframework.beans.factory.BeanCreationException: Could not autowire method: public void com.peabee.me.configration.MySwaggerConfig.setSpringSwaggerConfig(com.mangofactory.swagger.configuration.SpringSwaggerConfig);
	 nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'com.mangofactory.swagger.configuration.SpringSwaggerConfig': Injection of autowired dependencies failed;
	 nested exception is org.springframework.beans.factory.BeanCreationException: Could not autowire field: private java.util.List com.mangofactory.swagger.configuration.SpringSwaggerConfig.handlerMappings;
	 nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type [org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping] found for dependency [collection of org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping]: expected at least 1 bean which qualifies as autowire candidate for this dependency. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
