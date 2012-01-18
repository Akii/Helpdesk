<?php
/**
 * Class HttpResponse
 *
 * Manages header, response text and http status codes.
 */
 
namespace de\helpdesk\Network;
use \de\helpdesk\Error as Error;

class HttpResponse 
{
	// status
	private $_status = 200;
	
	// Protocol header
	private $_protocol = 'HTTP/1.1';
	
	// Content type header
	private $_contentType = 'text/html';
	
	// Charset
	private $_charset = 'UTF-8';
	
	
	// HTML headers
	private $_headers = array();
	
	// HTML body
	private $_body = null;
	
	/**
	 * Constructor
	 *
	 * @param array $vars Initial settings key=>value
	 */
	public function __construct($vars=null)
	{
		if(is_array($vars))
		{
			if(isset($vars['charset']))
				$this->charset($vars['charset']);
			if(isset($vars['statuscode']))
				$this->statusCode($vars['statuscode']);
			if(isset($vars['body']))
				$this->body($vars['body']);
		}
	}
	
	/**
	 * Sets the status code for the response
	 *
	 * @param int $code http status code
	 * @returns the current/new status code
	 * @throws Error\Exception in case of invalid status code
	 */
	 public function statusCode($code=null)
	 {
	 	if(!is_int($code) || $code < 100 || $code > 504)
	 		throw new Error\Exception("Invalid status code for response.");
	 		
	 	if($code===null)
	 		return $this->_status;
	 	else
	 		return $this->_status = $code;
	 }
	
	/**
	 * Sets the charset and returns it
	 *
	 * @param string $charset http charset
	 * @return string current charset
	 */
	 public function charset($charset = null)
	 {
	 	if($charset === null)
	 		return $this->_charset;
	 	else
	 		return $this->_charset = $charset;
	 }
	 
	 /**
	  * Sets/Gets the body
	  *
	  * @param string $body HTML body
	  * @return string Current body
	  */
	  public function body($body = null)
	  {
	  	if($body === null)
	  		return $this->_body;
	  	else
	  		return $this->_body = $body;
	  }
	  
	  /**
	   * Send the response to the client
	   */
	  public function sendResponse()
	  {
	  	$this->_sendHeader();
	  	$this->_sendBody();
	  }
	  
	  /**
	   * Sets headers
	   */
	  private function _sendHeader()
	  {
	  	
	  }
	  
	  /**
	   * Sends body
	   */
	  private function _sendBody()
	  {
	  	echo $this->_body;
	  }
}