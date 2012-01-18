<?php
/**
 * Class View
 *
 * Loads layouts and renders them
 */
namespace de\helpdesk\View;
use de\helpdesk\Core as Core;
use de\helpdesk\Controller as Cont;
use de\helpdesk\Error as Error;
use de\helpdesk\Utils as Utils;

class View
{
	/**
	 * @var $controller Controller to pull the vars from
	 */
	private $controller = null;
	
	/**
	 * @var array Contains the data for the view
	 */
	private $view_params = array();
	
	/**
	 * @var array Contains the scripts, css files
	 */
	private $head_params = array();
	
	/**
	 * @var string Name of the layout to use
	 */
	private $layout = 'default';
	
	/**
	 * @var string Name of the page to render
	 */
	private $page = '';
	
	/**
	 * @var boolean True if view is rendered
	 */
	private $has_rendered = false;
	
	/**
	 * @var string File name extension
	 */
	private $ext = '.inc';
	
	/**
	 * Constructor
	 */
	public function __construct($controller)
	{
		if($controller instanceof Cont\Controller)
			$this->controller = $controller;
		else
			throw new Error\Exception("Passed argument for the View is not an instance of de.helpdesk.Controller.Controller");
		
		$this->view_params["charset"] = Core\Configure::read('App.encoding');
	}
	
	/**
	 * Magic get/set for including templates
	 */
	public function __get($index)
	{
		if(isset($this->view_params[$index]))
			return $this->view_params[$index];
		else
			return "";
	}
	
	public function __set($index, $value)
	{
		$this->view_params[$index] = $value;
	}
	
	/**
	 * Render the View and return its result as a string
	 */
	public function render()
	{
		if($this->has_rendered === true) return true;
		$this->view_params = array_merge($this->view_params, $this->controller->getViewParams());
		
		$layout_params = array();
		
		$this->processHead();
		
		$this->renderPage();
		
		$this->has_rendered = true;
		
		return $this->renderLayout();
	}
	
	/**
	 * Renders the layout
	 *
	 * @param string $layout Name of the layout
	 * @return string Rendered content
	 */
	private function renderLayout($args=array())
	{
		$file = Core\Configure::read('App.app_root') . "/View/Layout/" . strtolower($this->layout . $this->ext);
		return $this->includeFile($file, $args);
	}
	
	private function renderPage()
	{
		if($this->page !== "")
		{
			$this->view_params["content_for_layout"] = $this->includeFile($this->page);
		}
	}
	
	/**
	 * Includes a file using output buffering
	 *
	 * @param string $file Complete filepath to the include file
	 * @param array $args For the placeholders
	 */
	private function includeFile($file, $args=array())
	{
		if(file_exists($file))
		{
			if(empty($args))
				$args = $this->view_params;
			
			extract($args, EXTR_SKIP);
			
			ob_start();
			include($file);
			return ob_get_clean();
		}
		return "Unable to include the file.";
	}
	
	/**
	 * Adds a script to the head
	 *
	 * @param string $type CSS, JS
	 * @param string $file Complete filename accessible by the browser
	 */
	public function addScript($type, $file)
	{
		if(!is_array($this->head_params[$type]))
		{
			$this->head_params[$type] = array();
		}
		
		$this->head_params[$type][] = $file;
	}
	
	/**
	 * Prepares the scripts and inserts them as head_params
	 */
	private function processHead()
	{
		$config = Core\Configure::read('View.script');
		if(is_array($config))
		{
			foreach($config as $key => $value)
				$this->addScript($key, $value);
		}
		
		$params = $this->head_params;
		
		$scripts_for_layout = "";
		
		foreach($params as $type => $urls)
		{
			foreach($urls as $url)
				$scripts_for_layout .= $this->getScriptTag($type, $url) . "\n";
		}
		
		$this->view_params["scripts_for_layout"] = $scripts_for_layout;
	}
	
	/**
	 * Creates the html markup for scripts
	 */
	private function getScriptTag($type, $url)
	{
		switch(strtolower($type))
		{
			case "css":
				return '<link rel="stylesheet" href="'.$url.'" type="text/css" />';
				break;
			case "js":
				return '<script type="text/javascript" src="'.$url.'"></script>';
				break;
		}
		
		return "";
	}
	
	/**
	 * Sets the page to render
	 */
	public function setPage($name)
	{
		$con = $this->controller;
		$file = Core\Configure::read('App.app_root') . "/View/" . $con::$name . "/" . $name . $this->ext;
		if(file_exists($file))
		{
			$this->page = $file;
			return true;
		}
		else
		{
			return false;
		}
	}
}