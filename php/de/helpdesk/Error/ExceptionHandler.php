<?php
/**
 * Class ExceptionHandler
 *
 * Handles all uncaught exceptions. Set upon startup
 * 
 * @see http://php.net/manual/en/function.set-exception-handler.php
 */
 
namespace de\helpdesk\Error;

class ExceptionHandler
{
	public static function handle_exception($e)
	{
		//echo "Exception: " . $e->getMessage();
		//var_dump($e);
		new ErrorController($e);
	}
}