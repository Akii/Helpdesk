<?php
/**
 * Interface IController
 *
 * All Controllers must implement this interface.
 */

namespace de\helpdesk\Controller;

interface IController
{
	/**
	 * Life cycle for controllers
	 * Called before rendering
	 */
	public function startup();
	
	/**
	 * Invokes the action the controller should perform
	 *
	 * @param array $action Action and parameters to perform
	 */
	public function invoke($action);
	
	/**
	 * Renders the view
	 */
	public function render();
	
	/**
	 * Life cycle for controllers
	 * Called after rendering
	 */
	public function shutdown();
}