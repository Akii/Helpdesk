<?php
/**
 * Class Configure
 *
 * Reads the configuration file and provides the settings on runtime
 *
 * @package de.helpdesk.Core
 */
 
namespace de\helpdesk\Core;
use \de\helpdesk\Error as Error;

class Configure
{
	/**
	 * @var array Contains all settings
	 */
	private static $_settings = array();
	
	/**
	 * Reads the configuration from file and adds paths
	 */
	public static function init()
	{
		self::write('App', array(
			'app_root' => APP_ROOT,
			'www_root' => WWW_ROOT
		));
		
		// include configuration
		$config_file = APP_ROOT . DS . 'Configure' . DS . 'config.php';
		if(!include $config_file)
		{
			throw new Error\Exception("The configuration file could not be found.");
		}
		
	}
	
	/**
	 * Adds a variable to configuration
	 *
	 * @param mixed $config Index under which the config is saved
	 * @param mixed $value Value for the index
	 */
	public static function write($config, $value=null)
	{
		if(!is_array($config))
		{
			$config = array($config => $value);
		}
		
		
		foreach($config as $index => $value)
		{
			if(strpos($index, '.') === false)
			{
				self::$_settings[$index] = $value;
			}
			else
			{
				$indexes = explode('.', $index, 3);
				switch(count($indexes))
				{
					case 2:
						self::$_settings[$indexes[0]][$indexes[1]] = $value;
						break;
					case 3:
						self::$_settings[$indexes[0]][$indexes[1]][$indexes[2]] = $value;
						break;
				}
			}
		}
	}
	
	/**
	 * Reads a variable from configuration
	 *
	 * @param String $index Index under which the config is saved
	 * @return mixed Value for index or false if not set
	 */
	public static function read($index=null)
	{
		if($index === null) return self::$_settings;
		
		if(isset(self::$_settings[$index]))	return self::$_settings[$index];
		
		
		$indexes = array();
		if(strpos($index, '.'))
		{
			$indexes = explode('.', $index, 3);
		}
		
		if(!isset(self::$_settings[$indexes[0]]) || !is_array(self::$_settings[$indexes[0]]))
			return null;
		
		switch(count($indexes))
		{
			case 3:
				if(isset(self::$_settings[$indexes[0]][$indexes[1]]) && is_array(self::$_settings[$indexes[0]][$indexes[1]]))
				{
					if(isset(self::$_settings[$indexes[0]][$indexes[1]][$indexes[2]]))
						return self::$_settings[$indexes[0]][$indexes[1]][$indexes[2]];
				}
				break;
			case 2:
				if(isset(self::$_settings[$indexes[0]][$indexes[1]]))
				{
					return self::$_settings[$indexes[0]][$indexes[1]];
				}
				break;
		}
		
		return null;
	}
}