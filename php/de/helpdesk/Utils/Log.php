<?php
/**
 * Class Log 
 *
 * For saving (debug) msgs in a static array or file
 * (not really implemented)
 */

namespace de\helpdesk\Utils;

class Log 
{
	public static $entries = array();
	
	public static function log($prefix="", $msg)
	{
		$out = "";
		if($prefix !== "")
		{
			$out .= "[$prefix] ";
		}
		
		$out .= $msg;
		
		self::$entries[] = $out;
	}
	
	public static function toString()
	{
		$out="";
		foreach(self::$entries as $value)
			$out .= $value . "\n";
		return $out;
	}
}