// Bootstrap 3.0.0 shiv for Meteor, since its default is 2.3.0

Package.describe({
	summary: "Load Bootstrap 3.0.0 script"
});

Package.on_use(function (api) {
	api.use(['jquery'], 'client');
	api.add_files(['../../public/bootstrap/fonts/glyphicons-halflings-regular.eot'], 'client');
	api.add_files(['../../public/bootstrap/fonts/glyphicons-halflings-regular.svg'], 'client');
	api.add_files(['../../public/bootstrap/fonts/glyphicons-halflings-regular.ttf'], 'client');
	api.add_files(['../../public/bootstrap/fonts/glyphicons-halflings-regular.woff'], 'client');
	api.add_files(['../../public/bootstrap/js/bootstrap.min.js'], 'client');
	api.add_files(['../../public/bootstrap/css/bootstrap.min.css'], 'client');

	// Font path override
	api.add_files('path-override.css', 'client');
});