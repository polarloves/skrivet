#! /bin/sh
name="@project.name@-@project.version@"
Xmx=1024m
Xms=1024m
Xmn=512m
PermSize=256m
MaxPermSize=512m
pid="${name}.pid"
jarName="${name}.jar"
start(){
    echo
        if [ -f "$pid" ]
        then
           PID=$(cat ${pid})
           echo -e "$name is running ,pid is $PID \n"
          exit 0;
        else
            echo -e  "start $name ...  \n"
            nohup java 	-Xmx${Xmx} -Xms${Xms} -Xmn${Xmn}   \
                -XX:PermSize=${PermSize} \
                -XX:MaxPermSize=${MaxPermSize} \
                -XX:+UseParNewGC \
                -XX:+UseConcMarkSweepGC \
                -XX:CMSFullGCsBeforeCompaction=3 \
                -XX:CMSInitiatingOccupancyFraction=60 -jar ${jarName} >/dev/null 2>&1 &   #注意：必须有&让其后台执行，否则没有pid生
            [ $? -eq 0 ] && echo  -e  "[$name start success]\n"
            echo $! > ${pid}   # 将jar包启动对应的pid写入文件中，为停止时提供pi

          fi
      }
#停止方法
stop(){
            echo -e "stop $name ...\n"
            if [ -f "$pid" ]
            then
                PID=$(cat ${pid})
		rm -rf $pid
                kill -9 $PID
                [ $? -eq 0 ] && echo  "[ok]"
                echo -e "stop $name succeed!\n"
            else
                echo -e  "[service is not running ...]\n"
             fi
        }
#查看状态的方法
status(){
   if [ -f "$pid" ]
   then
       PID=$(cat ${pid})
       echo -e "$name is running ,pid is $PID \n"
   else
     echo -e  "$name is not running.\n"
   fi
   exit 0
}
case "$1" in
     start)
        start  ;;
     stop)
        stop  ;;
     status)
	status ;;
     restart)
        stop
        start
        ;;
     *)
       printf 'Usage: server.sh { start|stop|restart|status }\n'
       exit 1
      ;;
esac