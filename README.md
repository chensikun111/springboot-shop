# springboot-shop
课设小项目，后端部分

使用mq在docker安装命令

docker run \
 -e RABBITMQ_DEFAULT_USER=guest \
 -e RABBITMQ_DEFAULT_PASS=guest \
 -v mq-plugins:/plugins \
 --name mq \
 --hostname mq \
 -p 15672:15672 \
 -p 5672:5672 \
 --network hm-net\
 --restart=always \
 -d \
 rabbitmq:3.8-management
