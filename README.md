<pre>
JavaCC
http://www.cnblogs.com/Gavin_Liu/archive/2009/03/07/1405029.html
https://javacc.java.net/
Jparsec
http://jparsec.codehaus.org/

GBK to UTF-8, find + -exec + iconv:
http://www.felix021.com/blog/read.php?entryid=1465&page=1&part=1
http://www.loisch.de/linux.html

find ./src -type d -exec mkdir -p utf8/{} \;
find ./src -type f -exec bash -c "iconv -f GBK -t UTF-8 {} > ./utf8/{}" \;
find ./test -type d -exec mkdir -p utf8/{} \;
find ./test -type f -exec bash -c "iconv -f GBK -t UTF-8 {} > ./utf8/{}" \;
</pre>