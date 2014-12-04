JavaCC
http://www.cnblogs.com/Gavin_Liu/archive/2009/03/07/1405029.html
https://javacc.java.net/
Jparsec
http://jparsec.codehaus.org/

find ./src -type d -exec mkdir -p utf8/{} \;
find ./src -type f -exec iconv -f GBK -t UTF-8 {} -o ./utf8/{} \;
