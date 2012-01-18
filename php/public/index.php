<?php
// check php is 5.3 or above
if(phpversion() < 5.3)
	die("<b>Fatal:</b> This application needs PHP v. 5.3 and above. The PHP version running on this server is " . phpversion() . ".");

// run the startup script
require_once("../de/helpdesk/startup.php");

use \de\helpdesk\Core as Core;
use \de\helpdesk\Routing as Routing;
use \de\helpdesk\Network as Network;

// pass responsibility to the dispatcher
$Dispatcher = new Routing\Dispatcher();
$Dispatcher->dispatch(
	new Network\HttpRequest(),
	new Network\HttpResponse(array('charset' => Core\Configure::read('App.encoding')))
);