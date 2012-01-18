<?php
/**
 * Static startup script
 *
 * Loads the ClassLoader, defines paths and loads the most basic classes.
 * You most probably don't want to change anything here.
 */
 
namespace de\helpdesk;

// define paths
define('DS', DIRECTORY_SEPARATOR);

define('ROOT', realpath('../'));
define('WWW_ROOT', ROOT . DS . 'public');
define('APP_ROOT', ROOT . DS . 'de' . DS . 'helpdesk');

define('CSS_DIR', WWW_ROOT . DS . 'css');
define('IMG_DIR', WWW_ROOT . DS . 'images');
define('JS_DIR', WWW_ROOT . DS . 'js');

// include the class loader and custom exceptions
include APP_ROOT . DS . 'Core' . DS . 'ClassLoader.php';
include APP_ROOT . DS . 'Error' . DS . 'exceptions.php';
include APP_ROOT . DS . 'Utils' . DS . 'Log.php';

// from now on, class App is in charge of loading classes
spl_autoload_register(__NAMESPACE__ . '\Core\ClassLoader::loadClass');

// set exception handler
set_exception_handler(__NAMESPACE__ . '\Error\ExceptionHandler::handle_exception');

Core\Configure::init();

Core\Configure::write('App.root', ROOT);
Core\Configure::write('App.www_root', WWW_ROOT);
Core\Configure::write('App.app_root', APP_ROOT);
Core\Configure::write('App.namespace', '\\' . __NAMESPACE__);