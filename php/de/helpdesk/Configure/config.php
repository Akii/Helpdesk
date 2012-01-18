<?php
use \de\helpdesk\Core as Core;

Core\Configure::write('App.encoding', 'UTF-8');

Core\Configure::write('DB.type', 'MySQL');
Core\Configure::write('DB.name', 'helpdesk');
Core\Configure::write('DB.host', 'localhost');
Core\Configure::write('DB.user', 'root');
Core\Configure::write('DB.pass', 'root');

Core\Configure::write('View.script.css', '/public/css/default.css');
Core\Configure::write('App.pagination.per_page', 2);

/**
 * @var Debug.level
 *
 * 0: No debug msgs are displayed.
 * 1: No SQL debug msgs are displayed.
 * 2: Debug + SQL msgs are displayed.
 */
Core\Configure::write('Debug.level', 2);