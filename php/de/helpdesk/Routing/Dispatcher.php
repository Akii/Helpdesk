<?php
/**
 * Class Dispatcher
 *
 * Takes the clients request, loads the proper controllers.
 * If the controller is set to auto render it will be rendered by the dispatcher.
 */
 
namespace de\helpdesk\Routing;

use \de\helpdesk\Core as Core;
use \de\helpdesk\Network as Net;
use \de\helpdesk\Error as Error;
use \de\helpdesk\Controller as Controller;

class Dispatcher
{
	/**
	 * @var string $controller Name of the controller without 'Controller' appended
	 */
	private $controller = "";
	
	/**
	 * @var array $action Action array with action on index 0 and parameters from 1 to n
	 */
	private $action = array();
	
	private $defaultController = "index";
	private $defaultAction = "default";
	
	private $request;
	private $response;
	
	public function dispatch(Net\HttpRequest $request, Net\HttpResponse $response)
	{
		/*
		1. parse url to /controller/action/
		2. check if there is a controller (throw controller not found exception if not)
		3. load controller, its dependencies (models) -> startup
		4. tell the controller to do the action -> invoke
		5. if autorender, do it -> render
		6. shut down the controller -> shutdown
		7. send the response
		*/
		
		$this->request = $request;
		$this->response = $response;
		
		$this->_parseUri($request);
		$controller = $this->_getController();
		
		if($controller === NULL)
			throw new Error\Exception("Failed to load the controller");
			
		if(!($controller instanceof Controller\Controller))
			throw new Error\Exception("The controller " . $controller::$name . " does not inherit from Controller.");
		
		$controller->startup();
		$controller->invoke($this->action);
		if($controller::$auto_render === true)
		{
			$controller->render();
		}
		$controller->shutdown();
	}
	
	
	/**
	 * Get the controller and action from the uri
	 *
	 * /index/help/ is IndexController and the action "help"
	 *
	 * @pararm Net\HttpRequest $request A HttpRequest to fetch the uri from
	 */
	private function _parseUri(Net\HttpRequest $request)
	{
		$uri = $request->uri;
		
		if(strpos($uri, "/") === 0)
			$uri = substr($uri, 1);
		
		list($controller, $action) = preg_split('/[\/\\\]/',$uri, 2);
		
		if(!isset($controller) || $controller === "")
			$controller = $this->defaultController;
		
		$this->controller = ucfirst(strtolower($controller));
		$this->action = $this->_parseAction($action);
	}
	
	/**
	 * Parse action from uri to an array
	 * 
	 * @param string $action Part of the uri containing the action
	 * @return array Array containing action and parameters
	 */
	private function _parseAction($action)
	{
		$parts = array();
		
		if(strpos($action, "/") === false)
			$parts[0] = $action;
		else
			$parts = preg_split('/[\/\\\]/',$action);
		
		if($parts[0] == "")
			$parts[0] = $this->defaultAction;
		
		$parts[0] = ucfirst(strtolower($parts[0]));
		
		return $parts;
	}
	
	
	/**
	 * Loads an instance of the controller
	 *
	 * @returns An instance of the controller
	 * @throws de.helpdesk.Error.MissingControllerException when the controller could not be found
	 */
	 private function _getController()
	 {
	 	$controllerName = $this->controller;
	 	$class = $controllerName . 'Controller';
	 	
	 	$className = Core\Configure::read('App.namespace') . '\\' . 'Controller\\' . $class;
	 	
	 	try
	 	{
	 		class_exists($className,true);
	 		$instance = new $className($this->request, $this->response);
	 	}
	 	catch(Error\MissingClassException $e)
	 	{
	 		// controller not found
	 		throw new Error\MissingControllerException(array($controllerName));
	 	}
	 	
	 	return $instance;
	 }
}