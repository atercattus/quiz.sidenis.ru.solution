<?php
$N = (int)$argv[1];
$Q = (int)$argv[2];
$debug = !empty($argv[3]);

$L = $N-1;

$max = 100000000;

$rnd = function() use($max) {
	$v = mt_rand(0, $max);
	if (mt_rand(0, 10) > 5) {
		$v = -$v;
	}
	return $v;
};

$map = [];

$sum = function($x1, $y1, $x2, $y2) use (&$map) {
	$s = 0;
	for ($y = $y1; $y <= $y2; ++$y) {
		for ($x = $x1; $x <= $x2; ++$x) {
			$s += $map[$y][$x];
		}
	}
	return $s;
};

for ($r = 0; $r < $N; ++$r) {
	for ($c = 0; $c < $N; ++$c) {
		$map[$r][$c] = $rnd();
	}
}

if ($debug) {
    echo "-1\n";
}

echo $N, "\n";
foreach ($map as $line) {
	echo implode(' ', $line), "\n";
}

echo $Q, "\n";
for ($q = 0; $q < $Q-1; ++$q) {
	$isUpdate = mt_rand(0, 10) > 5;
	if ($isUpdate) {
		$x = mt_rand(0, $L);
		$y = mt_rand(0, $L);
		$p = $rnd();
		echo "2 $x $y $p\n";
		$map[$y][$x] = $p;
	} else {
		$x1 = mt_rand(0, $L);
		$y1 = mt_rand(0, $L);
		$x2 = mt_rand(0, $L);
		$y2 = mt_rand(0, $L);
		
		if ($x1 > $x2) {
			list($x1, $x2) = [$x2, $x1];
		}
		if ($y1 > $y2) {
			list($y1, $y2) = [$y2, $y1];
		}
		
		if (!$debug) {
			echo "1 $x1 $y1 $x2 $y2\n";
		} else {
			$s = $sum($x1, $y1, $x2, $y2);
			echo "1 $s $x1 $y1 $x2 $y2\n";
		}
	}
}

if (!$debug) {
	echo "1 0 0 $L $L\n";
} else {
	$s = $sum($x1, $y1, $x2, $y2);
	echo "1 $s $x1 $y1 $x2 $y2\n";
}
