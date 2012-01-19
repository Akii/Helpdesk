<!DOCTYPE html>
<html>
	<head>
		<meta charset="<?php echo $charset; ?>" />
		<title><?php echo $title; ?></title>
		<?php echo $scripts_for_layout; ?>
	</head>
	<body>
		<div id="login_head" class="gradient_01">
			<div class="center_margin">
				<p><?php echo $_login; ?></p>
			</div>
		</div>
		<header class="gradient_02 text_shadow_01">
			<div class="center_margin">
				<img src="/public/images/helpd_logo_72.png" alt="Helpdesk Logo"/>
				<span>Software & Hardware</span>
			</div>
		</header>
		<nav class="gradient_03 box_shadow_01 text_shadow_01">
			<form accept="utf-8" method="POST" action="/search">
				<div class="center_margin">
					<ul>
						<li><a href="/">Home</a></li>
						<li><a href="/product">Products</a></li>
						<li><a href="/index/about">About</a></li>
						<li><a href="/help">Help</a></li>
						<input type="search" placeholder="Search" />
					</ul>
				</div>
			</form>
		</nav>
		<div class="center_margin">
			<?php echo $content_for_layout; ?>
			<footer class="text_shadow_02">
				© 2012 Software & Hardware GmbH | <a href="/index/impressum">Impressum</a> | <a href="/index/jobs">Jobs</a><br/>
			</footer>
		<!-- end .center_margin --></div>
	</body>
</html>