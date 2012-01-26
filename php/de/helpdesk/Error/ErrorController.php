<?php
/**
 * Class ErrorRenderer
 *
 * Bypassing all other business logic, this class renders all uncought exceptions
 * handled by the ExceptionHandler
 */

namespace de\helpdesk\Error;
use de\helpdesk\Network as Net;
use de\helpdesk\Core as Core;
use de\helpdesk\Controller as Con;

class ErrorController extends Con\Controller
{
	public static $name = "Error";
	
	/**
	 * @var Exception to render
	 */
	private $exception;
	
	/**
	 * @var Basic http codes
	 */
	private $codes = array(
		404 => "Not found.",
		500 => "Internal server error."
	);
	
	/**
	 * Constructor
	 *
	 * @param $exception Exception to render
	 */
	public function __construct($exception)
	{
		parent::__construct(new Net\HttpRequest(), new Net\HttpResponse(array('charset' => Core\Configure::read('App.encoding'))));
		
		$this->exception = $exception;
		
		// 500 means serious trouble including no database
		if($exception->getCode() < 500)
			$this->startup();
		
		$this->render();
	}
	
	/**
	 * Sends the output to the client after processing
	 */
	public function render()
	{
		$code = $this->exception->getCode();
		
		if(!array_key_exists($code, $this->codes))
			$code = 500;
		
		$this->view_params["title"] = "$code: ".$this->codes[$code];
		$this->view_params["error_code"] = $code;
		$this->view_params["error_msg"] = $this->codes[$code];
		
		if(Core\Configure::read('Debug.level') === 2 || (Core\Configure::read('Debug.level') === 1 && !($this->exception instanceOf SQLException)))
			$this->view_params["debug"] = "<hr/>".$this->exception->getMessage();
		
		$file = "error$code";
		
		$this->view->setPage($file);
		
		$this->response->body(
			$this->view->render()
		);
		
		echo $this->response->body();
	}
}