<?php
/**
 * Class HelpController
 *
 * Handles the requests for the product page
 */
 
namespace de\helpdesk\Controller;
use de\helpdesk\Model as Model;
use de\helpdesk\Utils as Utils;
use de\helpdesk\Error as Error;

class HelpController extends Controller
{
	public static $name = "Help";
	
	public function DefaultAction()
	{
		$this->action[0] = "Home";
		$this->view->setPage("default");
	}
	
	public function HomeAction() { $this->DefaultAction(); }
	
	public function TroubleshootAction()
	{
		$this->response->header("Location: /user/panel/new");
	}
}