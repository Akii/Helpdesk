<?php
/**
 * Class HttpRequest
 *
 * Handles the request from the client
 */
 
namespace de\helpdesk\Network;

class HttpRequest 
{
	// holds the url without get params
	public $uri = "";
	// $_GET requests go in here
	public $query = array();
	// $_POST data go in here
	public $data = array();
	
	/**
	 * Constructor
	 *
	 * Parses the request data/query and urls
	 */
	public function __construct()
	{
		$this->_processPost();
		$this->_processGet();
		$this->_processUri();
	}
	
	private function _processPost()
	{
		if(ini_get('magic_quotes_gpc') === '1')
			$data = $this->stripslashes_arr($_POST);
		else
			$data = $_POST;
			
		$this->data = $data;
	}
	
	
	private function _processGet()
	{
		if(ini_get('magic_quotes_gpc') === '1')
			$query = $this->stripslashes_arr($_GET);
		else
			$query = $_GET;
		
		$this->query = $query;
	}
	
	private function _processUri()
	{
		$uri = $_SERVER['REQUEST_URI'];
		
		if($pos = strpos($uri, "?") !== false)
		{
			$uri = substr($uri, 0, strpos($uri, "?"));
		}
		
		$this->uri = $uri;
	}
	
	/**
	 * Strips slashes in an array and returns it
	 *
	 * @param array $array Mixed array with values to strip slashes
	 * @return array with striped slashes
	 */
	private function stripslashes_arr($array)
	{
		if(!is_array($array)) return null;
		
		foreach($array as $key=>$value)
		{
			if(is_array($array[$key]))
				$array[$key] = $this->stripslashes_arr($array[$key]);
			else
				$array[$key] = stripslashes($value);
		}
		
		return $array;
	}
	
	/**
	 * @return true if this request is a post request.
	 */
	public function isPost()
	{
		return count($this->data) > 0;
	}
}