<?php
/**
 * Class UserController
 *
 * Handles login, logout and customer control-center 
 */
 
namespace de\helpdesk\Controller;
use de\helpdesk\Model as Model;
use de\helpdesk\Error as Error;

class UserController extends Controller
{
	public static $name = "User";
	
	private $tabs = array(
		"view" => "<li%s><a href=\"/user/panel/view\">View Tickets</a></li>\n",
		"new"  => "<li%s><a href=\"/user/panel/new\">New Ticket</a></li>\n",
		"details"=>""
	);
	
	/**
	 * @var array standard messages for changes for the customer
	 */
	private $changes = array(
		"employee_EID" 	=> "A new employee has been assigned to this ticket or has been changed.",
		"Problem"		=> "The problem has been modified.",
		"Solution"		=> "A solution has been added or changed.",
		"CategoryID"	=> "The category has been changed.",
		"StatusID"		=> "The status has been changed",
		"Topic"			=> "The topic has been changed"
	);
	
	/**
	 * Loggs in customers
	 */
	public function LoginAction()
	{
		if($this->request->isPost() === true && $this->components["Session"]->hasStarted() === true)
		{
			$data = $this->request->data;
			if(isset($data["username"]) && isset($data["password"]))
			{
				$result = $this->components["User"]->login($data["username"], $data["password"]);
				
				if($result === true)
					header("Location: /user/panel");
				else
					$this->view_params["login_error"] = '<div class="error box_shadow_01">Login failed!</div>';
			}
		}
		
		$this->view->setPage("login");
	}
	
	/**
	 * Loggs out customers
	 */
	public function LogoutAction()
	{
		$this->components["User"]->logout();
		
		header("Location: /");
	}
	
	public function DefaultAction() { $this->PanelAction(); }
	
	public function PanelAction()
	{
		if(!$this->components["User"]->loggedIn())
		{
			header("Location: /user/login");
			die();
		}
		
		// sub-action
		$action = "view";
		if(isset($this->action[1]))
		{
			$this->action[1] = strtolower($this->action[1]);
			if(array_key_exists($this->action[1], $this->tabs))
				$action = $this->action[1];
		}
		
		$this->view->setPage($action);
		
		$method = "perform" . ucfirst($action);
		if(method_exists($this, $method))
			$this->$method();
		
		$this->view_params["title"] = "User control panel";
		$this->view_params["tabs"] = $this->processTabs($action);
	}
	
	/**
	 * Displays the tickets for the customer in his user control panel
	 */
	private function performView()
	{
		$result = Model\TicketModel::getForCustomer($_SESSION["user_id"], function($db) {
			$out = "";
			while($row = $db->fetch_array("assoc"))
			{
				$employee = ($row["e_fn"] !== null) ? $row["e_fn"]." ".$row["e_ln"] : "none";
				
				if(strlen($row["Topic"]) > 80)
					$topic = substr($row["Topic"], 0, strrpos(substr($row["Topic"], 0, 80), ' ')) . '...';
				else
					$topic = $row["Topic"];
					
				$out .= sprintf('
					<tr>
						<td>%u</td>
						<td>%s</td>
						<td>%s</td>
						<td>%s</td>
						<td>%s</td>
						<td><a href="/user/panel/details/%u" ><img src="/public/images/document-preview.png" alt="Details" Title="Details"/></a></td>
					</tr>
				', $row["TID"], $row["status"], $topic, $employee, date("j M, Y", strtotime($row["last_update"])), $row["TID"]);
			}
			
			// if the user has no tickets
			if($db->num_rows() < 1)
			{
				$out = '<tr><td colspan="7" class="center"><b>You have no tickets yet. Click <a href="/user/panel/new">here</a> to create a new one!</b></td></tr>';
			}
			
			
			return $out;
		});
		
		$this->view_params["view"] = $result;
	}
	
	/**
	 * Opens a form for entering the required data for a new ticket
	 */
	private function performNew()
	{
		if($this->request->isPost())
		{
			$data = $this->request->data;
			if($data["category"] > 0 && $data["topic"] != "" && $data["problem"] != "")
			{
				$ticket = new Model\TicketModel();
				try
				{
					$ticket->CategoryID 	= (int) $data["category"];
					$ticket->StatusID		= 1;
					$ticket->customer_CID 	= $_SESSION["user_id"];
					$ticket->Topic 			= $data["topic"];
					$ticket->Problem		= $data["problem"];
					
					$ticket->create();
					
					if($ticket->TID > 0)
					{
						$_SESSION["ticket_new"] = '<div class="success gradient_05 text_shadow_01 box_shadow_02">You\'ve created a new ticket.</div>';
						$link = sprintf("Location: /user/panel/details/%u", $ticket->TID);
						header($link);
					}
				}
				catch(Error\SQLException $e)
				{
					$this->view_params["new_error"] = '<div class="error">Something terrible happened. Our monkeys are working on this problem as fast as they can. This should not happen!</div>';
				}
			}
			else
			{
				$this->view_params["new_error"] = '<div class="warning gradient_06  box_shadow_02">Please fill in all the fields.</div>';
				$this->view_params["topic"] 	= $data["topic"];
				$this->view_params["problem"] 	= $data["problem"];
			}
		}
		
		$this->view_params["category_options"] = Model\ProblemModel::getAll(
			function($db) {
				while($row = $db->fetch_array("assoc"))
					$out .= sprintf("<option value=\"%u\" >%s</option>\n", $row['CategoryID'], $row["description"]);
				
				return $out;
			}
		);
	}
	
	/**
	 * Displays the details of a certain ticket
	 */
	private function performDetails()
	{
		$ticket_id = $this->action[2];
		$ticket = Model\TicketModel::getByID($ticket_id);
		
		// no ticket with that number or not the customers ticket
		if($ticket === null || $ticket->customer_CID != $_SESSION["user_id"])
		{
			header("Location: /user/panel");
			exit;
		}
		
		// if we have a ticket and it is indeed the customers we can proceed displaying the details
		if(isset($_SESSION["ticket_new"]))
		{
			$this->view_params["ticket_new"] = $_SESSION["ticket_new"];
			unset($_SESSION["ticket_new"]);
		}
		
		$employee = Model\EmployeeModel::getByID($ticket->employee_EID);
		
		$this->view_params["category"] = Model\ProblemModel::getByID($ticket->CategoryID);
		$this->view_params["topic"] = htmlentities($ticket->Topic, ENT_COMPAT, "UTF-8");
		$this->view_params["problem"] = htmlentities($ticket->Problem, ENT_COMPAT, "UTF-8");
		
		$this->view_params["ticket_id"] = $ticket->TID;
		$this->view_params["assigned_to"] = ($employee != null) ? $employee->__toString() : "none";
		$this->view_params["status"] = Model\StatusModel::getByID($ticket->StatusID);
		$this->view_params["created_on"] = $ticket->created_on;
		$this->view_params["last_update"] = $ticket->last_update;
		
		$this->view_params["solution"] = ($ticket->Solution != "") ? $ticket->Solution : "No solution available yet.";
		
		$changes = Model\TicketHistoryModel::getHistory($ticket->TID);
		if(is_array($changes))
		{
			foreach($changes as $row)
			{
				if(array_key_exists($row["column_name"], $this->changes))
				{
					$this->view_params["changelog"] .= sprintf('
						<li>
							<span><b>%s:</b></span>
							<span>%s</span>
						</li>
					', 	date("j M, Y | h:i", strtotime($row["changed_on"])), $this->changes[$row["column_name"]]);
				}
			}
		}
	}
	
	private function processTabs($action)
	{
		$out = "";
		foreach($this->tabs as $type => $value)
		{
			$class = ($action === $type) ? ' class="active"' : "";
			$out .= sprintf($value, $class);
		}
		return $out;
	}
}