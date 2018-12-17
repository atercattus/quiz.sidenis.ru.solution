#!/bin/bash

javac Main.java || exit 1

echo "TEST"
java -Xmx256M -Xms16M -Xbatch -XX:+UseSerialGC -XX:-TieredCompilation -XX:CICompilerCount=1 Main < test.test.txt

java -Xmx256M -Xms16M -Xbatch -XX:+UseSerialGC -XX:-TieredCompilation -XX:CICompilerCount=1 Main < small.debug.txt
java -Xmx256M -Xms16M -Xbatch -XX:+UseSerialGC -XX:-TieredCompilation -XX:CICompilerCount=1 Main < medium.debug.txt
java -Xmx256M -Xms16M -Xbatch -XX:+UseSerialGC -XX:-TieredCompilation -XX:CICompilerCount=1 Main < big.debug.txt
java -Xmx256M -Xms16M -Xbatch -XX:+UseSerialGC -XX:-TieredCompilation -XX:CICompilerCount=1 Main < large.debug.txt

#exit

echo "BENCH"
>./out.lst
while true; do
    mt=`date +%s.%N`
    for i in {1..51}; do
        #/bin/true < large.txt
        java -Xmx256M -Xms16M -Xbatch -XX:+UseSerialGC -XX:-TieredCompilation -XX:CICompilerCount=1 Main < large.test.txt >> /dev/null # ./out.lst
    done
    echo -n 'elapsed '
    echo "`date +%s.%N` - $mt" | bc /dev/stdin

    sleep 1
done
