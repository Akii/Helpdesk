<?php
/**
 * Custom exceptions
 */
 
namespace de\helpdesk\Error;


/**
 * Class Exception
 *
 * Base class for all exceptions dealing with the MVC.
 *
 * @package de.helpdesk.Error
 */
class Exception extends \RuntimeException
{
	/**
	 * @var array values for the $_message
	 */
	protected $_attributes = array();
	
	/**
	 * @var String Template which will be filled with vsprintf
	 */
	protected $_message = '%s';
	
	
	/**
	 * Constructor
	 *
	 * @param mixed $message Array with values or a error message
	 * @param int $code Status code. Default = 500 for internal errors.
	 */
	public function __construct($message, $code=500)
	{
		if(!is_array($message))
			$message = array($message);
			
		$this->_attributes = $message;
		$message = vsprintf($this->_message, $message);
			
		parent::__construct($message, $code);
	}
}

/**
 * Class MissingClassException
 *
 * Thrown if a class cannot be found/loaded
 */
class MissingClassException extends Exception
{
	protected $_message = 'Class %s coult not be found';
}

/**
 * Class MissingControllerException
 *
 * Called when a controller cannot be found.
 *
 * @package de.helpdesk.Error
 */
class MissingControllerException extends Exception
{
	protected $_message = 'Controller %s could not be found.';
	
	public function __construct($message, $code = 404)
	{
		parent::__construct($message, $code);
	}
}

/**
 * Class MissingControllerPluginException
 *
 * Called when a plugin for a controller is listed but doesn't exist or cannot be loaded
 */
class MissingControllerPluginException extends Exception
{
	protected $_message = 'ControllerPlugin %s for Controller %s could not be found.';
	
	public function __construct($message, $code=404)
	{
		parent::__construct($message, $code);
	}
}

/**
 * Class MissingActionException
 *
 * Called when an action cannot be found.
 *
 * @package de.helpdesk.Error
 */
class MissingActionException extends Exception
{
	protected $_message = 'Action %s could not be found.';
	
	public function __construct($message, $code = 404)
	{
		parent::__construct($message, $code);
	}
}

/**
 * Class PrivateActionException
 *
 * Called when a requested action is private (starts with a leading '_')
 *
 * @package de.helpdesk.Error
 */
class PrivateActionException extends Exception
{
	protected $_message = 'Private Action %s::%s() is not accessible.';
	
	public function __construct($message, $code=404)
	{
		parent::__construct($message, $code);
	}
}

/**
 * Class DatabaseEngineNotFoundException
 *
 * If the config for DB.type is wrong this exception will be thrown
 */
class DatabaseEngineNotFoundException extends Exception
{
	protected $_message = 'The database %s does not exist. Please check the configuration DB.type in config.php';
	
	public function __construct($message, $code=500)
	{
		parent::__construct($message, $code);
	}
}

/**
 * Class SQLException
 *
 * Called upon sql errors
 *
 * @package de.helpdesk.Error
 */
class SQLException extends Exception
{
	protected $_message = '%d: %s';
	
	public function __construct($errno, $error, $code=500)
	{
		parent::__construct(array($errno, $error), $code);
	}
}

/**
 * Class ModelInteractionException
 *
 * Called when something goes wrong while doing CRUD
 *
 * @package de.helpdesk.error 
 */
class ModelInteractionException extends Exception
{
	protected $_message = "Something went wrong while performing %s on model %s";
	
	public function __construct($action, $modelname)
	{
		parent::__construct(array($action, $modelname));
	}
}