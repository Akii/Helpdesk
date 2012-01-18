<?php
/**
 * Class SessionComponent
 *
 * Provides session functionality
 */
namespace de\helpdesk\Controller\Component;

class SessionComponent extends Component
{
	/**
	 * @var boolean True if the session has started
	 */
	private $hasStarted = false;
	
	/**
	 * @Override
	 */
	public function startup()
	{
		if(!isset($_SESSION))
			$this->hasStarted = session_start();
		else
			$this->hasStarted = true;
	}
	
	/**
	 * Returns the state of the session
	 */
	public function hasStarted() { return $this->hasStarted; }
	
	/**
	 * @Override
	 */
	public function shutdown() {}
}