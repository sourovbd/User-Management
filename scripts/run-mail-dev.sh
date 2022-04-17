for pid in $(ps -ef | grep "maildev" | awk '{print $2}');
do kill -9 $pid; done
maildev