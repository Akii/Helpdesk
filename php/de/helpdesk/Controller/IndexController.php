<?php
/**
 * Class IndexController
 *
 * Just another controller, prolly replaced by default.
 */
 
namespace de\helpdesk\Controller;
use de\helpdesk\Model as Model;
use de\helpdesk\Utils as Utils;
use de\helpdesk\Error as Error;

class IndexController extends Controller
{
	public static $name = "Index";
	
	public function DefaultAction()
	{
		$this->action[0] = "Home";
		$this->view->setPage("default");
	}
	
	public function HomeAction()
	{
		$this->DefaultAction();
	}
	
	public function AboutAction() { $this->ImpressumAction(); }
	
	public function JobsAction()
	{
		$this->view->setPage("jobs");
	}
	
	public function ImpressumAction()
	{
		$this->view->setPage("impressum");
	}
}