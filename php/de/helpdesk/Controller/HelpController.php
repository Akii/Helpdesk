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
		$this->view->setPage("trouble");
		
		$this->view_params["customer_options"] = Model\CustomerModel::getAll(
			function($db) {
				while($row = $db->fetch_array("assoc"))
					$out .= sprintf("<option value=\"%u\" >%s</option>\n", $row['CID'], "{$row['firstName']} {$row['lastName']}");
				
				return $out;
			}
		);
		
		$this->view_params["category_options"] = Model\ProblemModel::getAll(
			function($db) {
				while($row = $db->fetch_array("assoc"))
					$out .= sprintf("<option value=\"%u\" >%s</option>\n", $row['CategoryID'], $row["description"]);
				
				return $out;
			}
		);
		
		$this->view_params["product_options"] = Model\ProductModel::getAll(
			function($db) {
				while($row = $db->fetch_array("assoc"))
					$out .= sprintf("<option value=\"%u\" >%s</option>\n", $row['PID'], $row["name"]);
				
				return $out;
			}
		);
		
		//var_dump($this->request->data);
	}
}