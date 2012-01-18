<?php
/**
 * Class App
 *
 * Responsible for path management and class loading.
 */
 
namespace de\helpdesk\Core;
use de\helpdesk\Utils as Utils;

class ClassLoader
{
	public static function loadClass($className)
	{
		$filename = ROOT . DS . str_replace('\\', '/', $className) . '.php';
		Utils\Log::log("debug", "Load Class: $className");
		//echo $filename . "<br/>";
		if(file_exists($filename))
		{
			require_once($filename);
		}
		else
		{
			throw new \de\helpdesk\Error\MissingClassException($className);
		}
	}
}