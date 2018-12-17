#!/bin/bash

javac Main.java || exit 1

/usr/bin/time -f 'mem:%M time:%e' java -Xmx256M -Xms16M -Xbatch -XX:+UseSerialGC -XX:-TieredCompilation -XX:CICompilerCount=1 Main < test.test.txt
#/usr/bin/time -f 'mem:%M time:%e' java -Xmx256M -Xms16M -Xbatch -XX:+UseSerialGC -XX:-TieredCompilation -XX:CICompilerCount=1 Main < small.test.txt
#/usr/bin/time -f 'mem:%M time:%e' java -Xmx256M -Xms16M -Xbatch -XX:+UseSerialGC -XX:-TieredCompilation -XX:CICompilerCount=1 Main < large.test.txt

