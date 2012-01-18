<?php
/**
 * Class ControllerAbstract
 *
 * Superclass all other controllers inherit from
 */
 
namespace de\helpdesk\Controller;

use de\helpdesk\Core as Core;
use de\helpdesk\Error as Error;
use de\helpdesk\View as View;
use de\helpdesk\Network as Net;

abstract class Controller implements IController
{
	/**
	 * @var boolean $auto_render If set to true the dispatcher will automatically initiate the rendering
	 */
	public static $auto_render = true;
	
	/**
	 * @var string $name Controller name
	 */
	public static $name = "";
	
	/**
	 * @var array Components used by this controller
	 */
	protected static $components_used = array("Session", "User");
	
	/**
	 * @var array Instances of the components used
	 */
	public $components = array();
	
	/**
	 * @var array Action and parameters
	 */
	protected $action = array();
	
	/**
	 * @var HttpRequest and HttpResponse
	 */
	protected $request;
	protected $response;
	
	/**
	 * @var array The ControllerPluginContainer goes here
	 */
	protected $plugins = null;
	
	/**
	 * @var View Instance of a view
	 */
	public $view = null;
	
	/**
	 * @var array View params
	 */
	public $view_params = array();
	
	/**
	 * Constructor
	 *
	 * Loads the controllers components and view.
	 */
	public function __construct(Net\HttpRequest $request, Net\HttpResponse $response)
	{
		$this->request = $request;
		$this->response = $response;
		$this->view = new View\View($this);
		
		$this->loadComponents();
	}
	
	public function startup()
	{
		foreach($this->components as $name => $instance)
		{
			$instance->startup();
		}
		
		$this->view->addScript("css", "/public/css/ui-lightness/jquery-ui-1.8.17.custom.css");
		$this->view->addScript("js", "/public/js/jquery-1.7.1.min.js");
		$this->view->addScript("js", "/public/js/jquery-ui-1.8.17.custom.min.js");
	}
	
	public function shutdown()
	{
		foreach($this->components as $name => $instance)
		{
			$instance->shutdown();
		}
	}
	
	/**
	 * Process the action
	 *
	 * @param array Containing action and parameters
	 */
	public function invoke($action=array())
	{
		if(count($action) === 0)
			return;
		
		$this->action = $action;
		
		$method = array_shift($action) . "Action";
		
		if(method_exists($this, $method))
		{
			$this->$method($action);
		}
		else
		{
			throw new Error\MissingActionException($method);
		}	
	}
	
	/**
	 * Calls the view and fills the HttpResponse with header/body and send it to the client
	 */
	public function render()
	{
		if(!isset($this->view_params["title"]))
			$this->view_params["title"] = static::$name . " > " . $this->action[0];
			
		$output = $this->view->render();
		echo $output;
	}
	
	/**
	 * Default action does pretty much nothing.
	 */
	public function DefaultAction()
	{
	}
	
	/**
	 * @returns array Params for the view
	 */
	public function getViewParams() { return $this->view_params; }
	
	/**
	 * Loads the specified components
	 */
	protected function loadComponents()
	{
		$components = array();
		foreach(static::$components_used as $name)
		{
			$classname = "de\\helpdesk\\Controller\\Component\\" . ucfirst(strtolower($name)) . "Component";
			
			if(class_exists($classname))
			{
				$components[$name] = new $classname($this);
			}
		}
		$this->components = $components;
	}
}