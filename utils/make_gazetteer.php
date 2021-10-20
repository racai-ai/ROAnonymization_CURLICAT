<?php

function addGazetteer($fout,$type,$fnameIn){
    $fin=fopen($fnameIn,"r");
    if($fin===false){
	echo "Cannot open [$fnameIn]\n";
	return ;
    }
    while(!feof($fin)){
	$line=fgets($fin);
	if($line===false)break;
	
	$line=trim($line);
	if(empty($line))continue;
	
	fwrite($fout,"$type $line\n");
    }
    fclose($fin);
}

$fout=fopen("ne.1.gazetteer","w");
addGazetteer($fout,"PER","pernames-utf8-uniq.txt");
addGazetteer($fout,"ORG","orgnames-utf8-uniq.txt");
addGazetteer($fout,"LOC","locnames-utf-8-uniq.txt");
addGazetteer($fout,"PER","pernames-aditional.txt");
addGazetteer($fout,"ORG","orgnames-aditional.txt");
addGazetteer($fout,"LOC","locnames-aditional.txt");
fclose($fout);
