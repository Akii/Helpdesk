<div id="content">
	<div id="login" class="content_form">
		<form method="POST" action="" class="center_margin box_shadow_02 gradient_04 corners_01">
			<h1>Login:</h1>
			<?php echo $login_error; ?>
			<fieldset>
				<label for="username">Username: </label>
					<input type="text" name="username" />
				<label for="password">Password: </label>
					<input type="password" name="password" />
				<input type="submit" name="sbmt" value="Login" />
			</fieldset>
		</form>
	</div>
</div>