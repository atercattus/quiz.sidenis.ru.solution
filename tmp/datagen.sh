#!/bin/bash
php datagen.php   20     30 1 > small.debug.txt 
php datagen.php  200    300 1 > medium.debug.txt 
php datagen.php  500    500 1 > big.debug.txt 
php datagen.php  550 100100 1 > large.debug.txt
#php datagen.php 1100 200200 1 > xlarge.debug.txt

php datagen.php   20     30 > small.test.txt 
php datagen.php  200    300 > medium.test.txt 
php datagen.php  500    500 > big.test.txt 
php datagen.php  550 100100 > large.test.txt
#php datagen.php 1100 200200 > xlarge.test.txt
