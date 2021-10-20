<?php

$WORDS=[];

function startsWith( $haystack, $needle ) {
     $length = strlen( $needle );
     return substr( $haystack, 0, $length ) === $needle;
}

function endsWith( $haystack, $needle ) {
    $length = strlen( $needle );
    if( !$length ) {
        return true;
    }
    return substr( $haystack, -$length ) === $needle;
}

function processFile($fname){

    global $WORDS;

    foreach(explode("\n",file_get_contents($fname)) as $line){
	$line=trim($line);
	if(empty($line))continue;
	$words=explode(" ",$line);
	
	for($i=0;$i<count($words);$i++){
	    $w=trim($words[$i],"-+.,/?><;':\"[]\\{}|\n\t\r ");
	    if(empty($w))continue;
	    
	    $wl=mb_strtolower($w);
	    if($wl==$w){
	    //if(preg_match("/^[a-zăîâșț]+\$/",$w)!==1)continue;
	    
		if(!isset($WORDS[$w]))$WORDS[$w]=1;
		else $WORDS[$w]++;
	    }
	}
    }
}

//processFile("/data/RELATE/DB/corpora/Marcell/files/mj_00000G3W4OATLEEA8OB2K2N9NH6C4BDZ.txt");
//var_dump($WORDS);

function processFolder($dir){

    $dh = opendir($dir);
    while (($file = readdir($dh)) !== false) {
	$fname="$dir/$file";
	if(is_file($fname) && endsWith($fname,".txt"))
	    processFile($fname);
    }
    closedir($dh);
    
}

//processFolder("/data/RELATE/DB/corpora/ne_marcell/files");
processFolder("/data/RELATE/DB/corpora/Marcell/files");
processFolder("/data/vasile/corola/clear");

arsort($WORDS);
$fout=fopen("n1.csv","w");
foreach($WORDS as $w=>$n)if($n>=50)fwrite($fout,"$n\t$w\n");
fclose($fout);

