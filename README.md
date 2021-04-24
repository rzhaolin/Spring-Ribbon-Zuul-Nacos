# Spring-Ribbon-Zuul-Nacos


基于Nacos + Ribbon + Zuul的微服务代码。

1) 服务提供者：user、cms，采用Ribbon作为负载均衡。

2) 消费者：zuul，它是网关，支持动态和静态路由。

   静态路由配置在bootstrap.yml中。
   
   动态路由则在nacos后台管理系统配置。

3) 服务发现：采用阿里的Nacos，将user、cms、zuul注册到Nacos。
