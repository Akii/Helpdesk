<?php
/**
 * Class Component
 *
 * Parent class for all components
 */
namespace de\helpdesk\Controller\Component;
use de\helpdesk\Controller as Cont;
use de\helpdesk\Error as Error;

abstract class Component
{
	protected $controller;
	
	/**
	 * Life cycle functions
	 */
	public abstract function startup();
	public abstract function shutdown();
	
	public function __construct($controller)
	{
		if($controller instanceof Cont\Controller)
			$this->controller = $controller;
		else
			throw new Error\Exception("Passed argument is not an instance of Controller.");
	}
}