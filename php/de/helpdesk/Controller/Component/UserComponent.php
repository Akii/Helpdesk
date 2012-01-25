<?php
/**
 * Class UserComponent
 *
 * Loggs user in and out. Also fills the view variable for $_login.
 */
namespace de\helpdesk\Controller\Component;
use de\helpdesk\Model as Model;

class UserComponent extends Component
{
	/**
	 * Instance of the user if logged in
	 */
	public $user = null;

	/**
	 * @Override
	 */
	public function startup()
	{
		$this->restore();
		$this->setLoginHead();
	}
	
	private function restore()
	{
		if(isset($_SESSION["bLoggedIn"]) && $_SESSION["bLoggedIn"] === true)
		{
			$customer = Model\CustomerModel::getByID($_SESSION["user_id"]);
			if($customer->bDeleted === "0")
			{
				$this->user = $customer;
				return;
			}
		}
		
		$this->logout();
	}
	
	/**
	 * Returns if the user is logged in
	 *
	 * @return boolean True if the user is logged in
	 */
	public function loggedIn()
	{
		if(isset($_SESSION))
			return $_SESSION["bLoggedIn"];
		else
			return false;
	}
	
	/**
	 * Login function for customers
	 *
	 * @param String $user Username
	 * @param String $pass Plain text password
	 * @return boolean True if login is valid
	 */
	public function login($user, $pass)
	{
		$ok = Model\CustomerModel::login($user, $pass);
		
		if($ok > 0)
		{
			$_SESSION["user_id"] = $ok;
			$_SESSION["bLoggedIn"] = true;
			
			$this->user = Model\CustomerModel::getByID($ok);
			return true;
		}
		
		$this->logout();
		
		return false;
	}
	
	/**
	 * Logs out a customer
	 */
	public function logout()
	{
		unset($_SESSION["user_id"]);
		$_SESSION["bLoggedIn"] = false;
	}
	
	/**
	 * Regenerates the link on the top of the page
	 */
	private function setLoginHead()
	{
		if($this->user !== null)
		{
			$this->controller->view_params["_login"] = sprintf('Welcome %s. <a href="/user/panel">Control Panel</a> <a href="/user/logout">Logout</a>', $this->user->firstName . " " . $this->user->lastName);
			return;
		}
		
		$this->controller->view_params["_login"] = 'Welcome guest. <a href="/user/login">Login</a>';
	}
	
	/**
	 * @Override
	 */
	public function shutdown() {}
}