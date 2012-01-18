<?php
/**
 * Interface IController
 *
 * All Controllers must implement this interface.
 */

namespace de\helpdesk\Controller;

interface IController
{
	public function startup();
	public function invoke($action=array());
	public function render();
	public function shutdown();
}